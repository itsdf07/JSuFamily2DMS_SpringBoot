/**
 * 调试接口:根据某个数据获取一个对象
 * @param username
 */
function getDemoEntityByUsername(username) {
    console.log("调试接口:根据某个数据获取一个对象,username=" + username);
    var data = [];
    var user1 = new Object();
    user1.name = "aso";
    user1.age = 30;
    user1.sex = "男";
    data.push(user1);

    var user2 = new Object();
    user2.name = "abin";
    user2.age = 30;
    user2.sex = "女";
    data.push(user2);
    var json2Data = JSON.stringify(data);
    console.log("json2Data:" + json2Data);

    $.ajax({
        type: "post",
        url: "/demo/getDemoEntity",
        dataType: "json",
        data: {
            username: "itsdf07",
            data: json2Data
        },
        // async: false,
        success: function (data) {
            console.log("msg:" + data.message);
            console.log("code:" + data.code);
            for (var i = 0; i < data.data.length; i++) {
                console.log("第" + (i + 1) + "个数据,username:" + data.data[i].dUsername);
            }
        },
        error: function () {
            console.log("error:");
        }
    });
}