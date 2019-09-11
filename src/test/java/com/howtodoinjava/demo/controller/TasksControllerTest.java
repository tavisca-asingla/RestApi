package com.howtodoinjava.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.demo.model.TaskDto;
import com.howtodoinjava.demo.repo.Task;
import com.howtodoinjava.demo.service.TasksService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TasksController.class)
public class TasksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TasksService tasksService;
    @Test
    public void getTasksTest() throws Exception {
        List<Task> duplicateTaskList = new ArrayList<Task>();
        duplicateTaskList.add( Task.of(1,"Deep Learning"));
        given(tasksService.getTasks()).willReturn(duplicateTaskList);
        this.mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'name': 'Deep Learning'}]"));
    }
    @Test
    public void getTasksByIdTest() throws Exception {
        List<Task> duplicateTaskList = new ArrayList();
        duplicateTaskList.add(Task.of(1,"Deep Learning"));
        duplicateTaskList.add(Task.of(2,"Growth"));
        given(tasksService.getTaskById(1)).willReturn(duplicateTaskList.get(1));
        this.mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 2,'name': 'Growth'}"));
    }

    @Test
    public void getTasksByIdWhenNoTaskExistsTest() throws Exception {
        List<Task> duplicateTaskList = new ArrayList();
        duplicateTaskList.add(Task.of(1,"Deep Learning"));
        duplicateTaskList.add(Task.of(2,"Growth"));
//        given(tasksService.getTaskById(1)).willReturn(duplicateTaskList.get(1));
        this.mockMvc.perform(get("/tasks/7"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("NO Task Found"));
    }

    @Test
    public void postTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(tasksService.add(new TaskDto(1,"Deep Learning"))).willReturn(true);
        mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Task.of(1,"Deep Learning"))))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTest() throws Exception{
        given(tasksService.updateTaskById(2,"rocks")).willReturn(true);
        this.mockMvc.perform(patch("/tasks/2?newTask=rocks"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }



    @Test
    public void deleteTest() throws Exception{
        given(tasksService.delete(2)).willReturn(Task.of(2,"mock"));
        this.mockMvc.perform(delete("/tasks/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateWholeResourceTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        given(tasksService.delete(2)).willReturn(Task.of(2,"aniket"));
        this.mockMvc.perform(put("/tasks/2")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Task.of(2,"aniket"))))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated successfully"));
    }
}