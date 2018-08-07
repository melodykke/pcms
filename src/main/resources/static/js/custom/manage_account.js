

var reservoirGrid = $("#reservoir_table_list").jqGrid({
    url: '/account/getuseraccounts',
    mtype: 'get',
    datatype: "json",
    height: 350,
    autowidth: true,
    shrinkToFit: true,
    // 多选列
    multiselect: true,
    multiselectWidth: 50,
    // 序号列
    rownumbers: true,
    rowNum: 14,
    rowList: [20, 40, 60],
    colNames: ['账号名', '水库名'],
    colModel: [
        { name: 'username', index: 'username', width: 50, align: "center" },
        { name: 'name', index: 'name', width: 80, align: "center" }
    ],
    pager: "#reservoir_pager_list",
    viewrecords: true,
    hidegrid: false,
    jsonReader: {//分页的关键
        id: "0",
        root: "content", total: "totalPages", page: "pageIndex", records: "numberOfElements", repeatitems: false
    },
    viewrecords: true,
    rowNum: 20,
    page: 1,
    rowList: [10, 20, 30],
    loadComplete: function (data) {
        gridData = data.content;
    }
});

// 获取选中数据
function getSelectedRowData() {
    var indexArr = $('#reservoir_table_list').jqGrid('getGridParam', 'selarrrow');
    var rowData;
    if (indexArr.length > 1) {
        alert("不能同时选择多条账号信息，请重新选择！");
        rowData = false;
    } else if (indexArr.length === 0) {
        alert("请选择账号！");
        rowData = false;
    } else {
        // rowData = $('#reservoir_table_list').jqGrid("getRowData", indexArr[0]);
        rowData = gridData[parseInt(indexArr[0]) - 1];
    }
    return rowData;
}
// 复杂参数按格式编译进url
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
 *     grid账号表操作功能
 */
// 创建账号
function createAccount() {
    $('#content').load('account/toaccount');
}
// 修改账号信息
function modifyAccount() {
    var rowData = getSelectedRowData();
    if (rowData) {
        var param = parseParam(rowData),
            urlParam = encodeURIComponent(param);
        location.href = "create_account.html?" + urlParam;
    }
}
// 删除账号
function deleteAccount() {
    var rowData = getSelectedRowData();
    requestGridData('', 'GET', rowData);
}
// 刷新
function refreshAccount() {
    requestGridData('/account/getuseraccounts', 'GET');
}
// 搜索
function searchAccount() {
    var condition = $("#search_condition").val();
    if (!condition.trim()) {
        alert("请输入条件!");
        return false;
    }
    // 重置Grid
    requestGridData("js/accountData1.json", 'GET', { accountName: condition });
}
// 请求服务
function requestGridData(url, ajaxType, requestData) {
    var postData = $('#reservoir_table_list').jqGrid("getGridParam", "postData");
    $.each(postData, function (k, v) {
        delete postData[k];
    });
    $("#reservoir_table_list").setGridParam({ datatype: 'json', page: 1 }).jqGrid('setGridParam', {
        url: url,
        type: ajaxType,
        page: 1,
        postData: requestData,
    }).trigger("reloadGrid");
}
$(function () {
    $(window).resize(function () {
        $("#reservoir_table_list").setGridWidth($(".panel-body").width());
    });
})