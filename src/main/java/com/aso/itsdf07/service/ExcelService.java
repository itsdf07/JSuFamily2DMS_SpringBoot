package com.aso.itsdf07.service;

import com.aso.itsdf07.entity.GoodEntity;
import com.aso.itsdf07.mapper.GoodEntityMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/28/028 10:16
 */
@Service
public class ExcelService {
    @Autowired
    private GoodEntityMapper goodEntityMapper;
    /**
     * 数据新增的模板行数
     */
    private int tempplateLine = 4;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<GoodEntity> userList = new ArrayList<GoodEntity>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            //TODO 上传文件格式不正确
            return false;
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        GoodEntity goodEntity;
        String store = sheet.getRow(sheet.getLastRowNum() - 3).getCell(0).getStringCellValue();
        String country = sheet.getRow(sheet.getLastRowNum() - 2).getCell(0).getStringCellValue();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dateTime = sdf.parse(sheet.getRow(sheet.getLastRowNum() - 1).getCell(0).getStringCellValue());
        Date createTime = new Date();

        for (int r = 1; r <= sheet.getLastRowNum() - 4; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            goodEntity = new GoodEntity();

            //product_name
            String product_name = row.getCell(0).getStringCellValue();
            //seller_sku
            String seller_sku = row.getCell(1).getStringCellValue();
            //sku_id
            String sku_id = row.getCell(2).getStringCellValue();
            //url
            String url = row.getCell(3).getStringCellValue();
            //sku_visitors
            String sku_visitors = row.getCell(4).getStringCellValue();
            //sku_pageviews
            String sku_pageviews = row.getCell(5).getStringCellValue();
            //visitor_value
            String visitor_value = row.getCell(6).getStringCellValue();
            //buyers
            String buyers = row.getCell(7).getStringCellValue();
            //orders
            String orders = row.getCell(8).getStringCellValue();
            //units_sold
            String units_sold = row.getCell(9).getStringCellValue();
            //revenue
            String revenue = row.getCell(10).getStringCellValue();
            //conversion_rate
            String conversion_rate = row.getCell(11).getStringCellValue();
            conversion_rate = conversion_rate.substring(0, conversion_rate.length() - 1);//需要去掉%
            //revenue_per_buyer
            String revenue_per_buyer = row.getCell(12).getStringCellValue();
            //wishlist_visitor
            String wishlist_visitor = row.getCell(13).getStringCellValue();
            //wishlists
            String wishlists = row.getCell(14).getStringCellValue();
            //add_to_car_visitors
            String add_to_car_visitors = row.getCell(15).getStringCellValue();
            //add_to_car_units
            String add_to_car_units = row.getCell(16).getStringCellValue();


            goodEntity.setProductName(product_name);
            goodEntity.setSellerSku(seller_sku);
            goodEntity.setSkuId(sku_id);
            goodEntity.setUrl(url);
            goodEntity.setSkuVisitors(Integer.parseInt(sku_visitors));
            goodEntity.setSkuPageviews(Integer.parseInt(sku_pageviews));
            goodEntity.setVisitorValue(Double.parseDouble(visitor_value));
            goodEntity.setBuyers(Integer.parseInt(buyers));
            goodEntity.setOrders(Integer.parseInt(orders));
            goodEntity.setUnitsSold(Integer.parseInt(units_sold));
            goodEntity.setRevenue(Double.parseDouble(revenue));
            goodEntity.setConversionRate(Double.parseDouble(conversion_rate));
            goodEntity.setRevenuePerBuyer(Double.parseDouble(revenue_per_buyer));
            goodEntity.setWishlistVisitor(Integer.parseInt(wishlist_visitor));
            goodEntity.setWishlists(Integer.parseInt(wishlists));
            goodEntity.setAddToCarVisitors(Integer.parseInt(add_to_car_visitors));
            goodEntity.setAddToCarUnits(Integer.parseInt(add_to_car_units));
            goodEntity.setStore(store);
            goodEntity.setCountry(country);
            goodEntity.setDatetime(dateTime);
            goodEntity.setCreateTime(createTime);

            userList.add(goodEntity);

        }
        for (GoodEntity goodData : userList) {
//            String sellerSku = goodData.getSellerSku();
//            String skuId = goodData.getSkuId();
//
//            Date dataTime = goodData.getDatetime();
            int cnt = goodEntityMapper.findGoodBySkuIdAndDataTime(goodData);
            if (cnt == 0) {
                goodEntityMapper.insert(goodData);
                System.out.println(" 插入 " + goodData.getSkuId());
            } else {
                goodEntityMapper.updateGoodBySkuIdAndDataTime(goodData);
                System.out.println(" 更新 " + goodData.getSkuId());
            }
        }
        return notNull;
    }


}