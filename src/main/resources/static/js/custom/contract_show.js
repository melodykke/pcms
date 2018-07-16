$(function () {
    var queryContractsUrl = "contract/getcontract"
    createTable(queryContractsUrl, 1);

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

    function createTable(url, state) { // param 0未审核 1已审核 -1未审核
        if (state == 1) {
            $('#contract_table1').DataTable({
                language:lang,  //提示信息
                stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
                processing: true,  //隐藏加载提示,自行处理
                serverSide: true,  //启用服务器端分页
                searching: false,  //禁用原生搜索
                orderMulti: false,  //启用多列排序
                order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
                renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
                pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
                destroy:true,

                pageLength: 15,
                responsive: true,
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    var param = {};
                    param.pageSize= data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                    param.startIndex = data.start;//开始的记录序号
                    param.pageIndex = (data.start / data.length);//当前页码
                    param.state = state
                    //ajax请求数据方法
                    $.ajax({
                        type: "get",
                        url: url,//url请求的地址
                        cache: false,  //禁用缓存
                        data: param,  //传入组装的参数
                        dataType: "json",
                        success: function (result) {
                            //封装返回数据重要
                            var returnData = {};
                            //这里直接自行返回了draw计数器,应该由后台返回
                            returnData.draw = result.data.draw;
                            //返回数据全部记录
                            returnData.recordsTotal = result.data.totalElements;
                            //后台不实现过滤功能，每次查询均视作全部结果
                            returnData.recordsFiltered = result.data.totalElements;
                            //返回的数据列表
                            var content = [];
                            result.data.content.map(function (item, index) {
                                var contentItem = {};
                                contentItem.id = item.id;
                                contentItem.name = item.name;
                                contentItem.number = item.number;
                                contentItem.type = item.type;
                                contentItem.signDate = item.signDate;
                                contentItem.partyA = item.partyA;
                                contentItem.partyB = item.partyB;
                                contentItem.price = item.price;
                                contentItem.content = item.content;
                                contentItem.remark = item.remark;
                                contentItem.label = item.label == 1 ? "合同内" : "合同外";
                                contentItem.state = item.state;
                                contentItem.contractImgs = item.contractImgs;
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
                    { "data": "name" },
                    { "data": "number" },
                    { "data": "type" },
                    { "data": "signDate" },
                    { "data": "partyA" },
                    { "data": "partyB" },
                    { "data": "price" },
                    { "data": "label" },
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
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    var $btn = $('<div class="btn-group text-cen">'+
                        '<button type="button" data-cid="'+ data.id +'" class="btn btn-sm btn-primary btn-view">查看</button>'+
                        '</div>'+
                        '</div>');
                    $('td', row).eq(8).append($btn);
                }
            });
        } else if (state == 0) {
            $('#contract_table2').DataTable({
                language:lang,  //提示信息
                stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
                processing: true,  //隐藏加载提示,自行处理
                serverSide: true,  //启用服务器端分页
                searching: false,  //禁用原生搜索
                orderMulti: false,  //启用多列排序
                order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
                renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
                pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
                destroy:true,

                pageLength: 15,
                responsive: true,
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    var param = {};
                    param.pageSize= data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                    param.startIndex = data.start;//开始的记录序号
                    param.pageIndex = (data.start / data.length);//当前页码
                    param.state = state
                    //ajax请求数据方法
                    $.ajax({
                        type: "get",
                        url: url,//url请求的地址
                        cache: false,  //禁用缓存
                        data: param,  //传入组装的参数
                        dataType: "json",
                        success: function (result) {
                            //封装返回数据重要
                            var returnData = {};
                            //这里直接自行返回了draw计数器,应该由后台返回
                            returnData.draw = result.data.draw;
                            //返回数据全部记录
                            returnData.recordsTotal = result.data.totalElements;
                            //后台不实现过滤功能，每次查询均视作全部结果
                            returnData.recordsFiltered = result.data.totalElements;
                            //返回的数据列表
                            var content = [];
                            result.data.content.map(function (item, index) {
                                var contentItem = {};
                                contentItem.id = item.id;
                                contentItem.name = item.name;
                                contentItem.number = item.number;
                                contentItem.type = item.type;
                                contentItem.signDate = item.signDate;
                                contentItem.partyA = item.partyA;
                                contentItem.partyB = item.partyB;
                                contentItem.price = item.price;
                                contentItem.content = item.content;
                                contentItem.remark = item.remark;
                                contentItem.label = item.label == 1 ? "合同内" : "合同外";
                                contentItem.state = item.state;
                                contentItem.contractImgs = item.contractImgs;
                                contentItem.role = result.msg;
                                content.push(contentItem);
                            });
                            returnData.data = content;
                            //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(returnData);
                        }
                    });
                },
                //列表表头字段
                columns: [
                    { "data": "name" },
                    { "data": "number" },
                    { "data": "type" },
                    { "data": "signDate" },
                    { "data": "partyA" },
                    { "data": "partyB" },
                    { "data": "price" },
                    { "data": "label" },
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
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    var $btn = '';
                    if (data.role == "checker") {
                        $btn = $('<div class="btn-group text-cen">'+
                            '<button type="button" id="contractApprovalBtn" data-cid="'+ data.id +'" class="btn btn-sm btn-primary">审批</button> '+
                            '<button type="button" data-cid="'+ data.id +'" class="btn btn-sm btn-primary btn-view">查看</button>'+
                            '</div>'+
                            '</div>');
                    } else {
                        $btn = $('<div class="btn-group text-cen">'+
                            '<button type="button" data-cid="'+ data.id +'" class="btn btn-sm btn-primary btn-view">查看</button>'+
                            '</div>'+
                            '</div>');
                    }

                    $('td', row).eq(8).append($btn);
                }
            });
        } else if (state == -1) {
            $('#contract_table3').DataTable({
                language:lang,  //提示信息
                stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
                processing: true,  //隐藏加载提示,自行处理
                serverSide: true,  //启用服务器端分页
                searching: false,  //禁用原生搜索
                orderMulti: false,  //启用多列排序
                order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
                renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
                pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
                destroy:true,

                pageLength: 15,
                responsive: true,
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    var param = {};
                    param.pageSize= data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                    param.startIndex = data.start;//开始的记录序号
                    param.pageIndex = (data.start / data.length);//当前页码
                    param.state = state
                    //ajax请求数据方法
                    $.ajax({
                        type: "get",
                        url: url,//url请求的地址
                        cache: false,  //禁用缓存
                        data: param,  //传入组装的参数
                        dataType: "json",
                        success: function (result) {
                            //封装返回数据重要
                            var returnData = {};
                            //这里直接自行返回了draw计数器,应该由后台返回
                            returnData.draw = result.data.draw;
                            //返回数据全部记录
                            returnData.recordsTotal = result.data.totalElements;
                            //后台不实现过滤功能，每次查询均视作全部结果
                            returnData.recordsFiltered = result.data.totalElements;
                            //返回的数据列表
                            var content = [];
                            result.data.content.map(function (item, index) {
                                var contentItem = {};
                                contentItem.id = item.id;
                                contentItem.name = item.name;
                                contentItem.number = item.number;
                                contentItem.type = item.type;
                                contentItem.signDate = item.signDate;
                                contentItem.partyA = item.partyA;
                                contentItem.partyB = item.partyB;
                                contentItem.price = item.price;
                                contentItem.content = item.content;
                                contentItem.remark = item.remark;
                                contentItem.label = item.label == 1 ? "合同内" : "合同外";
                                contentItem.state = item.state;
                                contentItem.contractImgs = item.contractImgs;
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
                    { "data": "name" },
                    { "data": "number" },
                    { "data": "type" },
                    { "data": "signDate" },
                    { "data": "partyA" },
                    { "data": "partyB" },
                    { "data": "price" },
                    { "data": "label" },
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
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    var $btn = '';
                    if (data.label=='合同外'&&data.state==-1){
                        $btn = $('<div class="btn-group text-cen">'+
                            '<button type="button" data-cid="'+ data.id +'" class="btn btn-sm btn-primary btn-view">查看</button>'+
                            '</div>'+
                            '</div>');
                    }else{
                        $btn = $('<div class="btn-group text-cen">'+
                            '<button type="button" data-cid="'+ data.id +'" class="btn btn-sm btn-primary btn-repost">重新提交</button>'+
                            '<button type="button" data-cid="'+ data.id +'" class="btn btn-sm btn-primary btn-view">查看</button>'+
                            '</div>'+
                            '</div>');
                    }
                    $('td', row).eq(8).append($btn);
                }
            });
        }
    }


    $('#show_tab_1').click(function () {
        createTable(queryContractsUrl, 1)
    });
    $('#show_tab_2').click(function () {
        createTable(queryContractsUrl, 0);
    });
    $('.table').on("click", '#contractApprovalBtn', function (e) {
        var target = e.currentTarget;
        $('#contract_approve_modal').modal('show');
        $('#contract_approve_submit').click(function () {
            var switchState = $("#contract-checkbox").prop("checked");  // true: 按钮为通过 false：按钮通过
            var checkinfo = $('#contract_approve_area').val();
            var contractId = target.dataset.cid;
                $.ajax({
                    url: "contract/approvecontract",
                    type: 'POST',
                    data: JSON.stringify({"switchState": switchState, "checkinfo": checkinfo, "contractId": contractId}),
                    contentType: 'application/json',
                    beforeSend: function () {
                        $('#loading').show();
                    },
                    success: function (data) {
                        if (data.code == 1002 || data.code == 1003) {
                            $('#contract_check_div').html('');
                            $('#contract_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 成 功</h1></div> <div class="modal-footer">\n' +
                                '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                                '            </div>');
                        } else {
                            $('#contract_check_div').html('');
                            $('#contract_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
                                '                <div class="form-group animated fadeIn" ><label style="font-size: 15px;">' + data.msg + '</label></div>\n' +
                                '            </div><div class="modal-footer">\n' +
                                '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                                '            </div>');
                        }
                        $('#contract_approve_modal').modal('hide');
                        $('#main_content', parent.document).load('contract/tocontract');
                    },
                    complete: function () {
                        $("#loading").hide();
                    },
                    error: function (data) {
                        console.info("error: " + data.msg);
                    }
                });
        });

    });
    $("[name='my-checkbox']").bootstrapSwitch({
        onText : "拒绝",
        offText : "通过",
        onColor : "danger",
        offColor : "success",
        size : "large",
        onSwitchChange : function() {
            var checkedOfAll=$("#my-checkbox").prop("checked");
            if (checkedOfAll==false){
                $('#contract_approve_input').hide()
            }
            else {
                $('#contract_approve_input').show();
                $('#contract_approve_area').text('');
            }
        }
    });


    $('#show_tab_3').click(function () {
        createTable(queryContractsUrl, -1);
    });

    $('#contract_show_tab_4').click(function () {
        $.getJSON("contract/addouttercontract", function (data) {
            if (data.code == 1003) {
                $('#contract_new_modal').modal('show');
                $('#contract_new_msg_div').html('');
                $('#contract_new_msg_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
                    '                <div class="form-group animated fadeIn" ><label style="font-size: 15px;">在项目合同未审批通过的前提下，不能新增合同外项目</label></div>\n' +
                    '            </div><div class="modal-footer">\n' +
                    '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                    '            </div>');
            } else {
                $('#contract_modal', parent.document).modal('show');
            }
        })
    });


    $('#contract_table1').on('click', '.btn-view', function (e) {
        var target = e.currentTarget;
        var cid = target.dataset.cid;
        $.ajax({
            type: "get",
            url: "contract/getcontractbyid",//url请求的地址
            cache: false,  //禁用缓存
            data: {"id": cid},  //传入组装的参数
            dataType: "json",
            success: function (data) {
                $('#show_contract_name').val(data.data.name);
                $('#show_contract_type').val(data.data.type);
                $('#show_contract_party_a').val(data.data.partyA);
                $('#show_contract_amount').val(data.data.price);
                $('#show_contract_num').val(data.data.number);
                $('#show_contract_time').val(data.data.signDate);
                $('#show_contract_party_b').val(data.data.partyB);
                $('#show_contract_main_content').val(data.data.content);
                $('#show_contract_remark').val(data.data.remark);
                var contract_file_html = '';
                data.data.contractImgVOs.map(function (item, index) {
                    contract_file_html += '<div class="file-box">\n' +
                        '                                                                                    <div class="file">\n' +
                        '                                                                                        <span class="corner"></span>\n' +
                        '                                                                                        <div class="image" style="background:url(' + item.thumbnailAddr + ');background-size:cover;">\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                        <div class="file-name">\n' +
                        '                                                                                            文件\n' +
                        '                                                                                            <br/>\n' +
                        '                                                                                            <small>'+ item.createTime +'</small>\n' +
                        '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/contractfile?fileId='+item.imgAddr+'">下载</a>\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                    </div>\n' +
                        '                                                                                </div>'
                    
                });
                $('#contract_file_display_div').html("");
                $('#contract_file_display_div').html(contract_file_html);



                $('#contract_show_modal').modal();
            }
        });
    });
    $('#contract_table2').on('click', '.btn-view', function (e) {
        var target = e.currentTarget;
        var cid = target.dataset.cid;
        $.ajax({
            type: "get",
            url: "contract/getcontractbyid",//url请求的地址
            cache: false,  //禁用缓存
            data: {"id": cid},  //传入组装的参数
            dataType: "json",
            success: function (data) {
                $('#show_contract_name').val(data.data.name);
                $('#show_contract_type').val(data.data.type);
                $('#show_contract_party_a').val(data.data.partyA);
                $('#show_contract_amount').val(data.data.price);
                $('#show_contract_num').val(data.data.number);
                $('#show_contract_time').val(data.data.signDate);
                $('#show_contract_party_b').val(data.data.partyB);
                $('#show_contract_main_content').val(data.data.content);
                $('#show_contract_remark').val(data.data.remark);
                var contract_file_html = '';
                data.data.contractImgVOs.map(function (item, index) {
                    contract_file_html += '<div class="file-box">\n' +
                        '                                                                                    <div class="file">\n' +
                        '                                                                                        <span class="corner"></span>\n' +
                        '                                                                                        <div class="image" style="background:url(' + item.thumbnailAddr + ');background-size:cover;">\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                        <div class="file-name">\n' +
                        '                                                                                            文件\n' +
                        '                                                                                            <br/>\n' +
                        '                                                                                            <small>'+ item.createTime +'</small>\n' +
                        '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/contractfile?fileId='+item.imgAddr+'">下载</a>\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                    </div>\n' +
                        '                                                                                </div>'

                });
                $('#contract_file_display_div').html("");
                $('#contract_file_display_div').html(contract_file_html);



                $('#contract_show_modal').modal();
            }
        });
    });
    $('#contract_table3').on('click', '.btn-view', function (e) {
        var target = e.currentTarget;
        var cid = target.dataset.cid;
        $.ajax({
            type: "get",
            url: "contract/getcontractbyid",//url请求的地址
            cache: false,  //禁用缓存
            data: {"id": cid},  //传入组装的参数
            dataType: "json",
            success: function (data) {
                $('#show_contract_name').val(data.data.name);
                $('#show_contract_type').val(data.data.type);
                $('#show_contract_party_a').val(data.data.partyA);
                $('#show_contract_amount').val(data.data.price);
                $('#show_contract_num').val(data.data.number);
                $('#show_contract_time').val(data.data.signDate);
                $('#show_contract_party_b').val(data.data.partyB);
                $('#show_contract_main_content').val(data.data.content);
                $('#show_contract_remark').val(data.data.remark);
                var contract_file_html = '';
                data.data.contractImgVOs.map(function (item, index) {
                    contract_file_html += '<div class="file-box">\n' +
                        '                                                                                    <div class="file">\n' +
                        '                                                                                        <span class="corner"></span>\n' +
                        '                                                                                        <div class="image" style="background:url(' + item.thumbnailAddr + ');background-size:cover;">\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                        <div class="file-name">\n' +
                        '                                                                                            文件\n' +
                        '                                                                                            <br/>\n' +
                        '                                                                                            <small>'+ item.createTime +'</small>\n' +
                        '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/contractfile?fileId='+item.imgAddr+'">下载</a>\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                    </div>\n' +
                        '                                                                                </div>'

                });
                $('#contract_file_display_div').html("");
                $('#contract_file_display_div').html(contract_file_html);



                $('#contract_show_modal').modal();
            }
        });
    });
    $('#contract_table3').on('click', '.btn-repost', function (e) {
        $('#contract_modal', parent.document).modal('show');
    });

    $('#contract_modal_show_close1,#contract_modal_show_close').click(function () {
        $("#contract_show_modal").modal('hide');
    });

   /* $('#contract_show_tab_4').click(function () {

    })*/


});