package com.myy.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.Collection;

public class Demo {

    public static void main(String[] args) {
        ExcelWriter excelWriter = EasyExcel.write("D:\\test.xlsx").build();
        WriteSheet writeSheet = EasyExcel.writerSheet().head(Person.class).registerWriteHandler(new ExcelStyleWriteHandler()).build();

        excelWriter.write((Collection<?>) null, writeSheet);

        excelWriter.finish();
    }

}
