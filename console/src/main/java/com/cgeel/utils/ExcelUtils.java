package com.cgeel.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxw on 2016/11/3.
 */
public class ExcelUtils
{

    private Map<String, Integer> rowColNext = new HashMap<>();
    private HSSFWorkbook wb = new HSSFWorkbook();
    private String defaultSheetName;
    private HSSFSheet sheet;
    private HSSFCellStyle defaultStyle;
    private int lastRow;
    private Map<String, HSSFSheet> sheetMap = new HashMap<>();

    public ExcelUtils(String sheetName){
        sheet = wb.createSheet(sheetName);
        this.defaultSheetName = sheetName;
        sheetMap.put(sheetName, sheet);
    }

    public void addSheet(String sheetName){
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheetMap.put(sheetName, sheet);
    }

    public void setDefaultStyle(HSSFCellStyle defaultStyle){
        this.defaultStyle = defaultStyle;
    }

    public void setDefaultHeight(int height){
        setDefaultHeight(defaultSheetName, height);
    }

    public void setDefaultHeight(String sheetName, int height){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            sheet.setDefaultRowHeight((short) height);
        }
    }

    public void setDefaultWidth(int width){
        setDefaultWidth(defaultSheetName, width);
    }

    public void setDefaultWidth(String sheetName, int width){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            sheet.setDefaultColumnWidth(width);
        }
    }

    public void setWidth(int col, int width){
        setWidth(defaultSheetName, col, width);
    }

    public void setWidth(String sheetName, int col, int width){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            sheet.setColumnWidth(col, width);
        }
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public HSSFRow getRow(int rowNum){
        return getRow(defaultSheetName, rowNum);
    }

    public HSSFRow getRow(String sheetName, int rowNum){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            HSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            return row;
        }
        return null;
    }

    public HSSFCell addCell(int row, int colNum, Object value){
        return addCell(defaultSheetName, row, colNum, value);
    }

    public HSSFCell addCell(String sheetName, int row, int colNum, Object value){
        return addCell(sheetName, row, colNum, value, defaultStyle);
    }

    public HSSFCell addCell(int row, int colNum, Object value, HSSFCellStyle style){
        return addCell(defaultSheetName, row, colNum, value, style);
    }

    public HSSFCell addCell(String sheetName, int row, int colNum, Object value, HSSFCellStyle style){
        if(colNum == 0){
            return null;
        }
        Integer col = rowColNext.get(sheetName + "_" + row);
        if(col == null){
            col = 0;
        }
        HSSFCell cell = setCell(sheetName, row, row, col, colNum + col - 1, value, style);
        rowColNext.put(sheetName + "_" + row, colNum + col);
        return cell;
    }

    public HSSFCell setCell(int startRow, int endRow, int startCol, int endCol, Object value, HSSFCellStyle style){
        return setCell(defaultSheetName, startRow, endRow, startCol, endCol, value, style);
    }

    public HSSFCell setCell(String sheetName, int startRow, int endRow, int startCol, int endCol, Object value, HSSFCellStyle style){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            CellRangeAddress addr = new CellRangeAddress(startRow, endRow, startCol, endCol);
            sheet.addMergedRegion(addr);
            HSSFRow row = sheet.getRow(startRow);
            if (row == null) {
                row = sheet.createRow(startRow);
            }
            HSSFCell cell = row.getCell(startCol);
            if (cell == null) {
                cell = row.createCell(startCol);
            }
            if (style != null) {
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = startCol; j <= endCol; j++) {
                        HSSFRow rowI = sheet.getRow(i);
                        if (rowI == null) {
                            rowI = sheet.createRow(i);
                        }
                        HSSFCell cellJ = rowI.getCell(j);
                        if (cellJ == null) {
                            cellJ = rowI.createCell(j);
                        }
                        cellJ.setCellStyle(style);
                    }
                }
            }
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Integer) {
                cell.setCellValue(Double.valueOf(value.toString()));
            } else if(value instanceof Long){
                cell.setCellValue(Double.valueOf(value.toString()));
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            }
            return cell;
        }
        return null;
    }

    public HSSFCell setCellFormula(int row, int colNum, String value){
        return setCellFormula(defaultSheetName, row, colNum, value);
    }

    public HSSFCell setCellFormula(String sheetName, int row, int colNum, String value){
        return setCellFormula(sheetName, row, colNum, value, defaultStyle);
    }

    public HSSFCell setCellFormula(int row, int colNum, String value, HSSFCellStyle style){
        return setCellFormula(defaultSheetName, row, colNum, value, style);
    }

    public HSSFCell setCellFormula(String sheetName, int row, int colNum, String value, HSSFCellStyle style){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            if (colNum == 0) {
                return null;
            }
            Integer col = rowColNext.get(sheetName + "_" + row);
            if (col == null) {
                col = 0;
            }
            HSSFCell cell = setCellFormula(sheetName, row, row, col, colNum + col - 1, value, style);
            rowColNext.put(sheetName + "_" + row, colNum + col);
            return cell;
        }
        return null;
    }

    public HSSFCell setCellFormula(int startRow, int endRow, int startCol, int endCol, String value, HSSFCellStyle style){
        return setCellFormula(defaultSheetName, startRow, endRow, startCol, endCol, value, style);
    }

    public HSSFCell setCellFormula(String sheetName, int startRow, int endRow, int startCol, int endCol, String value, HSSFCellStyle style){
        HSSFSheet sheet = sheetMap.get(sheetName);
        if(sheet != null) {
            CellRangeAddress addr = new CellRangeAddress(startRow, endRow, startCol, endCol);
            sheet.addMergedRegion(addr);
            HSSFRow row = sheet.getRow(startRow);
            if (row == null) {
                row = sheet.createRow(startRow);
            }
            HSSFCell cell = row.getCell(startCol);
            if (cell == null) {
                cell = row.createCell(startCol);
            }
            if (style != null) {
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = startCol; j <= endCol; j++) {
                        HSSFRow rowI = sheet.getRow(i);
                        if (rowI == null) {
                            rowI = sheet.createRow(i);
                        }
                        HSSFCell cellJ = rowI.getCell(j);
                        if (cellJ == null) {
                            cellJ = rowI.createCell(j);
                        }
                        cellJ.setCellStyle(style);
                    }
                }
            }
            cell.setCellFormula(value);
            return cell;
        }
        return null;
    }

    public Integer getNextCol(int row){
        return getNextCol(defaultSheetName, row);
    }

    public Integer getNextCol(String sheetName, int row){
        Integer col = rowColNext.get(sheetName + "_" + row);
        if(col == null){
            return 0;
        }
        return col;
    }

    public void export(){
        try {
            FileOutputStream fos = new FileOutputStream("d:/sc.xls");
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void export(String name, HttpServletResponse response){
        try {
            response.reset();
            response.setHeader("Content-Type", "application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("gbk"), "iso8859-1") + ".xls");
            wb.write(response.getOutputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HSSFCellStyle createStyle(){
        return wb.createCellStyle();
    }

    public HSSFCellStyle createDefaultStyle(){
        HSSFCellStyle tableStyle = wb.createCellStyle();
        tableStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        tableStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 居中
        tableStyle.setBorderBottom((short)1);
        tableStyle.setBorderTop((short) 1);
        tableStyle.setBorderLeft((short) 1);
        tableStyle.setBorderRight((short) 1);
        tableStyle.setWrapText(true);
        return tableStyle;
    }

    public HSSFFont creatFont(){
        return wb.createFont();
    }

    public HSSFPalette getPalette(){
        return wb.getCustomPalette();
    }

    public static void main(String [] args){
        ExcelUtils scheduleTableExcel = new ExcelUtils("test");
        HSSFCellStyle style = scheduleTableExcel.createStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 居中
        style.setBorderBottom((short)1);
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        scheduleTableExcel.setDefaultStyle(style);
        scheduleTableExcel.setDefaultHeight(500);
        scheduleTableExcel.setDefaultWidth(30);
        scheduleTableExcel.addCell(0, 1, "111");
        scheduleTableExcel.addCell(0, 2, "222");
        scheduleTableExcel.addCell(0, 1, "333");
        scheduleTableExcel.addCell(0, 3, "444");
        scheduleTableExcel.export();
    }

}
