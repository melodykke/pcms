$(document).ready(function(){
    var saveReportUrl = '/monthlyreport/savereport';
    var saveFilesUrl = '/monthlyreport/addfiles';
    var rtFileTempPath; // 服务器文件暂存地址
    var uploadFileFlag = true;
    $("#wizard").steps();
    $("#form").steps({
        bodyTag: "fieldset",
        onStepChanging: function (event, currentIndex, newIndex)
        {
            // Always allow going backward even if the current step contains invalid fields!
            if (currentIndex > newIndex)
            {
                return true;
            }

            // Forbid suppressing "Warning" step if the user is to young
            if (newIndex === 3 && Number($("#age").val()) < 18)
            {
                return false;
            }

            var form = $(this);

            // Clean up if user went backward before
            if (currentIndex < newIndex)
            {
                // To remove error styles
                $(".body:eq(" + newIndex + ") label.error", form).remove();
                $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
            }

            // Disable validation on fields that are disabled or hidden.
            form.validate().settings.ignore = ":disabled,:hidden";

            // Start validation; Prevent going forward if false
            return form.valid();
        },
        onStepChanged: function (event, currentIndex, priorIndex)
        {
            // Suppress (skip) "Warning" step if the user is old enough.
            if (currentIndex === 2 && Number($("#age").val()) >= 18)
            {
                $(this).steps("next");
            }

            // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
            if (currentIndex === 2 && priorIndex === 2)
            {
                $(this).steps("previous");
            }
        },
        onFinishing: function (event, currentIndex)
        {
            var form = $(this);

            // Disable validation on fields that are disabled.
            // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
            form.validate().settings.ignore = ":disabled";

            // Start validation; Prevent form submission if false
            return form.valid();
        },
        onFinished: function (event, currentIndex)
        {
            var projectMonthlyReportVO = {};
            var form = $(this);
            projectMonthlyReportVO.civilEngineering = $('#civil_engineering').val();
            projectMonthlyReportVO.metalMechanism = $('#metal_mechanism').val();
            projectMonthlyReportVO.independentCost = $('#independent_cost').val();
            projectMonthlyReportVO.electromechanicalEquipment = $('#electromechanical_equipment').val();
            projectMonthlyReportVO.temporaryWork = $('#temporary_work').val();
            projectMonthlyReportVO.reserveFunds = $('#reserve_funds').val();
            projectMonthlyReportVO.resettlementArrangement = $('#resettlement_arrangement').val();
            projectMonthlyReportVO.environmentalProtection = $('#environmental_protection').val();
            projectMonthlyReportVO.waterConservation = $('#water_conservation').val();
            projectMonthlyReportVO.otherCost = $('#other_cost').val();
            projectMonthlyReportVO.sourceCentralInvestment = $('#source_central_investment').val();
            projectMonthlyReportVO.sourceProvincialInvestment = $('#source_provincial_investment').val();
            projectMonthlyReportVO.sourceLocalInvestment = $('#source_local_investment').val();
            projectMonthlyReportVO.availableCentralInvestment = $('#available_central_investment').val();
            projectMonthlyReportVO.availableProvincialInvestment = $('#available_provincial_investment').val();
            projectMonthlyReportVO.availableLocalInvestment = $('#available_local_investment').val();
            projectMonthlyReportVO.openDug = $('#open_dug').val();
            projectMonthlyReportVO.backfill = $('#backfill').val();
            projectMonthlyReportVO.concrete = $('#concrete').val();
            projectMonthlyReportVO.grout = $('#grout').val();
            projectMonthlyReportVO.holeDug = $('#hole_dug').val();
            projectMonthlyReportVO.masonry = $('#masonry').val();
            projectMonthlyReportVO.rebar = $('#rebar').val();
            projectMonthlyReportVO.submitDate = $('#submit_date').val()+'-01';
            projectMonthlyReportVO.labourForce = $('#labour_force').val();
            projectMonthlyReportVO.constructionContent = $('#construction_content').val();
            projectMonthlyReportVO.difficulty = $('#difficulty').val();
            projectMonthlyReportVO.suggestion = $('#suggestion').val();
            projectMonthlyReportVO.visualProgress = $('#visual_progress').val();
            projectMonthlyReportVO.measure = $('#measure').val();
            projectMonthlyReportVO.remark = $('#remark').val();
            if (rtFileTempPath) {
                projectMonthlyReportVO.rtFileTempPath = rtFileTempPath;
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
                        url: saveReportUrl,
                        type: 'POST',
                        data: JSON.stringify(projectMonthlyReportVO),
                        contentType: 'application/json',
                        success: function (data) {
                           if (data.code == 1002) {
                               swal({
                                   title: "成功",
                                   text: "月报提交成功！",
                                   type: "success",
                               }, function () {
                                   parent.$('#main_content').load('reporter/projectmonths');
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
        errorPlacement: function (error, element)
        {
            element.before(error);
        },
        rules: {
            confirm: {
                equalTo: "#password"
            }
        }
    });

    $('#data_4 .input-group.date').datepicker({
        language: "zh-CN",
        format: "yyyy-mm",
        minViewMode: 1,
        keyboardNavigation: false,
        forceParse: false,
        forceParse: false,
        autoclose: true,
        todayHighlight: true
    });

    // 文件上传
    $("#uploadfile").fileinput({
        language:'zh',
        theme: 'fa',
        uploadUrl: saveFilesUrl, // you must set a valid URL here else you will get an error
        uploadAsync:false,
        allowedFileExtensions: ['jpg', 'png', 'gif', 'docx', 'doc', 'xlsx','xls', 'pdf', 'pjeg', 'mp4','3gp','avi'],
        overwriteInitial: false,
        maxFileSize: 1000000,
        maxFilesNum: 10,
        layoutTemplates:{
            actionUpload:'',
            actionDelete:''
        }
      /*  slugCallback: function(filename) {
            uploadFileFlag = false;
            return filename.replace('(', '_').replace(']', '_');
        },
        uploadExtraData: function(previewId, index) {   //额外参数的关键点
            var obj = {};
            obj.date = submitDate();
            return obj;
        }*/

    });
    /* 清空文件后响应事件*/
    $("#uploadfile").on("filecleared",function(event, data, msg){
        uploadFileFlag = true;
        rtFileTempPath = null;
    });
    /*选择文件后处理事件*/
    $("#uploadfile").on("filebatchselected", function(event, files) {
        uploadFileFlag = false;

    });
    //同步上传错误处理
    $('#uploadfile').on('filebatchuploaderror', function(event, data, msg) {
        uploadFileFlag = false;
    });
    //同步上传返回结果处理
    $("#uploadfile").on("filebatchuploadsuccess", function (event, data, previewId, index) {
        if (data.response.code == 1002) {
            uploadFileFlag = true;
            rtFileTempPath = data.response.data;

        }
    });

    $('#uploadfile').click(function () {
        $("#uploadfile").fileinput('refresh');
    })
});


