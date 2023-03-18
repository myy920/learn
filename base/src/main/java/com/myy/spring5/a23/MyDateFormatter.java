package com.myy.spring5.a23;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateFormatter implements Formatter<Date> {

    private String name;

    public MyDateFormatter() {
    }

    public MyDateFormatter(String name) {
        this.name = name;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        System.out.println("MyDateFormatter parse调用 name=" + name);
        return new SimpleDateFormat("yyyy|MM|dd").parse(text);
    }

    @Override
    public String print(Date object, Locale locale) {
        System.out.println("MyDateFormatter print调用 name=" + name);
        return new SimpleDateFormat("yyyy|MM|dd").format(object);
    }
}
