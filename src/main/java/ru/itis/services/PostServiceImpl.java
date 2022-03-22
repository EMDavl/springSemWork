package ru.itis.services;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.itis.dto.PostRequestDto;
import ru.itis.models.BookAuthor;
import ru.itis.models.ModeratedPost;
import ru.itis.models.Tag;
import ru.itis.models.User;
import ru.itis.repositories.BookAuthorRepository;
import ru.itis.repositories.PostsRepository;
import ru.itis.repositories.TagRepository;
import ru.itis.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private UserRepository userRepository;

    //TODO Change signature to linking post with real user and also do refactoring

    @Override
    public ModeratedPost save(PostRequestDto post) {
        Set<Tag> tags = getPostTags(post.getTags());
        BookAuthor author = getBookAuthor(post.getBookAuthor());
        User postAuthor = userRepository.save(User.builder().nickname("Super Papik").build());

        ModeratedPost toBeSaved = ModeratedPost.builder()
                .bookAuthor(author)
                .tags(tags)
                .liked(new HashSet<>())
                .disliked(new HashSet<>())
                .postText(post.getPostText())
                .bookName(post.getBookName())
                .author(postAuthor)
                .build();

        author.getPosts().add(toBeSaved);
        return postsRepository.save(toBeSaved);
    }

    private Set<Tag> getPostTags(Set<String> tags) {
        Set<Tag> res = new HashSet<>();

        for (String stringTag : tags) {
            stringTag = stringTag.toLowerCase();
            Optional<Tag> tag = tagRepository.findByName(stringTag);
            String finalStringTag = stringTag;
            res.add(tag.orElseGet(() -> tagRepository
                    .save(Tag.builder()
                            .name(finalStringTag)
                            .build())));
        }

        return res;
    }

    private BookAuthor getBookAuthor(String bookAuthor) {

        return bookAuthorRepository
                .findBookAuthorByValue(bookAuthor)
                .orElseGet(() -> bookAuthorRepository
                        .save(BookAuthor.builder()
                                .value(bookAuthor)
                                .posts(new HashSet<>())
                                .build()));
    }

    @Override
    public Page<ModeratedPost> getWithPagination(Integer lastPageElement, Integer pagingLimit) {
        return null;
    }

    @Override
    public ModeratedPost delete(Long id) {
        return null;
    }

    @Override
    public ModeratedPost update(PostRequestDto post) {
        return null;
    }

    @Override
    public void changeRating(Set<User> liked, Set<User> disliked) {
        throw new NotYetImplementedException("Rating changes currently not supported");
    }
}
