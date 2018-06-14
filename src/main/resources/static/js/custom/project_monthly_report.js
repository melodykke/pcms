$(document).ready(function(){
    var saveReportUrl = '/monthlyreport/savereport';
    var saveFilesUrl = '/monthlyreport/addfiles';

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
            if (currentIndex === 2 && priorIndex === 3)
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
            var monthlyReport = {};
            var form = $(this);
            monthlyReport.civilEngineering = $('#civil_engineering').val();
            monthlyReport.metalMechanism = $('#metal_mechanism').val();
            monthlyReport.independentCost = $('#independent_cost').val();
            monthlyReport.electromechanicalEquipment = $('#electromechanical_equipment').val();
            monthlyReport.temporaryWork = $('#temporary_work').val();
            monthlyReport.reserveFunds = $('#reserve_funds').val();
            monthlyReport.resettlementArrangement = $('#resettlement_arrangement').val();
            monthlyReport.environmentalProtection = $('#environmental_protection').val();
            monthlyReport.waterConservationl = $('#water_conservation').val();
            monthlyReport.otherCost = $('#other_cost').val();
            monthlyReport.openDug = $('#open_dug').val();
            monthlyReport.backfill = $('#backfill').val();
            monthlyReport.concrete = $('#concrete').val();
            monthlyReport.grout = $('#grout').val();
            monthlyReport.holeDug = $('#hole_dug').val();
            monthlyReport.masonry = $('#masonry').val();
            monthlyReport.rebar = $('#rebar').val();
            monthlyReport.submitDate = $('#submit_date').val();
            monthlyReport.labourForce = $('#labour_force').val();
            monthlyReport.constructionContent = $('#construction_content').val();
            monthlyReport.difficulty = $('#difficulty').val();
            monthlyReport.suggestion = $('#suggestion').val();
            monthlyReport.visualProgress = $('#visual_progress').val();
            monthlyReport.measure = $('#measure').val();
            monthlyReport.remark = $('#remark').val();
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
                        data: JSON.stringify(monthlyReport),
                        contentType: 'application/json',
                        success: function (data) {
                            alert('chenggong')
                        }
                    })
                    swal("成功!", "已经成功提交!", "success");
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


    //获得额外参数的方法
    submitDate = function() {
        var remindTime = $("#submit_date").val();
        var str = remindTime.toString();
        str = str.replace("/-/g", "/");
        var oDate = new Date(str);
        return oDate.getFullYear()+"/"+parseInt(oDate.getMonth()+1);
    };

    // 文件上传
    $("#uploadfile").fileinput({
        language:'zh',
        theme: 'fa',
        uploadUrl: saveFilesUrl, // you must set a valid URL here else you will get an error
        uploadAsync:false,
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1000000,
        maxFilesNum: 10,
        allowedFileTypes: ['image', 'video', 'flash'],
      /*  slugCallback: function(filename) {
            uploadFileFlag = false;
            return filename.replace('(', '_').replace(']', '_');
        },*/
        uploadExtraData: function(previewId, index) {   //额外参数的关键点
            var obj = {};
            obj.date = submitDate();
            return obj;
        }

    });
    /* 清空文件后响应事件*/
    $("#uploadfile").on("filecleared",function(event, data, msg){
        uploadFileFlag = true;
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
        uploadFileFlag = true;
        console.log(data.response.code)
    });


});


