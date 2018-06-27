$(function () {
    var getOperationLogsUrl = "operationlog/getlog";
    // Configuration for jqGrid Example 1
    $("#table_list_1").jqGrid({
        url: getOperationLogsUrl,
        datatype: "json",
        height: 410,
        autowidth: true,
        shrinkToFit: true,
        rowNum: 20,
        rowList: [10, 20, 30],
        colNames: ['Log ID', '操作人员', '事件', '时间'],
        colModel: [
            {name: 'operationLogId', index: 'operationLogId', width: 80, sorttype: "text"},
            {name: 'userId', index: 'userId', width: 80, sorttype: "text"},
            {name: 'msg',  index: 'msg'},
            {name: 'createTime',width: 80, index: 'createTime',align: "center", sorttype: "date", formatter: "date"},
        ],
        pager: "#pager_list_1",
        viewrecords: true,
        caption: "用户操作日志记录",
        hidegrid: false
    });
})