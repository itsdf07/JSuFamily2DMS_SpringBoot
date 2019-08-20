package com.aso.itsdf07.controller;

import com.aso.itsdf07.entity.GoodEntity;
import com.aso.itsdf07.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: Excel 导入导出
 * @Auther: itsdf07
 * @Date: 2019/7/28/028 10:08
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    /**
     * Excel 文件数据导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importGoods")
    public boolean importGoods(@RequestParam("file") MultipartFile file) {
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        try {
            isSuccess = excelService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * Excel 文件数据导入
     *
     * @param response
     * @param filterGoodEntity 过滤条件
     * @return
     */
    @PostMapping("/exportGoods")
    public void exportGoodsForm(HttpServletResponse response, GoodEntity filterGoodEntity) throws IOException {
        String[] header = {"Product Name",
                "Seller SKU",
                "SKU ID",
                "URL",
                "SKU Visitors",
                "SKU Pageviews",
                "Visitor Value",
                "Buyers",
                "Orders",
                "Units Sold",
                "Revenue",
                "Conversion Rate",
                "Revenue per Buyer",
                "Wishlist Visitor",
                "Wishlists",
                "Add to Cart Visitors",
                "Add to Cart Units",
                "店铺",
                "国家",
                "数据日期",
                "导入日期"};
        excelService.batchExport(response, filterGoodEntity, header);
    }


    /**
     * Excel 文件数据导入：Transaction
     *
     * @param file
     * @return
     */
    @PostMapping("/importTransaction")
    public boolean importTransaction(@RequestParam("file") MultipartFile file) {
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        try {
            isSuccess = excelService.batchImportTransaction(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

}
