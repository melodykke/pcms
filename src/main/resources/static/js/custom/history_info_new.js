$(function () {
    var validator=$('#form').validate({
        errorPlacement: function (error, element)
        {
            element.before(error);
        },
        rules: {
            confirm: {
                equalTo: "#password"
            }
        },
    });
    validator.resetForm();

    $("#history_info_file").fileinput({
        language:'zh',
        theme:'fa',
        uploadUrl: 'http://www.baidu.com', // you must set a valid URL here else you will get an error
        uploadExtraData:{"month1":123},
        allowedFileExtensions: ['jpg', 'png', 'gif','pdf'],
        overwriteInitial: false,
        layoutTemplates :{
            // actionDelete:'', //去除上传预览的缩略图中的删除图标
            actionUpload:'',//去除上传预览缩略图中的上传图片；
            // actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
        },
        autoReplace:true,
        maxFileSize: 1000,
        maxFilesNum: 10,


        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    });
    $('#history_info_approve_submit').click(function () {
        var checkedOfAll=$("#my-checkbox").prop("checked");
        var checkinfo=$('#approve_area').val();
        alert(checkedOfAll+checkinfo)
    })

    $('#history_info_submit').click(function () {
        if (validator.form()){
            swal({
                title: "确认提交吗?",
                text: "请检查数据是否填写正确后再提交!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "已确认,提交!",
                cancelButtonText:"取消",
                closeOnConfirm: false
            }, function (){
                var historyMonthlyReportStatisticVO = {};
                historyMonthlyReportStatisticVO.historyCivilEngineering = $('#historyCivilEngineering').val();
                historyMonthlyReportStatisticVO.historyMetalMechanism = $('#historyMetalMechanism').val();
                historyMonthlyReportStatisticVO.historyTemporaryWork = $('#historyTemporaryWork').val();
                historyMonthlyReportStatisticVO.historyResettlementArrangement = $('#historyResettlementArrangement').val();
                historyMonthlyReportStatisticVO.historyWaterConservation = $('#historyWaterConservation').val();
                historyMonthlyReportStatisticVO.historyIndependentCost = $('#historyIndependentCost').val();
                historyMonthlyReportStatisticVO.historyElectromechanicalEquipment = $('#historyElectromechanicalEquipment').val();
                historyMonthlyReportStatisticVO.historyEnvironmentalProtection = $('#historyEnvironmentalProtection').val();
                historyMonthlyReportStatisticVO.historyOtherCost = $('#historyOtherCost').val();
                historyMonthlyReportStatisticVO.historySourceCentralInvestment = $('#historySourceCentralInvestment').val();
                historyMonthlyReportStatisticVO.historyAvailableCentralInvestment = $('#historyAvailableCentralInvestment').val();
                historyMonthlyReportStatisticVO.historySourceProvincialInvestment = $('#historySourceProvincialInvestment').val();
                historyMonthlyReportStatisticVO.historyAvailableProvincialInvestment = $('#historyAvailableProvincialInvestment').val();
                historyMonthlyReportStatisticVO.historySourceLocalInvestment = $('#historySourceLocalInvestment').val();
                historyMonthlyReportStatisticVO.historyAvailableLocalInvestment = $('#historyAvailableLocalInvestment').val();
                historyMonthlyReportStatisticVO.historyOpenDug = $('#historyOpenDug').val();
                historyMonthlyReportStatisticVO.historyConcrete = $('#historyConcrete').val();
                historyMonthlyReportStatisticVO.historyRebar = $('#historyRebar').val();
                historyMonthlyReportStatisticVO.historyHoleDug = $('#historyHoleDug').val();
                historyMonthlyReportStatisticVO.historyGrout = $('#historyGrout').val();
                historyMonthlyReportStatisticVO.historyLabourForce = $('#historyLabourForce').val();
                historyMonthlyReportStatisticVO.historyBackfill = $('#historyBackfill').val();
                historyMonthlyReportStatisticVO.historyMasonry = $('#historyMasonry').val();
                $.ajax({
                    url: 'monthlyreport/savehistorystatistic',
                    type: 'POST',
                    data: JSON.stringify(historyMonthlyReportStatisticVO),
                   contentType: 'application/json',
                    success: function (data) {
                       if (data.code == 1002) {
                           swal({
                                   title:"成功!",
                                   text:"已经成功提交!",
                                   type:"success"
                               },function () {
                                   $("#pre_progress_modal").modal('hide');
                                   $('#main_content', parent.document).load('monthlyreport/tomonthshistoryshow');
                               }
                           )
                       }
                    }
                });
            });
        }
        else {
            swal({
                title:"错误!",
                text:"有数据未填写，或者格式错误!",
                type:"error"
            })
        }
    });
});