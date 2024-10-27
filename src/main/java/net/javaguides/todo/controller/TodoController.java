package net.javaguides.todo.controller;

import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    //Create add Todo rest Api
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodoDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    //Create get Todo rest Api
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    //Create get All Todo rest Api
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todoDtos = todoService.getAllTodos();
        //return new ResponseEntity<>(todoDtos,HttpStatus.OK);
        return ResponseEntity.ok(todoDtos);
    }

    //Update Todo rest Api
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId){
        TodoDto updatedTodoDto = todoService.updateTodo(todoDto, todoId);
        return  ResponseEntity.ok(updatedTodoDto);
    }

    //Delete Todo rest Api
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
         todoService.deleteTodo(todoId);
         return ResponseEntity.ok("Todo deleted successfully");
    }

    //getComplete Todo rest Api
    @GetMapping("/getCompleteTodo")
    public ResponseEntity<List<TodoDto>> getCompleteTodo(){
        List<TodoDto> todoDtos =  todoService.getCompleteTodo();
        return ResponseEntity.ok(todoDtos);
    }

    //Complete Todo rest Api
    @PatchMapping("CompleteTodo/{id}")
    public ResponseEntity<TodoDto> CompleteTodo(@PathVariable("id") Long todoID){
        TodoDto todoDto = todoService.completeTodo(todoID);
        return ResponseEntity.ok(todoDto);
    }

    //In-Complete Todo rest Api
    @PatchMapping("inCompleteTodo/{id}")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoID){
        TodoDto todoDto = todoService.inCompleteTodo(todoID);
        return ResponseEntity.ok(todoDto);
    }
}
