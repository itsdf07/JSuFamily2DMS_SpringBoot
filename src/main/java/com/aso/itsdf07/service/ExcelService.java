package com.aso.itsdf07.service;

import com.aso.itsdf07.entity.GoodEntity;
import com.aso.itsdf07.entity.TransactionEntity;
import com.aso.itsdf07.mapper.GoodEntityMapper;
import com.aso.itsdf07.mapper.TransactionEntityMapper;
import com.aso.itsdf07.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
    @Autowired
    private TransactionEntityMapper transactionEntityMapper;
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
        String store = sheet.getRow(sheet.getLastRowNum() - 2).getCell(0).getStringCellValue();
        String country = sheet.getRow(sheet.getLastRowNum() - 1).getCell(0).getStringCellValue();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Cell cell = sheet.getRow(sheet.getLastRowNum()).getCell(0);
        cell.setCellType(CellType.STRING);
        Date dateTime = sdf.parse(cell.getStringCellValue());
        Date createTime = new Date();

        for (int r = 1; r <= sheet.getLastRowNum() - 3; r++) {
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

    /**
     * 批量导出
     *
     * @param goodEntity
     * @return
     */
    public void batchExport(HttpServletResponse response, GoodEntity goodEntity, String[] header) throws IOException {
        List<GoodEntity> datas = goodEntityMapper.findByParams(goodEntity);
        List<List<String>> excelData = new ArrayList<>();
        SimpleDateFormat sdfDataTime = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfCreateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        List<String> title = new ArrayList<>();
        title.add(header[0]);
        title.add(header[1]);
        title.add(header[2]);
        title.add(header[3]);
        title.add(header[4]);
        title.add(header[5]);
        title.add(header[6]);
        title.add(header[7]);
        title.add(header[8]);
        title.add(header[9]);
        title.add(header[10]);
        title.add(header[11]);
        title.add(header[12]);
        title.add(header[13]);
        title.add(header[14]);
        title.add(header[15]);
        title.add(header[16]);
        title.add(header[17]);
        title.add(header[18]);
        title.add(header[19]);
        title.add(header[20]);
        excelData.add(title);
        for (GoodEntity data : datas) {
            List<String> data2Row = new ArrayList<>();
            data2Row.add(data.getProductName());
            data2Row.add(data.getSellerSku());
            data2Row.add(data.getSkuId());
            data2Row.add(data.getUrl());
            data2Row.add(data.getSkuVisitors() + "");
            data2Row.add(data.getSkuPageviews() + "");
            data2Row.add(data.getVisitorValue() + "");
            data2Row.add(data.getBuyers() + "");
            data2Row.add(data.getOrders() + "");
            data2Row.add(data.getUnitsSold() + "");
            data2Row.add(data.getRevenue() + "");
            data2Row.add(data.getConversionRate() + "%");
            data2Row.add(data.getRevenuePerBuyer() + "");
            data2Row.add(data.getWishlistVisitor() + "");
            data2Row.add(data.getWishlists() + "");
            data2Row.add(data.getAddToCarVisitors() + "");
            data2Row.add(data.getAddToCarUnits() + "");
            data2Row.add(data.getStore());
            data2Row.add(data.getCountry());
            data2Row.add(sdfDataTime.format(data.getDatetime()));
            data2Row.add(sdfCreateTime.format(data.getCreateTime()));
            excelData.add(data2Row);
        }
        ExcelUtils.exportExcel(response, excelData, "无效数据", "InvalidDatas", 15);
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean batchImportTransaction(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<TransactionEntity> transactionList = new ArrayList<TransactionEntity>();
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
        TransactionEntity transactionEntity;

        Date createTime = new Date();

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            transactionEntity = new TransactionEntity();

            Date transaction_date = row.getCell(0) == null ? null : row.getCell(0).getDateCellValue();
            String transaction_type = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue();
            String fee_name = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue();
            String transaction_number = row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue();
            String details = row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue();
            String seller_sku = row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue();
            String lazada_sku = row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue();
            double amount = row.getCell(7) == null ? 0 : row.getCell(7).getNumericCellValue();
            double vat_in_amount = row.getCell(8) == null ? 0 : row.getCell(8).getNumericCellValue();
            double wht_amount = row.getCell(9) == null ? 0 : row.getCell(9).getNumericCellValue();
            String wht_included_in_amount = row.getCell(10) == null ? "" : row.getCell(10).getStringCellValue();
            String statement = row.getCell(11) == null ? "" : row.getCell(11).getStringCellValue();
            String paid_status = row.getCell(12) == null ? "" : row.getCell(12).getStringCellValue();
            String order_no = new BigDecimal(row.getCell(13) == null ? 0 : row.getCell(13).getNumericCellValue()).toString();
            System.out.println("order_no:" + order_no);
            String order_item_no = new BigDecimal(row.getCell(14) == null ? 0 : row.getCell(14).getNumericCellValue()).toString();
            String order_item_status = row.getCell(15) == null ? "" : row.getCell(15).getStringCellValue();
            String shipping_provider = row.getCell(16) == null ? "" : row.getCell(16).getStringCellValue();
            String shipping_speed = row.getCell(17) == null ? "" : row.getCell(17).getStringCellValue();
            String shipment_type = row.getCell(18) == null ? "" : row.getCell(18).getStringCellValue();
            String reference = new BigDecimal(row.getCell(19) == null ? 0 : row.getCell(19).getNumericCellValue()).toString();
            String comment = row.getCell(20) == null ? "" : row.getCell(20).getStringCellValue();
            String paymenttrfid = row.getCell(21) == null ? "" : row.getCell(21).getStringCellValue();

            transactionEntity.setTransactionDate(transaction_date);
            transactionEntity.setTransactionType(transaction_type);
            transactionEntity.setFeeName(fee_name);
            transactionEntity.setTransactionNumber(transaction_number);
            transactionEntity.setDetails(details);
            transactionEntity.setSellerSku(seller_sku);
            transactionEntity.setLazadaSku(lazada_sku);
            transactionEntity.setAmount(amount);
            transactionEntity.setVatInAmount(vat_in_amount);
            transactionEntity.setWhtAmount(wht_amount);
            transactionEntity.setWhtIncludedInAmount(wht_included_in_amount);
            transactionEntity.setStatement(statement);
            transactionEntity.setPaidStatus(paid_status);
            transactionEntity.setOrderNo(order_no);
            transactionEntity.setOrderItemNo(order_item_no);
            transactionEntity.setOrderItemStatus(order_item_status);
            transactionEntity.setShippingProvider(shipping_provider);
            transactionEntity.setShippingSpeed(shipping_speed);
            transactionEntity.setShipmentType(shipment_type);
            transactionEntity.setReference(reference);
            transactionEntity.setComment(comment);
            transactionEntity.setPaymentrefid(paymenttrfid);
            transactionEntity.setCreateTime(createTime);
            transactionList.add(transactionEntity);
        }
        for (TransactionEntity entity : transactionList) {
            int cnt = transactionEntityMapper.findTransactionByOrderNoAndDataTime(entity);
            System.out.println("orderNo:" + entity.getOrderNo() + "," + cnt);
            if (cnt == 0) {
                transactionEntityMapper.insert(entity);
            } else {
                transactionEntityMapper.updateTransactionByOrderNoAndDataTime(entity);
            }
        }
        return notNull;
    }
}
