$(function () {

    var getThisUserUrl = "/user/getthisuser"; //拿到当前用户信息的url
    var getThisProjectUrl = "/user/getthisproject";
    var getAllUnreadUrl = "/notification/getallunchecked";
    var contentDiv = $('#main_content');
    $('#project_monthly_report').click(function () {
        contentDiv.load('reporter/projectmonthlyreport');
    })
    $('#project_months').click(function () {
        contentDiv.load('reporter/projectmonths');
    })
    $('#person_info').click(function () {
        contentDiv.load('user/personinfo');
    })
    $('#account_config').click(function () {
        contentDiv.load('account/accountconfig');
    })
    $('#notification_entrance').on('click', 'a', function (e) {
        var target = e.currentTarget;
        if ('monthly_report_notification' == target.dataset.id){
            contentDiv.load('notification/tonotification');
        }
    })
    $('#operation_log').click(function () {
        contentDiv.load('operationlog/tooperationlog');
    })


    getThisUser(getThisUserUrl);
    getThisProject(getThisProjectUrl);
    getAllUnread(getAllUnreadUrl);
    setInterval(function () {
        getAllUnread(getAllUnreadUrl)
    }, 30000);


    function getThisUser(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
                // 从返回的JSON当中获取product对象的信息，并赋值给表单
                var userInfo = data.data;
                $('#username .font-bold').html(userInfo.name);
                $('#name').html(userInfo.username + ' <b class="caret"></b>');
            }
        });
    }

    // 若果有 存入域 否则提示完善资料
    function getThisProject(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
              // 若果有 存入域 否则提示完善资料
            }
        });
    }

    // 拿到所有未审批的项目
    function getAllUnread(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
                var notificationVOs = data.data;
                $('#countUnread').text(notificationVOs.countAllUnread);
                var htmlTemp = "";
                $('#notification_entrance').html('')
                notificationVOs.notificationVOs.map(function (item, index) {
                    htmlTemp += ' <li>\n' +
                        '                                <a href="#" data-id="'+ item.url +'">\n' +
                        '                                    <div>\n' +
                        '                                        <i class="fa fa-envelope fa-fw"></i> 新的'+ item.type +'信息待审批\n' +
                        '                                        <span class="pull-right text-muted small">'+ item.timeDiff +'</span>\n' +
                        '                                    </div>\n' +
                        '                                </a>\n' +
                        '                            </li>\n' +
                        '                            <li class="divider"></li>'
                    
                })
                $('#notification_entrance').append(htmlTemp)
            }
        });
    }


    <!--水库基本信息填报js-->
    $("#base_info").click(function() {

        // 若无数据
        swal({
                title: "未查到项目信息",
                text: "请先填写项目基本信息!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好的，去填写!",
                cancelButtonText: "取消"
            }, function (isConfirm) {
                if (isConfirm) {
                    $('#base_info_modal').modal();
                } else {
                    $(location).attr("href", "index.html")
                }
                ;
            }
        );

        $("#basic_info_form").steps({
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
            onFinished: function (event, currentIndex) {
                swal({
                    title: "确认提交吗?",
                    text: "请检查数据是否填写正确后再提交!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "已确认,提交!",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                }, function () {
                    swal({
                            title: "成功!",
                            text: "已经成功提交!",
                            type: "success"
                        }, function () {
                            $("#base_info_modal").modal('hide');
                            $('#main-page').load('basic_info_show.html');
                            $('#small-chat').hide();
                        }
                    )
                });
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

        $("#basic_info_file").fileinput({
            // language:'zh',
            theme: 'fa',
            uploadUrl: 'http://www.baidu.com', // you must set a valid URL here else you will get an error
            uploadExtraData: {"month1": 123},
            allowedFileExtensions: ['jpg', 'png', 'gif', 'pdf'],
            overwriteInitial: false,
            layoutTemplates: {
                // actionDelete:'', //去除上传预览的缩略图中的删除图标
                actionUpload: '',//去除上传预览缩略图中的上传图片；
                // actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
            },
            autoReplace: true,
            maxFileSize: 1000,
            maxFilesNum: 10,


            //allowedFileTypes: ['image', 'video', 'flash'],
            slugCallback: function (filename) {
                return filename.replace('(', '_').replace(']', '_');
            }
        });
    });



});

