$(function () {

    var getThisUserUrl = "/user/getthisuser"; //拿到当前用户信息的url
    var getThisProjectUrl = "/user/getthisproject";
    var getAllUnreadUrl = "/notification/getallunchecked";
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
    $('#notification_entrance').on('click', 'a', function (e) {
        var target = e.currentTarget;
        if ('monthly_report_notification' == target.dataset.id){
            contentDiv.load('notification/tonotification');
        }
    })
    $('#operation_log').click(function () {
        contentDiv.load('operationlog/tooperationlog');
    })


    getThisUser(getThisUserUrl);
    getThisProject(getThisProjectUrl);
    getAllUnread(getAllUnreadUrl);
    setInterval(function () {
        getAllUnread(getAllUnreadUrl)
    }, 30000);


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

    // 拿到所有未审批的项目
    function getAllUnread(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
                var notificationVOs = data.data;
                $('#countUnread').text(notificationVOs.countAllUnread);
                var htmlTemp = "";
                $('#notification_entrance').html('')
                notificationVOs.notificationVOs.map(function (item, index) {
                    htmlTemp += ' <li>\n' +
                        '                                <a href="#" data-id="'+ item.url +'">\n' +
                        '                                    <div>\n' +
                        '                                        <i class="fa fa-envelope fa-fw"></i> 新的'+ item.type +'信息待审批\n' +
                        '                                        <span class="pull-right text-muted small">'+ item.timeDiff +'</span>\n' +
                        '                                    </div>\n' +
                        '                                </a>\n' +
                        '                            </li>\n' +
                        '                            <li class="divider"></li>'
                    
                })
                $('#notification_entrance').append(htmlTemp)
            }
        });
    }
});

