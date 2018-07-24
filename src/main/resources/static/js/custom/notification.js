$(function () {
    var contentDiv = $('#main_content');
    var queryNotificationUrl = "/notification/querynotification"
    createTable();
    var lang = {
        "sProcessing": "处理中...",
        "sLengthMenu": "每页 _MENU_ 项 ",
        "sZeroRecords": "没有匹配结果",
        "sInfo": " 当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
        "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页",
            "sJump": "跳转"
        },
    };
    // 按钮
    function createTable(type) {
         $('.dataTables-example').DataTable({
            language:lang,  //提示信息
            stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            searching: true,  //禁用原生搜索
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
            destroy:true,

            pageLength: 15,
            responsive: true,
            dom: '<"html5buttons"B>lTfgitp',
            buttons: [
                {extend: 'copy'},
                {extend: 'csv'},
                {extend: 'excel', title: '水库项目基本信息'},
                {extend: 'pdf', title: '水库项目基本信息'},

                {extend: 'print',
                    customize: function (win){
                        $(win.document.body).addClass('white-bg');
                        $(win.document.body).css('font-size', '10px');

                        $(win.document.body).find('table')
                            .addClass('compact')
                            .css('font-size', 'inherit');
                    }
                }
            ],

            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.pageSize= data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.startIndex = data.start;//开始的记录序号
                param.pageIndex = (data.start / data.length);//当前页码
                (typeof(type) == 'undefined')? param.type = data.search.value : param.type = type;

                //ajax请求数据方法
                $.ajax({
                    type: "get",
                    url: queryNotificationUrl,//url请求的地址
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result)
                        //封装返回数据重要
                        var returnData = {};
                        //这里直接自行返回了draw计数器,应该由后台返回
                        returnData.draw = result.draw;
                        //返回数据全部记录
                        returnData.recordsTotal = data.totalElements;
                        //后台不实现过滤功能，每次查询均视作全部结果
                        returnData.recordsFiltered = result.totalElements;
                        //返回的数据列表
                        var content = [];
                        result.content.map(function (item, index) {
                            var contentItem = {};
                            contentItem.notificationId = item.notificationId;
                            contentItem.submitter = item.submitter;
                            contentItem.type = item.type;
                            contentItem.createTime = item.createTime;
                            contentItem.checked = item.checked;
                            contentItem.url = item.url;
                            contentItem.typeId = item.typeId;
                            content.push(contentItem);
                        })
                        returnData.data = content;

                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                    }
                });
            },
            //列表表头字段
            columns: [
                { "data": "notificationId" },
                { "data": "submitter" },
                { "data": "type" },
                { "data": "createTime" },
                //新建列的 定义
                {
                    className : "td-operation text-center",
                    data: null,
                    defaultContent:"",
                    orderable : false,

                }
            ],
            //新建列的 数据内容
            "createdRow": function ( row, data, index ) {  // data 实际就是 callback(returnData)的内容
                var faIcon = data.checked ? '<i class="fa fa-envelope-open-o" aria-hidden="true"></i>' : '<i class="fa fa-envelope"></i>';
                //行渲染回调,在这里可以对该行dom元素进行任何操作
                var $btn = $('<div class="btn-group text-cen">'+
                    '<button type="button" data-nid="'+ data.notificationId +'" data-name="'+ data.type +'" data-id="' +  data.typeId + '" data-href="'+ data.url +'" class="btn btn-sm btn-primary btn-view">查看</button>'+
                    '&nbsp '+ faIcon +' ' +
                    '</div>'+
                    '</div>');
                $('td', row).eq(4).append($btn);
            }
        });
    }

    $('.table').on('click', '.btn-view', function (e) {
        var href;
        var target = e.currentTarget;
        var url = "notification/changetochecked?notificationId=" + target.dataset.nid;
        if (target.dataset.name == "月报") {
            href = target.dataset.href+'?projectMonthlyReportId='+target.dataset.id;
            contentDiv.load(href)
        }
        if (target.dataset.name == "项目基本信息") {
            href = target.dataset.href;
            contentDiv.load(href)
        }
        if (target.dataset.name == "项目前期信息") {
            href = target.dataset.href;
            contentDiv.load(href)
        }
        if (target.dataset.name == "月报历史数据") {
            href = target.dataset.href;
            contentDiv.load(href)
        }
        if (target.dataset.name == "年度投资计划") {
            href = target.dataset.href;
            contentDiv.load(href)
        }
        if (target.dataset.name == "招标项目备案") {
            href = target.dataset.href;
            contentDiv.load(href)
        }
        $.getJSON(url, function (data) {});
       /* 如果还有其他的往这里加*/
    })

    $('#categoryUl').on('click', 'a', function (e) {
        var target = e.currentTarget;
        var type = target.dataset.name;

        createTable(type);
    })



})