package com.myy.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private Long total;

    private List<T> list;

    public static <T> PageResult<T> of(Long total, List<T> list) {
        return new PageResult<>(total, list);
    }

    public static <T> PageResult<T> of(Page<T> page) {
        return of(page.getTotalElements(), page.getContent());
    }

    public static <T, R> PageResult<R> of(Page<T> page, Function<T, R> mapper) {
        return of(page.getTotalElements(), page.getContent().stream().map(mapper).collect(Collectors.toList()));
    }

    public static <T> PageResult<T> empty() {
        return of(0L, Collections.emptyList());
    }

}
