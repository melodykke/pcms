var pagerOp = {     // 分页参数
    page: 1,
    rows: 5,
    total: 1
}
var timer = 0;  // 动画事件标识
// 向前翻页
function noticePre() {
    var prePage = pagerOp.page - 1;
    // 判断是否可以向上翻页
    if (prePage > 0 && timer === 0) {
        pagerOp.page = prePage;
        requestNotice(pagerOp);
    }
}
// 向后翻页
function noticeNext() {
    var nextPage = pagerOp.page + 1;
    // 判断是否可以向上翻页
    if (nextPage <= pagerOp.total && timer === 0) {
        pagerOp.page = nextPage;
        requestNotice(pagerOp);
    }
}
// 请求服务
function requestNotice(requestData) {
    $.ajax({
        url: 'announcement/getannouncements',
        type: 'GET',
        dataType: 'json',
        data: requestData || {},
        success: function (data) {
            var noticeData = data.list;
            // 分页赋值
            pagerOp.page = data.number+1;
            pagerOp.total = data.totalPages;
            $(".notice-pager>.current-page").text(data.number+1);
            $(".notice-pager>.total-pages").text(data.totalPages);
            // 公告赋值
            setNotic(noticeData);
            // 是否显示分页
            if (data.totalPages > 1) {
                $(".notice-pager").show();
            }
        },
        error: function (err) {
            console.log(err);
        }
    })
}
function setNotic(data) {
    // 淡出动画效果
    $(".list-group.notice-list").children().each(function (i, dom) {
        setTimeout(function () {
            $(dom).removeClass("fadeInRight").addClass("fadeOutRight");
        }, timer);
        timer += 200;
    });
    // 清空淡入
    setTimeout(function () {
        // 清空
        $(".notice-list").html("");
        timer = 1;
        // 公告赋值
        $.each(data, function (i, n) {
            setTimeout(function () {
                var noticeStr = [
                    '<li class="list-group-item animated fadeInRight"><a class="notice-page" announcement-id=' + n.announcementId + '>' + n.title + '</a><div>',
                    '<div class="public-depart"><span>' + n.keyword + '</span></div>',
                    '<div class="public-time"><i class="fa fa-clock-o"></i><span>' + n.updateTime + '</span></div></div></li>'
                ]
                $(".notice-list").append(noticeStr.join(""));
                // 判断动画结束，清空事件标识
                if (i === data.length - 1) {
                    timer = 0;
                }
            }, timer);
            timer += 200;
        });
    }, timer);
}
$(function () {
    requestNotice(pagerOp);
    $(".list-group").on("click", 'li>.notice-page', function () {
        var announcementId = $(this).attr("announcement-id");
        $("#main_content").load("announcement/toannouncementshow?announcementId="+announcementId)
    });

    /*滚播公告*/
    $.getJSON("announcement/gethotlatests", function (data) {
        var announcementItems = data.data.content;
        var announcementHtml = '';
        announcementItems.map(function (item, index) {
            var itemHtml = '  <li>\n' +
                '                        <a announcement-id="'+ item.announcementId +'">'+ item.title +'</a>\n' +
                '                    </li>'
            announcementHtml += itemHtml;
        });
        $('#loopNotice').html(announcementHtml)
    });

    $('#loopNotice').on('click', 'a', function (e) {
        var announcementId = $(this).attr("announcement-id");
        if ($("#main_content").length > 0) {
            $("#main_content").load("announcement/toannouncementshow?announcementId="+announcementId)
        } else {
            $("#content").load("announcement/toannouncementshow?announcementId="+announcementId)
        }

    })
});

