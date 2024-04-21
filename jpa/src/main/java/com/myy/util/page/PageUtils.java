package com.myy.util.page;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtils {

    public static <T> PageResult<T> toResult(Page<T> page) {
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    public static <T, R> PageResult<R> toResult(Page<T> page, Function<T, R> mapper) {
        List<R> list = page.getContent().stream().map(mapper).collect(Collectors.toList());
        return new PageResult<>(page.getTotalElements(), list);
    }

    public static <T> PageResult<T> toResult(long total, List<T> list) {
        return new PageResult<>(total, list);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L, new ArrayList<>());
    }

}
