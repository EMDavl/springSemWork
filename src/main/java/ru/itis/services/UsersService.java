package ru.itis.services;


import org.springframework.ui.Model;
import ru.itis.dto.PostDto;
import ru.itis.dto.SignUpDto;

import java.util.Set;

public interface UsersService {

    String signUp(SignUpDto credentials);

    void confirm(String code, Model model);

    String getProfile(String email, Model model);

    Set<PostDto> getModeratedPosts(String email);

    Set<PostDto> getUnmoderatedPosts(String email);
}
