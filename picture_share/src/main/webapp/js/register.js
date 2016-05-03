function register(grantedAuthority) {
    var clientId = $("#email").val();
    var clientSecret = $("#password").val();
    var phone = $('#phone').val();
    var repassword = $('#repassword').val();

    var postData = {
        username: clientId,
        password: clientSecret
    };
    var requestUrl = server_host + "/pictureshare/register"; //要访问的api

    var errorMessage = "";
    var alias = '';
    $("#error_message").html(errorMessage);
    if (clientId == "" && clientSecret == "") {
        errorMessage = "请输入账号和密码";
        $("#error_message").html(errorMessage);
        return;
    }
    if (clientSecret == null || clientSecret == "") {

        errorMessage = "请输入密码";

        $("#error_message").html(errorMessage);
        return;
    }

    if (phone == "") {
        errorMessage = "请输入电话号码";
        $("#error_message").html(errorMessage);
        return;
    }

    if (clientId == null || clientId == "") {
        errorMessage = "请输入账号";

        $("#error_message").html(errorMessage);
        return;
    }


    //var patt2 = new RegExp("^[1][3,4,5,7,8][0-9]{9}$");
    var patt2 = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
    if (!patt2.test(clientId)) {
        errorMessage = "邮箱格式不正确";

        $("#error_message").html(errorMessage);
        return;
    } else {
        localStorage.setItem("clientId", clientId);
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
            email: clientId,
            password: clientSecret,
            phone: phone,
            grantedAuthority: grantedAuthority
        },
        success: function (msg) {
            if (msg.code == 'OK') {
                alert(msg.msg);
                if (grantedAuthority == "normal_user") {
                    window.location.href = 'normal_login.html';
                } else {
                    window.location.href = 'login.html';
                }
            } else {
                $("#error_message").html(msg.msg);
            }
        }
    });
}