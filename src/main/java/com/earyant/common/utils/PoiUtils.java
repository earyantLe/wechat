package com.earyant.common.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * POI文件处理工具类
 *
 * @author 韩志伟   2016-4-6
 */
@SuppressWarnings("deprecation")
public class PoiUtils {
    /**
     * 传递一个文件名解析为workbook返回
     * 2016-4-6
     *
     * @param filePath 被解析的excel文件路径
     * @return
     * @throws IOException
     * @author 韩志伟
     */
    public static Workbook parseFileToExcel(String filePath) throws IOException {
        Workbook workbook = null;
        if (isExcel2003(filePath)) {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
        } else if (isExcel2010(filePath)) {
            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        }
        return workbook;
    }

    /**
     * 文件是excel2010
     * 2013-5-15 下午06:25:34
     *
     * @param fileName
     * @return
     * @author 韩志伟
     */
    public static boolean isExcel2010(String fileName) {
        String fileTail = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (fileTail != null && fileTail.length() > 0 && fileTail.equals("xlsx")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 文件是excel2003
     * 2016-4-6
     *
     * @param fileName
     * @return
     * @author 韩志伟
     */
    public static boolean isExcel2003(String fileName) {
        String fileTail = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (fileTail != null && fileTail.length() > 0 && fileTail.equals("xls")) {
            return true;
        } else {
            return false;
        }
    }


    public static void writer(String path, String fileName, String fileType, ArrayList<ArrayList<String>> list, Map map) throws IOException {
        //创建工作文档对象  
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook();
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook();
        }
        // 创建sheet对象
        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ArrayList<String> list1 = list.get(i);
                Row row = (Row) sheet1.createRow(i);
                for (int j = 0; j < list1.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(list1.get(j));
                }
            }
            if (map != null && map.get("row") != null && map.get("data") != null) {
                if (!map.get("row").toString().equals("") && !map.get("data").toString().equals("")) {
                    String[] textlist = map.get("data").toString().split(",");
                    try {
                        int rowcell = Integer.parseInt(map.get("row").toString());
                        sheet1 = setHSSFValidation(sheet1, textlist, 1, list.size() + 2, rowcell, rowcell);// 第一列的前501行都设置为选择列表形式.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }


        // 创建文件流
        OutputStream stream = new FileOutputStream(path + fileName + "." + fileType);
        //写入数据  
        wb.write(stream);
        //关闭文件流  
        stream.close();
    }

    public static ArrayList<ArrayList<String>> read(String path, String fileType) throws Exception {
        InputStream stream = new FileInputStream(path);
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        }
        Sheet sheet1 = wb.getSheetAt(0);
        for (Row row : sheet1) {
            ArrayList<String> list1 = new ArrayList<String>();
            for (Cell cell : row) {
                String str = "";
                try {
                    str = cell.getStringCellValue();
                } catch (Exception e) {
                    str = "";
                }
                list1.add(str);
            }
            list.add(list1);
        }
        return list;
    }


    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> list1 = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        list1.add("第一列");
        list1.add("第二列");
        list1.add("第三列");
        list2.add("aaa111");
        list2.add("bbb222");
        list2.add("ccc333");
        list.add(list1);
        list.add(list2);
        PoiUtils.writer("F:\\", "abc", "xls", list, null);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static Sheet setHSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol,
                                          int endCol) {
        // 加载下拉列表内容  
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象  
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
                                          String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
        // 构造constraint对象  
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");
        // 四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象  
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }


}
