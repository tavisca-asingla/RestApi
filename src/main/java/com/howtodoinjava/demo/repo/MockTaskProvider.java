package com.howtodoinjava.demo.repo;

import com.howtodoinjava.demo.model.TaskDto;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class MockTaskProvider {
    private List<Task> mockTaskStore;
    public MockTaskProvider() {
        mockTaskStore = new ArrayList<Task>();
        mockTaskStore.add(Task.of(1, "Machine Learning"));
        mockTaskStore.add(Task.of(2, "Deep Learning"));
        mockTaskStore.add(Task.of(3, "Data Structures"));
        mockTaskStore.add(Task.of(4, "Spring"));
        mockTaskStore.add(Task.of(5, "Summer"));
    }
    public List<Task> getTasks() {
        return mockTaskStore;
    }
    public Task findTaskById(long id) {
        for (Task Task : mockTaskStore) {
            if (Task.getId() == id) {
                return Task;
            }
        }
        return null;
    }
    public boolean add(TaskDto dto) {
        return mockTaskStore.add(Task.of(dto.getId(), dto.getName()));
    }
    public Task delete(long id) {
        int idx = 0;
        for (; idx < mockTaskStore.size(); idx++) {
            if (mockTaskStore.get(idx).getId() == id) {
                break;
            }
        }
        if(idx == mockTaskStore.size())
            return  null;
        return mockTaskStore.remove(idx);
    }

    public boolean update(long id ,String newTask){
        Task task = null;
        for (Task Task : mockTaskStore) {
            if (Task.getId() == id) {
                task = Task;
            }
        }
        if(task == null){
            return false;
        }
        task.setName(newTask);
        return true;
    }
}