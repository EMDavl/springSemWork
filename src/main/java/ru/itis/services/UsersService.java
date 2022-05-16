package ru.itis.services;


import org.springframework.ui.Model;
import ru.itis.dto.PostCreationDto;
import ru.itis.dto.SignUpDto;

public interface UsersService {

    String signUp(SignUpDto credentials);

    void confirm(String code, Model model);

    String getProfile(String email, Model model);

    void createPost(PostCreationDto post, String userEmail);
}
