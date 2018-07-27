$(function () {
    var openId = getQueryString('openId');

    $('#binding_confirm_btn').on('click', function (e) {
        var username = $('#username').val();
        var password = $('#password').val();
        $.ajax({
            url: "/wechatlogin/wechatbinding",
            type: "POST",
            data: JSON.stringify({"openId": openId, "username": username, "password": password}),
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data.code == 1003) {
                    $('#msg').html(data.data);
                } else if (data.code == 1002) {
                    alert("绑定成功！")
                    window.location.href = "/login";
                }
            }
        })
    })

});