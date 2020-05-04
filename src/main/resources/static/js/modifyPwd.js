//找回密码
function changePwd() {
    let pwd = $.trim($("#pwd").val());
    let confirmPwd = $.trim($("#confirmPwd").val());

    if (pwd !== confirmPwd) {
        alert("密码不一致，请重新输入");
        $("#confirmPwd").attr("value", "");
        return;
    }
    $.ajax({
        url: "/modifyPwd",
        type: "POST",
        data: {
            "password": pwd
        },
        dataType: "json",
        success: function (response) {
            if (response.code == 200) {
                alert("密码修改成功！");
                window.open("/", "_self");
            } else {
                alert(response.message);
            }
        }
    })
}

