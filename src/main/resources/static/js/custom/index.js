$(function () {
    var hasProject = false;
    var getThisUserUrl = "/user/getthisuser"; //拿到当前用户信息的url
    var getThisProjectUrl = "/user/getthisproject";
    var getoverallnotificationUrl = "/notification/getoverallnotification";
    var getoverallfeedbackUrl = "/feedback/getoverallfeedback";
    var saveFilesUrl = '/baseinfo/addfiles';
    var saveBaseInfoUrl = '/baseinfo/savebaseinfo';
    var getThisCardUrl = 'reporter/getthiscard';
    // 提交个人信息
    var savePersonInfoUrl = '/user/personinfosubmit';
    var rtFileTempPath; // 服务器文件暂存地址
    var uploadFileFlag = true;

    var contentDiv = $('#main_content');


    $('#project_monthly_report').click(function () {
        $.ajax({
            url: 'baseinfo/hasbaseinfo',
            type: 'GET',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == 1003) {
                    swal({
                        title: "出错",
                        text: "请优先配置水库基本信息!",
                        type: "warning",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确认!",
                        closeOnConfirm: false
                    });
                } else if (data.code == 1310) {
                    swal({
                        title: "出错",
                        text: "水库基本信息审批通过后再重试!",
                        type: "warning",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确认!",
                        closeOnConfirm: false
                    });
                } else if (data.code == 1002) {
                    contentDiv.load('reporter/projectmonthlyreport');
                }
            }
        });
    })
    $('#project_months').click(function () {
        contentDiv.load('reporter/projectmonths');
    });
    $('#account_config').click(function () {
        contentDiv.load('account/accountconfig');
    });
    $('#feedback_entrance').on('click', 'a', function (e) {
        var target = e.currentTarget;
        contentDiv.load('/feedback/tofeedback');
    });
    $('#notification_entrance').on('click', 'a', function (e) {
        var target = e.currentTarget;
        contentDiv.load('notification/tonotification');
    });
    $('#operation_log').click(function () {
        contentDiv.load('operationlog/tooperationlog');
    });
    $('#annual_investment_plan_new_entry').click(function () {
        if (hasProject == true) {
            contentDiv.load('annualinvestment/tonewannualinvestment');
        } else {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        }

    });
    $('#annual_investment_plan_manage_entry').click(function () {
        if (hasProject == true) {
            contentDiv.load('annualinvestment/toannualinvestmentshow');
        } else {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        }

    });
    $('#tender_new_entry').click(function () {
        if (hasProject == true) {
            contentDiv.load('tender/tonewtender');
        } else {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        }

    });
    $('#tender_manage_entry').click(function () {
        if (hasProject == true) {
            contentDiv.load('tender/totendershow');
        } else {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        }

    });
    $('#announcement_a').click(function () {
        $('#content').load('announcement/toannouncement');
    });
    $('#announcement_management').click(function () {
        $('#content').load('announcement/toannouncementmanagement');
    });
    $('#account_a').click(function () {
        $('#content').load('account/toaccount');
    });
    $('#account_management').click(function () {
        $('#content').load('account/toaccountmanagement');
    });
    $('#reservoir_dic').click(function () {
        $('#content').load('manage/toreservoirdic');
    });

    $('#project_status_a').click(function () {
        if (hasProject == true) {
            contentDiv.load('index/toprojectstatus');
        } else {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        }
    });
    getThisUser(getThisUserUrl);
    if ($('#notification_entrance').length > 0 || $('#feedback_entrance').length > 0) {
        getThisProject(getThisProjectUrl);
    }
    if ($('#notification_entrance').length > 0) {
        getOverallNotification(getoverallnotificationUrl);
      /*  setInterval(function () {
            getOverallNotification(getoverallnotificationUrl);
        }, 30000);*/
    }
    if ($('#feedback_entrance').length > 0) {
    getOverallFeedback(getoverallfeedbackUrl);
    /*    setInterval(function () {
            getOverallFeedback(getoverallfeedbackUrl);
        }, 30000);*/
    }
    if ($('#self_card').length > 0) {
        getThisCard(getThisCardUrl);
    }



    function getThisUser(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                // 从返回的JSON当中获取product对象的信息，并赋值给表单
                var userInfo = data.data;
                $('#username .font-bold').html(userInfo.name);
                dows(userInfo.username);
                if (data.data.profileImg === null) {
                    $('#profile_img').attr("src", "img/portait_logo.png")
                } else {
                    $('#profile_img').attr("src", data.data.profileImg)
                }
                if (data.data.nickname === null) {
                    $('#name').html(userInfo.username + ' <b class="caret"></b>');
                } else {
                    $('#name').html(data.data.nickname + ' <b class="caret"></b>');
                }
            }
        });
    }
    function getThisCard(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                console.log(data)
                var card = data.data;
                var cardHtml = '<div class="item-name">\n' +
                    '                            '+ card.plantName +'\n' +
                    '                        </div>\n' +
                    '                        <div class="item-others">\n' +
                    '                            <div>\n' +
                    '                                <label>法人：</label>\n' +
                    '                                <span>'+ card.legalPersonName +'</span>\n' +
                    '                            </div>\n' +
                    '                            <div>\n' +
                    '                                <label>规模：</label>\n' +
                    '                                <span>'+ card.scale +'</span>\n' +
                    '                            </div>\n' +
                    '                            <div>\n' +
                    '                                <label>等级：</label>\n' +
                    '                                <span>'+ card.level +'</span>\n' +
                    '                            </div>\n' +
                    '                            <div>\n' +
                    '                                <label>项目状态：</label>\n' +
                    '                                <span>'+ card.projectStatus +'</span>\n' +
                    '                            </div>\n' +
                    '                        </div>';
                $('#self_card').html(cardHtml);
            } else {
                $('#self_card').html("<h3>账号设置顺序</h3><p>1.完善个人信息</p></br><p>2.配置资料保送子账号</p><p>3.完善项目概况等信息</p>");
            }
        });
    }
    // 若果有 存入域 否则提示完善资料
    function getThisProject(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                // 若果有 存入域 否则提示完善资料
                hasProject = true;
            }
        });
    }

    // 拿到所有未审批的项目
    function getOverallNotification(url) {
        $.getJSON(url, function (data) {
            var htmlTemp = "";
            if (data.code == 1002) {
                $('#countUnread').text(data.data.allUncheckedNum);
                $('#notification_entrance').html('');
                for (var key in data.data.article) {
                    htmlTemp += ' <li>\n' +
                        '                                <a href="#" data-id="/notification/tonotification">\n' +
                        '                                    <div>\n' +
                        '                                        <i class="fa fa-envelope fa-fw"></i> ' + key + '\n' +
                        '                                        <span class="pull-right text-muted small">' + data.data.article[key] + '</span>\n' +
                        '                                    </div>\n' +
                        '                                </a>\n' +
                        '                            </li>\n' +
                        '                            <li class="divider"></li>'
                }
            }
            htmlTemp += ' <li>\n' +
                '  <a href="#" data-id="/notification/tonotification">\n' +
                '  <i class="fa fa-envelope fa-fw"></i> 查看所有\n' +
                '  </a>\n' +
                '  </li>\n' +
                '  <li class="divider"></li>'
            $('#notification_entrance').append(htmlTemp)
        });
    }

    // 拿到所有审批的消息通知
    function getOverallFeedback(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                $('#countFeedback').text(data.data.allUncheckedNum);
                var htmlTemp = "";
                $('#feedback_entrance').html('');
                for (var key in data.data.article) {
                    htmlTemp += ' <li>\n' +
                        '                                <a href="#" data-id="/feedback/tofeedback">\n' +
                        '                                    <div>\n' +
                        '                                        <i class="fa fa-envelope fa-fw"></i> ' + key + '\n' +
                        '                                        <span class="pull-right text-muted small">' + data.data.article[key] + '</span>\n' +
                        '                                    </div>\n' +
                        '                                </a>\n' +
                        '                            </li>\n' +
                        '                            <li class="divider"></li>'
                }
                htmlTemp += ' <li>\n' +
                    '  <a href="#" data-id="/feedback/tofeedback">\n' +
                    '  <i class="fa fa-envelope fa-fw"></i> 查看所有\n' +
                    '  </a>\n' +
                    '  </li>\n' +
                    '  <li class="divider"></li>'
                $('#feedback_entrance').append(htmlTemp)
            }
        });
    }


    <!--水库基本信息填报js-->
    $("#base_info").click(function () {
        $.ajax({
            url: 'baseinfo/hasbaseinfo',
            type: 'GET',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == 1003) {
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
                                $('#base_info_modal').modal('show');
                            }
                        }
                    );
                } else if (data.code == 1002) {
                    $('#main_loading').show();
                    contentDiv.load('baseinfo/baseinfoshow', function () {
                        $('#main_loading').hide();
                    });
                    $('#small-chat').hide();
                } else if (data.code == 1310) {

                    swal({
                            title: "项目信息",
                            text: "项目基础信息申报未通过!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "查看",
                            cancelButtonText: "重新填报"
                        }, function (isConfirm) {
                            if (isConfirm) {
                                contentDiv.load('baseinfo/baseinfoshow');
                                $('#small-chat').hide();
                            } else {
                                $('#base_info_modal').modal();
                            }
                        }
                    );
                }
            }
        });
    });
    $('.base_info_modal_close').click(function () {
        $('#base_info_modal').modal('hide');
    })


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
            var baseInfoVO = {};
            var form = $(this);
            console.log($('#plantName'))
            console.log($('#plantName').val())
            baseInfoVO.plantName = $('#plantName').val();
            baseInfoVO.totalInvestment = $('#totalInvestment').val();
            baseInfoVO.projectType = $('#projectType').val();
            baseInfoVO.level = $('#level').val();
            baseInfoVO.longitude = $('#longitude').val();
            baseInfoVO.scale = $('#scale').val();
            baseInfoVO.storage = $('#storage').val();
            baseInfoVO.hasSignedConstructionContract = $('#hasSignedConstructionContract').val();
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
            baseInfoVO.catchmentArea = $('#catchmentArea').val();
            baseInfoVO.spillway = $('#spillway').val();
            baseInfoVO.irrigatedArea = $('#irrigatedArea').val();
            baseInfoVO.installedCapacity = $('#installedCapacity').val();
            baseInfoVO.livestock = $('#livestock').val();
            baseInfoVO.waterSupplyPopulation = $('#waterSupplyPopulation').val();
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
                    type: "info",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "已确认,提交!",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                }, function () {
                    $.ajax({
                        url: saveBaseInfoUrl,
                        type: 'POST',
                        data: JSON.stringify(baseInfoVO),
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 1002) {
                                swal({
                                    title: "项目基础信息提交成功",
                                    text: "请至 项目概况 栏目中查看详情",
                                    type: "success",
                                }, function () {
                                    $("#base_info_modal").modal('hide');
                                    $('#small-chat').hide();
                                    top.location.reload();
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
    // 文件上传
    $("#uploadfile").fileinput({
        language: 'zh',
        theme: 'fa',
        uploadUrl: saveFilesUrl, // you must set a valid URL here else you will get an error
        uploadAsync: false,
        allowedFileExtensions: ['jpg', 'png', 'gif', 'docx', 'doc', 'xlsx', 'xls', 'pdf', 'pjeg', 'mp4', '3gp', 'avi'],
        overwriteInitial: false,
        maxFileSize: 1000000,
        maxFilesNum: 10,
        layoutTemplates: {
            actionUpload: '',
            actionDelete: ''
        }
    });
    /* 清空文件后响应事件*/
    $("#uploadfile").on("filecleared", function (event, data, msg) {
        uploadFileFlag = true;
        rtFileTempPath = null;
    });
    /*选择文件后处理事件*/
    $("#uploadfile").on("filebatchselected", function (event, files) {
        uploadFileFlag = false;

    });
    //同步上传错误处理
    $('#uploadfile').on('filebatchuploaderror', function (event, data, msg) {
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

    /*处理用户个人信息 判断是否已经填写过个人信息*/
    function hasPersonInfo() {
        var flag = false;
        $.getJSON('user/haspersoninfo', function (data) {
            flag = true;
        });
        return flag;
    }

    // 显示用户个人信息
    $('#person_info').click(function () {
        $.getJSON('/user/haspersoninfo', function (data) {
            if (data.code == 1003) {
                $('#person_info_modal').modal('show')
            } else if (data.code == 1002) {
                contentDiv.load('user/personinfo');
            }
        })
    });

    $("#person_info_form").validate({
        rules: {
            name: {
                required: true,
                minlength: 2,
                maxlength: 64
            },
            tel: {
                required: true,
                minlength: 2,
                maxlength: 64
            },
            qq: {
                required: true,
                digits: true
            },
            email: {
                required: true,
                email: true
            },
            id_num: {
                required: true,
                minlength: 18,
                maxlength: 18
            },
            title: {
                required: true,
                minlength: 2,
                maxlength: 64
            },
            address: {
                required: true,
                minlength: 10,
                maxlength: 64
            }
        },
        submitHandler: function (form) {
            if (confirm("若以上信息确认无误，请确认提交!")) {
                var personInfoVO = {};
                personInfoVO.name = $('#person_name').val();
                personInfoVO.tel = $('#tel').val();
                personInfoVO.qq = $('#qq').val();
                personInfoVO.email = $('#email').val();
                personInfoVO.id_num = $('#id_num').val();
                personInfoVO.title = $('#title').val();
                personInfoVO.address = $('#address').val();
                $.ajax({
                    url: "user/personinfosubmit",
                    type: "POST",
                    data: JSON.stringify(personInfoVO),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 1002) {
                            $('#person_info_modal').modal('hide');
                            $('#person_info').click();
                        } else {
                            $('#person_info_rt_msg').text("错误(" + data.code + ")：" + data.msg);
                        }
                    }
                });
            }
        }
    });
    $('.person_info_modal_close_btn').click(function () {
        $('#person_info_modal').modal('hide');
    });

    /*配置向导*/
    var enjoyhint_script_data = [
        {
            selector: '#person_menu',
            event: 'click',
            description: '请点击用户',
            "skipButton": {text: "退出"},
        },
        {
            selector: '#person_info',
            event: 'custom',
            event_type: 'next',
            "nextButton": {text: "下一步"},
            description: '请点击个人设置',
            "skipButton": {text: "退出"},
        },
        {
            selector: '#menu_1',
            event: 'click',
            "skipButton": {text: "退出"},
            description: '请点击项目储备!',
        },
        {
            selector: '#menu_2',
            event: 'click',
            "skipButton": {text: "退出"},
            description: '请点击基本信息维护!',
        },
        {
            selector: '#base_info',
            event: 'custom',
            event_type: 'next',
            "nextButton": {text: "下一步"},
            "skipButton": {text: "退出"},
            description: '请点击项目概况!',
        },
        {
            selector: '#basic_info',
            event: 'custom',
            event_type: 'next',
            "nextButton": {text: "下一步"},
            "skipButton": {text: "退出"},
            description: '这是项目基本情况!',
        },
        {
            selector: '#basic_jd',
            event: 'custom',
            event_type: 'next',
            "nextButton": {text: "下一步"},
            "skipButton": {text: "退出"},
            description: '这是项目审批情况!',
        }]


    $('#help_page').click(function () {
        enjoyhint_instance = new EnjoyHint({
            onEnd: function () {
                swal({
                    title: "完成!",
                    text: "恭喜你，完成基本配置!",
                    type: "success"
                })
            }
        });
        enjoyhint_instance.setScript(enjoyhint_script_data);
        enjoyhint_instance.runScript();
    })
    /*    $('body').on('hidden.bs.modal', '.modal', function () {
            $('#basic_info_form')[0].reset()
        });*/
    $('#basic_info_repeat1').click(function () {
        $('#base_info_modal').modal();
    })


    /*websocket*/
    function reconnect(username) {
        websocket = new WebSocket('ws://pcms.natapp1.cc/websocket/' + username)
    }

    function dows(username) {
        var websocket = null;
        //ws心跳检查
        var heartCheck = {
            timeout: 20000,//20ms
            timeoutObj: null,
            reset: function () {
                clearTimeout(this.timeoutObj);
                this.start();
            },
            start: function () {
                this.timeoutObj = setTimeout(function () {
                    websocket.send("HeartBeat:" + username, "beat");
                }, this.timeout)
            }
        }
        if ('WebSocket' in window) {
            websocket = new WebSocket('ws://pcms.natapp1.cc/websocket/' + username)
        } else {
            alert('该浏览器不支持ws！');
        }
        websocket.onopen = function (event) {
            console.log('建立连接');
            heartCheck.start();
        }
        websocket.onclose = function (event) {
            console.log('连接关闭')
        }
        websocket.onmessage = function (event) {
            console.log('收到消息：' + event.data);
            if (event.data.startsWith('echo')) {
                heartCheck.reset();
            } else {
                var wsMessage = eval("(" + event.data + ")");
                showNotification(wsMessage.title, wsMessage.msg, wsMessage.url);
            }

        }
        websocket.onerror = function (event) {
            reconnect(username);
        }
        websocket.onbeforeunload = function () {
            websocket.close();
        }
    }

    function showNotification(title, msg, url) {
        toastr.options = {
            "closeButton": true,
            "debug": false,
            "progressBar": true,
            "preventDuplicates": false,
            "positionClass": "toast-top-right",
            "onclick": null,
            "showDuration": "400",
            "hideDuration": "1000",
            "timeOut": "7000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };
        toastr.options.onclick = function () {
            contentDiv.load(url);
        };
        var $toast = toastr['info'](msg, title); // Wire up an event handler to a button in the toast, if it exists
    }


    // 项目前期
    var countEntries = 0;
    $('#preProgressEntry_body').on('click', '.date-input', function (e) {
        var target = $(e.currentTarget);
        target.datepicker({
            language: 'zh-CN',
            minViewMode: 0,
            keyboardNavigation: false,
            forceParse: false,
            forceParse: false,
            autoclose: true,
            todayHighlight: true
        });
        target.datepicker('show');
    });
    $('#preProgressEntry_body').on('changeDate', '.date-input', function (e) {
        var target = $(e.currentTarget);
        target.datepicker('hide');
    });
    $("#pre_progress_new").click(function () {
        $.ajax({
            url: 'preprogress/haspreprogress',
            type: 'GET',
            contentType: 'application/json',
            success: function (data) {
                if (data.code == 1003) {
                    swal({
                            title: "未查到前期进度",
                            text: "请先填写项目前期进度信息!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "好的，去填写!",
                            cancelButtonText: "取消"
                        }, function (isConfirm) {
                            $.ajax({
                                url: 'preprogress/initthispage',
                                type: 'GET',
                                contentType: 'application/json',
                                success: function (data) {
                                    var preProgressEntryHtml = '';
                                    data.data.map(function (item, index) {
                                        preProgressEntryHtml += '      <tr id="entry_' + item.serialNumber + '">\n' +
                                            '                                                <th id="serialNumber_' + item.serialNumber + '">' + item.serialNumber + '</th>\n' +
                                            '                                                <th id="planProject_' + item.serialNumber + '">' + item.planProject + '</th>\n' +
                                            '                                                <th><select id="approvalStatus_' + item.serialNumber + '"><option selected="selected" disabled="disabled"  style="display: none"></option><option data-value="编制">编制</option><option data-value="内部评审">内部评审</option><option data-value="政府评审">政府评审</option><option data-value="完成">完成</option></select></th>\n' +
                                            '                                                <th><input id="compileUnit_' + item.serialNumber + '" class="form-control" type="text"/></th>\n' +
                                            '                                                <th><input id="approvalUnit_' + item.serialNumber + '" class="form-control" type="text"/></th>\n' +
                                            '                                                <th><div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span><input  id="approvalDate_' + item.serialNumber + '" type="text"  class="form-control date-input" readonly></div></th>\n' +
                                            '                                                <th><input id="referenceNumber_' + item.serialNumber + '" class="form-control" type="text"/></th>\n' +
                                            '                                            </tr>';
                                        countEntries += 1;
                                        $('#preProgressEntry_body').html(preProgressEntryHtml);

                                    })
                                }
                            });
                            if (isConfirm) {
                                $('#pre_progress_modal').modal();

                            } else {

                            }
                        }
                    );
                } else if (data.code == 1002) {
                    contentDiv.load('preprogress/topreprogress');
                } else if (data.code == 2203) {
                    swal({
                        title: "出错",
                        text: "请优先配置水库基本信息!",
                        type: "warning",
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确认!",
                        closeOnConfirm: false
                    });
                }
            }
        })
    });
    $('#pre_progress_table').DataTable({
        bFilter: false,    //去掉搜索框
        bInfo: false,       //去掉显示信息
        retrieve: true,    //多次加载不会显示缓存数据
        pageLength: 10,
        responsive: true,
        ordering: false,
        paging: false,
        dom: '<"html5buttons"B>lTfgitp',
        buttons: []
    });
    $('.pre_progress_modal_close').click(function () {
        $('#pre_progress_modal').modal('hide');
    })
    $('#pre_progress_submit').click(function () {
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
                var preProgressEntries = [];
                var serialNumberEntries = $('[id^="serialNumber_"]');
                var planProjectEntries = $('[id^="planProject_"]');
                var approvalStatusEntries = $('[id^="approvalStatus_"]');
                var compileUnitEntries = $('[id^="compileUnit_"]');
                var approvalUnitEntries = $('[id^="approvalUnit_"]');
                var approvalDateEntries = $('[id^="approvalDate_"]');
                var referenceNumberEntries = $('[id^="referenceNumber_"]');
                for (var i = 0; i < serialNumberEntries.length; i++) {
                    var preProgressEntry = {};
                    preProgressEntry.serialNumber = parseInt(serialNumberEntries.eq(i).text());
                    preProgressEntry.planProject = planProjectEntries.eq(i).text();
                    console.log(planProjectEntries.eq(i))
                    preProgressEntry.approvalStatus = approvalStatusEntries.eq(i).find('option').not(
                        function () {
                            return !this.selected;
                        }).data('value');
                    preProgressEntry.compileUnit = compileUnitEntries.eq(i).val();
                    preProgressEntry.approvalUnit = approvalUnitEntries.eq(i).val();
                    preProgressEntry.approvalDate = approvalDateEntries.eq(i).val();
                    preProgressEntry.referenceNumber = referenceNumberEntries.eq(i).val();
                    preProgressEntries.push(preProgressEntry);
                }
                console.log(preProgressEntries);
                $.ajax({
                    url: 'preprogress/save',
                    type: 'POST',
                    data: JSON.stringify({
                        preProgressEntries: JSON.stringify(preProgressEntries),
                        rtFileTempPath: rtFileTempPath
                    }),
                    contentType: 'application/json',
                    success: function (data) {
                        if (data.code == 1002) {
                            swal({
                                title: "项目前期信息提交成功",
                                text: "请至 项目前期 栏目中查看详情",
                                type: "success",
                            }, function () {
                                $("#base_info_modal").modal('hide');
                                $('#small-chat').hide();
                                top.location.reload();
                            })
                        } else {
                            console.log(data)
                            swal("失败!", data.msg, "error");
                        }
                    }
                });
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
    });
    $("#pre_progress_file").fileinput({
        language: 'zh',
        theme: 'fa',
        uploadUrl: 'preprogress/addfiles', // you must set a valid URL here else you will get an error
        uploadAsync: false,
        /*  uploadExtraData: {"month1": 123},*/
        allowedFileExtensions: ['jpg', 'png', 'gif', 'docx', 'doc', 'xlsx', 'xls', 'pdf', 'pjeg', 'mp4', '3gp', 'avi'],
        overwriteInitial: false,
        maxFileSize: 1000000,
        maxFilesNum: 10,
        layoutTemplates: {
            actionUpload: '',
            actionDelete: ''
        },
        autoReplace: true,
    });
    /* 清空文件后响应事件*/
    $("#pre_progress_file").on("filecleared", function (event, data, msg) {
        uploadFileFlag = true;
        rtFileTempPath = null;
    });
    /*选择文件后处理事件*/
    $("#pre_progress_file").on("filebatchselected", function (event, files) {
        uploadFileFlag = false;

    });
    //同步上传错误处理
    $('#pre_progress_file').on('filebatchuploaderror', function (event, data, msg) {
        uploadFileFlag = false;
    });
    //同步上传返回结果处理
    $("#pre_progress_file").on("filebatchuploadsuccess", function (event, data, previewId, index) {
        if (data.response.code == 1002) {
            uploadFileFlag = true;
            rtFileTempPath = data.response.data;

        }
    });
    $('#pre_progress_file').click(function () {
        $("#pre_progress_file").fileinput('refresh');
    });


    /*合同管理 contract*/
    $('#new_contract_time').click(function () {
        $('#new_contract_time').datepicker({
            language: 'zh-CN',
            minViewMode: 0,
            keyboardNavigation: false,
            forceParse: false,
            forceParse: false,
            autoclose: true,
            todayHighlight: true
        });
        $('#new_contract_time').datepicker('show');
    });
    $("#contract").click(function () {
        if (hasProject == false) {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        } else {
            $.ajax({
                url: 'contract/hascontract',
                type: 'GET',
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 1003) {
                        // 若无数据
                        swal({
                                title: "未查到合同备案",
                                text: "请先填写合同备案!",
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "好的，去填写!",
                                cancelButtonText: "取消"
                            }, function (isConfirm) {
                                if (isConfirm) {
                                    $('#contract_modal').modal();
                                }
                            }
                        );
                    } else if (data.code == 1002) {
                        contentDiv.load('contract/tocontract');
                        $('#small-chat').hide();
                    } else if (data.code == 2203) {
                        swal({
                            title: "出错",
                            text: "请优先配置水库基本信息!",
                            type: "warning",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确认!",
                            closeOnConfirm: false
                        });
                    }
                }
            });
        }
    });
    $('#contract_submit').click(function () {
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
            var contract = {};
            contract.name = $('#new_contract_name').val();
            contract.type = $('#new_contract_type').find('option').not(
                function () {
                    return !this.selected;
                }).data('value');
            contract.partyA = $('#new_contract_party_a').val();
            contract.price = $('#new_contract_amount').val();
            contract.number = $('#new_contract_num').val();
            contract.signDate = $('#new_contract_time').val();
            contract.partyB = $('#new_contract_party_b').val();
            contract.content = $('#new_contract_main_content').val();
            contract.remark = $('#new_contract_remark').val();
            contract.rtFileTempPath = rtFileTempPath;
            $.ajax({
                url: 'contract/save',
                type: 'POST',
                data: JSON.stringify(contract),
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 1002) {
                        swal({
                            title: "合同信息提交成功",
                            text: "请至 合同备案 栏目中查看详情",
                            type: "success",
                        }, function () {
                            $('#small-chat').hide();
                            $('#contract_modal').modal('hide');
                            $('#main_content').load('contract/tocontract');
                        })
                    } else {
                        console.log(data)
                        swal("失败!", data.msg, "error");
                    }
                }
            });
        });
    });
    $("#contract_file").fileinput({
        language: 'zh',
        theme: 'fa',
        uploadUrl: 'contract/addfiles', // you must set a valid URL here else you will get an error
        uploadAsync: false,
        /*  uploadExtraData: {"month1": 123},*/
        allowedFileExtensions: ['jpg', 'png', 'gif', 'docx', 'doc', 'xlsx', 'xls', 'pdf', 'pjeg', 'mp4', '3gp', 'avi'],
        overwriteInitial: false,
        maxFileSize: 1000000,
        maxFilesNum: 10,
        layoutTemplates: {
            actionUpload: '',
            actionDelete: ''
        },
        autoReplace: true,
    });
    /* 清空文件后响应事件*/
    $("#contract_file").on("filecleared", function (event, data, msg) {
        uploadFileFlag = true;
        rtFileTempPath = null;
    });
    /*选择文件后处理事件*/
    $("#contract_file").on("filebatchselected", function (event, files) {
        uploadFileFlag = false;

    });
    //同步上传错误处理
    $('#contract_file').on('filebatchuploaderror', function (event, data, msg) {
        uploadFileFlag = false;
    });
    //同步上传返回结果处理
    $("#contract_file").on("filebatchuploadsuccess", function (event, data, previewId, index) {
        if (data.response.code == 1002) {
            uploadFileFlag = true;
            rtFileTempPath = data.response.data;
        }
    });
    $('#contract_file').click(function () {
        $("#contract_file").fileinput('refresh');
    });
    $('.contract_modal_close').on('click', function (e) {
        $('#contract_modal').modal('hide');
    });


    // 历史数据填写
    $("#month_history_statistic").click(function(){
        if (hasProject == false) {
            swal({
                title: "未查到项目信息",
                text: "请先填写项目概况!",
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好确定!",
            })
        } else {
            $.ajax({
                url: 'monthlyreport/hashistorystatistic',
                type: 'GET',
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 1002){
                        swal({
                                title: "已配置！",
                                text: "已设置过该水库的历史统计数据，无法重复设置!",
                                type: "success",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "查看",
                                cancelButtonText:"取消"
                            }, function () {
                                contentDiv.load('monthlyreport/tomonthshistoryshow');
                            }
                        );
                    } else if (data.code == 1004) {
                        swal({
                                title:"正在审核!",
                                text:"历史数据正在审核中，您可以选择查看目前状态或重新提交!",
                                type:"warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "查看",
                                cancelButtonText:"取消"
                            },function () {
                                $("#pre_progress_modal").modal('hide');
                                contentDiv.load('monthlyreport/tomonthshistoryshow');
                            }
                        );
                    }else if (data.code == 1005) {
                        swal({
                                title: "审核未通过!",
                                text: "历史数据审核未通过，您可以选择查看详情或重新提交!",
                                type: "error",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "查看",
                                cancelButtonText: "取消"
                            }, function () {
                                $("#pre_progress_modal").modal('hide');
                                contentDiv.load('monthlyreport/tomonthshistoryshow');
                            }
                        );
                    } else {
                        contentDiv.load('monthlyreport/tomonthshistory');
                    }
                }
            });
        }
    });

    $('#bind_wechat_btn').click(function () {
        $('#auth_img').attr("src", "/wechatlogin/generateqrcode4login?time="+new Date());
        $('#wechat_binding_modal_body');
        $('#wechat_binding_modal').modal('show');

    });


    //微信modal
    $('.wechat-register-modal-close').on('click', function (e) {
        $('#wechat_binding_modal').modal('hide');
    });




   /* 管理部主页js*/
    function initMap() {
        var def = $.Deferred();
        // 百度地图API功能
        var map = new BMap.Map("allmap", { mapType: BMAP_HYBRID_MAP });
        var point = new BMap.Point(106.630905, 26.674511);
        map.centerAndZoom(point, 9); // 初始化地图，设置中心店坐标和地图级别

        map.addControl(new BMap.MapTypeControl()); //添加地图类型控件

        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_LARGE });
        map.addControl(ctrl_nav);


        map.setCurrentCity("贵阳");
        map.enableScrollWheelZoom(true);

        //控制地图的最大和最小缩放级别
        map.setMinZoom(8);
        //map.setMaxZoom(18);

        var b = new BMap.Bounds(new BMap.Point(102.795069, 24.879701), new BMap.Point(111.69936, 29.04118)); // 范围 左下角，右上角的点位置
        try {    // js中尽然还有try catch方法，可以避免bug引起的错误
            BMapLib.AreaRestriction.setBounds(map, b); // 已map为中心，已b为范围的地图
        } catch (e) {
            // 捕获错误异常
            alert(e);
        }
        // 监听地图模块加载完成
        map.addEventListener("tilesloaded", function () {
            def.resolve(map);
        })

        return def.promise();
    }

    // 地图打点
    function createMarker(data, map) {
        var myIcon = new BMap.Icon("icon/水库点.png", new BMap.Size(25, 25), {
            imageSize: new BMap.Size(25, 25)
        });
        // 创建标注对象并添加到地图

        var point_item = new BMap.Point(data.longitude, data.latitude);
        var marker = new BMap.Marker(point_item, {icon: myIcon});
        map.addOverlay(marker);

        // 定义展示数据
        var str = [
            "<div class='map-tips'><label>水库：</label><span class='plant-name'>" + data.plantName + "</span></div>",
            "<div class='map-tips'><label>位置：</label><span>" + data.location + "</span></div>",
            // "<label>类型:</label><span>" + data.dam_type + "</span><br/>",
            "<div class='map-tips'><label>规模：</label><span>" + data.scale + "</span></div>",
            "<div class='map-tips'><label>等级：</label><span>" + data.level + "</span></div>"
            // "<label>用途:</label><span>" + data.project_task + "</span>"
        ]
        var detail = str.join("");

        // 设置点击事件
        marker.addEventListener("click", function () {
            addProjectDetail(data);
            // 默认选择概述项标签
            $("#container1").find("li:first").addClass("active").siblings().removeClass("active");
            // 默认选择概述项内容
            $("#tab-3").addClass("active").siblings().removeClass("active");
            if(!$("#container1").hasClass("active")) {
                $("#toggle1").click();
            }
            $(".overview-video").addClass("video-show");
            initItemVideo();
        });

        // 设置鼠标hover事件
        marker.addEventListener("mouseover", function (e) {
            var label = new BMap.Label(detail, { offset: new BMap.Size(20, 20) });//为标注设置一个标签
            label.setStyle({
                width: "auto",
                color: '#000',
                background: '#fff',
                border: '1px solid "#fff"',
                borderRadius: "3px",
                // textAlign: "center",
                height: "auto",
                textAlign: "left",
                padding: "5px"
                //lineHeight: "26px"
            });
            marker.setLabel(label);
            marker.setTop(true,999);

            /* var opts = { width: 120, height: 150, title: "详细信息" };
            var infoWindow = new BMap.InfoWindow(detail, opts);
            map.openInfoWindow(infoWindow, point); */
        });

        // 设置鼠标离开事件
        marker.addEventListener("mouseout", function (e) {
            var label = this.getLabel();
            label.setContent("");//设置标签内容为空
            label.setStyle({ border: "none", width: "0px", padding: "0px" });//设置标签边框宽度为0
            marker.setTop(false);
        });

    }
    /**
     *  监控展开并初始化
     */
    function initItemVideo() {
        var obj = document.getElementById("DPSDK_OCX");
        // 初始化
        var gWndId = obj.DPSDK_CreateSmartWnd(0, 0, 100, 100);
        obj.DPSDK_SetWndCount(gWndId, 1);
        obj.DPSDK_SetSelWnd(gWndId, 0);
        for (var i = 1; i <= 4; i++)
            obj.DPSDK_SetToolBtnVisible(i, false);
        obj.DPSDK_SetToolBtnVisible(7, false);
        obj.DPSDK_SetToolBtnVisible(9, false);
        obj.DPSDK_SetControlButtonShowMode(1, 0);
        obj.DPSDK_SetControlButtonShowMode(2, 0);

        // 登录
        var nRet1 = obj.DPSDK_Login("58.16.188.145", "9000", "向敬光", "123456");
        // 加载组织结构
        obj.DPSDK_LoadDGroupInfo();
        // 获取组织结构
        var videoXML = obj.DPSDK_GetDGroupStr();
        // 根据通道ID连接DMS
        var nRet2 = obj.DPSDK_ConnectDmsByChnlId("1000063$1$0$1");
        // 播放视频
        var szCameraId = "1000063$1$0$1";            // 视频通道
        var nStreamType = "1";       // 主码流
        var nMediaType = "1";       //视频
        var nTransType = "1";       //视频

        var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
        var nRet = obj.DPSDK_StartRealplayByWndNo(gWndId, nWndNo, szCameraId, nStreamType, nMediaType, nTransType);
        if (nRet == 0) {
            obj.DPSDK_SetIvsShowFlagByWndNo(gWndId, nWndNo, 1, 1);
            obj.DPSDK_SetIvsShowFlagByWndNo(gWndId, nWndNo, 2, 1);
            obj.DPSDK_SetIvsShowFlagByWndNo(gWndId, nWndNo, 3, 1);
        }
    }

    /**
     *  关闭地图数据的下拉面板
     */
    function closeItemPanel() {
        $("#itemDetails").slideUp();
    }
    /**
     *  概览数据添加
     */
    function addSurveyData(data) {
        $("#total_budget").text(data[0]);
        $("#total_investment").text(data[1]);
        $("#finish_investment").text(data[2]);
        $("#actual_investment").text(data[3]);
    }

    /**
     *  代办信息
     */
    function addCommission(data) {
        var strHtml = [];
        $.each(data, function (i, n) {
            var str = [
                '<tr><td title="' + n.plant_name + '">',
                '<a>' + n.plant_name + '</a></td>',
                '<td title="' + n.update_time + '">' + n.update_time + '</td>',
                '<td title="' + n.legal_representative_name + '">' + n.legal_representative_name + '</td>',
                '<td class="text-navy" title="' + n.local_accumulative_payment + '">' + n.local_accumulative_payment + '</td></tr>'
            ];
            strHtml.push(str.join(""));
        })
        $("#commission_detail").html(strHtml.join(""));
    }

    /**
     *  申请信息
     */
    function addApplication(data) {
        var strHtml = [];
        $.each(data, function (i, n) {
            var str = [
                '<tr><td title="' + n.plant_name + '">',
                '<a>' + n.plant_name + '</a></td>',
                '<td title="' + n.update_time + '">' + n.update_time + '</td>',
                '<td title="' + n.legal_representative_name + '">' + n.legal_representative_name + '</td>',
                '<td class="text-navy" title="' + n.total_investment + '">' + n.total_investment + '</td></tr>'
            ];
            strHtml.push(str.join(""));
        });
        $("#application_detail").html(strHtml.join(""));
    }

    /**
     *  项目概述
     */
    function addProjectDetail(data) {
        var strHtml = [
            '<div class="panel-detail-title">' + data.plantName + '</div>',
            '<div class="col-lg-12 panel-item"><div><label>水库规模：</label>',
            '<span id="scale">' + data.scale + '</span></div>',
            ' <div><label>水库等级：</label>',
            '<span id="level">' + data.level + '</span></div>',
            '<div><label>所属单位：</label>',
            '<span id="legal_representative_name">' + data.legalRepresentativeName + '</span></div>',
            '<div><label>所在位置：</label>',
            '<span id="location">' + data.location + '</span></div>',
            '<div><label>主要功能：</label>',
            '<span id="project_task">' + data.projectTask + '</span></div>',

            '<div><label>水库概述：</label>',
            '<span id="overview">' + data.overview + '</span></div>'
        ]
        $("#plant_name").text(data.plantName);
        $(".item-detail").html(strHtml.join(""));
    }

    /**
     *     弹出框初步JS
     */
    function openDialog() {
        // 弹出层
        $("#dialog").html('<div>twafsdgewwadsgfsaegjioajsfdosjafoaslfnsalkgnmsdoizajgpoiewjapokdsaglkjfdspogjewpkfdsp</div><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal5" onclick="openDialog()">test Modal</button>');
        var dialogHtml = [
            // 头部标题栏
            '<div class="modal-header">dialog_header',
            '<button type="button" class="close" data-dismiss="modal" onclick="closeDialog()"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>',
            '</div>',
            // 弹出框内容区
            '<div class="modal-body">dialog_body</div>',
            // 弹出框操作区
            '<div class="modal-footer"><button type="button" class="btn btn-white" data-dismiss="modal">Close</button><button type="button" class="btn btn-primary">Save changes</button></div>'
        ]
        $("#dialog").html(dialogHtml.join(""));
        var height = $("#dialog").height(),
            width = $("#dialog").width();
        $("#dialog").addClass("dialog-position");
        $("#dialog").css({ "margin-top": -height / 2, "margin-left": -width / 2 });
        $("#dialog").removeClass("dialog-hide");
        // $("#dialog").slideDown();
        // 遮罩层
        $("#mask").show();
    }
    // 关闭弹出框
    function closeDialog() {
        $("#dialog").addClass("dialog-hide");
        $("#mask").hide();
    }

    // 全屏JS
    function fullScreen() {
        $("#fullscreen_map").toggleClass("full-screen");
        $("#fullscreen_map").show();
        $("body").toggleClass("hide-body");
    }

    // 动画
    function toggleMove(id) {
        $('.right-panel').stop().animate({ marginRight: "-450px" }, 300);
        setTimeout(function () {
            if (!$("[id=" + id + "]").hasClass("active")) {
                $("[id=" + id + "]").siblings().removeClass('active');
                $('.right-panel').stop().animate({ marginRight: "0" }, 500);
            }
            $("[id=" + id + "]").toggleClass('active');
        }, 500);
    }

    // 标签动画
    function moveInTip(id) {
        $("[id='" + id + "']").animate({ width: "90px" }, 150);
    }
    function moveOutTip(id) {
        $("[id='" + id + "']").animate({ width: "20px" }, 150);
    }
    // 饼图
    function initPieChart(id, chartData) {
        var myChart = echarts.init(document.getElementById(id));

        var legnedData = [];
        $.each(chartData, function (i, n) {
            legnedData.push(n.name);
        });
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "<div style='text-align:left'>{b} <br/>￥{c}({d}%)</div>"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: legnedData,
                show: false
            },
            series: [
                {
                    name: '资金',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '14',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: chartData
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        console.log('success, done');
    }
    // 柱状图
    function initBarChart(id) {
        var myChart = echarts.init(document.getElementById(id));
        var option = {
            color: ['#fff'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter: "<div style='text-align:left'>{b}<br/>￥{c}(万元)</div>"
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['总概算', '总项目投资', '实际投资完成量', '实际投资拨付'],
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: '#565656'
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontFamily: 'Microsoft YaHei'
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisTick: {
                        show: false
                    },
                    // y 轴线
                    axisLine: {
                        show: false,
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#f0ab58',
                            fontFamily: 'Lccd'
                        }
                    },
                    splitLine: {
                        show: true,  //显示分割线
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: '#565656',
                            type: 'dotted'
                        }
                    }
                }
            ],
            series: [
                {
                    name: '资金',
                    type: 'bar',
                    barWidth: '50%',
                    data: [5000, 7000, 1000, 800],
                    itemStyle: {
                        normal: {
                            color: function (params) {
                                var colorList = [
                                    '#ee4065', '#41daea', '#39eac6', '#ffde3a'

                                ];
                                return colorList[params.dataIndex]
                            },
                            label: {
                                show: true,
                                position: 'top',
                                formatter: function (params) {
                                    return params.value + '(万元)';
                                }
                            }
                        },
                        emphasis: {
                            label: {
                                show: true,
                                textStyle: {
                                    fontSize: 12
                                }

                            }
                        }
                    }
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        console.log('success, done');
    }
    function createEchartData(data) {
        var paymentData = [],
            investmentData = [];
        var paymentData = [{ value: 1500000, name: '中央累计拨付' }, { value: 2100000, name: '当前累计拨付' }]
        initPieChart("payment_chart", paymentData);
        var investmentData = [{ value: 1500000, name: '省政府投资' }, { value: 2100000, name: '中央投资' }, { value: 3100000, name: '当前投资' }]
        $.each(data, function(i, n) {

        });
    }
    $(function () {
        /*
         * 右侧工具栏动画
         */
        $(document).ready(function () {
            // 标签点击事件
            $('#toggle1').click(function () {
                toggleMove("container1");
            });
            $('#toggle2').click(function () {
                toggleMove("container2");
            });
            $('#toggle3').click(function () {
                toggleMove("container3");
            });
            // 标签hover事件
            $('#toggle1').mouseover(function () {
                moveInTip("toggle1");
            });
            $('#toggle2').mouseover(function () {
                moveInTip("toggle2");
            });
            $('#toggle3').mouseover(function () {
                moveInTip("toggle3");
            });
            // 标签离开事件
            $('#toggle1').mouseleave(function () {
                moveOutTip("toggle1");
            });
            $('#toggle2').mouseleave(function () {
                moveOutTip("toggle2");
            });
            $('#toggle3').mouseleave(function () {
                moveOutTip("toggle3");
            });
        });

        /*
         * 填充页面数据
         */
        // 概览数据
        var surveyData = [1, 2, 3, 4]
        addSurveyData(surveyData);
        // 代办信息 & 申请信息
        var commissionData = [
            { "plant_name": "a水库", "update_time": "2015-11-23", "legal_representative_name": "A", "local_accumulative_payment": "110000", "total_investment": "2000000" },
            { "plant_name": "b水库", "update_time": "2016-09-20", "legal_representative_name": "B", "local_accumulative_payment": "120000", "total_investment": "5000000" },
            { "plant_name": "c水库", "update_time": "2017-01-06", "legal_representative_name": "C", "local_accumulative_payment": "130000", "total_investment": "7000000" },
            { "plant_name": "d水库", "update_time": "2017-03-18", "legal_representative_name": "D", "local_accumulative_payment": "140000", "total_investment": "4000000" },
            { "plant_name": "e水库", "update_time": "2017-05-23", "legal_representative_name": "E", "local_accumulative_payment": "150000", "total_investment": "10200000" },
            { "plant_name": "f水库", "update_time": "2017-10-12", "legal_representative_name": "F", "local_accumulative_payment": "160000", "total_investment": "8900000" },
            { "plant_name": "f水库", "update_time": "2017-10-12", "legal_representative_name": "F", "local_accumulative_payment": "160000", "total_investment": "8900000" }
        ];
        // 代办
        addCommission(commissionData);
        // 申请
        addApplication(commissionData);

    });

    /**
     *  initRegionPie 投资分布图
     */
    function initRegionPie(id, data, seriesKey, name, orient) {
        var myChart = echarts.init(document.getElementById(id));
        var chartData = getData(data, seriesKey);
        var option = {
            tooltip: {
                trigger: 'item',
                // position: "right",
                position: [10, 10],
                textStyle: {
                    fontFamily: 'Microsoft YaHei'
                },
                formatter: "{a} <br/>{b} :<span class='map-num'> {c} ({d}%)</span>"
            },
            legend: {
                type: 'scroll',
                orient: orient ? 'vertical' : 'horizontal',
                right: 10,
                top: 5,
                bottom: 20,
                pageIconColor: "#fff",
                textStyle: {
                    color: "#fff"
                },
                pageButtonItemGap: 1,
                pageTextStyle: {
                    color: "#fff"
                },
                pageIconSize: 10,
                itemWidth: 20,
                itemHeight: 10,
                data: chartData.legendData,
            },
            series: [
                {
                    name: name,
                    type: 'pie',
                    radius: '55%',
                    center: ['40%', '50%'],
                    label: {
                        show: false,
                        // position: "inside"
                    },
                    data: chartData.seriesData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        // 设置参数并初始化
        myChart.setOption(option);

        myChart.on('click', function (params) {
            if (params.componentType === 'series' && params.data.children && params.data.children.length > 0) {
                slideDetail("grid", params.data.children, params.data.name);
            } else {
                closeDetail();
            }
        });
    }
    function getData(data, seriesKey) {
        var legendData = [],
            seriesData = [];
        $.each(data, function (i, n) {
            legendData.push(n.name);
            n.value = n[seriesKey];
            seriesData.push(n);
        });
        var chartData = {
            legendData: legendData,
            seriesData: seriesData
        }
        return chartData;
    }
    /**
     *  programStatus 项目状态统计图
     */
    function initProgramStatus(id, data) {

    }
    /**
     *  initProgramFunnel 明星工程
     */
    function initProgramFunnel(id, data, name, hasChild, orient) {
        var myChart = echarts.init(document.getElementById(id));
        var chartData = getData(data, "value");
        var option = {
            tooltip: {
                trigger: 'item',
                textStyle: {
                    fontFamily: 'Microsoft YaHei'
                },
                formatter: "{a} <br/>{b} : <span class='map-num'>{c}%</span>"
            },
            legend: {
                type: 'scroll',
                orient: orient ? 'vertical' : 'horizontal',
                right: 10,
                top: 5,
                bottom: 20,
                pageIconColor: "#fff",
                textStyle: {
                    color: "#fff"
                },
                pageButtonItemGap: 1,
                pageTextStyle: {
                    color: "#fff"
                },
                pageIconSize: 10,
                itemWidth: 20,
                itemHeight: 10,
                data: chartData.legendData
            },
            series: [
                {
                    name: name,
                    type: 'funnel',
                    left: '10%',
                    width: '80%',
                    maxSize: '80%',
                    label: {
                        normal: {
                            show: false,
                            position: 'inside',
                            formatter: '{b}',
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        emphasis: {
                            position: 'inside',
                            formatter: "{b}: {c}%",
                            textStyle: {
                                color: '#fff',
                                fontFamily: 'Lccd'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            // opacity: 0.5,
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    },
                    data: chartData.seriesData
                }
            ]
        };
        // 设置参数并初始化
        myChart.setOption(option);
        // 点击事件
        myChart.on('click', function (params) {
            if (params.componentType === 'series' && params.data.children && Object.keys(params.data.children).length > 0) {
                $("#item_title").text(params.name);
                $.when(slideDetail("chart")).done(function () {
                    setTimeout(function () {
                        initProgramInvestment("chart_detail", params.data.children, ["申请拨付", "实际拨付"]);
                    }, 500);
                });
            } else {
                closeDetail();
            }
        });
    }
    /**
     *  initProgramInvestment 各项目进度图
     */
    function initProgramInvestment(id, data, legendData) {
        var myChart = echarts.init(document.getElementById(id));
        var chartData = getBarData(data);
        var option = {
            tooltip: {
                trigger: 'axis',
                position: [10, 10],
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                textStyle: {
                    fontFamily: 'Microsoft YaHei'
                },
                formatter: "<span style='color:#f0ab58'>{b}</span> <br/>{a0} : <span class='map-num'>{c0}</span>(万元)<br/>{a1} : <span class='map-num'>{c1}</span>(万元)"
            },
            legend: {
                orient: 'horizontal',
                top: 5,
                textStyle: {
                    color: "#fff"
                },
                itemWidth: 20,
                itemHeight: 10,
                data: legendData
            },
            grid: {
                left: '2px',
                containLabel: true
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: '#565656'
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontFamily: 'Microsoft YaHei'
                        }
                    },
                    data: chartData.xAxisData
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisTick: {
                        show: false
                    },
                    // y 轴线
                    axisLine: {
                        show: false,
                    },
                    splitLine: {
                        show: true,  //显示分割线
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: '#565656',
                            type: 'dotted'
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#f0ab58',
                            fontFamily: 'Lccd'
                        }
                    }
                }
            ],
            series: [
                {
                    name: legendData[0],
                    type: 'bar',
                    color: '#1ab394 ',
                    data: chartData.actualSeries
                },
                {
                    name: legendData[1],
                    type: 'bar',
                    color: '#f0ab58',
                    data: chartData.totalSeries
                }
            ]
        };
        myChart.setOption(option);

        // 点击事件
        myChart.on('click', function (params) {
            if (params.componentType === 'series' && params.data.children && Object.keys(params.data.children).length > 0) {
                $.when(slideDetail("chart")).done(function () {
                    $("#item_title").text(params.name);
                    setTimeout(function () {
                        initProgramInvestment("chart_detail", params.data.children, ["实际投资", "总投资"]);
                    }, 500);
                });
            } else {
                closeDetail();
            }
        });
    }
    function getBarData(data) {
        var xAxisData = [],
            totalSeries = [],
            actualSeries = [];
        $.each(data, function (i, n) {
            xAxisData.push(n.name || n.time);
            totalSeries.push({ value: n.total, children: n.children });
            actualSeries.push({ value: n.actual, children: n.children });
        });
        var chartData = {
            xAxisData: xAxisData,
            totalSeries: totalSeries,
            actualSeries: actualSeries
        };
        return chartData;
    }
    function closeItemChart() {
        $(".chart-div").slideUp(300);
        $(".overview-grid").slideUp(300);
        setTimeout(function () {
            $("#chart_div").html("<div id='chart_detail' />");
            $(".overview-grid").html('<table id="table_list_1"></table><div id="pager_list_1"></div>');
        }, 300);
    }
    function slideDetail(open, data, name) {
        var def = $.Deferred();
        closeDetail();
        if (open === "grid") {
            setTimeout(function () {
                initGrid("table_list_1", data, name);
                $(".overview-grid").slideDown(300);
                def.resolve();
            }, 500);
        } else {
            setTimeout(function () {
                $(".chart-div").slideDown(300);
                def.resolve();
            }, 500);
        }
        return def.promise();
    }
    function closeDetail() {
        // 隐藏所有详情
        $(".chart-div").slideUp(300);
        $(".overview-grid").slideUp(300);
        setTimeout(function () {
            $("#chart_div").html("<div id='chart_detail' />");
            $(".overview-grid").html('<table id="table_list_1"></table><div id="pager_list_1"></div>');
        }, 300);
    }
    function initGrid(id, data, name) {
        $(".overview-grid").html('<table id="table_list_1"></table><div id="pager_list_1"></div>');
        var mydata = [];
        var index = 1;
        $.each(data, function (i, n) {
            n.seq = index;
            n.percent = n.actual / n.total;
            mydata.push(n);
            index++;
        });
        $("#table_list_1").jqGrid({
            data: mydata,
            datatype: "local",
            height: 210,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 14,
            rowList: [10, 20, 30],
            colNames: ['序号', '水库', '实际投资', '总投资', '项目进度'],
            colModel: [
                { name: 'seq', index: 'seq', width: 60, sorttype: "int" },
                { name: 'name', index: 'name', width: 100 },
                { name: 'actual', index: 'actual', width: 80, align: "right", sorttype: "float", formatter: "number" },
                { name: 'total', index: 'total', width: 80, align: "right", sorttype: "float", formatter: "number" },
                { name: 'percent', index: 'percent', width: 80, align: "right", sorttype: "float" },
            ],
            pager: "#pager_list_1",
            viewrecords: true,
            caption: name,
            hidegrid: false
        });
    }
    if ($("#allmap") && $("#allmap").length > 0) {
        $.when(initMap()).done(function (map) {
            $.ajax({
                url: "index/getallbaseinfo",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    allData = data.data;
                    for (var i = 0; i < allData.length; i++) {
                        createMarker(allData[i], map);
                    }
                    // 主页面搜索
                    $(".search-location").on("click", "i", function() {
                        searchLocation(map, allData);
                    });
                    $(".search-location").on("keypress", "input", function () {
                        var keycode = event.keyCode || event.which || event.charCode;
                        if (keycode === 13) {
                            searchLocation(map, allData);
                        }
                    });
                }
            });
        });
        // echart数据处理
        // 饼图
        var paymentData = [{ value: 1500000, name: '中央累计拨付' }, { value: 2100000, name: '当前累计拨付' }]
        initPieChart("payment_chart", paymentData);
        var investmentData = [{ value: 1500000, name: '省政府投资' }, { value: 2100000, name: '中央投资' }, { value: 3100000, name: '当前投资' }]
        initPieChart("investment_chart", investmentData);
        // 投资分布
        var regionData = [
            { name: "安顺", total: 698600.98, actual: 225969, children: [{ name: "西秀区干沟水库", total: 698600.98, actual: 225969 }, { name: "西秀区雨棚水库", total: 2543666.74, actual: 213930 }, { name: "西秀区新场河水库工程", total: 435870.32, actual: 195615 }, { name: "紫云县打冲沟水库", total: 1808457.64, actual: 504616 }, { name: "镇宁自治县白沙水库", total: 1243110.22, actual: 843085 }, { name: "织金县白泥坡水库", total: 1259083.94, actual: 227307 }, { name: "普定县木栱河水库", total: 1371855.03, actual: 468491 }, { name: "镇宁县纳井田水库工程", total: 1148453.13, actual: 344755 }, { name: "长顺县马龙田水库", total: 1715567.5803, actual: 827124 }] },
            { name: "毕节", total: 2543666.74, actual: 213930, children: [{ name: "黔西县附廓水库加高扩建工程", total: 698600.98, actual: 225969 }, { name: "赫章县万家沟水库", total: 2543666.74, actual: 213930 }, { name: "七星关区普宜水库", total: 435870.32, actual: 195615 }, { name: "七星关区龙官桥水库", total: 1808457.64, actual: 504616 }, { name: "七星关区双河口水库", total: 1243110.22, actual: 843085 }, { name: "织金县洗马塘水库", total: 1259083.94, actual: 227307 }, { name: "赫章县河口水库", total: 1371855.03, actual: 468491 }, { name: "大方县岔河水库灌溉工程", total: 1148453.13, actual: 344755 }, { name: "纳雍县金蟾水库灌溉工程", total: 1715567.5803, actual: 827124 }] },
            { name: "贵阳", total: 435870.32, actual: 195615, children: [{ name: "修文县金龙水库", total: 698600.98, actual: 225969 }, { name: "花溪区栗木水库", total: 2543666.74, actual: 213930 }, { name: "开阳县杉木林水库工程", total: 435870.32, actual: 195615 }, { name: "贵阳市红岩水库", total: 1808457.64, actual: 504616 }, { name: "黔西县附廓水库加高扩建工程", total: 1243110.22, actual: 843085 }, { name: "威宁县杨湾桥水库", total: 1259083.94, actual: 227307 }, { name: "清镇市燕尾水库", total: 1371855.03, actual: 468491 }, { name: "长顺县马龙田水库", total: 1148453.13, actual: 344755 }, { name: "习水县铜灌口水库灌区工程", total: 1715567.5803, actual: 827124 }] },
            { name: "六盘水", total: 1808457.64, actual: 504616, children: [{ name: "钟山区中坡水库", total: 698600.98, actual: 225969 }, { name: "水城县出水沟水库", total: 2543666.74, actual: 213930 }, { name: "六盘水市双桥水库供水工程", total: 435870.32, actual: 195615 }, { name: "盘县卡河水库工程", total: 1808457.64, actual: 504616 }, { name: "钟山区关门山水库工程", total: 1243110.22, actual: 843085 }, { name: "盘县石桥水库", total: 1259083.94, actual: 227307 }, { name: "钟山区韭菜坪水库", total: 1371855.03, actual: 468491 }, { name: "六枝特区纳革水库", total: 1148453.13, actual: 344755 }, { name: "钟山区红岩水库", total: 1715567.5803, actual: 827124 }] },
            { name: "黔东南", total: 1243110.22, actual: 843085, children: [{ name: "黄平县印地坝水库", total: 698600.98, actual: 225969 }, { name: "镇远县天印水库", total: 2543666.74, actual: 213930 }, { name: "天柱鱼塘水库首部枢纽及库区复建工程", total: 435870.32, actual: 195615 }, { name: "剑河县地豆水库", total: 1808457.64, actual: 504616 }, { name: "镇远县狗鱼塘水库", total: 1243110.22, actual: 843085 }, { name: "施秉县潭子湾水库", total: 1259083.94, actual: 227307 }, { name: "雷山县西江水库", total: 1371855.03, actual: 468491 }, { name: "从江县登盆水库", total: 1148453.13, actual: 344755 }, { name: "台江县南开水库工程", total: 1715567.5803, actual: 827124 }] },
            { name: "黔南", total: 1259083.94, actual: 227307, children: [{ name: "三都县甲晒水库", total: 698600.98, actual: 225969 }, { name: "长顺县马龙田水库", total: 2543666.74, actual: 213930 }, { name: "福泉市官阳冲水库", total: 435870.32, actual: 195615 }, { name: "惠水县平寨水库", total: 1808457.64, actual: 504616 }, { name: "荔波县拉寨水库", total: 1243110.22, actual: 843085 }, { name: "荔波县拉毛水库工程", total: 1259083.94, actual: 227307 }, { name: "三都县甲照水库工程", total: 1371855.03, actual: 468491 }, { name: "都匀市大河水库", total: 1148453.13, actual: 344755 }, { name: "罗甸县林霞水库", total: 1715567.5803, actual: 827124 }] },
            { name: "黔西南", total: 1371855.03, actual: 468491, children: [{ name: "普安县大湾水库", total: 698600.98, actual: 225969 }, { name: "普安县三岔河水库", total: 2543666.74, actual: 213930 }, { name: "册亨县者述水库", total: 435870.32, actual: 195615 }, { name: "册亨县新花水库", total: 1808457.64, actual: 504616 }, { name: "晴隆县西泌河水库工程", total: 1243110.22, actual: 843085 }, { name: "马岭水利枢纽工程", total: 1259083.94, actual: 227307 }, { name: "安龙县者山河水库", total: 1371855.03, actual: 468491 }, { name: "兴义市木浪河水库扩建工程", total: 1148453.13, actual: 344755 }, { name: "册亨县册亨水库工程", total: 1715567.5803, actual: 827124 }] },
            { name: "铜仁", total: 1148453.13, actual: 344755, children: [{ name: "思南县沙坝水库工程", total: 698600.98, actual: 225969 }, { name: "思南县双河口水库", total: 2543666.74, actual: 213930 }, { name: "思南县枹木寨水利灌溉工程", total: 435870.32, actual: 195615 }, { name: "松桃县盐井水利工程", total: 1808457.64, actual: 504616 }, { name: "江口县鱼粮水库工程", total: 1243110.22, actual: 843085 }, { name: "松桃县白泥水库工程", total: 1259083.94, actual: 227307 }, { name: "江口县军屯水库工程", total: 1371855.03, actual: 468491 }, { name: "尖坡水库", total: 1148453.13, actual: 344755 }, { name: "万山区老山口水库", total: 1715567.5803, actual: 827124 }] },
            { name: "遵义", total: 1715567.5803, actual: 827124, children: [{ name: "余庆县小乌江水库", total: 698600.98, actual: 225969 }, { name: "播州区清水河水库", total: 2543666.74, actual: 213930 }, { name: "道真县沙坝水库扩建加高工程", total: 435870.32, actual: 195615 }, { name: "播州区苟江水库", total: 1808457.64, actual: 504616 }, { name: "习水县铜灌口水库灌区工程", total: 1243110.22, actual: 843085 }, { name: "汇川区麻沟水库工程", total: 1259083.94, actual: 227307 }, { name: "遵义市中桥水库工程", total: 1371855.03, actual: 468491 }, { name: "播州区平正水库", total: 1148453.13, actual: 344755 }, { name: "正安县杨柳溪水库工程", total: 1715567.5803, actual: 827124 }] }
        ]
        initRegionPie("region_pie", regionData, "total", "投资");
        // 项目状态
        var statusData = [
            { name: "大坝开挖", value: 25 },
            { name: "大坝填筑", value: 38 },
            { name: "前期工作", value: 174 },
            { name: "三通一平", value: 6 },
            { name: "施工准备", value: 13 },
            { name: "通水验收", value: 1 },
            { name: "项目关闭", value: 0 },
            { name: "蓄水验收", value: 33 },
            { name: "蓄水准备", value: 36 }
        ]
        initRegionPie("program_status", statusData, "value", "阶段数量(占比)");
        // 总投资与实际投资占比
        initProgramInvestment("program_investment", regionData, ["实际投资", "总投资"]);

        // 明星工程
        var programStarData = [
            { value: 30, name: '镇远县狗鱼塘水库', children: [{ time: "2017-01-01", actual: 225969, total: 698600.98 }, { time: "2017-01-02", actual: 213930, total: 2543666.74 }, { time: "2017-01-03", actual: 195615, total: 435870.32 }, { time: "2017-01-04", actual: 504616, total: 1808457.64 }, { time: "2017-01-05", actual: 843085, total: 1243110.22 }, { time: "2017-01-06", actual: 227307, total: 1259083.94 }, { time: "2017-01-07", actual: 468491, total: 1371855.03 }, { time: "2017-01-08", actual: 344755, total: 1148453.13 }, { time: "2017-01-09", actual: 827124, total: 1715567.5803 }] },
            { value: 10, name: '思南县枹木寨水利灌溉工程', children: [{ time: "2017-01-01", actual: 225239, total: 698600.98 }, { time: "2017-01-02", actual: 2121930, total: 2543666.74 }, { time: "2017-01-03", actual: 635615, total: 435870.32 }, { time: "2017-01-04", actual: 534616, total: 1808457.64 }, { time: "2017-01-05", actual: 837085, total: 1243110.22 }, { time: "2017-01-06", actual: 221207, total: 1259083.94 }, { time: "2017-01-07", actual: 484491, total: 1371855.03 }, { time: "2017-01-08", actual: 348755, total: 1148453.13 }, { time: "2017-01-09", actual: 884124, total: 1715567.5803 }] },
            { value: 5, name: '播州区苟江水库', children: [{ time: "2017-01-01", actual: 223739, total: 698600.98 }, { time: "2017-01-02", actual: 212120, total: 2543666.74 }, { time: "2017-01-03", actual: 694615, total: 435870.32 }, { time: "2017-01-04", actual: 53716, total: 1808457.64 }, { time: "2017-01-05", actual: 873085, total: 1243110.22 }, { time: "2017-01-06", actual: 223207, total: 1259083.94 }, { time: "2017-01-07", actual: 484491, total: 1371855.03 }, { time: "2017-01-08", actual: 348235, total: 1148453.13 }, { time: "2017-01-09", actual: 884834, total: 1715567.5803 }] },
            { value: 50, name: '黔西县附廓水库加高扩建工程', children: [{ time: "2017-01-01", actual: 278739, total: 698600.98 }, { time: "2017-01-02", actual: 2121340, total: 2543666.74 }, { time: "2017-01-03", actual: 699315, total: 435870.32 }, { time: "2017-01-04", actual: 523516, total: 1808457.64 }, { time: "2017-01-05", actual: 879485, total: 1243110.22 }, { time: "2017-01-06", actual: 223297, total: 1259083.94 }, { time: "2017-01-07", actual: 423491, total: 1371855.03 }, { time: "2017-01-08", actual: 349435, total: 1148453.13 }, { time: "2017-01-09", actual: 8884534, total: 1715567.5803 }] },
            { value: 80, name: '七星关区双河口水库', children: [{ time: "2017-01-01", actual: 278349, total: 698600.98 }, { time: "2017-01-02", actual: 2154340, total: 2543666.74 }, { time: "2017-01-03", actual: 635315, total: 435870.32 }, { time: "2017-01-04", actual: 523226, total: 1808457.64 }, { time: "2017-01-05", actual: 884485, total: 1243110.22 }, { time: "2017-01-06", actual: 242297, total: 1259083.94 }, { time: "2017-01-07", actual: 428391, total: 1371855.03 }, { time: "2017-01-08", actual: 3459435, total: 1148453.13 }, { time: "2017-01-09", actual: 8354534, total: 1715567.5803 }] }
        ]
        initProgramFunnel("program_funnel", programStarData, "工程进度");

    }
    function searchLocation(map, allData) {
        var plantName = $(".search-location>input").val(),
            meetConditions = [];
        map.clearOverlays();
        allData.map(function (item, i, allData) {
            if (item["plantName"].indexOf(plantName) > -1) {
                // meetConditions.push(item);
                createMarker(item, map);
            }
        });
    }
    // 首页更多信息
    $(".all-details").on("click", "a", function() {
        var baseInfoId = $(this).attr("project-id");
        location.href = "reservoir_index.html?baseInfoId" + baseInfoId;
    });

    // 关闭监控
    $(".video-close").on("click", function() {
        $(".overview-video").removeClass("video-show");
    });
});

function closeDetail() {
    // 隐藏所有详情
    $(".chart-div").slideUp(300);
    $(".overview-grid").slideUp(300);
    setTimeout(function () {
        $("#chart_div").html("<div id='chart_detail' />");
        $(".overview-grid").html('<table id="table_list_1"></table><div id="pager_list_1"></div>');
    }, 300);
}


