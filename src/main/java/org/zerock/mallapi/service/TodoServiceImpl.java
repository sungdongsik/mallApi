package org.zerock.mallapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.dto.TodoDto;
import org.zerock.mallapi.entity.Todo;
import org.zerock.mallapi.repository.TodoRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;
    @Override
    public TodoDto get(Long tno) {

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        return entityToDTO(todo);
    }

    @Override
    public Long register(TodoDto dto) {

        Todo todo = entityToEntity(dto);
        Todo result = todoRepository.save(todo);

        return result.getTno();
    }

    @Override
    public void modify(TodoDto dto) {

        Optional<Todo> result = todoRepository.findById(dto.getTno());
        Todo todo = result.orElseThrow();

        todo.setTitle(todo.getTitle());
        todo.setContent(todo.getContent());
        todo.setComplete(todo.isComplete());
        todo.setDueDate(todo.getDueDate());

        todoRepository.save(todo);


    }

    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);
    }
}
