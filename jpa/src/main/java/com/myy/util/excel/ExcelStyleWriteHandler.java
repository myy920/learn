package com.myy.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

public class ExcelStyleWriteHandler implements WorkbookWriteHandler {

    @Override
    public void beforeWorkbookCreate(WorkbookWriteHandlerContext context) {
        // 在此处替换自定义的excel样式
        System.out.println(1);

        WriteContext writeContext = context.getWriteContext();
        WriteHolder writeHolder = writeContext.currentWriteHolder();
        if (!(writeHolder instanceof WriteSheetHolder)) {
            return;
        }
        WriteSheetHolder writeSheetHolder = (WriteSheetHolder) writeHolder;
        WriteSheet writeSheet = writeSheetHolder.getWriteSheet();
        WriteSheetHolder sheetHolder = new WriteSheetHolder(writeSheet, writeContext.writeWorkbookHolder());

        WriteSheet tempWriteSheet = EasyExcel.writerSheet().head(StandardTableStyle.class).build();
        WriteSheetHolder tempSheetHolder = new WriteSheetHolder(tempWriteSheet, writeContext.writeWorkbookHolder());

       // new ExcelWriteHeadProperty(writeHolder, )





    }
}
