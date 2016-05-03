function register(grantedAuthority) {

    if (sessionStorage.getItem("accessToken") == null) {
        alert("请先登录");
        window.location.href = "normal_login.html";
    }

    var clientId = $("#email").val();
    var clientSecret = $("#password").val();
    var phone = $('#phone').val();
    var repassword = $('#repassword').val();

    var postData = {
        username: clientId,
        password: clientSecret
    };
    var requestUrl = server_host + "/pictureshare/updateAccount"; //要访问的api

    var errorMessage = "";
    var alias = '';
    $("#error_message").html(errorMessage);
    if (clientSecret == null || clientSecret == "") {

        errorMessage = "请输入密码";

        $("#error_message").html(errorMessage);
        return;
    }

    //var patt1 = new RegExp("^[0-9a-zA-Z_`~\\!@#\\$\\%\\^&\\*()\\-\\+=]{6,20}$");
    var patt1 = /^[0-9a-zA-Z_`~\\!@#\\$\\%\\^&\\*()\\-\\+=]{5,20}$/;
    if (!patt1.test(clientSecret)) {
        errorMessage = "密码格式不正确";

        $("#error_message").html(errorMessage);
        return;
    }


    if (clientSecret != repassword) {
        errorMessage = "两次输入的密码不一致";

        $("#error_message").html(errorMessage);
        return;
    }

    $.ajax({
        url: requestUrl,
        global: false,
        data: {
            password: clientSecret,
            phone: phone,
            accessToken: sessionStorage.getItem("accessToken")
        },
        success: function (msg) {
            if (msg.code == 'OK') {
                alert(msg.msg);
                window.location.href = "normal_login.html";
            } else {
                $("#error_message").html(msg.msg);
            }
        }
    });
}