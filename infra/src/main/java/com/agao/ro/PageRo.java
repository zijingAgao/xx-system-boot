package com.agao.ro;

import com.agao.constant.PageConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author Agao
 * @date 2024/2/10 23:34
 */
@Getter
@Setter
public class PageRo {
    @ApiModelProperty(value = "当前页", example = "0")
    private int page = PageConstants.DEFAULT_PAGE;
    @ApiModelProperty(value = "页面大小", example = "10")
    private int size = PageConstants.DEFAULT_SIZE;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
