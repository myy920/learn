package excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

public class MyMergeStrategy extends AbstractMergeStrategy {

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer integer) {
        CellAddress address = cell.getAddress();
        int row = address.getRow();
        int column = address.getColumn();

        if (row == 2 && column == 0) {
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
            Cell cell1 = sheet.getRow(1).getCell(0);
            CellStyle cellStyle1 = cell1.getCellStyle();

            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.cloneStyleFrom(cellStyle1);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cell1.setCellStyle(cellStyle);
        }
    }
}
