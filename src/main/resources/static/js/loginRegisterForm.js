function loginForm() {
    $(".email-login").delay(100).fadeIn(100);
    $(".email-signup").fadeOut(100);
    $("#login-box-link").addClass("active");
    $("#signup-box-link").removeClass("active");
}

function registerForm() {
    $(".email-login").fadeOut(100);
    $(".email-signup").delay(100).fadeIn(100);
    $("#login-box-link").removeClass("active");
    $("#signup-box-link").addClass("active");
}

function register() {
    let email = $.trim($("#email").val());
    let pwd = $.trim($("#pwd").val());

    if (email == null || email == "") {
        alert("邮箱号不能为空")
        return;
    }
    if (pwd === null || pwd === "") {
        alert("密码不能为空")
        return;
    }

    $.ajax({
        type: "POST",
        url: "/register",
        //stringify方法将js对象转换为字符串
        data: {
            "accountId": email,
            "password": pwd
        },
        dataType: "json",
        success: function (response) {
            if (response.code === 200) {
                alert("注册成功，快去登录吧！");
                window.open("/login", "_self");
            } else {
                alert(response.message)
            }
        },
    });
}

function login() {
    let email = $("#loginEmail").val();
    let pwd = $.trim($("#loginPwd").val());

    if (email == null || email.trim().length === 0) {
        alert("邮箱号不能为空")
        return;
    }
    if (pwd === null || pwd === "") {
        alert("密码不能为空")
        return;
    }

    $.ajax({
        type: "POST",
        url: "/login",
        data: {
            "accountId": email,
            "pwd": pwd
        },
        dataType: "json",
        success: function (response) {
            if (response.code === 200) {
                alert("登录成功！");
                window.open("/", "_self");
            } else {
                alert(response.message)
            }
        },
    });
}
