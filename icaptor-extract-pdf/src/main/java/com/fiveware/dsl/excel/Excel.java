package com.fiveware.dsl.excel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Iterator;


/**
 * Created by valdisnei on 6/30/17.
 */
class Excel implements IExcel{

    private String fileExcel;

    private HSSFWorkbook my_xls_workbook;
    private HSSFSheet my_worksheet;
    private Cell cell;

    protected Excel(){
    }

    public IExcel open(String fileExcel) throws IOException {
        this.fileExcel = fileExcel;
        FileInputStream input_document = new FileInputStream(new File(fileExcel));
        my_xls_workbook = new HSSFWorkbook(input_document);
        return this;
    }

    public Integer totalSheets(){
        return my_xls_workbook.getNumberOfSheets();
    }



    public IExcel sheet(int sheet){
        my_worksheet = my_xls_workbook.getSheetAt(sheet);
        return this;
    }

    public IExcel cell(String referenceCell){
        buildCell(referenceCell);
        return this;
    }

    @Override
    public String text() {
        return cell.getStringCellValue();
    }

    @Override
    public String getFormula() {
        return cell.getCellFormula();
    }

    @Override
    public IExcel formula(String referenceCell, String formula) {
        buildCell(referenceCell);
        cell.setCellFormula(formula);
        cell.setCellType(CellType.FORMULA);
        return this;
    }

    @Override
    public IExcel cell(String referenceCell, String value) {
        buildCell(referenceCell);
        cellValue(value);
        return this;
    }

    @Override
    public IExcel cell(String referenceCell, Double value) {
        buildCell(referenceCell);
        cellValue(value);
        return this;
    }

    @Override
    public IExcel cell(String referenceCell, LocalDate value) {
        buildCell(referenceCell);
        cellValue(value);
        return this;
    }

    private void cellValue(Object value) {
        if (value instanceof String){
            cell.setCellValue((String) value);
            cell.setCellType(CellType.STRING);
        }else if (value instanceof Double){
            cell.setCellValue((Double) value);
            cell.setCellType(CellType.NUMERIC);
        }else if (value instanceof LocalDate){
            LocalDate value1 = (LocalDate) value;
            Date date = Date.valueOf(value1);
            cell.setCellValue(date);
        }
    }

    private void buildCell(String referenceCell) {
        CellReference cellReference = new CellReference(referenceCell);
        Row row = my_worksheet.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
    }

    @Override
    public Object build() throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(this.fileExcel)) {
            my_xls_workbook.write(outputStream);
            my_xls_workbook.close();
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
        }

        return null;
    }


    public static void main(String[] args) throws Exception{
        String rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
        String fileExcel = rootDir + File.separator + "sal.xls";
        String outPathFile = rootDir + File.separator + "out.pdf";

        //First we read the Excel file in binary format into FileInputStream
        FileInputStream input_document = new FileInputStream(new File(fileExcel));
        // Read workbook into HSSFWorkbook
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
        // Read worksheet into HSSFSheet
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        //We will create output PDF document objects at this point
        Document iText_xls_2_pdf = new Document();
        PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream(outPathFile));
        iText_xls_2_pdf.open();
        //we have two columns in the Excel sheet, so we create a PDF table with two columns
        //Note: There are ways to make this dynamic in nature, if you want to.
        PdfPTable my_table = new PdfPTable(2);
        //We will use the object below to dynamically add new data to the table
        PdfPCell table_cell;
        //Loop through rows.
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL
                switch(cell.getCellType()) { //Identify CELL type
                    //you need to add more code here based on
                    //your requirement / transformations
                    case Cell.CELL_TYPE_STRING:
                        //Push the data from Excel to PDF Cell
                        table_cell=new PdfPCell(new Phrase(cell.getStringCellValue()));
                        //feel free to move the code below to suit to your needs
                        my_table.addCell(table_cell);
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        //Push the data from Excel to PDF Cell
                        table_cell=new PdfPCell(new Phrase(cell.getCellFormula()));
                        //feel free to move the code below to suit to your needs
                        my_table.addCell(table_cell);
                        break;
                }
                //next line
            }

        }
        //Finally add the table to PDF document
        iText_xls_2_pdf.add(my_table);
        iText_xls_2_pdf.close();
        //we created our pdf file..
        input_document.close(); //close xls
    }

}
