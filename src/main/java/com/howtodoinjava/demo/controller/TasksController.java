package com.howtodoinjava.demo.controller;

import com.howtodoinjava.demo.model.TaskDto;
import com.howtodoinjava.demo.repo.Task;
import com.howtodoinjava.demo.service.TasksService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Setter
public class TasksController {
    @Autowired private final TasksService service;
    @GetMapping
    public List<Task> getTasks() {
        return service.getTasks();
    }
    @PostMapping
    public ResponseEntity<?> postTasks(@RequestBody TaskDto dto) {
        System.out.println(dto.getId()+dto.getName());
        if(service.add(dto))
            return new ResponseEntity<String>("Added New record", HttpStatus.CREATED);
        return new ResponseEntity<String>("Could not add record", HttpStatus.IM_USED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(required = true) long id) {

        Task task = service.getTaskById(id);
        if(task!=null){
            return new ResponseEntity<Task>(task, HttpStatus.OK);
        }
        return new ResponseEntity<String>("NO Task Found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) long id) {
        if(service.delete(id)!= null)
            return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<String>("Deleted Previously",HttpStatus.GONE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWholeResource(@RequestBody TaskDto dto){
        service.delete(dto.getId());
        service.add(dto);
//        System.out.println("added record");
        return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateTask(
            @RequestParam String newTask, @PathVariable("id") long id) {

        boolean result = service.updateTaskById( id,newTask);
        return ResponseEntity.ok(result);
    }
}