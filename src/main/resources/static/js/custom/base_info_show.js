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
        } else if(data.data.state == -1){
            $('#check_btn').html('<span class="label label-danger"><i class="fa fa-times"></i> 审批未通过</span><a id="basic_info_repeat" data-toggle="tooltip" data-placement="bottom" title="点击后重新填报！"><i class="fa fa-repeat fa-lg"></i></a>');
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
    $('#monthly_report_check_div').on('click', '#check_result_confirm_btn', function (e) {
        $('#main_content', parent.document).load('reporter/projectmonths');
    })
    $('#check_btn').on('click', '#basic_info_repeat', function (e) {
        $("#re_basic_info_form").steps({
            bodyTag: "fieldset",
            transitionEffect: "slideLeft",
            onStepChanging: function (event, currentIndex, newIndex) {
                // Always allow going backward even if the current step contains invalid fields!
                if (currentIndex > newIndex) {
                    return true;
                }

                // Forbid suppressing "Warning" step if the user is to young
                if (newIndex === 3 && Number($("#age").val()) < 18) {
                    return false;
                }

                var form = $(this);

                // Clean up if user went backward before
                if (currentIndex < newIndex) {
                    // To remove error styles
                    $(".body:eq(" + newIndex + ") label.error", form).remove();
                    $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
                }

                // Disable validation on fields that are disabled or hidden.
                form.validate().settings.ignore = ":disabled,:hidden";

                // Start validation; Prevent going forward if false
                return form.valid();
            },
            onStepChanged: function (event, currentIndex, priorIndex) {
                // Suppress (skip) "Warning" step if the user is old enough.
                if (currentIndex === 2 && Number($("#age").val()) >= 18) {
                    $(this).steps("next");
                }

                // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
                if (currentIndex === 2 && priorIndex === 2) {
                    $(this).steps("previous");
                }
            },
            onFinishing: function (event, currentIndex) {
                var form = $(this);

                // Disable validation on fields that are disabled.
                // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
                form.validate().settings.ignore = ":disabled";

                // Start validation; Prevent form submission if false
                return form.valid();
            },
            onFinished: function (event, currentIndex)
            {
                var baseInfoVO = {};
                var form = $(this);
                baseInfoVO.plantName = $('#re_plantName').val();
                baseInfoVO.projectType = $('#re_projectType').val();
                baseInfoVO.level = $('#re_level').val();
                baseInfoVO.longitude = $('#re_longitude').val();
                baseInfoVO.scale = $('#re_scale').val();
                baseInfoVO.storage = $('#re_storage').val();
                baseInfoVO.hasSignedConstructionContract = $('#re_hasSignedConstructionContract').val();
                baseInfoVO.supervisorBid = $('#supervisorBid').val();
                baseInfoVO.legalPersonName = $('#legalPersonName').val();
                baseInfoVO.legalRepresentativeName = $('#legalRepresentativeName').val();
                baseInfoVO.location = $('#location').val();
                baseInfoVO.latitude = $('#latitude').val();
                baseInfoVO.timeLimit = $('#timeLimit').val();
                baseInfoVO.utilizablCapacity = $('#utilizablCapacity').val();
                baseInfoVO.hasProjectCompleted = $('#hasProjectCompleted').val();
                baseInfoVO.hasAcceptCompletion = $('#hasAcceptCompletion').val();
                baseInfoVO.damType = $('#damType').val();
                baseInfoVO.maxDamHeight = $('#maxDamHeight').val();
                baseInfoVO.floodControlElevation = $('#floodControlElevation').val();
                baseInfoVO.watersupply = $('#watersupply').val();
                baseInfoVO.areaCoverage = $('#areaCoverage').val();
                baseInfoVO.ruralHumanWater = $('#ruralHumanWater').val();
                baseInfoVO.centralInvestment = $('#centralInvestment').val();
                baseInfoVO.localInvestment = $('#localInvestment').val();
                baseInfoVO.centralAccumulativePayment = $('#centralAccumulativePayment').val();
                baseInfoVO.localAccumulativePayment = $('#localAccumulativePayment').val();
                baseInfoVO.provincialLoan = $('#provincialLoan').val();
                baseInfoVO.catchmentArea = $('#catchmentArea').val();
                baseInfoVO.spillway = $('#spillway').val();
                baseInfoVO.irrigatedArea = $('#irrigatedArea').val();
                baseInfoVO.installedCapacity = $('#installedCapacity').val();
                baseInfoVO.livestock = $('#livestock').val();
                baseInfoVO.waterSupplyPopulation = $('#waterSupplyPopulation').val();
                baseInfoVO.provincialInvestment = $('#provincialInvestment').val();
                baseInfoVO.provincialAccumulativePayment = $('#provincialAccumulativePayment').val();
                baseInfoVO.unitProjectAmount = $('#unitProjectAmount').val();
                baseInfoVO.unitProjectOverview = $('#unitProjectOverview').val();
                baseInfoVO.cellProjectAmount = $('#cellProjectAmount').val();
                baseInfoVO.cellProjectOverview = $('#cellProjectOverview').val();
                baseInfoVO.branchProjectAmount = $('#branchProjectAmount').val();
                baseInfoVO.branchProjectOverview = $('#branchProjectOverview').val();
                baseInfoVO.remark = $('#remark').val();
                baseInfoVO.constructionLand = $('#constructionLand').val();
                baseInfoVO.county = $('#county').val();
                baseInfoVO.landReclamationPlan = $('#landReclamationPlan').val();
                baseInfoVO.overview = $('#overview').val();
                baseInfoVO.projectSource = $('#projectSource').val();
                baseInfoVO.projectTask = $('#projectTask').val();
                console.log(baseInfoVO);
                if (rtFileTempPath) {
                    baseInfoVO.rtFileTempPath = rtFileTempPath;
                }
                if (uploadFileFlag == true) {
                    swal({
                        title: "确认提交吗?",
                        text: "请检查数据是否填写正确后再提交!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "已确认,提交!",
                        closeOnConfirm: false
                    }, function () {
                        $.ajax({
                            url: saveBaseInfoUrl,
                            type: 'POST',
                            data: JSON.stringify(baseInfoVO),
                            contentType: 'application/json',
                            success: function (data) {
                                if (data.code == 1002) {
                                    swal({
                                        title: "成功",
                                        text: "项目基础信息提交成功！",
                                        type: "success",
                                    }, function () {
                                        $("#base_info_modal").modal('hide');
                                        parent.$('#main_content').load('baseinfo/baseinfoshow');
                                        $('#small-chat').hide();
                                    })
                                } else {
                                    console.log(data)
                                    swal("失败!", data.msg, "error");
                                }
                            }
                        })

                    });
                } else {
                    swal({
                        title: "稍等...",
                        text: "存在未上传或正在传输的文件!",
                        type: "warning",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确认!",
                        closeOnConfirm: false
                    });
                }
            }
        }).validate({
            errorPlacement: function (error, element) {
                element.before(error);
            },
            rules: {
                confirm: {
                    equalTo: "#password"
                }
            }
        });
    })

})