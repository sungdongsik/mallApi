package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
