package com.myy.util.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;

public class StandardStyleWriteHandler extends AbstractVerticalCellStyleStrategy {


    @Override
    protected WriteCellStyle headCellStyle(Head head) {
        return super.headCellStyle(head);
    }
}
