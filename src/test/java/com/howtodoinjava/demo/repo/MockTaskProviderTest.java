package com.howtodoinjava.demo.repo;

import com.howtodoinjava.demo.model.TaskDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MockTaskProviderTest {
    private MockTaskProvider mockTaskProvider;
    @Before
    public void setUp(){
        mockTaskProvider = new MockTaskProvider();
    }
    @Test
    public void getTasksTest() {
        List mocklist = new ArrayList<Task>();
        mocklist.add(Task.of(1, "Machine Learning"));
        mocklist.add(Task.of(2, "Deep Learning"));
        mocklist.add(Task.of(3, "Data Structures"));
        mocklist.add(Task.of(4, "Spring"));
        mocklist.add(Task.of(5, "Summer"));

        boolean isEqual = mocklist.equals(mockTaskProvider.getTasks());
        assertEquals(true,isEqual);

    }

    @Test
    public void findTaskByIdTest() {
        assertEquals(mockTaskProvider.findTaskById(1) ,Task.of(1,"Machine Learning"));
    }

    @Test
    public void addTest() {
        mockTaskProvider.add(new TaskDto(8,"MockItem"));
        assertEquals(mockTaskProvider.findTaskById(8) ,Task.of(8,"MockItem"));
    }

    @Test
    public void deleteTest() {
        Task deletedTask = mockTaskProvider.delete(5);
        assertEquals(Task.of(5,"Summer"),deletedTask);
    }

    @Test
    public void updateTest() {
        boolean  updated = mockTaskProvider.update(5,"summer");
        assertEquals(mockTaskProvider.findTaskById(5) ,Task.of(5,"summer"));
    }
}