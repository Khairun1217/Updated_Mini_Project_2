package appUtils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;

    /**
     * Constructor - load Excel file and sheet
     */
    public ExcelUtils(String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(excelPath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file: " + excelPath);
        }
    }

    /**
     * Get row count
     */
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * Get column count
     */
    public int getColCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    /**
     * Get cell data as String
     */
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter(); // formats as String (no matter number, date, etc.)
        return formatter.formatCellValue(cell);
    }

    /**
     * Close workbook
     */
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
