$(function () {
    var getbaseinfourl = 'baseinfo/getbaseinfo';
    getBaseInfo(getbaseinfourl)
    function getBaseInfo(url) {
        $.ajax({
            url: url,
            type: 'GET',
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                refreshContents(data);
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });
    }



    function refreshContents(data){
        $('#plantName').text(data.data.plantName);
        $('#plantName').attr("data-id",data.data.baseInfoId );
        data.data.state == 0 ? $('#state').text("待审核") :  $('#state').text("已审核");
        if (data.data.state == 1) {
            $('#check_btn').html('<span class="label label-primary"><i class="fa fa-check"></i> 已审批通过</span>');
        } else {
            $('#check_btn').html('<span class="label label-danger"><i class="fa fa-times"></i> 审批未通过</span><i class="fa fa-external-link"></i>');
        }
        $('#owner').text(data.data.owner);
        $('#submitTime').text(data.data.createTime);
        data.data.state == 0 ? $('#state_bar').css("width", "50%") : $('#state_bar').css("width", "100%");
        data.data.state == 0 ? $('#state_msg').text("等待上级审批") : $('#state_msg').text("审核通过");

        $('#plantName_h2').text(data.data.plantName);
        $('#projectType').text(data.data.projectType);
        $('#level').text(data.data.level);
        $('#longitude').text(data.data.longitude);
        $('#scale').text(data.data.scale);
        $('#storage').text(data.data.storage);
        $('#hasSignedConstructionContract').text(data.data.hasSignedConstructionContract);
        $('#supervisorBid').text(data.data.supervisorBid);
        $('#legalPersonName').text(data.data.legalPersonName);
        $('#legalRepresentativeName').text(data.data.legalRepresentativeName);
        $('#location').text(data.data.location);
        $('#latitude').text(data.data.latitude);
        $('#timeLimit').text(data.data.timeLimit);
        $('#utilizablCapacity').text(data.data.utilizablCapacity);
        $('#hasProjectCompleted').text(data.data.hasProjectCompleted);
        $('#hasAcceptCompletion').text(data.data.hasAcceptCompletion);
        $('#damType').text(data.data.damType);
        $('#maxDamHeight').text(data.data.maxDamHeight);
        $('#floodControlElevation').text(data.data.floodControlElevation);
        $('#watersupply').text(data.data.watersupply);
        $('#areaCoverage').text(data.data.areaCoverage);
        $('#ruralHumanWater').text(data.data.ruralHumanWater);
        $('#centralInvestment').text(data.data.centralInvestment);
        $('#localInvestment').text(data.data.localInvestment);
        $('#centralAccumulativePayment').text(data.data.centralAccumulativePayment);
        $('#localAccumulativePayment').text(data.data.localAccumulativePayment);
        $('#provincialLoan').text(data.data.provincialLoan);
        $('#catchmentArea').text(data.data.catchmentArea);
        $('#spillway').text(data.data.spillway);
        $('#irrigatedArea').text(data.data.irrigatedArea);
        $('#installedCapacity').text(data.data.installedCapacity);
        $('#livestock').text(data.data.livestock);
        $('#waterSupplyPopulation').text(data.data.waterSupplyPopulation);
        $('#provincialInvestment').text(data.data.provincialInvestment);
        $('#totalInvestment').text(data.data.totalInvestment);
        $('#provincialAccumulativePayment').text(data.data.provincialAccumulativePayment);
        $('#totalAccumulativePayment').text( parseInt(data.data.centralAccumulativePayment) + parseInt(data.data.provincialAccumulativePayment) + parseInt(data.data.localAccumulativePayment));
        $('#unitProjectAmount').text(data.data.unitProjectAmount);
        $('#unitProjectOverview').text(data.data.unitProjectOverview);
        $('#cellProjectAmount').text(data.data.cellProjectAmount);
        $('#cellProjectOverview').text(data.data.cellProjectOverview);
        $('#branchProjectAmount').text(data.data.branchProjectAmount);
        $('#branchProjectOverview').text(data.data.branchProjectOverview);
        $('#constructionLand').text(data.data.constructionLand);
        $('#county').text(data.data.county);
        $('#landReclamationPlan').text(data.data.landReclamationPlan);
        $('#overview').text(data.data.overview);
        $('#projectSource').text(data.data.projectSource);
        $('#projectTask').text(data.data.projectTask);
        $('#remark').text(data.data.remark);
        var baseInfoImgVOs = data.data.baseInfoImgVOs;
        var file_display_html = '';
        baseInfoImgVOs.map(function (item, index) {
            file_display_html += '<div class="file-box">\n' +
                '                                                                                    <div class="file">\n' +
                '                                                                                        <span class="corner"></span>\n' +
                '                                                                                        <div class="image" style="background:url(' + item.thumbnailAddr + ');background-size:cover;">\n' +
                '                                                                                        </div>\n' +
                '                                                                                        <div class="file-name">\n' +
                '                                                                                            文件\n' +
                '                                                                                            <br/>\n' +
                '                                                                                            <small>'+ item.createTime +'</small>\n' +
                '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/baseinfofile?fileId='+item.imgAddr+'">下载</a>\n' +
                '                                                                                        </div>\n' +
                '                                                                                    </div>\n' +
                '                                                                                </div>'
        });
        $('#files_display_div').html("");
        $('#files_display_div').html(file_display_html);
    }

    <!--审批modal-->
    $("[name='baseinfo-checkbox']").bootstrapSwitch({
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
        var baseInfoId = $('#plantName').attr("data-id");
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
                if (data.code == 1002) {
                    $('#monthly_report_check_div').html('');
                    $('#monthly_report_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 成 功</h1></div> <div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                } else {
                    $('#monthly_report_check_div').html('');
                    $('#monthly_report_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
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
    $('#monthly_report_check_div').on('click', '#check_result_confirm_btn', function (e) {
        $('#main_content', parent.document).load('reporter/projectmonths');
    })
})