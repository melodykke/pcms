// Examle data for jqGrid
function initReservoirGrid(gridData) {
    $("#reservoir_table_list").jqGrid({
        data: gridData,
        datatype: "local",
        height: 320,
        autowidth: true,
        shrinkToFit: true,
        rownumbers: true,
        rowNum: 15,
        rowList: [15, 30, 45],
        colNames: ['水库名', '水库规模', '等级', '地理位置', '防洪标高', '当前累计拨付', '当前投资', '省累计付款', '省政府投资', '省政府贷款', '总投资', '中央累计拨付', '中央投资'],
        colModel: [
            { name: 'plantName', index: 'plantName', width: 120, sorttype: false,
                formatter: function(cellvalue, options, rowObject) {
                    return '<a reservoir-id="' + rowObject.baseInfoId + '" class="grid-link">' + rowObject.plantName + '</a>';
                    // return cellvalue;
                } },
            { name: 'scale', index: 'scale', width: 70, sorttype: false },
            { name: 'level', index: 'level', width: 30, sorttype: false },
            { name: 'location', index: 'location', width: 150, align: "left", sorttype: "float", sortable: false },
            { name: 'floodControlElevation', index: 'floodControlElevation', width: 80, align: "left", sortable: false },
            { name: 'localAccumulativePayment', index: 'localAccumulativePayment', width: 100, align: "right", sortable: false, formatter: rmbNum },
            { name: 'localInvestment', index: 'localInvestment', width: 100, sortable: false,  align: "right",formatter: rmbNum },
            { name: 'provincialAccumulativePayment', index: 'provincialAccumulativePayment', width: 100, sortable: false,  align: "right",formatter: rmbNum },
            { name: 'provincialInvestment', index: 'provincialInvestment', width: 100, sortable: false,  align: "right", formatter: rmbNum},
            { name: 'provincialLoan', index: 'provincialLoan', width: 100, sortable: false,  align: "right", formatter: rmbNum },
            { name: 'totalInvestment', index: 'totalInvestment', width: 100, sortable: false,  align: "right", formatter: rmbNum },
            { name: 'centralAccumulativePayment', index: 'centralAccumulativePayment', width: 100, sortable: false,  align: "right", formatter: rmbNum },
            { name: 'centralInvestment', index: 'centralInvestment', width: 100, sortable: false,  align: "right", formatter: rmbNum }
        ],
        pager: "#reservoir_pager_list",
        // caption: "Example jqGrid 1",
        hidegrid: false,
        viewrecords: true,
        jsonReader: {//分页的关键
            id: "0",
            root: "content", total: "totalPages", page: "pageIndex", records: "numberOfElements", repeatitems: false
        },
        viewrecords: true,
        rowNum: 15,
        page: 1,
        rowList: [15, 30, 45],
        loadComplete: function (data) {
            gridData = data.content;
        }
    });
}
// 请求服务
function requestGridData(data) {
    $("#reservoir_table_list").jqGrid('clearGridData');
    $("#reservoir_table_list").jqGrid('setGridParam', {
        data: data,
        datatype: "local",
        page: 1,
    }).trigger("reloadGrid");
}
// 搜索
var allData;
function searchGrid() {
    var condition = $(".search-block>input").val();
    if (!condition.trim()) {
        alert("请输入条件!");
        return false;
    }
    // 数据过滤
    $.when(allData).done(function (data) {
        var newData = [];
        data.map(function (item, i) {
            console.log(item);
            if (item.plantName && item.plantName.indexOf(condition) > -1) {
                newData.push(item);
            }
        });
        // 重置Grid
        requestGridData(newData);
    });
}
function rmbNum(cellvalue, options, rowObject) {
    return '<span class="grid-rmb">' + cellvalue + '</span>';
}

function getAllReservoirData(param) {
    var def = $.Deferred();
    $.ajax({
        url: "index/getallbaseinfo",
        type: 'GET',
        dataType: 'json',
        postData: param || {},
        success: function (data) {
            def.resolve(data.data);
        },
        error: function (err) {
            // 处理本地数据字段
            var dataJson = err.responseText;
            var dataList = dataJson.split('),'),
                itemList = [];
            dataList.map(function (item) {
                var itemJson;
                if (item.trim().indexOf("(") === 0) {
                    itemJson = item.trim().substr(1);
                } else {
                    itemJson = item.trim();
                }
                if (itemJson.trim().lastIndexOf(")") === itemJson.trim().length - 1) {
                    itemJson = itemJson.trim().substr(item.trim().length - 1);
                }
                item = itemJson.split(",");
                var itemMap = {};
                $.each(item, function (i, n) {
                    itemMap[keyJson[i]] = n.replace(/'/g,"");
                });
                itemList.push(itemMap);
            });
            def.resolve(itemList);
        }
    });
    return def.promise();
}
$(function () {
    allData = getAllReservoirData();
    $.when(allData).done(function(data){
        initReservoirGrid(data);
    });
    $(".jqGrid_wrapper.self-grid").on("click", "td>a.grid-link", function(){
        var baseInfoId = $(this).attr("reservoir-id");
        $('#content').load("manage/toreservoirindex?baseInfoId=" + baseInfoId);
    });
    // 搜索功能事件绑定
    $(".search-block>a>i").on("click", function() {
        searchGrid();
    });
    $(".search-block>input").on("keyup", function() {
        var keycode = event.keyCode || event.which || event.charCode;
        if(keycode === 13) {
            searchGrid();
        }
    });
    $(window).resize(function () {
        $("#reservoir_table_list").setGridWidth($(".panel-body").width());
    });
})
