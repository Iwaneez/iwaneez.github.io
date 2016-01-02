package com.iwaneez.stuffer.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    public static final String DEFAULT_ERROR_VIEW_NAME = "error/error";

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest request, Model model, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // Otherwise setup and send the user to a default error-view.
        model.addAttribute("exception", e);
        model.addAttribute("url", request.getRequestURL());
        
        return DEFAULT_ERROR_VIEW_NAME;
    }
}
