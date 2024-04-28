package org.zerock.mallapi.service;

import jakarta.transaction.Transactional;
import org.zerock.mallapi.dto.TodoDto;
import org.zerock.mallapi.entity.Todo;
@Transactional
public interface TodoService {
    TodoDto get(Long tno);

    Long register(TodoDto dto);

    void modify(TodoDto dto);

    void remove(Long tno);

    default TodoDto entityToDTO(Todo todo){
        TodoDto todoDto = TodoDto.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .build();

        return todoDto;
    }

    default Todo entityToEntity(TodoDto todoDto){
        Todo todo = Todo.builder()
                .tno(todoDto.getTno())
                .title(todoDto.getTitle())
                .content(todoDto.getContent())
                .complete(todoDto.isComplete())
                .dueDate(todoDto.getDueDate())
                .build();

        return todo;
    }
}
