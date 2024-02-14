package com.agao.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Agao
 * @date 2024/2/11 0:05
 */
@Data
@NoArgsConstructor
public class PageData<T> {

    private List<T> data;
    private Pagination pagination;

    public PageData(Page<T> page) {
        this.data = page.getContent();
        this.pagination = new Pagination(page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public PageData(List<T> list, Page page){
        this.data = list;
        this.pagination = new Pagination(page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }
}
