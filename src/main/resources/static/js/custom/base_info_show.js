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
        $('#year_tag').text(data.data.year);
        $('#month').text(data.data.month+' 月');
        data.data.state == 0 ? $('#state').text("待审核") :  $('#state').text("已审核");
        if (data.data.state == 1) {
            $('#check_btn').html('<span class="label label-primary"><i class="fa fa-check"></i> 已审批通过</span>');
        }
        $('#submitter').text(data.data.submitter);
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



})