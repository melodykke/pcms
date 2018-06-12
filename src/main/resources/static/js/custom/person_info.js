$(function () {
    // 提交个人信息
    var savePersonInfoUrl = '/user/personinfosubmit';
    // 用户个人信息页面判断是否已经提交过，如果已经提交则显示
    var hasPersonInfoSubmittedUrl = '/user/doesthisuserhaspersoninfo';
    showPersonInfo();



    // 显示用户个人信息
    function showPersonInfo() {
        $.getJSON(hasPersonInfoSubmittedUrl, function (data) {
            if (data.code) {
                $('#person_name').val(data.data.name).attr('readonly', true);
                $('#tel').val(data.data.tel).attr('readonly', true);
                $('#qq').val(data.data.qq).attr('readonly', true);
                $('#email').val(data.data.email).attr('readonly', true);
                $('#id_num').val(data.data.id_num).attr('readonly', true);
                $('#title').val(data.data.title).attr('readonly', true);
                $('#address').val(data.data.address).attr('readonly', true);
                $('#person_info_ops_btns').remove();
            }
        })
    }


    var validator = $("#person_info_form").validate({
        rules : {
            name : {
                required:true,
                minlength:2,
                maxlength:64
            },
            tel : {
                required:true,
                minlength:2,
                maxlength:64
            },
            qq : {
                required:true,
                digits:true
            },
            email : {
                required:true,
                email: true
            },
            id_num : {
                required:true,
                minlength:18,
                maxlength:18
            },
            title : {
                required:true,
                minlength:2,
                maxlength:64
            },
            address : {
                required:true,
                minlength:10,
                maxlength:64
            }
        },
        submitHandler:function(form) {
            if (confirm("若以上信息确认无误，请确认提交!")) {
                var formData = new FormData();
                var personInfo = {};  // 空对象
                personInfo.name = $('#person_name').val();
                personInfo.tel = $('#tel').val();
                personInfo.qq = $('#qq').val();
                personInfo.email = $('#email').val();
                personInfo.id_num = $('#id_num').val();
                personInfo.title = $('#title').val();
                personInfo.address = $('#address').val();
                formData.append('personInfoStr', JSON.stringify(personInfo));
                $.ajax({
                    url: savePersonInfoUrl,
                    type: 'POST',
                    data: formData,
                    contentType : false,
                    processData : false,
                    cache : false,
                    success: function (data) {
                        showPersonInfo();
                    }
                });
            }
        }
    });
});

