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
        $('#re_plantName').text(data.data.plantName);
        $('#re_plantName').attr("data-id",data.data.baseInfoId );
            if (data.data.state == 0) {
                $('#state').text("待审核");
                $('#repost_div').html(' <a id="basic_info_repeat" data-toggle="tooltip" data-placement="bottom" title="点击后重新填报！"><i class="fa fa-repeat fa-lg"></i></a>');
            } else if (data.data.state == -1){
                $('#state').text("审核未通过");
                $('#repost_div').html(' <a id="basic_info_repeat" data-toggle="tooltip" data-placement="bottom" title="点击后重新填报！"><i class="fa fa-repeat fa-lg"></i></a>');
            } else if (data.data.state == 1) {
                $('#state').text("审核通过");
            }
            if (data.data.state == 1) {
                $('#check_btn').html('<span class="label label-primary"><i class="fa fa-check"></i> 已审批通过</span>');
            } else if(data.data.state == -1){
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

        $('#re_plantName_h2').text(data.data.plantName);
        $('#re_projectType').text(data.data.projectType);
        $('#re_level').text(data.data.level);
        $('#re_longitude').text(data.data.longitude);
        $('#re_scale').text(data.data.scale);
        $('#re_storage').text(data.data.storage);
        $('#re_hasSignedConstructionContract').text(data.data.hasSignedConstructionContract);
        $('#re_supervisorBid').text(data.data.supervisorBid);
        $('#re_legalPersonName').text(data.data.legalPersonName);
        $('#re_legalRepresentativeName').text(data.data.legalRepresentativeName);
        $('#re_location').text(data.data.location);
        $('#re_latitude').text(data.data.latitude);
        $('#re_timeLimit').text(data.data.timeLimit);
        $('#re_utilizablCapacity').text(data.data.utilizablCapacity);
        $('#re_hasProjectCompleted').text(data.data.hasProjectCompleted);
        $('#re_hasAcceptCompletion').text(data.data.hasAcceptCompletion);
        $('#re_damType').text(data.data.damType);
        $('#re_maxDamHeight').text(data.data.maxDamHeight);
        $('#re_floodControlElevation').text(data.data.floodControlElevation);
        $('#re_watersupply').text(data.data.watersupply);
        $('#re_areaCoverage').text(data.data.areaCoverage);
        $('#re_ruralHumanWater').text(data.data.ruralHumanWater);
        $('#re_centralInvestment').text(data.data.centralInvestment);
        $('#re_localInvestment').text(data.data.localInvestment);
        $('#re_centralAccumulativePayment').text(data.data.centralAccumulativePayment);
        $('#re_localAccumulativePayment').text(data.data.localAccumulativePayment);
        $('#re_provincialLoan').text(data.data.provincialLoan);
        $('#re_catchmentArea').text(data.data.catchmentArea);
        $('#re_spillway').text(data.data.spillway);
        $('#re_irrigatedArea').text(data.data.irrigatedArea);
        $('#re_installedCapacity').text(data.data.installedCapacity);
        $('#re_livestock').text(data.data.livestock);
        $('#re_waterSupplyPopulation').text(data.data.waterSupplyPopulation);
        $('#re_provincialInvestment').text(data.data.provincialInvestment);
        $('#re_totalInvestment').text(data.data.totalInvestment);
        $('#re_provincialAccumulativePayment').text(data.data.provincialAccumulativePayment);
        $('#re_totalAccumulativePayment').text( parseInt(data.data.centralAccumulativePayment) + parseInt(data.data.provincialAccumulativePayment) + parseInt(data.data.localAccumulativePayment));
        $('#re_unitProjectAmount').text(data.data.unitProjectAmount);
        $('#re_unitProjectOverview').text(data.data.unitProjectOverview);
        $('#re_cellProjectAmount').text(data.data.cellProjectAmount);
        $('#re_cellProjectOverview').text(data.data.cellProjectOverview);
        $('#re_branchProjectAmount').text(data.data.branchProjectAmount);
        $('#re_branchProjectOverview').text(data.data.branchProjectOverview);
        $('#re_constructionLand').text(data.data.constructionLand);
        $('#re_county').text(data.data.county);
        $('#re_landReclamationPlan').text(data.data.landReclamationPlan);
        $('#re_overview').text(data.data.overview);
        $('#re_projectSource').text(data.data.projectSource);
        $('#re_projectTask').text(data.data.projectTask);
        $('#re_remark').text(data.data.remark);
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
       /* top.location.reload()*/
        $('#main_content', parent.document).load('baseinfo/baseinfoshow');
    })


    $('#repost_div').on('click', '#basic_info_repeat', function (e) {
        $('#base_info_modal', parent.document).modal();
    })

})