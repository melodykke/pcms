$(function () {
    // 提交个人信息
    var savePersonInfoUrl = '/user/personinfosubmit';
    // 用户个人信息页面判断是否已经提交过，如果已经提交则显示
    var hasPersonInfoSubmittedUrl = '/user/doesthisuserhaspersoninfo';
    var getAccountInfoUrl = '/user/getaccountinfo';
    showPersonInfo();
    getAccountInfo();
    // 获取系统账号信息
    function getAccountInfo() {
        $.getJSON(getAccountInfoUrl, function (data) {
            var accountInfo = data.data;
            var username = accountInfo.username;
            var accountType = accountInfo.accountType;
            var active = '';
            if (accountInfo.active == 1) {
                active = "已激活"
            } else {
                active = "未激活"
            }
            var acountInfoHtml = '<div>\n' +
                '                                        <label>系统账户名：</label>\n' +
                '                                        <span>'+ username +'</span>\n' +
                '                                    </div>\n' +
                '                                    <div>\n' +
                '                                        <label>账号类型：</label>\n' +
                '                                        <span>'+ accountType +'</span>\n' +
                '                                    </div>\n' +
                '                                    <div>\n' +
                '                                        <label>状态：</label>\n' +
                '                                        <span>'+ active +'</span>\n' +
                '                                    </div>';
            $('#accountInfoDiv').html(acountInfoHtml)
        })
    }

    // 显示用户个人信息
    function showPersonInfo() {
        $.getJSON(hasPersonInfoSubmittedUrl, function (data) {
            if (data.code) {
                if (data.data.profileImg === null || data.data.profileImg == "") {
                    $('#detail_profile_img').attr("src", "img/portait_logo.png")
                } else {
                    $('#detail_profile_img').attr("src", data.data.profileImg)
                }
                $('#nickname').html(data.data.nickName);
                $('#country').html(data.data.country);
                $('#province').html(data.data.province);
                $('#city').html(data.data.city);

                $('#person_name').val(data.data.name).attr('readonly', true);
                $('#tel').val(data.data.tel).attr('readonly', true);
                $('#qq').val(data.data.qq).attr('readonly', true);
                $('#email').val(data.data.email).attr('readonly', true);
                $('#id_num').val(data.data.id_num).attr('readonly', true);
                $('#title').val(data.data.title).attr('readonly', true);
                $('#address').val(data.data.address).attr('readonly', true);
                $('#person_info_submit').hide();
            }
        })
    }

    $('#person_detail_modify').on('click', function (e) {
        $('#person_name').attr('readonly', false);
        $('#tel').attr('readonly', false);
        $('#qq').attr('readonly', false);
        $('#email').attr('readonly', false);
        $('#id_num').attr('readonly', false);
        $('#title').attr('readonly', false);
        $('#address').attr('readonly', false);
        $('#person_info_submit').show();
    })

    $('#person_info_submit').on('click', function (e) {
        var personInfoVO = {};
        personInfoVO.name = $('#person_name').val();
        personInfoVO.tel = $('#tel').val();
        personInfoVO.qq = $('#qq').val();
        personInfoVO.email = $('#email').val();
        personInfoVO.id_num = $('#id_num').val();
        personInfoVO.title = $('#title').val();
        personInfoVO.address = $('#address').val();
        $.ajax({
            url: "user/personinfosubmit",
            type: "POST",
            data: JSON.stringify(personInfoVO),
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                if (data.code == 1002) {
                    swal({
                        title: "SUCCESS！",
                        text: "修改成功！",
                        type: "success",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定!",
                        closeOnConfirm: true
                    }, function () {
                        $('#person_name').attr('readonly', true);
                        $('#tel').attr('readonly', true);
                        $('#qq').attr('readonly', true);
                        $('#email').attr('readonly', true);
                        $('#id_num').attr('readonly', true);
                        $('#title').attr('readonly', true);
                        $('#address').attr('readonly', true);
                        $('#person_info_submit').hide();
                    })

                } else {
                    swal({
                        title: "敬告",
                        text: data.msg,
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定!",
                        closeOnConfirm: false
                    })
                }
            }
        })

    })
});

