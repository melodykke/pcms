$(function () {
    // 提交个人信息
    var modifyPasswordUrl = '/account/modifypassword';
    // 用户个人信息页面判断是否已经提交过，如果已经提交则显示
    var addSubAccountUrl = '/user/xxxx';

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
                required:6,
                minlength:6,
                maxlength:12
            }
        },
        submitHandler:function(form) {
            if (confirm("若确认无误，请继续提交!")) {
                var accountVO = {};  // 空对象
                accountVO.oriPassword = $('#ori_password').val();
                accountVO.newPassword = $('#new_password').val();
                accountVO.reNewPassword = $('#re_new_password').val();
                $.ajax({
                    url: modifyPasswordUrl,
                    type: 'POST',
                    data: JSON.stringify(accountVO),
                    contentType: 'application/json',
                    beforeSend: function () {

                    },
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
});

