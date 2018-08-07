// Examle data for jqGrid
var gridData = [
    { id: "1", title: "标题1", type: "公告类型1", keyword: "关键字1", hot: "否", updateTime: "2018-08-02 13:00", content: "xxx1" },
    { id: "2", title: "标题2", type: "公告类型2", keyword: "关键字2", hot: "否", updateTime: "2018-08-02 13:00", content: "xxx2" },
    { id: "3", title: "标题3", type: "6", keyword: "关键字3", hot: "否", updateTime: "2018-08-02 13:00", content: "xxx3" },
    { id: "4", title: "标题4", type: "公告类型4", keyword: "关键字4", hot: "否", updateTime: "2018-08-02 13:00", content: "xxx4" },
    { id: "5", title: "标题5", type: "公告类型5", keyword: "关键字5", hot: "否", updateTime: "2018-08-02 13:00", content: "xxx5" },
    { id: "6", title: "标题6", type: "公告类型6", keyword: "关键字6", hot: "是", updateTime: "2018-08-02 13:00", content: "xxx6" },
];
function initGrid(url, type, param) {
    // Configuration for jqGrid Example 1
    $(".self-grid").html('<table id="reservoir_table_list"></table><div id="reservoir_pager_list"></div>');
    var grid = $("#reservoir_table_list").jqGrid({
        url: url,
        datatype: "json",
        mtype : type,
        postData: param || {},
        height: 350,
        autowidth: true,
        shrinkToFit: true,
        // 多选列
        multiselect: true,
        multiselectWidth: 30,
        // 序号列
        rownumbers: true,
        colNames: ['标题', '公告类型', '关键字', '置顶', '发布时间'],
        colModel: [
            { name: 'title', index: 'title', width: 50, align:"center", sorttype: false },
            { name: 'type', index: 'type', width: 50, align: "center", sorttype: false },
            { name: 'keyword', index: 'keyword', width: 50, align: "center", sorttype: false },
            { name: 'hot', index: 'hot', width: 50, align: "center", sorttype: false },
            { name: 'updateTime', index: 'updateTime', width: 100, align:"center", sortable: false}
        ],
        pager: "#reservoir_pager_list",
        jsonReader:{//分页的关键
            id: "0",
            root: "content",total: "totalPages",page: "pageIndex",records: "totalElements",repeatitems: false
        },
        viewrecords: true,
        rowNum: 20,
        page: 1,
        rowList: [20, 40, 60],
        loadComplete: function (data) {
            gridData = data.content;

        }
    });

}

function getSelectedRowData() {
    var indexArr = $('#reservoir_table_list').jqGrid('getGridParam', 'selarrrow');
    var rowData;
    if (indexArr.length > 1) {
        alert("不能同时选择多条公告，请重新选择！");
        rowData = false;
    } else if (indexArr.length === 0) {
        alert("请选择公告！");
        rowData = false;
    } else {
        // rowData = $('#reservoir_table_list').jqGrid("getRowData", indexArr[0]);
        rowData = gridData[parseInt(indexArr[0]) - 1];
    }
    return rowData;
}
// 参数编译进url
var parseParam = function (paramObj, key) {
    var paramStr = "";
    if (paramObj instanceof String || paramObj instanceof Number || paramObj instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(paramObj);
    } else {
        $.each(paramObj, function (i) {
            var k = key == null ? i : key + (paramObj instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + parseParam(this, k);
        });
    }
    return paramStr.substr(1);
};
/**
 *    grid操作
 */
// 发布
function toPublishNotice() {
    $('#content').load('announcement/toannouncement');
}
// 修改
function modifyNotice() {
    var rowData = getSelectedRowData();
    if (rowData) {
        $('#content').load('/announcement/toannouncement?announcementId=' + rowData.announcementId);
    }
}
// 删除
function deleteNotice() {
    var rowData = getSelectedRowData();
    // 重置Grid
    requestGridData("", 'GET', rowData);
}
// 刷新
function refreshNotice() {
    // 重置Grid
    requestGridData("", 'GET');
}
// 搜索
function searchNotice() {
    var condition = $("#search_condition").val();
    if (!condition.trim()) {
        alert("请输入条件!");
        return false;
    }
    // 重置Grid
    requestGridData("", 'GET', { title: condition });
}
// 请求服务
function requestGridData(url, ajaxType, requestData) {
    initGrid(url, ajaxType, requestData);
}
$(function () {
    // 初始化表格
    requestGridData("announcement/getannouncements", 'GET');
});