$(function () {
    var contentDiv = $('#main_content');
    var getNotificationsUrl = "/notification/getallunchecked"

    getNotifications(getNotificationsUrl)

    function getNotifications(url) {
        $('#notification_body').html("")
        var tempHtml = '';
        $.getJSON(url, function (data) {
            console.log(data)
            var notifications = data.data;
            notifications.notificationVOs.map(function (item, index) {
                item.object.map(function (itemobject, index) {
                    var faIcon = itemobject.checked ? '<i class="fa fa-envelope-open-o" aria-hidden="true"></i>' : '<i class="fa fa-envelope"></i>';
                    tempHtml += '<tr class="unread">\n' +
                        '                        <td class="check-mail">\n' +
                        '                              ' + faIcon + '\n' +
                        '                        </td>\n' +
                        '                        <td class="mail-ontact"><a href="mail_detail.html">' + itemobject.submitter + '</a></td>\n' +
                        '                        <td class="mail-subject"><a data-url="toapprove" data-id="'+ itemobject.notificationId +'" data-href="' + itemobject.url + '?projectMonthlyReportId=' + itemobject.typeId + '">' + itemobject.type + '(' + itemobject.yearmonth + ')' + '</a></td>\n' +
                        '                        <td class=""><i class="fa fa-paperclip"></i></td>\n' +
                        '                        <td class="text-right mail-date">' + itemobject.createTime + '</td>\n' +
                        '                    </tr>';

                })
            })

            $('#notification_body').append(tempHtml);
        })
    }


    $('#notification_body').on('click', 'a', function (e) {
        var target = e.currentTarget;
        var url = "notification/changetochecked?notificationId=" + target.dataset.id;
        if ("toapprove" == target.dataset.url) {
            contentDiv.load(target.dataset.href);
            $.getJSON(url, function (data) {
                
            })
        }
        
    })

})