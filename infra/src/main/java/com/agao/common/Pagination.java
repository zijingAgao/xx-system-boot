package com.agao.common;

import lombok.AllArgsConstructor;

/**
 * 返回前端的分页参数
 *
 * @author Agao
 * @date 2024/2/11 0:10
 */
@AllArgsConstructor
public class Pagination {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;

    public Pagination(Integer currentPage, Integer totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
