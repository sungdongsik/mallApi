package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDto;
import org.zerock.mallapi.service.TodoService;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;


    @GetMapping("/{tno}")
    public TodoDto get(@PathVariable("tno") Long tno){

        return todoService.get(tno);
    }
    //pathvariable, queryString
    @GetMapping("/list")
    public PageResponseDTO<TodoDto> list(PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO + "=====>");

        return todoService.getList(pageRequestDTO);
    }


    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDto dto){

        log.info(dto + "======dto");
        Long tno = todoService.register(dto);


        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno") Long tno, @RequestBody TodoDto todoDto){


        todoDto.setTno(tno);
        todoService.modify(todoDto);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable("tno") Long tno){

        todoService.remove(tno);

        return Map.of("RESULT", "SUCCESS");
    }
}
