$(function () {
    // 提交个人信息
    var modifyPasswordUrl = '/account/modifypassword';
    // 用户个人信息页面判断是否已经提交过，如果已经提交则显示
    var addSubAccountUrl = '/account/addsubaccount';
    var getSubAccountInfoUrl = '/account/getsubaccountinfo';

    var thisUserId;
    var active;
    getSubAccountInfo();// 获取子账号信息

    function getModal(modal_title, sub_title, modal_content) {
        var modalTemplateHtml = '<div class="modal inmodal" id="myModal2" tabindex="-1" role="dialog" aria-hidden="true">\n' +
            '                                <div class="modal-dialog">\n' +
            '                                <div class="modal-content animated flipInY">\n' +
            '                                <div class="modal-header">\n' +
            '                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>\n' +
            '                            <h4 class="modal-title" id="modal_title">'+ modal_title + '</h4>\n' +
            '                            <small class="font-bold" id="sub_title">'+ sub_title +'</small>\n' +
            '                            </div>\n' +
            '                            <div class="modal-body" id="modal_content">\n' +
            '                             \n' + modal_content +
            '                            </div>\n' +
            '                            <div class="modal-footer">\n' +
            '                                <button type="button" class="btn btn-primary" data-dismiss="modal"> 确定 </button>\n' +
            '                            </div>\n' +
            '                            </div>\n' +
            '                            </div>\n' +
            '                            </div>';
        return modalTemplateHtml;
    }
    var validator = $("#modify_password_form").validate({
        rules : {
            ori_password : {
                required:true,
                minlength:2,
                maxlength:12
            },
            new_password : {
                required:true,
                minlength:6,
                maxlength:12
            },
            re_new_password : {
                required:true,
                minlength:6,
                maxlength:12
            }
        },
        submitHandler:function(form) {
            if (confirm("若确认无误，请继续提交!")) {
                var accountPasswordVO = {};  // 空对象
                accountPasswordVO.oriPassword = $('#ori_password').val();
                accountPasswordVO.newPassword = $('#new_password').val();
                accountPasswordVO.reNewPassword = $('#re_new_password').val();
                $.ajax({
                    url: modifyPasswordUrl,
                    type: 'POST',
                    data: JSON.stringify(accountPasswordVO),
                    contentType: 'application/json',
                    success: function (data) {
                        if (data.code == 1000) {
                            var modal_title = '修改成功！';
                            var sub_title = '密码修改成功，请牢记！';
                            var modal_content = '<p>  新的密码为： ' + data.msg + '</p>';
                            $('#mymodal').html(getModal(modal_title, sub_title, modal_content));
                            $('#myModal2').modal('show');
                            $('#modify_password_form')[0].reset();
                        } else if (data.code == 1201) {
                            var modal_title = '出错！';
                            var sub_title = '密码修改出错！';
                            var modal_content = '<p> 错误信息： ' + data.msg + '</p>';
                            $('#mymodal').html(getModal(modal_title, sub_title, modal_content));
                            $('#myModal2').modal('show');
                            $('#modify_password_form')[0].reset();
                        }
                    }
                });
            }
        }
    });


    var validator = $("#sub_account_form").validate({
        rules : {
            username : {
                required:true,
                minlength:4,
                maxlength:12
            },
            password : {
                required:true,
                minlength:6,
                maxlength:12
            },
            re_password : {
                required:true,
                minlength:6,
                maxlength:12
            }
        },
        submitHandler:function(form) {
            if (confirm("若确认无误，请继续提交!")) {
                var accountVO = {};  // 空对象
                accountVO.username = $('#sub_username').val();
                accountVO.password = $('#password').val();
                accountVO.rePassword = $('#re_password').val();
                $.ajax({
                    url: addSubAccountUrl,
                    type: 'POST',
                    data: JSON.stringify(accountVO),
                    contentType: 'application/json',
                    success: function (data) {
                        if (data.code == 1002) {
                            var modal_title = '成功！';
                            var sub_title = '子账号添加成功！';
                            var modal_content = '<p>  账号名： ' + data.msg + '</p>';
                            $('#mymodal').html(getModal(modal_title, sub_title, modal_content));
                            $('#myModal2').modal('show');
                            $('#sub_account_form')[0].reset();

                        } else if (data.code == 1202) {
                            var modal_title = '出错！';
                            var sub_title = '添加子账号出错！';
                            var modal_content = '<p> 错误信息： ' + data.msg + '</p>';
                            $('#mymodal').html(getModal(modal_title, sub_title, modal_content));
                            $('#myModal2').modal('show');
                            $('#sub_account_form')[0].reset();
                        }
                    }
                });
            }
        }
    });

    function getSubAccountInfo() {
        $.ajax({
            url: getSubAccountInfoUrl,
            type: 'GET',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == 1002) {
                    active = data.data.active;
                    if (active) {
                        var stateHtml = '已激活';
                        var activeBtnHtml = '<button id="inactivate_btn" class="btn btn-primary dim" type="button"><i class="fa fa-check"></i></button>';
                        var templateHtml = '<p>'+ activeBtnHtml +'<span class="badge badge-primary">'+ stateHtml +'</span></p> <h5> 已配置的子账号：'+ data.data.username +'</h5>\n';
                        $('#sub_account_content').html(templateHtml)

                    } else {
                        var stateHtml = '未激活';
                        var activeBtnHtml = '<button id="activate_btn" class="btn btn-warning dim" type="button"><i class="fa fa-warning"></i></button>';
                        var templateHtml = '<p>'+ activeBtnHtml +'<span class="badge badge-primary">'+ stateHtml +'</span></p> <h5> 已配置的子账号：'+ data.data.username +'</h5>\n';
                        $('#sub_account_content').html(templateHtml)
                    }
                }
            }
        });
    };
    $('#sub_account_content').on('click','button', function (e) {
        $.ajax({
            url: 'account/activate',
            type: 'POST',
            data: JSON.stringify({"active": active}),
            contentType: 'application/json',
            success: function () {
                getSubAccountInfo();
            }
        })
    });

});

