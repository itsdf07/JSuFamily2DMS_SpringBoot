package com.aso.itsdf07.controller;

import com.aso.itsdf07.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
