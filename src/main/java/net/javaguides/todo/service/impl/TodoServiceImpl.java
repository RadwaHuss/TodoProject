package net.javaguides.todo.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.entity.Todo;
import net.javaguides.todo.exception.ResourceNotFoundException;
import net.javaguides.todo.repository.TodoRepository;
import net.javaguides.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        //convert dto -> Entity
        Todo todo = modelMapper.map(todoDto, Todo.class);

        //add entity to DB
        Todo savedTodo = todoRepository.save(todo);

        //convert savedEntity -> dto to return method
        TodoDto savedTodoDto = modelMapper.map(todo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Todo not found with id"+id));

        //convert savedEntity -> dto to return method
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        //convert savedEntity -> dto to return method
        List<TodoDto> todoDtos = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        return todoDtos;
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Todo not found with id"+id));

        //convert dto -> Entity --editing a document instead of starting from scratch
        //todo = modelMapper.map(todoDto, Todo.class);
        todo.setDescription(todoDto.getDescription());
        todo.setTittle(todoDto.getTittle());
        todo.setCompleted(todoDto.getIsCompleted());

        //add entity to DB
        Todo updatedTodo = todoRepository.save(todo);

        //convert updatedTodoDto -> dto to return method
        TodoDto updatedTodoDto = modelMapper.map(todo, TodoDto.class);

        return updatedTodoDto;

    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Todo not found with id"+id));

        todoRepository.deleteById(id);
    }

    @Override
    public List<TodoDto> getCompleteTodo() {
        List<Todo> Todos = todoRepository.findAll();
        List<Todo> newTodo = new ArrayList<>();
        for (Todo t : Todos) {
            System.out.println(t);
            if(t.getIsCompleted() == true)
            {
                newTodo.add(t);
            }
        }
        //loop over list where complete true
        //add in another list
        //convert updatedTodoDto -> dto to return method
        //convert savedEntity -> dto to return method
        List<TodoDto> todoDtos = newTodo.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        return todoDtos;
    }


    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Todo not found with id"+id));

        todo.setCompleted(Boolean.TRUE);
        todoRepository.save(todo);
        //convert updatedTodoDto -> dto to return method
        TodoDto completedTodoDto = modelMapper.map(todo, TodoDto.class);

        return completedTodoDto;
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Todo not found with id"+id));

        todo.setCompleted(Boolean.FALSE);
        todoRepository.save(todo);
        //convert updatedTodoDto -> dto to return method
        TodoDto completedTodoDto = modelMapper.map(todo, TodoDto.class);

        return completedTodoDto;
    }
}
