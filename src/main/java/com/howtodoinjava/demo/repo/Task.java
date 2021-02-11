package com.howtodoinjava.demo.repo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Task {
    private long id;
    private String name;
    @Override
    public boolean equals(Object toCompare){
        String thisAsString = this.id + this.name;
        Task taskToCompare = (Task)toCompare;
        String toCompareAsString = taskToCompare.id + taskToCompare.name;
        return thisAsString.equals(toCompareAsString);
    }
}