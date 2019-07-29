
function refurbishIndex() {
    var data = [];
    var person1 = new Object();
    person1.name="itsdf0707";
    person1.age=18;
    person1.sex="男";
    data.push(person1);

    var person2 = new Object();
    person2.name="小丽";
    person2.age=20;
    person2.sex="女";
    data.push(person2);

    var json_str = JSON.stringify(data);
    console.log("json_str:" + json_str);
    $.ajax({
        type: "post",
        url: "/user/getAllUsers",
        dataType: "json",
        data: {
            username: json_str
        },
        // async: false,
        success: function (data) {
            alert("data---------------");
            console.log("data:" + data);
            alert("data:" + data.users.length);
            var name = '${users}';
            console.log("data:：" + name);

        },
        error: function () {
            alert("error33333:");
        }
    });
}