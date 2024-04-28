package org.zerock.mallapi.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mallapi.dto.TodoDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Log4j2
class TodoServiceTest {
    @Autowired
    TodoService todoService;

    @Test
    public void testGet(){
        Long tno = 50L;

        log.info(todoService.get(tno));
    }


    @Test
    public void testRegister(){
        TodoDto todoDto = TodoDto.builder()
                .title("AAA")
                .content("CCCC")
                .dueDate(LocalDate.of(2023,12,31))
                .build();


        log.info(todoService.register(todoDto));
    }
}