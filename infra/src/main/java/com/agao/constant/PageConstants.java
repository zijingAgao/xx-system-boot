package com.agao.constant;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 分页参数
 *
 * @author Agao
 * @date 2024/2/10 23:26
 */
public class PageConstants {
    public static int DEFAULT_PAGE = 0;
    public static int DEFAULT_SIZE = 10;

    public static Pageable getDefaultPageable() {
        return PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);
    }
}
