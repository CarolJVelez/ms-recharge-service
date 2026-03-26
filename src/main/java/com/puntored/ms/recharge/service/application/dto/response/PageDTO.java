package com.puntored.ms.recharge.service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;

    public PageDTO() {
        this.content = new ArrayList<>();
    }
}
