package org.zerock.mallapi.repository.search;

import org.springframework.data.domain.Page;
import org.zerock.mallapi.entity.Todo;

public interface TodoSearch {

    Page<Todo> search1();

}
