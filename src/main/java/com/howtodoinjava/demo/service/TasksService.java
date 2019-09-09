package com.howtodoinjava.demo.service;
import com.howtodoinjava.demo.model.TaskDto;
import com.howtodoinjava.demo.repo.MockTaskProvider;
import com.howtodoinjava.demo.repo.Task;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Setter
public class TasksService {
    @Autowired private final MockTaskProvider mockTaskProvider;

    public boolean add(TaskDto dto) {
        return mockTaskProvider.add(dto);
    }

    public Task delete(long id) {
        return mockTaskProvider.delete(id);
    }

    public List<Task> getTasks() {
        return mockTaskProvider.getTasks();
    }

    public Task getTaskById(long id) {
        return mockTaskProvider.findTaskById(id);
    }

    public boolean updateTaskById(long id , String newTask){return  mockTaskProvider.update(id,newTask);}
}