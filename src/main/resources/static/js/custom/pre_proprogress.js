$(function () {
    var getpreprogressurl = 'preprogress/getpreprogress';

    getPreProgress(getpreprogressurl)

    function getPreProgress(url) {
        $.ajax({
            url: url,
            type: 'GET',
            contentType: 'application/json',
            beforeSend: function () {
                $('#loading').show();
            },
            success: function (data) {
                $('#plantName').text(data.data.plantName);
                $('#plantName').attr("data-id",data.data.preProgressId);
                if (data.data.state == 0) {
                    $('#state').text("待审核");
                    $('#repost_div').html(' <a id="pre_progress_info_repeat" data-toggle="tooltip" data-placement="bottom" title="点击后重新填报！"><i class="fa fa-repeat fa-lg"></i></a>');
                } else if (data.data.state == -1) {
                    $('#state').text("审核未通过");
                    $('#repost_div').html(' <a id="pre_progress_info_repeat" data-toggle="tooltip" data-placement="bottom" title="点击后重新填报！"><i class="fa fa-repeat fa-lg"></i></a>');
                } else if (data.data.state == 1) {
                    $('#state').text("审核通过");
                }
                if (data.data.state == 1) {
                    $('#check_btn').html('<span class="label label-primary"><i class="fa fa-check"></i> 已审批通过</span>');
                } else if (data.data.state == -1) {
                    $('#check_btn').html('<span class="label label-danger"><i class="fa fa-times"></i> 审批未通过</span>');
                }
                $('#owner').text(data.data.owner);
                $('#submitTime').text(data.data.createTime);
                data.data.state == 0 ? $('#state_bar').css("width", "50%") : $('#state_bar').css("width", "100%");
                if (data.data.state == 0) {
                    $('#state_msg').text("等待上级审批");
                } else if (data.data.state == 1) {
                    $('#state_msg').text("审核通过");
                } else if (data.data.state == -1) {
                    $('#state_msg').text("审核未通过");
                }
                $('#updateTime').text(data.data.updateTime);
                $('#createTime').text(data.data.createTime);

                var entries = [];
                data.data.preProgressEntries.map(function (item, index) {
                    var entry = [];
                    entry.push(item.serialNumber);
                    entry.push(item.planProject);
                    entry.push(item.approvalStatus);
                    entry.push(item.compileUnit);
                    entry.push(item.approvalUnit);
                    entry.push(item.approvalDate);
                    entry.push(item.referenceNumber);
                    entries.push(entry);
                });

                var preProgressImgVOs = data.data.preProgressImgVOs;
                var file_display_html = '';
                preProgressImgVOs.map(function (item, index) {
                    file_display_html += '<div class="file-box">\n' +
                        '                                                                                    <div class="file">\n' +
                        '                                                                                        <span class="corner"></span>\n' +
                        '                                                                                        <div class="image" style="background:url(' + item.thumbnailAddr + ');background-size:cover;">\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                        <div class="file-name">\n' +
                        '                                                                                            文件\n' +
                        '                                                                                            <br/>\n' +
                        '                                                                                            <small>' + item.createTime + '</small>\n' +
                        '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/preprogressfile?fileId=' + item.imgAddr + '">下载</a>\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                    </div>\n' +
                        '                                                                                </div>'
                });
                $('#files_display_div').html("");
                $('#files_display_div').html(file_display_html);


                $('.dataTables-example').DataTable({
                    data: entries,
                    // bFilter: false,    //去掉搜索框
                    bInfo: true,       //去掉显示信息
                    retrieve: true,    //多次加载不会显示缓存数据
                    pageLength: 23,
                    responsive: true,
                    paging: true,
                    dom: '<"html5buttons"B>lTfgitp',
                    buttons: [
                        {extend: 'copy'},
                        {extend: 'excel', title: '项目前期表格'},
                        {
                            extend: 'print',
                            customize: function (win) {
                                $(win.document.body).addClass('white-bg');
                                $(win.document.body).css('font-size', '10px');
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }
                        }
                    ]
                });
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });
    }


    <!--审批modal-->
    $("[name='preprogress-checkbox']").bootstrapSwitch({
        onText: "拒绝",
        offText: "通过",
        onColor: "danger",
        offColor: "success",
        size: "large",
        onSwitchChange: function () {
            var checkedOfAll = $("#my-checkbox").prop("checked");
            if (checkedOfAll == false) {
                $('#progress_approve_input').hide()
            }
            else {
                $('#progress_approve_input').show();
                $('#progress_approve_area').text('');
            }
        }
    });
    $('#pre_progress_approve_submit').click(function () {
        var switchState = $("#preprogress-checkbox").prop("checked");  // true: 按钮为通过 false：按钮通过
        var checkinfo = $('#progress_approve_area').val();
        var preProgressId = $('#plantName').attr("data-id");
        $.ajax({
            url: "preprogress/approvepreprogress",
            type: 'POST',
            data: JSON.stringify({"switchState": switchState, "checkinfo": checkinfo, "preProgressId": preProgressId}),
            contentType: 'application/json',
            beforeSend: function () {
                $('#loading').show();
            },
            success: function (data) {
                console.log(data)
                if (data.code == 1002 || data.code == 1003) {
                    $('#pre_progress_check_div').html('');
                    $('#pre_progress_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 成 功</h1></div> <div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                } else {
                    $('#pre_progress_check_div').html('');
                    $('#pre_progress_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
                        '                <div class="form-group animated fadeIn" ><label style="font-size: 15px;">' + data.msg + '</label></div>\n' +
                        '            </div><div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                }
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });
    });
    $('#repost_div').on('click', '#pre_progress_info_repeat', function (e) {
        var countEntries = 0;
        $('#pre_progress_modal', parent.document).modal();
        $.ajax({
            url: 'preprogress/initthispage',
            type: 'GET',
            contentType: 'application/json',
            success: function (data) {
                var preProgressEntryHtml = '';
                data.data.map(function (item, index) {
                    preProgressEntryHtml += '      <tr id="entry_' + item.serialNumber + '">\n' +
                        '                                                <th id="serialNumber_' + item.serialNumber + '">' + item.serialNumber + '</th>\n' +
                        '                                                <th id="planProject_' + item.serialNumber + '">' + item.planProject + '</th>\n' +
                        '                                                <th><select id="approvalStatus_' + item.serialNumber + '"><option selected="selected" disabled="disabled"  style="display: none"></option><option data-value="编制">编制</option><option data-value="内部评审">内部评审</option><option data-value="政府评审">政府评审</option><option data-value="完成">完成</option></select></th>\n' +
                        '                                                <th><input id="compileUnit_' + item.serialNumber + '" class="form-control" type="text"/></th>\n' +
                        '                                                <th><input id="approvalUnit_' + item.serialNumber + '" class="form-control" type="text"/></th>\n' +
                        '                                                <th><div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span><input  id="approvalDate_' + item.serialNumber + '" type="text"  class="form-control date-input" readonly></div></th>\n' +
                        '                                                <th><input id="referenceNumber_' + item.serialNumber + '" class="form-control" type="text"/></th>\n' +
                        '                                            </tr>';
                    countEntries += 1;
                    $('#preProgressEntry_body').html(preProgressEntryHtml);

                })
            }
        });
    })
    $('#pre_progress_check_div').on('click', '#check_result_confirm_btn', function (e) {
        /*top.location.reload()*/
        $('#main_content', parent.document).load('preprogress/topreprogress');
    });


})