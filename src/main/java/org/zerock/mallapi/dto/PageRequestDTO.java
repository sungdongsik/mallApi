package org.zerock.mallapi.dto;


import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder //상속하기 위해서 사용
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;


}
