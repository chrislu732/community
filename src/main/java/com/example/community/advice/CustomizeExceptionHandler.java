package com.example.community.advice;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


// customize the return page for exceptions
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Model model, Throwable ex) {
        if (ex instanceof CustomizeException) {
            ex.printStackTrace();
            model.addAttribute("message", ex.getMessage());
        }else {
            ex.printStackTrace();
            model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
        }
        return new ModelAndView("error");
    }

}
