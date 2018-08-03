$(function () {

    $('#announcement_post').on('click', function (e) {
        swal({
            title: "提示",
            text: "确认发布吗?",
            type: "warning",
            showCancelButton: true,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "发布!",
            closeOnConfirm: false
        }, function () {

            var announcement = getNoticeData();
            console.log(announcement)
            $.ajax({
                url: "announcement/post",
                type: "POST",
                data: JSON.stringify(announcement),
                contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    console.log(data)
                }
            })
        });
    });


});
var type_value, type_text;
// 下拉框渲染
$('#notice_type').dropdown({    // 发布类型
    readOnly: true,
    input: '<input type="text" maxLength="20" placeholder="请输入搜索">',
    choice: function () {
        console.log(arguments, this);
        type_value = $(event.target).data('value');
        type_text = $(event.target).data('value');
    }
});

// 富文本编辑器渲染
var E = window.wangEditor
var editor = new E('#editor')
editor.create()

function openPreview() {
    openDialog(getNoticeData());
}
/**
 *     预览弹出框初步JS
 */
function openDialog(data) {
    // 弹出层
    $("#dialog").html('');
    var noticeTemp = [
        '<div class="notice-detail">',
        '<div class="notice-title">',
        '<div>' + data.title + '</div>',
        '<span aria-hidden="true" class="notice-close" onclick="closeDialog()">×</span>',
        '<div class="notice-triangle"></div>',
        '<div class="notice-under-line"></div>',
        '<div class="notice-time">',
        '<span>发布时间：</span>',
        '<span>' + data.time + '</span>',
        '</div></div>',
        '<div class="notice-content">' + data.content + '</div></div>'
    ]
    var dialogHtml = [
        // 弹出框内容区
        '<div class="modal-body">' + noticeTemp.join('') + '</div>'
    ]
    $("#dialog").html(dialogHtml.join(""));
    var height = $("#dialog").height(),
        width = $("#dialog").width();
    $("#dialog").addClass("dialog-position");
    $("#dialog").css({ "margin-top": -height / 2, "margin-left": -width / 2 });
    $("#dialog").removeClass("dialog-hide");
    // 遮罩层
    $("#mask").show();
}
// 关闭弹出框
function closeDialog() {
    $("#dialog").addClass("dialog-hide");
    $("#mask").hide();
}
function getNoticeData() {
    var date = new Date(),
        dateStr = date.toISOString(),
        hourStr = date.toTimeString();
    var time = dateStr.substring(0, dateStr.indexOf("T")) + " " + hourStr.substring(0, hourStr.lastIndexOf(":"));
    var noticeData = {
        type_value: $("#notice_type").find("select").val(),
        type: $("#notice_type").find("select").find("option:selected").text(),
        hot: $("[data-for=noticeTop]").is(":checked"),
        title: $("[data-for=noticeTitle]").val(),
        time: time,
        keyword: $("[data-for=noticeKeywords]").val(),
        content: editor.txt.html()
    }
    return noticeData;
}
function getRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var param = decodeURIComponent(url),
        urlParam = decodeURIComponent(param);
    var theRequest = new Object();
    if (urlParam.indexOf("?") != -1) {
        var str = urlParam.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
    } else {
        theRequest = false;
    }
    return theRequest;
}
function initThisPage() {
    var param = getRequest();
    if (param) {
        // 页面赋值
        // 下拉框赋值
        $("#notice_type").find("select").val(param.type);
        var type_value = $("#notice_type").find("select").find("[value='" + param.type + "']").text();
        $("#notice_type .dropdown-display .dropdown-selected").text(type_value);
        $("[data-for=noticeTop]").prop("checked", param.hot);
        $("[data-for=noticeTitle]").val(param.title);
        $("[data-for=noticeKeywords]").val(param.keyword);
        editor.txt.html(param.content);
    }
}

$(function () {
    initThisPage();
});