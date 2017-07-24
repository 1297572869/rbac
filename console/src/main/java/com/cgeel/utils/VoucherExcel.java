package com.cgeel.utils;


import com.cgeel.model.Voucher;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-06-20.
 */
public class VoucherExcel
{
    public static ExcelUtils toExcel(List<Voucher> vouchers){
        //FFCC99  8080FF  FF00
        Map<String, Integer> thLevelMap = new HashMap<>();
        ExcelUtils excelUtils = new ExcelUtils("电子码表");
        excelUtils.setDefaultHeight(600);
        excelUtils.setWidth(1, 6000);
        excelUtils.setWidth(3, 6000);
        excelUtils.setWidth(5, 6000);
        excelUtils.setWidth(6, 6000);
        excelUtils.setWidth(7, 6000);
        HSSFCellStyle style = excelUtils.createStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 居中
        style.setBorderBottom((short) 1);
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setWrapText(true);
        HSSFFont f = excelUtils.creatFont();
        f.setFontHeightInPoints((short) 11);//字号
        f.setFontName("宋体");
        style.setFont(f);
        excelUtils.setDefaultStyle(style);
        int row = 0;
        HSSFPalette palette = excelUtils.getPalette();

        HSSFFont tf  = excelUtils.creatFont();
        tf.setFontHeightInPoints((short) 11);//字号
        tf.setBoldweight((short)600);
        tf.setFontName("宋体");


        HSSFCellStyle titleStyleA = excelUtils.createDefaultStyle();
        short[] co = getColor("#99CCFF");
        titleStyleA.setFillForegroundColor(palette.findSimilarColor(co[0], co[1], co[2]).getIndex());
        titleStyleA.setFillPattern(CellStyle.SOLID_FOREGROUND);
        titleStyleA.setFont(tf);

        HSSFCellStyle titleStyleB = excelUtils.createDefaultStyle();
        short[] coB = getColor("#FF8080");
        titleStyleB.setFillForegroundColor(palette.findSimilarColor(coB[0], coB[1], coB[2]).getIndex());
        titleStyleB.setFillPattern(CellStyle.SOLID_FOREGROUND);
        titleStyleB.setFont(tf);

        HSSFCellStyle titleStyleC = excelUtils.createDefaultStyle();
        short[] coC = getColor("#00FF00");
        titleStyleC.setFillForegroundColor(palette.findSimilarColor(coC[0], coC[1], coC[2]).getIndex());
        titleStyleC.setFillPattern(CellStyle.SOLID_FOREGROUND);
        titleStyleC.setFont(tf);

        HSSFCellStyle tableStyleA = excelUtils.createDefaultStyle();
        tableStyleA.setFillForegroundColor(palette.findSimilarColor(co[0], co[1], co[2]).getIndex());
        tableStyleA.setFillPattern(CellStyle.SOLID_FOREGROUND);

        HSSFCellStyle tableStyleB = excelUtils.createDefaultStyle();
        tableStyleB.setFillForegroundColor(palette.findSimilarColor(coB[0], coB[1], coB[2]).getIndex());
        tableStyleB.setFillPattern(CellStyle.SOLID_FOREGROUND);

        HSSFCellStyle tableStyleC = excelUtils.createDefaultStyle();
        tableStyleC.setFillForegroundColor(palette.findSimilarColor(coC[0], coC[1], coC[2]).getIndex());
        tableStyleC.setFillPattern(CellStyle.SOLID_FOREGROUND);

        HSSFCellStyle titleStyle = excelUtils.createDefaultStyle();
        titleStyle.setFont(tf);
        excelUtils.addCell(row, 10, "电子码表", titleStyle);
        row++;
        excelUtils.addCell(row, 1, "电子码名称");
        excelUtils.addCell(row, 1, "电子码编号");
        excelUtils.addCell(row, 1, "电子码使用范围");
        excelUtils.addCell(row, 1, "电子码有效期");
        row++;
        for(Voucher voucher:vouchers){
            excelUtils.addCell(row, 1, voucher.getName());
            excelUtils.addCell(row, 1, voucher.getSn());
            excelUtils.addCell(row, 1, voucher.getWareName());
            excelUtils.addCell(row, 1, voucher.getYxq());
            row++;
        }
        return excelUtils;
    }


    private static short[] getColor(String color){
        short[] ss = new short[3];
        ss[0] = Short.valueOf(color.substring(1,3), 16);
        ss[1] = Short.valueOf(color.substring(3, 5), 16);
        ss[2] = Short.valueOf(color.substring(5, 7), 16);
        return ss;
    }
}
