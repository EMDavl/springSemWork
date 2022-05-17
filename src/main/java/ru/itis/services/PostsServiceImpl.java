package ru.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;
import ru.itis.dto.PostUpdateDto;
import ru.itis.dto.TagDto;
import ru.itis.exceptions.PostNotFound;
import ru.itis.models.BookAuthor;
import ru.itis.models.Post;
import ru.itis.models.Tag;
import ru.itis.models.User;
import ru.itis.repositories.PostRepository;
import ru.itis.repositories.UsersRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

    @Override
    public PostDto update(PostUpdateDto postDto) {

        Post post = getPostById(postDto.getId());

        doUpdate(post, postDto);

        taskService.createTask(post);
        postRepository.save(post);
        return PostDto.from(post);
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
        if (isAuthorAndOptPresent(email, postOpt)) {
            Post post = postOpt.get();
            post.setStatus(Post.PostStatus.DELETED);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAuthor(Long postId, String email) {
        Optional<Post> postOpt = postRepository.findById(postId);
        return isAuthorAndOptPresent(email, postOpt);
    }

    @Override
    public Post findById(Long postId) {
        return getPostById(postId);
    }

    private boolean isAuthorAndOptPresent(String email, Optional<Post> post) {
        return post
                .map(value -> value.getAuthor()
                        .getEmail()
                        .equalsIgnoreCase(email))
                .orElse(false);
    }

    private Post getPostById(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isEmpty()) {
            throw new PostNotFound();
        }
        return byId.get();
    }
}
