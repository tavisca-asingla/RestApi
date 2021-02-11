package com.howtodoinjava.demo.service;

import com.howtodoinjava.demo.model.TaskDto;
import com.howtodoinjava.demo.repo.MockTaskProvider;
import com.howtodoinjava.demo.repo.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;



import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TasksServiceTest {
    TasksService tasksService;
    @Mock
    MockTaskProvider mockTaskProvider;

    @Before
    public void initTaskService(){
//        this.mockTaskProvider = Mockito.mock(MockTaskProvider.class);
        tasksService = new TasksService(mockTaskProvider);
    }
    @Test
    public void addTest() {

        TaskDto taskDto = new TaskDto(1,"Learn");
        given(mockTaskProvider.add(taskDto)).willReturn(true);
        boolean added = tasksService.add(taskDto);
        assertEquals(added,true);
    }

    @Test
    public void deleteTest() {
        Task task = Task.of(1,"Learn Mocking");
        given(mockTaskProvider.delete(1)).willReturn(task);
        Task expectedTask = tasksService.delete(1);
        assertEquals(expectedTask,task);
    }

    @Test
    public void getTasksTest() {
        List mocklist = new ArrayList<Task>();
        mocklist.add(Task.of(1, "Machine Learning"));
        mocklist.add(Task.of(2, "Deep Learning"));
        mocklist.add(Task.of(3, "Data Structures"));
        given(mockTaskProvider.getTasks()).willReturn(mocklist);
        List results = tasksService.getTasks();
        assertEquals(results,mocklist);

    }

    @Test
    public void getTaskById() {
        List mocklist = new ArrayList<Task>();
        mocklist.add(Task.of(1, "Machine Learning"));
        mocklist.add(Task.of(2, "Deep Learning"));
        given(mockTaskProvider.findTaskById(2)).willReturn(Task.of(2,"Deep Learning"));
        Task result = tasksService.getTaskById(2);
        assertEquals(result,mocklist.get(1));
    }

    @Test
    public void updateTaskById() {
        given(mockTaskProvider.update(1,"Mockito"))
                .willReturn(true);
        boolean result = tasksService.updateTaskById(1,"Mockito");
        assertEquals(result,true);
    }
}