package com.agao.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回前端的分页参数
 *
 * @author Agao
 * @date 2024/2/11 0:10
 */
@Data
@AllArgsConstructor
public class Pagination {
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;
    @ApiModelProperty(value = "总页数")
    private Integer totalPages;
    @ApiModelProperty(value = "总条数")
    private Long totalElements;

    public Pagination(Integer currentPage, Integer totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
