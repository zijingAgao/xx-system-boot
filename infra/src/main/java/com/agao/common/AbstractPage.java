package com.agao.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Agao
 * @date 2024/2/10 23:34
 */
@Getter
@Setter
public abstract class AbstractPage {
    @ApiModelProperty(value = "当前页", example = "0")
    private int page = PageConstants.DEFAULT_PAGE;
    @ApiModelProperty(value = "页面大小", example = "10")
    private int size = PageConstants.DEFAULT_SIZE;

    @ApiIgnore
    public Pageable obtainPageable() {
        return PageRequest.of(page, size);
    }
}
