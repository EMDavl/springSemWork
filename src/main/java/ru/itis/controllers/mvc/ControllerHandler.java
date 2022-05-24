package ru.itis.controllers.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class ControllerHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(NoHandlerFoundException ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        model.addAttribute("sc", "NOT FOUND");
        return "error";
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(RuntimeException ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        model.addAttribute("sc", "Internal Server Error");
        return "error";
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(Exception ex, Model model) {
        model.addAttribute("msg", ex.getMessage());
        model.addAttribute("sc", "Bad request");
        return "error";
    }

}
