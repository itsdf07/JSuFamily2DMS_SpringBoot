
function onExportExcel() {
    var goodFilter = new Object();
    goodFilter.productName=$("#product_name").val();
    goodFilter.sellerSku=$("#seller_sku").val();
    goodFilter.skuId=$("#sku_id").val();
    goodFilter.url=$("#url").val();
    goodFilter.skuVisitorsurl=$("#sku_visitors").val();
    goodFilter.skuPageviews=$("#sku_pageviews").val();
    goodFilter.visitorValue=$("#visitor_value").val();
    goodFilter.buyers=$("#buyers").val();
    goodFilter.orders=$("#orders").val();
    goodFilter.unitsSold=$("#units_sold").val();
    goodFilter.revenue=$("#revenue").val();
    goodFilter.conversionRate=$("#conversion_rate").val();
    goodFilter.revenuePerBuyer=$("#revenue_per_buyer").val();
    goodFilter.wishlistVisitor=$("#wishlist_visitor").val();
    goodFilter.wishlists=$("#wishlists").val();
    goodFilter.addToCarVisitors=$("#add_to_car_visitors").val();
    goodFilter.addToCarUnits=$("#add_to_car_units").val();
    goodFilter.store=$("#store").val();
    goodFilter.country=$("#country").val();
    var json_str = JSON.stringify(goodFilter);
    console.log("json_str:" + json_str);
    $.ajax({
        type: "post",
        url: "../excel/exportGoods",
        dataType: "json",
        data: {
            data: json_str
        },
        // async: false,
        success: function (data) {
            alert("data---------------");
            console.log("data:" + data);

        },
        error: function () {
            alert("error33333:");
        }
    });
}


// function onExportExcelForm() {
//     //定义一个form表单,通过form表单来发送请求
//     var form=$("<form>");
//     //设置表单状态为不显示
//     form.attr("style","display:none");
//     //method属性设置请求类型为post
//     form.attr("method","post");
//     /*
//      * action属性设置请求路径,
//      *  请求类型是post时,路径后面跟参数的方式不可用
//      *  可以通过表单中的input来传递参数
//      */
//     form.attr("action","/excel/exportGoods");
//     $("body").append(form);//将表单放置在web中
//     //在表单中添加input标签来传递参数
//     //如有多个参数可添加多个input标签
//     var input=$("<input>");
//     input.attr("type","hidden");//设置为隐藏域
//     input.attr("name","index");//设置参数名称
//     input.attr("value",$("#id_menuIndex").val());//设置参数值
//     form.append(input);//添加到表单中
//     form.submit();//表单提交
//
// }
