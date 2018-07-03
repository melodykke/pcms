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

});

