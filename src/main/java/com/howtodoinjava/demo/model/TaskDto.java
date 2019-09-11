package com.howtodoinjava.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor(staticName = "of")
public class TaskDto {

    private long id;
    private String name;

    public TaskDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TaskDto() {
    }
}