package com.example.community.helper;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// tag library
@Component
public class TagHelper {
    private final List<String> tags = Arrays.asList("java", "c", "c++", "python", "javascript",
            "php", "html", "matlab", "sql", "go", "spring", "css");

    public List<String> getTags() {
        return tags;
    }
}
