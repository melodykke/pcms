$(function () {

    var getThisUserUrl = "/user/getthisuser"; //拿到当前用户信息的url
    var getThisProjectUrl = "/user/getthisproject";
    var contentDiv = $('#main_content');
    $('#project_monthly_report').click(function () {
        contentDiv.load('reporter/projectmonthlyreport');
    })
    $('#project_months').click(function () {
        contentDiv.load('reporter/projectmonths');
    })
    $('#person_info').click(function () {
        contentDiv.load('user/personinfo');
    })
    $('#account_config').click(function () {
        contentDiv.load('account/accountconfig');
    })




    getThisUser(getThisUserUrl);
    getThisProject(getThisProjectUrl);


    function getThisUser(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
                // 从返回的JSON当中获取product对象的信息，并赋值给表单
                var userInfo = data.data;
                $('#username .font-bold').html(userInfo.name);
                $('#name').html(userInfo.username + ' <b class="caret"></b>');
            }
        });
    }

    // 若果有 存入域 否则提示完善资料
    function getThisProject(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
              // 若果有 存入域 否则提示完善资料
            }
        });
    }
});

