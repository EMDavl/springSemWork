package ru.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.*;
import ru.itis.exceptions.AccountNotConfirmedException;
import ru.itis.exceptions.PostNotFound;
import ru.itis.models.BookAuthor;
import ru.itis.models.Post;
import ru.itis.models.Tag;
import ru.itis.models.User;
import ru.itis.repositories.PostRepository;
import ru.itis.repositories.UsersRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsServiceImpl implements PostsService {

    private final PostRepository postRepository;
    private final UsersRepository userRepository;
    private final BookAuthorService bookAuthorService;
    private final TaskService taskService;
    private final TagService tagService;

    @Override
    @Transactional
    public PostDto create(PostCreationDto postDto, String userEmail) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).orElseThrow(() -> new UsernameNotFoundException(userEmail));
        checkIsUserConfirmed(user);
        BookAuthor bookAuthor = bookAuthorService.findOrCreateByName(postDto.getBookAuthor());

        log.info("Post creation instantiated. User {} post title {}", userEmail, postDto.getPostTitle());

        Post post = Post.builder()
                .author(user)
                .bookAuthor(bookAuthor)
                .bookName(postDto.getBookName())
                .postText(postDto.getPostText())
                .postTitle(postDto.getPostTitle())
                .liked(new HashSet<>())
                .disliked(new HashSet<>())
                .status(Post.PostStatus.ON_MODERATION)
                .tags(tagService.findOrCreate(postDto.getTags()))
                .build();

        user.getPosts().add(post);
        postRepository.save(post);
        userRepository.save(user);
        taskService.createTask(post);

        log.info("Post created. Id {}", post.getId());
        return PostDto.from(post);
    }

    private void checkIsUserConfirmed(User user) {
        if (user.getStatus().equals(User.ACCOUNT_STATUS.NOT_CONFIRMED)) throw new AccountNotConfirmedException();
    }

    @Override
    @Transactional
    public boolean update(PostUpdateDto postDto, String authorEmail) {
        if (!isValidInput(postDto.getId(), authorEmail)) {
            return false;
        }

        Post post = getPostById(postDto.getId());
        Post.PostStatus beforeUpdate = post.getStatus();

        doUpdate(post, postDto);
        if (beforeUpdate.equals(Post.PostStatus.APPROVED))
            taskService.createTask(post);

        postRepository.save(post);

        return true;
    }

    private void doUpdate(Post post, PostUpdateDto postDto) {

        post.setPostText(postDto.getPostText());
        post.setPostTitle(postDto.getPostTitle());
        post.setBookName(postDto.getBookName());
        post.setTags(getTagsChanges(post.getTags(), Arrays.stream(postDto.getTagsString()
                        .split(";"))
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.toSet())));
        post.setStatus(Post.PostStatus.ON_MODERATION);

    }

    private Set<Tag> getTagsChanges(Set<Tag> tags, Set<String> tagsFromForm) {
        Set<Tag> result = new HashSet<>();

        for (Tag tag : tags) {
            String tagName = tag.getName().toLowerCase();
            if (tagsFromForm.contains(tagName)) {
                result.add(tag);
                tagsFromForm.remove(tagName);
            }
        }

        tagsFromForm.stream()
                .map(t -> TagDto.builder().value(t).build())
                .map(tagService::findOrCreate)
                .forEach(result::add);

        return result;
    }

    @Override
    public boolean delete(Long id, String email) {
        Optional<Post> postOpt = postRepository.findById(id);
        if (isValidInput(id, email)) {
            Post post = postOpt.get();
            if (post.getStatus().equals(Post.PostStatus.ON_MODERATION)) taskService.deleteTaskByPost(post);
            post.setStatus(Post.PostStatus.DELETED);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidInput(Long postId, String email) {
        Optional<Post> postOpt = postRepository.findById(postId);

        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            User author = post.getAuthor();
            checkIsUserConfirmed(author);
            return author.getEmail().equalsIgnoreCase(email) && !post.getStatus().equals(Post.PostStatus.DELETED);
        }

        return false;
    }

    @Override
    public Post findById(Long postId) {
        return getPostById(postId);
    }

    @Override
    @Transactional
    public ResponseEntity<RatingChanges> like(Long postId, String email) {
        Optional<User> userOpt = userRepository
                .findByEmailIgnoreCase(email);

        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOpt.get();
        Post post = postOpt.get();

        if (user.getLikedPosts().contains(post)) {
            user.getLikedPosts().remove(post);

            userRepository.save(user);
        } else {
            user.getDislikedPosts().remove(post);
            post.getDisliked().remove(user);

            user.getLikedPosts().add(post);
            post.getLiked().add(user);

            userRepository.save(user);
        }

        return ResponseEntity
                .accepted()
                .body(RatingChanges.builder()
                        .likesCount((long) post.getLiked().size())
                        .dislikesCount((long) post.getDisliked().size())
                        .build());
    }

    @Override
    @Transactional
    public ResponseEntity<RatingChanges> dislike(Long postId, String email) {
        Optional<User> userOpt = userRepository
                .findByEmailIgnoreCase(email);

        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOpt.get();
        Post post = postOpt.get();

        if (user.getDislikedPosts().contains(post)) {
            user.getDislikedPosts().remove(post);
            post.getDisliked().remove(user);

            userRepository.save(user);
        } else {
            user.getLikedPosts().remove(post);
            post.getLiked().remove(user);

            user.getDislikedPosts().add(post);
            post.getDisliked().add(user);

            userRepository.save(user);
        }

        return getRatingChangesResponse(post);
    }

    @Override
    public List<PostDto> getByTag(Long id) {
        return PostDto.from(postRepository.findAllByTag(id));
    }

    @Override
    public List<PostDto> getByBookAuthor(Long id) {
        return PostDto.from(postRepository.findAllByBookAuthor(id));
    }

    @Override
    public List<PostDto> getByBookName(String name) {
        return PostDto.from(postRepository.findAllByBookName(name));
    }

    private ResponseEntity<RatingChanges> getRatingChangesResponse(Post post) {
        return ResponseEntity
                .accepted()
                .body(RatingChanges.builder()
                        .likesCount((long) post.getLiked().size())
                        .dislikesCount((long) post.getDisliked().size())
                        .build());
    }

    private Post getPostById(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isEmpty()) {
            throw new PostNotFound();
        }
        return byId.get();
    }
}
