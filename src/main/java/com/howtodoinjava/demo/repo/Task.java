package com.howtodoinjava.demo.repo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Task {
    private long id;
    private String name;
}