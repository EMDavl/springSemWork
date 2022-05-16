package ru.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;
import ru.itis.models.BookAuthor;
import ru.itis.models.Post;
import ru.itis.models.User;
import ru.itis.repositories.PostRepository;
import ru.itis.repositories.UsersRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsServiceImpl implements PostsService {

    private final PostRepository postRepository;
    private final UsersRepository userRepository;
    private final BookAuthorService bookAuthorService;
    private final TagService tagService;

    @Override
    @Transactional
    public PostDto create(PostCreationDto postDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException(userEmail));
        BookAuthor bookAuthor = bookAuthorService.findOrCreateByName(postDto.getBookAuthor());

        log.info("Post creation initialized. User {} post title {}", userEmail, postDto.getPostTitle());

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
        return PostDto.from(post);
    }

    @Override
    public PostDto update(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto delete(Long id) {
        return null;
    }
}
