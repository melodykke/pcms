function initPage(announcementId) {
    $.ajax({
        url: 'announcement/getannouncement?announcementId='+announcementId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var announcement = data.data;
            // 页面赋值
            $("[name=noticeTitle]").text(announcement.title);
            $("[name=noticeTime]").text(announcement.updateTime);
            $("[name=noticeContent]").html(announcement.content);
        }
    })
}
$(function () {
    initPage(announcementId);
})