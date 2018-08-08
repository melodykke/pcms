$(function () {
    $.ajax({
        url: "tender/isedit?tenderId="+tenderId,
        type: "GET",
        dataType: "json",
        success: function (data) {
            var tender = data.data;
            $('#tenderFilingUnit').val(tender.tenderFilingUnit);
            $('#nameOfLots').val(tender.nameOfLots);
            $('#bidPlanDate').val(tender.bidPlanDate);
            $('#bidCompleteDate').val(tender.bidCompleteDate);
            $('#bidAgent').val(tender.bidAgent);
            $('#tenderAgent').val(tender.tenderAgent);
            tenderId = tender.tenderId;
        }

    });


    var rtFileTempPath = '';
    var uploadFileFlag = true;
    $('.input-group.date').datepicker({
        language: "zh-CN",
        format: 'yyyy-mm-dd',
        minViewMode: 0,
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        todayHighlight: true
    });
    $("#tender_new_new_file").fileinput({
        language:'zh',
        theme:'fa',
        uploadUrl: 'tender/addfiles',
        uploadAsync: false,
        allowedFileExtensions: ['jpg', 'png', 'gif', 'docx', 'doc', 'xlsx', 'xls', 'pdf', 'pjeg', 'mp4', '3gp', 'avi'],
        overwriteInitial: false,
        layoutTemplates: {
            actionUpload: '',
            actionDelete: ''
        },
        autoReplace: true,
        maxFileSize: 1000,
        maxFilesNum: 10,
    });
    /* 清空文件后响应事件*/
    $("#tender_new_new_file").on("filecleared", function (event, data, msg) {
        uploadFileFlag = true;
        rtFileTempPath = null;
    });
    /*选择文件后处理事件*/
    $("#tender_new_new_file").on("filebatchselected", function (event, files) {
        uploadFileFlag = false;

    });
    //同步上传错误处理
    $('#tender_new_new_file').on('filebatchuploaderror', function (event, data, msg) {
        uploadFileFlag = false;
    });
    //同步上传返回结果处理
    $("#tender_new_new_file").on("filebatchuploadsuccess", function (event, data, previewId, index) {
        if (data.response.code == 1002) {
            uploadFileFlag = true;
            rtFileTempPath = data.response.data;

        }
    });
    $('#tender_new_new_file').click(function () {
        $("#tender_new_new_file").fileinput('refresh');
    });

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
    $('#annual_tender_new_new_submit').click(function () {
        if (validator.form()){
            if (uploadFileFlag == true) {
                swal({
                    title: "确认提交吗?",
                    text: "请检查数据是否填写正确后再提交!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "已确认,提交!",
                    cancelButtonText: "取消",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                }, function () {
                    var tenderVO = {};
                    if (tenderId != '') {
                        tenderVO.tenderId = tenderId;
                    }
                    tenderVO.tenderFilingUnit = $('#tenderFilingUnit').val();
                    tenderVO.nameOfLots = $('#nameOfLots').val();
                    tenderVO.bidPlanDate = $('#bidPlanDate').val();
                    tenderVO.bidCompleteDate = $('#bidCompleteDate').val();
                    tenderVO.bidAgent = $('#bidAgent').val();
                    tenderVO.tenderAgent = $('#tenderAgent').val();
                    tenderVO.rtFileTempPath = rtFileTempPath;
                    $.ajax({
                        url: "tender/save",
                        type: "POST",
                        data: JSON.stringify(tenderVO),
                        contentType: "application/json",
                        dataType: "json",
                        success: function (data) {
                            if (data.code == 1002) {
                                swal({
                                    title: "提交成功",
                                    text: "新的招标备案提交成功，请耐心等待审批!",
                                    type: "success",
                                    confirmButtonText: "确定",
                                }, function () {
                                    $('#main_content', parent.document).load('tender/totendershow');
                                });
                            } else {
                                swal({
                                    title: "出错",
                                    text: data.msg,
                                    type: "warning",
                                    confirmButtonText: "确定",
                                });
                            }
                        }
                    });
                });
            } else {
                swal({
                    title: "敬告",
                    text: "存在未上传的附件，请清空附件或上传后重试！",
                    type: "warning",
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                });
            }
        } else {
            swal({
                title:"错误!",
                text:"有数据未填写，或者格式错误!",
                type:"error"
            })
        }
    })

})