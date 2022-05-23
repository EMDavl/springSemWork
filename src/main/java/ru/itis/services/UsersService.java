package ru.itis.services;


import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.PostDto;
import ru.itis.dto.SignUpDto;
import ru.itis.models.Post;

import java.util.List;

public interface UsersService {

    String signUp(SignUpDto credentials, MultipartFile file);

    void confirm(String code, Model model);

    String getProfile(String email, Model model);

    List<PostDto> getModeratedPosts(String email);

    List<PostDto> getUnmoderatedPosts(String email);

    void notifyPostWasDeclined(Post post);
}
