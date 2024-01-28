package excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        ExcelWriter ew = new ExcelWriterBuilder().file("D://测试.xlsx").excelType(ExcelTypeEnum.XLSX).build();
        WriteSheet ws = new ExcelWriterSheetBuilder(ew).sheetNo(0).sheetName("导出").head(MyEntity.class)
                .registerWriteHandler(new MyMergeStrategy())
                .build();

        List<MyEntity> data = new ArrayList<>();
        data.add(MyEntity.builder().name("JoJo").remark("测试1").build());
        data.add(MyEntity.builder().name("JoJo").remark("测试2").build());

        ew.write(data, ws);
        ew.finish();
    }

}
