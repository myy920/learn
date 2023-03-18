package com.myy.spring5.Util;

import java.util.ArrayList;

public class List {

    public static <T> java.util.List<T> of(T... ts) {
        ArrayList<T> list = new ArrayList<>();
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

}
