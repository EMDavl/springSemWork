package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.itis.dto.DefaultUserDto;
import ru.itis.dto.PostDto;
import ru.itis.dto.SignUpDto;
import ru.itis.models.Post;
import ru.itis.models.User;
import ru.itis.models.VerificationToken;
import ru.itis.models.enums.Role;
import ru.itis.repositories.PostRepository;
import ru.itis.repositories.TokenRepository;
import ru.itis.repositories.UsersRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final PostRepository postRepository;
    private static final String CONFIRMATION_SUBJECT = "Account confirmation";


    @Value("${confirmation.url}")
    private String confirmationUrl;

    @Override
    @Transactional
    public String signUp(SignUpDto formData) {

        if (emailTaken(formData.getEmail())) {
            return "redirect:/signUp?error=email_taken";
        }

        if (nicknameTaken(formData.getNickname())) {
            return "redirect:/signUp?error=nickname_taken";
        }

        User user = User.builder()
                .email(formData.getEmail())
                .password(passwordEncoder.encode(formData.getPassword()))
                .isPublicAccount(true)
                .role(Role.DEFAULT)
                .status(User.ACCOUNT_STATUS.NOT_CONFIRMED)
                .nickname(formData.getNickname())
                .dislikedPosts(new HashSet<>())
                .subscribers(new HashSet<>())
                .subscriptions(new HashSet<>())
                .likedPosts(new HashSet<>())
                .build();

        VerificationToken token = VerificationToken.builder()
                .expires(VerificationToken.calculateExpires())
                .user(user)
                .value(UUID.randomUUID())
                .build();

        userRepository.save(user);
        tokenRepository.save(token);

        mailService.sendSignUpConfirmationMail(user.getEmail(), CONFIRMATION_SUBJECT, confirmationUrl + token.getValue().toString());
        return "redirect:/signIn";
    }

    @Override
    public Set<PostDto> getModeratedPosts(String email) {
        User user = getUserFromEmail(email);
        Set<Post> posts = postRepository.findByAuthorAndStatus(user, Post.PostStatus.APPROVED);
        return PostDto.from(posts);
    }

    @Override
    public Set<PostDto> getUnmoderatedPosts(String email) {
        User user = getUserFromEmail(email);
        Set<Post> posts = postRepository.findByAuthorAndStatus(user, Post.PostStatus.ON_MODERATION);
        return PostDto.from(posts);
    }

    // TODO refactor
    @Override
    public void confirm(String code, Model model) {

        Optional<VerificationToken> tokenOpt =
                tokenRepository.findByValue(UUID.fromString(code));

        String messageName = "message";
        if (tokenOpt.isEmpty()) {
            model.addAttribute(messageName, "Wrong code");
            return;
        }

        VerificationToken token = tokenOpt.get();
        Optional<User> userOptById = userRepository.findById(token.getUser().getId());
        if (userOptById.isEmpty()) {
            model.addAttribute(messageName, "User not found");
            tokenRepository.delete(token);
            return;
        }

        User user = userOptById.get();
        if (user.getStatus().equals(User.ACCOUNT_STATUS.CONFIRMED)) {
            model.addAttribute(messageName, "Account already confirmed");
            return;
        }

        if (VerificationToken.isExpired(token)) {
            model.addAttribute(messageName, "Code is expired, sign up again");
            userRepository.delete(user);
            tokenRepository.delete(token);
            return;
        }

        user.setStatus(User.ACCOUNT_STATUS.CONFIRMED);
        userRepository.save(user);
        tokenRepository.delete(token);
        model.addAttribute(messageName, "Successfully confirmed");
    }

    @Override
    public String getProfile(String email, Model model) {

        User user = getUserFromEmail(email);
        final String USER_ATTRIBUTE = "user";

        switch (user.getRole()) {
            case DEFAULT:
                model.addAttribute(USER_ATTRIBUTE, DefaultUserDto.from(user));
                return "userProfile";
            case MODERATOR:
                return "moderatorProfile";
            case ADMIN:
                return "adminProfile";
        }

        throw new IllegalArgumentException("Wrong email");
    }

    private User getUserFromEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User - " + email + " not found"));
    }


    private boolean nicknameTaken(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    private boolean emailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

}
