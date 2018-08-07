$(function () {

    // 获取账号信息
    function getAccountData() {
        var username = $("#account_name").val(),
            name = $("#reservoir_name").val();
        return {username: username, name: name};
    }

    // 创建账号
    function createAccount() {
        var param = getAccountData();
        $.ajax({
            url: '/account/createaccount',
            type: 'POST',
            data: JSON.stringify(param),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                if (data.code == 1002) {
                    swal({
                        title: "成功！",
                        text: "用户账号开通成功！",
                        type: "success",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        closeOnConfirm: true
                    }, function () {
                        $('#content').load('account/toaccountmanagement');
                    });
                } else {
                    swal({
                        title: "提示",
                        text: data.msg,
                        type: "error",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确认",
                        closeOnConfirm: true
                    });
                }
            },
            error: function (err) {
                alert(text + "失败！");
            }
        });
    }

    $('#create_account_btn').on('click', function (e) {
        createAccount();
    })
});