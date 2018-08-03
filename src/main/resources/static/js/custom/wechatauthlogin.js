$(function () {
    var openId = getQueryString('openId');
   $('#wechat_auth_login_btn').on('click', function (e) {
       $.ajax({
           url: "/wechatlogin/wechatauthlogin",
           type: "POST",
           contentType: "application/json",
           dataType: "json",
           data: JSON.stringify({"openId":openId}),
           success: function (data) {
               if (data.code == "1002") {
                   window.location.href = "/index";
               } else {
                    $('#msg').html(data.msg)
               }
           }
       })
   })
});