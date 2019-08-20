package com.aso.itsdf07.utils;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/31/031 10:41
 */
public class ExcelUtils {

    /**
     * Excel表格导出
     *
     * @param response    HttpServletResponse对象
     * @param excelData   Excel表格的数据，封装为List<List<String>>
     * @param sheetName   sheet的名字
     * @param fileName    导出Excel的文件名
     * @param columnWidth Excel表格的宽度，建议为15
     * @throws IOException 抛IO异常
     */
    public static void exportExcel(HttpServletResponse response,
                                   List<List<String>> excelData,
                                   String sheetName,
                                   String fileName,
                                   int columnWidth) throws IOException {

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);

        //写入List<List<String>>中的数据
        int rowIndex = 0;
        for (List<String> data : excelData) {
            //创建一个row行，然后自增1
            HSSFRow row = sheet.createRow(rowIndex++);

            //遍历添加本行数据
            for (int i = 0; i < data.size(); i++) {
                //创建一个单元格
                HSSFCell cell = row.createCell(i);

                //创建一个内容对象
                HSSFRichTextString text = new HSSFRichTextString(data.get(i));

                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
            }
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("multipart/form-data");

        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());

        //关闭workbook
        workbook.close();
    }

    /**
     * 描述：导出
     *
     * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据（data1,data2,data3...）
     * @return
     * @author mao2080@sina.com
     * @created 2017年8月26日 下午2:39:13
     * @since
     */
//    public static boolean exportCsv(File file, List<String> dataList) {
//        FileOutputStream out = null;
//        OutputStreamWriter osw = null;
//        BufferedWriter bfw = null;
//        try {
//            out = new FileOutputStream(file);
//            osw = new OutputStreamWriter(out, "gbk");
//            bfw = new BufferedWriter(osw);
//            if (dataList != null && !dataList.isEmpty()) {
//                for (String data : dataList) {
//                    bfw.append(data).append("\r");
//                }
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        } finally {
//            IOUtil.closeQuietly(bfw, osw, out);
//        }
//    }

    /**
     * 描述：导入
     *
     * @param file csv文件(路径+文件名)
     * @return
     * @author mao2080@sina.com
     * @created 2017年8月26日 下午2:42:08
     * @since
     */
    public static List<String> importCsv(File file) {
        List<String> dataList = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception e) {

        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }


}
