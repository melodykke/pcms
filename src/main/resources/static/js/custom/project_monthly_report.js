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
            projectMonthlyReportVO.statisticalLeader = $('#statistical_leader').val();
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
    }).on('changeDate',function(ev){
        // 对每个所填项增加本年和开工至今的统计数据
        if($('#submit_date').val()!=''){
            $.ajax({
                url: "reporter/getmonthlyreportposthistory",
                type: 'GET',
                data: {'currentDate': $('#submit_date').val()},
                dataType: 'json',
                success: function (data) {
                    var content = data.data;
                    cont = content;
                    $('#year_civil_engineering').html(content.yearCivilEngineering);
                    $('#sofar_civil_engineering').html(content.sofarCivilEngineering);
                    $('#year_metal_mechanism').html(content.yearMetalMechanism);
                    $('#sofar_metal_mechanism').html(content.sofarMetalMechanism);
                    $('#year_independent_cost').html(content.yearIndependentCost);
                    $('#sofar_independent_cost').html(content.sofarIndependentCost);
                    $('#year_electromechanical_equipment').html(content.yearElectromechanicalEquipment);
                    $('#sofar_electromechanical_equipment').html(content.sofarElectromechanicalEquipment);
                    $('#year_temporary_work').html(content.yearTemporaryWork);
                    $('#sofar_temporary_work').html(content.sofarTemporaryWork);
                    $('#year_resettlement_arrangement').html(content.yearResettlementArrangement);
                    $('#sofar_resettlement_arrangement').html(content.sofarResettlementArrangement);
                    $('#year_environmental_protection').html(content.yearEnvironmentalProtection);
                    $('#sofar_environmental_protection').html(content.sofarEnvironmentalProtection);
                    $('#year_water_conservation').html(content.yearWaterConservation);
                    $('#sofar_water_conservation').html(content.sofarWaterConservation);
                    $('#year_other_cost').html(content.yearOtherCost);
                    $('#sofar_other_cost').html(content.sofarOtherCost);
                    $('#year_source_central_investment').html(content.yearSourceCentralInvestment);
                    $('#sofar_source_central_investment').html(content.sofarSourceCentralInvestment);
                    $('#year_source_provincial_investment').html(content.yearSourceProvincialInvestment);
                    $('#sofar_source_provincial_investment').html(content.sofarSourceProvincialInvestment);
                    $('#year_source_local_investment').html(content.yearSourceLocalInvestment);
                    $('#sofar_source_local_investment').html(content.sofarSourceLocalInvestment);
                    $('#year_available_central_investment').html(content.yearAvailableCentralInvestment);
                    $('#sofar_available_central_investment').html(content.sofarAvailableCentralInvestment);
                    $('#year_available_provincial_investment').html(content.yearAvailableProvincialInvestment);
                    $('#sofar_available_provincial_investment').html(content.sofarAvailableProvincialInvestment);
                    $('#year_available_local_investment').html(content.yearAvailableLocalInvestment);
                    $('#sofar_available_local_investment').html(content.sofarAvailableLocalInvestment);
                    $('#year_open_dug').html(content.yearOpenDug);
                    $('#sofar_open_dug').html(content.sofarOpenDug);
                    $('#year_backfill').html(content.yearBackfill);
                    $('#sofar_backfill').html(content.sofarBackfill);
                    $('#year_concrete').html(content.yearConcrete);
                    $('#sofar_concrete').html(content.sofarConcrete);
                    $('#year_grout').html(content.yearGrout);
                    $('#sofar_grout').html(content.sofarGrout);
                    $('#year_hole_dug').html(content.yearHoleDug);
                    $('#sofar_hole_dug').html(content.sofarHoleDug);
                    $('#year_masonry').html(content.yearMasonry);
                    $('#sofar_masonry').html(content.sofarMasonry);
                    $('#year_rebar').html(content.yearRebar);
                    $('#sofar_rebar').html(content.sofarRebar);
                    $('#year_labour_force').html(content.yearLabourForce);
                    $('#sofar_labour_force').html(content.sofarLabourForce);
                }
            })
        }
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
    });

    // 控制填报单元格与本年和开工至今数据的加和
    var cont = null;
    var errorNoteFunc = function errorNote() {
        swal({
            title: "错误！",
            text: "请先选取填报月份！",
            type: "error",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认!",
            closeOnConfirm: false
        });
    };
    $('#civil_engineering').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#civil_engineering').val() == '' ? '0' : parseFloat($('#civil_engineering').val());
            $('#year_civil_engineering').html(parseFloat(cont.yearCivilEngineering)+value);
            $('#sofar_civil_engineering').html(parseFloat(cont.sofarCivilEngineering)+value);
        }
    });
    $('#metal_mechanism').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#metal_mechanism').val() == '' ? '0' : parseFloat($('#metal_mechanism').val());
            $('#year_metal_mechanism').html(parseFloat(cont.yearMetalMechanism)+value);
            $('#sofar_metal_mechanism').html(parseFloat(cont.sofarMetalMechanism)+value);
        }
    });
    $('#independent_cost').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#independent_cost').val() == '' ? '0' : parseFloat($('#independent_cost').val());
            $('#year_independent_cost').html(parseFloat(cont.yearIndependentCost)+value);
            $('#sofar_independent_cost').html(parseFloat(cont.sofarIndependentCost)+value);
        }
    });
    $('#electromechanical_equipment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#electromechanical_equipment').val() == '' ? '0' : parseFloat($('#electromechanical_equipment').val());
            $('#year_electromechanical_equipment').html(parseFloat(cont.yearElectromechanicalEquipment)+value);
            $('#sofar_electromechanical_equipment').html(parseFloat(cont.sofarElectromechanicalEquipment)+value);
        }
    });
    $('#temporary_work').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#temporary_work').val() == '' ? '0' : parseFloat($('#temporary_work').val());
            $('#year_temporary_work').html(parseFloat(cont.yearTemporaryWork)+value);
            $('#sofar_temporary_work').html(parseFloat(cont.sofarTemporaryWork)+value);
        }
    });
    $('#resettlement_arrangement').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#resettlement_arrangement').val() == '' ? '0' : parseFloat($('#resettlement_arrangement').val());
            $('#year_resettlement_arrangement').html(parseFloat(cont.yearResettlementArrangement)+value);
            $('#sofar_resettlement_arrangement').html(parseFloat(cont.sofarResettlementArrangement)+value);
        }
    });
    $('#environmental_protection').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#environmental_protection').val() == '' ? '0' : parseFloat($('#environmental_protection').val());
            $('#year_environmental_protection').html(parseFloat(cont.yearEnvironmentalProtection)+value);
            $('#sofar_environmental_protection').html(parseFloat(cont.sofarEnvironmentalProtection)+value);
        }
    });
    $('#water_conservation').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#water_conservation').val() == '' ? '0' : parseFloat($('#water_conservation').val());
            $('#year_water_conservation').html(parseFloat(cont.yearWaterConservation)+value);
            $('#sofar_water_conservation').html(parseFloat(cont.sofarWaterConservation)+value);
        }
    });
    $('#other_cost').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#other_cost').val() == '' ? '0' : parseFloat($('#other_cost').val());
            $('#year_other_cost').html(parseFloat(cont.yearOtherCost)+value);
            $('#sofar_other_cost').html(parseFloat(cont.sofarOtherCost)+value);
        }
    });
    $('#source_central_investment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#source_central_investment').val() == '' ? '0' : parseFloat($('#source_central_investment').val());
            $('#year_source_central_investment').html(parseFloat(cont.yearSourceCentralInvestment)+value);
            $('#sofar_source_central_investment').html(parseFloat(cont.sofarSourceCentralInvestment)+value);
        }
    });
    $('#source_provincial_investment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#source_provincial_investment').val() == '' ? '0' : parseFloat($('#source_provincial_investment').val());
            $('#year_source_provincial_investment').html(parseFloat(cont.yearSourceProvincialInvestment)+value);
            $('#sofar_source_provincial_investment').html(parseFloat(cont.sofarSourceProvincialInvestment)+value);
        }
    });
    $('#source_local_investment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#source_local_investment').val() == '' ? '0' : parseFloat($('#source_local_investment').val());
            $('#year_source_local_investment').html(parseFloat(cont.yearSourceLocalInvestment)+value);
            $('#sofar_source_local_investment').html(parseFloat(cont.sofarSourceLocalInvestment)+value);
        }
    });
    $('#available_central_investment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#available_central_investment').val() == '' ? '0' : parseFloat($('#available_central_investment').val());
            $('#year_available_central_investment').html(parseFloat(cont.yearAvailableCentralInvestment)+value);
            $('#sofar_available_central_investment').html(parseFloat(cont.sofarAvailableCentralInvestment)+value);
        }
    });
    $('#available_provincial_investment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#available_provincial_investment').val() == '' ? '0' : parseFloat($('#available_provincial_investment').val());
            $('#year_available_provincial_investment').html(parseFloat(cont.yearAvailableProvincialInvestment)+value);
            $('#sofar_available_provincial_investment').html(parseFloat(cont.sofarAvailableProvincialInvestment)+value);
        }
    });
    $('#available_local_investment').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#available_local_investment').val() == '' ? '0' : parseFloat($('#available_local_investment').val());
            $('#year_available_local_investment').html(parseFloat(cont.yearAvailableLocalInvestment)+value);
            $('#sofar_available_local_investment').html(parseFloat(cont.sofarAvailableLocalInvestment)+value);
        }
    });
    $('#open_dug').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#open_dug').val() == '' ? '0' : parseFloat($('#open_dug').val());
            $('#year_open_dug').html(parseFloat(cont.yearOpenDug)+value);
            $('#sofar_open_dug').html(parseFloat(cont.sofarOpenDug)+value);
        }
    });
    $('#backfill').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#backfill').val() == '' ? '0' : parseFloat($('#backfill').val());
            $('#year_backfill').html(parseFloat(cont.yearBackfill)+value);
            $('#sofar_backfill').html(parseFloat(cont.sofarBackfill)+value);
        }
    });
    $('#concrete').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#concrete').val() == '' ? '0' : parseFloat($('#concrete').val());
            $('#year_concrete').html(parseFloat(cont.yearConcrete)+value);
            $('#sofar_concrete').html(parseFloat(cont.sofarConcrete)+value);
        }
    });
    $('#grout').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#grout').val() == '' ? '0' : parseFloat($('#grout').val());
            $('#year_grout').html(parseFloat(cont.yearGrout)+value);
            $('#sofar_grout').html(parseFloat(cont.sofarGrout)+value);
        }
    });
    $('#hole_dug').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#hole_dug').val() == '' ? '0' : parseFloat($('#hole_dug').val());
            $('#year_hole_dug').html(parseFloat(cont.yearHoleDug)+value);
            $('#sofar_hole_dug').html(parseFloat(cont.sofarHoleDug)+value);
        }
    });
    $('#masonry').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#masonry').val() == '' ? '0' : parseFloat($('#masonry').val());
            $('#year_masonry').html(parseFloat(cont.yearMasonry)+value);
            $('#sofar_masonry').html(parseFloat(cont.sofarMasonry)+value);
        }
    });
    $('#rebar').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#rebar').val() == '' ? '0' : parseFloat($('#rebar').val());
            $('#year_rebar').html(parseFloat(cont.yearRebar)+value);
            $('#sofar_rebar').html(parseFloat(cont.sofarRebar)+value);
        }
    });
    $('#labour_force').on('input', function (e) {
        if(cont === null) {
            errorNoteFunc();
        } else {
            var value = $('#labour_force').val() == '' ? '0' : parseFloat($('#labour_force').val());
            $('#year_labour_force').html(parseFloat(cont.yearLabourForce)+value);
            $('#sofar_labour_force').html(parseFloat(cont.sofarLabourForce)+value);
        }
    });
});


