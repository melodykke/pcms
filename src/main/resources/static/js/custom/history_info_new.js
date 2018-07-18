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
        // language:'zh',
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
                swal({
                        title:"成功!",
                        text:"已经成功提交!",
                        type:"success"
                    },function () {
                        $("#pre_progress_modal").modal('hide');
                        $('#main-page').load('history_info_show.html');
                    }
                )
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