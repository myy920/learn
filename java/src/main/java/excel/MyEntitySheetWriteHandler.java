package excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class MyEntitySheetWriteHandler implements SheetWriteHandler {


    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        sheet.addMergedRegion(new CellRangeAddress(1,2,0,0));
        Row row = sheet.getRow(1);
        if (row == null){
            row = sheet.createRow(1);
        }
        Cell cell = row.getCell(0);
        if (cell == null){
            cell = row.createCell(0);
        }
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}
