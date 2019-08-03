/**
 * 登录
 */
function onLogin() {
    var user = new Object();
    user.username = $("#username").val();
    user.passwd = $("#passwd").val();
    var user2Json = JSON.stringify(user);
    //user2Login:{"username":"itsdf07","passwd":"123456"}
    console.log("user2Login:" + user2Json);

    $.ajax({
        type: "post",
        url: "../user/user2Login",
        dataType: "json",
        data: {
            data:user2Json
        },
        // async: false,
        success: function (data) {
            console.log("data-code:" + data.code + ",message:" + data.message);
            if (data.code == 200) {
                window.location.href = "mainHtml";
            } else {
                alert("登录失败," + data.message + "!");
            }
        },
        error: function () {
            alert("请求失败！");
        }
    });
}