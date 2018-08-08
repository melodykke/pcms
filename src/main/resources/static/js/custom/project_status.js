$(function () {

    $.ajax({
        url: "/index/getprojectstatus",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code == 1002) {
                console.log(data)
                var projectStatus = data.data;
                if (projectStatus.length > 0) {
                    $('#update_status_a').remove();
                }
                projectStatus.map(function (item, index) {

                    if (index == projectStatus.length - 1) {
                        $('#vertical-timeline').prepend(buildHtml(item.timeLineItem.type, item.timeLineItem.msg, item.timeLineItem.timeLineItemId))
                    } else {
                        $('#vertical-timeline').prepend(buildHtmlWithoutBtn(item.timeLineItem.type, item.timeLineItem.msg, item.timeLineItem.timeLineItemId))
                    }

                });
            } else if (data.code == 1003) {

            } else {
                swal({
                    title: "提示",
                    text: data.msg,
                    type: "warning",
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确认",
                    closeOnConfirm: true
                })
            }
        }
    });



    $('#vertical-timeline').on('click', '#update_status_a', function (e) {
        swal({
            title: "确认更下到下一阶段吗?",
            text: "项目状态节点的更新将不可逆，请慎重选择!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "已确认,提交!",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            showLoaderOnConfirm: true
        }, function () {
            var target = e.currentTarget;
            $.ajax({
                url: "/index/updateprojectstatus",
                type: "GET",
                data: {id: target.dataset.id},
                contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    console.log(data)
                    if (data.code == 1002) {
                        target.remove();
                        var nextTimeLineItem = data.data;
                        $('#vertical-timeline').prepend(buildHtml(nextTimeLineItem.type, nextTimeLineItem.msg, nextTimeLineItem.timeLineItemId))
                        swal({
                            title: "成功",
                            text: "项目状态更新成功！",
                            type: "success",
                        })
                    } else if (data.code == 403) {
                        
                        swal({
                            title: "未授权",
                            text: "您的此项操作未被授权，请联系上一级账号使用人员!",
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确认!",
                            closeOnConfirm: true
                        }, function () {
                            
                        })
                    } else {
                        swal({
                            title: "提示",
                            text: data.msg,
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确认!",
                            closeOnConfirm: true
                        })
                    }
                }
            });
        });
    });
    function buildHtml(type, msg, id) {
        if (id == 9) {
            return '                            <div class="vertical-timeline-block">\n' +
                '                                <div class="vertical-timeline-icon blue-bg">\n' +
                '                                    <i class="fa fa-file-text"></i>\n' +
                '                                </div>\n' +
                '                                <div class="vertical-timeline-content">\n' +
                '                                    <h2>'+ type +'</h2>\n' +
                '                                    <p>'+ msg +'</p>\n' +
                '                                    <a id="#" data-id="'+ id +'" href="#" class="btn btn-sm btn-success">项目已完结</a>\n' +
                '                                    <span class="vertical-date">\n' +
                '                                        <br/>\n' +
                '                                        <small> </small>\n' +
                '                                    </span>\n' +
                '                                </div>\n' +
                '                            </div>';
        } else {
            return '                            <div class="vertical-timeline-block">\n' +
                '                                <div class="vertical-timeline-icon blue-bg">\n' +
                '                                    <i class="fa fa-file-text"></i>\n' +
                '                                </div>\n' +
                '                                <div class="vertical-timeline-content">\n' +
                '                                    <h2>'+ type +'</h2>\n' +
                '                                    <p>'+ msg +'</p>\n' +
                '                                    <div><a id="update_status_a" data-id="'+ id +'" href="#" class="btn btn-sm btn-success">更新至下一阶段并保存</a></div>\n' +
                '                                    <span class="vertical-date">\n' +
                '                                        <br/>\n' +
                '                                        <small> </small>\n' +
                '                                    </span>\n' +
                '                                </div>\n' +
                '                            </div>';
        }
    }
    function buildHtmlWithoutBtn(type, msg, id) {
        if (id == 9) {
            return '                            <div class="vertical-timeline-block">\n' +
                '                                <div class="vertical-timeline-icon blue-bg">\n' +
                '                                    <i class="fa fa-file-text"></i>\n' +
                '                                </div>\n' +
                '                                <div class="vertical-timeline-content">\n' +
                '                                    <h2>'+ type +'</h2>\n' +
                '                                    <p>'+ msg +'</p>\n' +
                '                                    <div id="updateBtn">已完结</div>\n' +
                '                                    <span class="vertical-date">\n' +
                '                                        <br/>\n' +
                '                                        <small> </small>\n' +
                '                                    </span>\n' +
                '                                </div>\n' +
                '                            </div>';
        } else {
            return '                            <div class="vertical-timeline-block">\n' +
                '                                <div class="vertical-timeline-icon blue-bg">\n' +
                '                                    <i class="fa fa-file-text"></i>\n' +
                '                                </div>\n' +
                '                                <div class="vertical-timeline-content">\n' +
                '                                    <h2>'+ type +'</h2>\n' +
                '                                    <p>'+ msg +'</p>\n' +
                '                                       <div id="updateBtn"></div>\n' +
                '                                    <span class="vertical-date">\n' +
                '                                        <br/>\n' +
                '                                        <small> </small>\n' +
                '                                    </span>\n' +
                '                                </div>\n' +
                '                            </div>';
        }
    }
});