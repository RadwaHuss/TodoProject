package net.javaguides.todo.service;

import net.javaguides.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
   TodoDto addTodo(TodoDto todoDto);

   TodoDto getTodo(Long id);

   List<TodoDto> getAllTodos();

   TodoDto updateTodo(TodoDto todoDto, Long id);

   void deleteTodo(Long id);

   List<TodoDto> getCompleteTodo(); //completed=true

   TodoDto completeTodo(Long id); //mark existing todo to completed

   TodoDto inCompleteTodo(Long id); //mark existing todo to in-completed
}
