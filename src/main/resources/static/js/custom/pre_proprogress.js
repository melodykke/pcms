$(function () {
    var getpreprogressurl = 'preprogress/getpreprogress';

    getPreProgress(getpreprogressurl)

    function getPreProgress(url) {
        $.ajax({
            url: url,
            type: 'GET',
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
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
                        '                                                                                            <small>'+ item.createTime +'</small>\n' +
                        '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/preprogressfile?fileId='+item.imgAddr+'">下载</a>\n' +
                        '                                                                                        </div>\n' +
                        '                                                                                    </div>\n' +
                        '                                                                                </div>'
                });
                $('#files_display_div').html("");
                $('#files_display_div').html(file_display_html);

                $('.dataTables-example').DataTable({
                    data: entries ,
                    // bFilter: false,    //去掉搜索框
                    bInfo:true,       //去掉显示信息
                    retrieve: true,    //多次加载不会显示缓存数据
                    pageLength: 23,
                    responsive: true,
                    paging: true,
                    dom: '<"html5buttons"B>lTfgitp',
                    buttons: [
                        {extend: 'copy'},
                        {extend: 'excel', title: '项目前期表格'},
                        {extend: 'print',
                            customize: function (win){
                                $(win.document.body).addClass('white-bg');
                                $(win.document.body).css('font-size', '10px');
                                $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                            }}
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
   $("[name='my-checkbox']").bootstrapSwitch({
         onText : "拒绝",
         offText : "通过",
         onColor : "danger",
         offColor : "success",
         size : "large",
         onSwitchChange : function() {
             var checkedOfAll=$("#my-checkbox").prop("checked");
             if (checkedOfAll==false){
                 $('#progress_approve_input').hide()
             }
             else {
                 $('#progress_approve_input').show();
                 $('#progress_approve_area').text('');
             }
         }
     });
     $('#submit').click(function () {
         var checkedOfAll=$("#my-checkbox").prop("checked");
         var checkinfo=$('#progress_approve_area').val();
         alert(checkedOfAll+checkinfo)
     });
     $('#pre_progress_repeat').click(function () {
         $('#pre_progress_modal',parent.document).modal();
     });


     <!--审批modal-->
    /* $("[name='baseinfo-checkbox']").bootstrapSwitch({
         onText : "拒绝",
         offText : "通过",
         onColor : "danger",
         offColor : "success",
         size : "large",
         onSwitchChange : function() {
             var checkedOfAll=$("#baseinfo-checkbox").prop("checked");
             if (checkedOfAll==false){
                 $('#base_info_approve_input').hide();
             }
             else {
                 $('#base_info_approve_input').show();
                 $('#base_info_approve_area').text('');
             }
         }
     });
     $('#base_info_approve_submit').click(function () {
         var checkedOfAll=$("#baseinfo-checkbox").prop("checked");
         var checkinfo=$('#base_info_approve_area').val();
     })
     $('#base_info_approve_submit').click(function () {
         var switchState = $("#baseinfo-checkbox").prop("checked");  // true: 按钮为通过 false：按钮通过
         var checkinfo = $('#base_info_approve_area').val();
         var baseInfoId = $('#re_plantName').attr("data-id");
         $.ajax({
             url: "baseinfo/approvebaseinfo",
             type: 'POST',
             data: JSON.stringify({"switchState":switchState, "checkinfo":checkinfo, "baseInfoId":baseInfoId}),
             contentType: 'application/json',
             beforeSend:function () {
                 $('#loading').show();
             },
             success: function (data) {
                 console.log(data)
                 if (data.code == 1002 || data.code == 1003) {
                     $('#base_info_check_div').html('');
                     $('#base_info_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 成 功</h1></div> <div class="modal-footer">\n' +
                         '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                         '            </div>');
                 } else {
                     $('#base_info_check_div').html('');
                     $('#base_info_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
                         '                <div class="form-group animated fadeIn" ><label style="font-size: 15px;">'+ data.msg +'</label></div>\n' +
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
     })
     $('#base_info_check_div').on('click', '#check_result_confirm_btn', function (e) {
         /!* top.location.reload()*!/
         $('#main_content', parent.document).load('baseinfo/baseinfoshow');
     })


     $('#repost_div').on('click', '#basic_info_repeat', function (e) {
         $('#base_info_modal', parent.document).modal();
     })*/

})