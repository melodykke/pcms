$(function () {

    var getThisUserUrl = "/user/getthisuser"; //拿到当前用户信息的url
    var getThisProjectUrl = "/user/getthisproject";
    var getoverallnotificationUrl = "/notification/getoverallnotification";
    var getoverallfeedbackUrl = "/feedback/getoverallfeedback";
    var saveFilesUrl = '/baseinfo/addfiles';
    var saveBaseInfoUrl = '/baseinfo/savebaseinfo';
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
        contentDiv.load('annualinvestment/tonewannualinvestment');
    });
    $('#annual_investment_plan_manage_entry').click(function () {
        contentDiv.load('annualinvestment/toannualinvestmentshow');
    });
    $('#tender_new_entry').click(function () {
        contentDiv.load('tender/tonewtender');
    });
    $('#tender_manage_entry').click(function () {
        contentDiv.load('tender/totendershow');
    });
    getThisUser(getThisUserUrl);
    getThisProject(getThisProjectUrl);
    getOverallNotification(getoverallnotificationUrl);
    getOverallFeedback(getoverallfeedbackUrl);
    setInterval(function () {
        getOverallNotification(getoverallnotificationUrl);
    }, 30000);
    setInterval(function () {
        getOverallFeedback(getoverallfeedbackUrl);
    }, 30000);

    function getpn() {
        console.log($('#plantName'));
    }


    function getThisUser(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                // 从返回的JSON当中获取product对象的信息，并赋值给表单
                var userInfo = data.data;
                $('#username .font-bold').html(userInfo.name);
                $('#name').html(userInfo.username + ' <b class="caret"></b>');
                dows(userInfo.username);
            }
        });
    }

    // 若果有 存入域 否则提示完善资料
    function getThisProject(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                // 若果有 存入域 否则提示完善资料
            }
        });
    }

    // 拿到所有未审批的项目
    function getOverallNotification(url) {
        $.getJSON(url, function (data) {
            if (data.code == 1002) {
                $('#countUnread').text(data.data.allUncheckedNum);
                var htmlTemp = "";
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
                htmlTemp += ' <li>\n' +
                    '  <a href="#" data-id="/notification/tonotification">\n' +
                    '  <i class="fa fa-envelope fa-fw"></i> 查看所有\n' +
                    '  </a>\n' +
                    '  </li>\n' +
                    '  <li class="divider"></li>'
                $('#notification_entrance').append(htmlTemp)
            }
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
            getpn();
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
        })

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
                var formData = new FormData();
                var personInfo = {};  // 空对象
                personInfo.name = $('#person_name').val();
                personInfo.tel = $('#tel').val();
                personInfo.qq = $('#qq').val();
                personInfo.email = $('#email').val();
                personInfo.id_num = $('#id_num').val();
                personInfo.title = $('#title').val();
                personInfo.address = $('#address').val();
                formData.append('personInfoStr', JSON.stringify(personInfo));
                $.ajax({
                    url: savePersonInfoUrl,
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
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
        websocket = new WebSocket('ws://sell01.natapp1.cc/websocket/' + username)
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
            websocket = new WebSocket('ws://sell01.natapp1.cc/websocket/' + username)
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
                closeOnConfirm: false
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
            closeOnConfirm: false
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

    });

    $('#bind_wechat_btn').click(function () {
        $('#auth_img').attr("src", "/wechatlogin/generateqrcode4login?time="+new Date())
        $('#wechat_binding_modal_body')
        $('#wechat_binding_modal').modal('show');

    })


    //微信modal
    $('.wechat-register-modal-close').on('click', function (e) {
        $('#wechat_binding_modal').modal('hide');
    })





    /*var map = new BMap.Map("allmap");
           var point = new BMap.Point(116.331398,39.897445);
           map.centerAndZoom(point,12); */
    /*
    var marker = new BMap.Marker(point);        // 创建标注
    map.addOverlay(marker); */                    // 将标注添加到地图中

    // 百度地图API功能
    var map = new BMap.Map("allmap", { mapType: BMAP_HYBRID_MAP });
    var point = new BMap.Point(106.630905, 26.674511);
    map.centerAndZoom(point, 8); // 初始化地图，设置中心店坐标和地图级别


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

    // 地图打点
    function createMarker(data) {
        var myIcon = new BMap.Icon("markers1.png", new BMap.Size(23, 25), {
            // 指定定位位置。
            // 当标注显示在地图上时，其所指向的地理位置距离图标左上
            // 角各偏移10像素和25像素。您可以看到在本例中该位置即是
            // 图标中央下端的尖角位置。
            anchor: new BMap.Size(10, 25),
            // 设置图片偏移。
            // 当您需要从一幅较大的图片中截取某部分作为标注图标时，您
            // 需要指定大图的偏移位置，此做法与css sprites技术类似。
            imageOffset: new BMap.Size(0, 0 - 25)   // 设置图片偏移
        });
        // 创建标注对象并添加到地图
        // var marker = new BMap.Marker(point, {icon: myIcon});
        var point_item = new BMap.Point(data.longitude, data.latitude);
        var marker = new BMap.Marker(point_item);
        map.addOverlay(marker);

        // 定义展示数据
        var str = [
            "<div class='map-tips'><label>水库：</label><span class='plant-name'>" + data.plant_name + "</span></div>",
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
            /* var opts = { width: 120, height: 150, title: "详细信息" };
            var infoWindow = new BMap.InfoWindow(detail, opts);
            map.openInfoWindow(infoWindow, point); */
        });

        // 设置鼠标离开事件
        marker.addEventListener("mouseout", function (e) {
            var label = this.getLabel();
            label.setContent("");//设置标签内容为空
            label.setStyle({ border: "none", width: "0px", padding: "0px" });//设置标签边框宽度为0
        });

        /*  marker.addEventListener("mouseout", function (e) {
             map.closeInfoWindow();//设置标签内容为空
         }); */
    }

    // var allData = [[106.630905, 26.674511, "小石", "28y", "170cm", "70kg"], [108.259864, 27.944777, "小王", "28y", "160cm", "50kg"]];
    var allData = [{ "latitude": 26.9852206, "longitude": 107.7936172, "plant_name": "黄平县印地坝水库", "location": "黄平县旧州镇境内", "dam_type": "碾压混凝土重力坝", "legal_representative_name": "黔东南州水利投资有限责任公司", "scale": "中型", "level": "Ⅲ", "overview": "黄平县印地坝水库工程位于黄平县旧州镇境内，坝址位于舞阳河支流冷水河段，水库正常蓄水位720m，死水位693m，设计洪水位722.24m（P=2%），校核洪水位723.27m（P=0.2%）；水库总库容为1462万m3，正常蓄水位相应库容为1154万m3，死库容为50万m3，兴利库容为1104万m3。印地坝水库是以城镇供水、农田灌溉和农村人畜饮水为建设任务的中型水库工程。", "central_accumulative_payment": "中央累计拨付", "central_investment": "中央投资", "local_accumulative_payment": "当前累计拨付", "local_investment": "当前投资", "provincial_investment": "省政府投资", "provincial_loan": "省政府贷款", "total_investment": "总投资", "project_task": "主要任务：城镇供水；农田灌溉和农村人畜饮水。\n主要建筑物：水库枢纽工程由碾压混凝土重力坝、坝顶溢流表孔、坝 身取水兼放空建筑物及右岸山体处理等组成。" },
        { "latitude": 27.270933, "longitude": 108.383663, "plant_name": "镇远县天印水库", "location": "镇远县都坪镇天印村", "scale": "中型", "dam_type": "碾压混凝土重力坝", "legal_representative_name": "黔东南州水利投资有限责任公司", "level": "Ⅲ", "overview": "天印水库拟建于龙江河中游右岸一级支流龙洞河上，坝址在镇远县都坪镇天印村，距龙洞河汇口约4km。天印水库坝址以上集雨面积85.3km2，多年平均年径流量4644万m3，多年平均流量1.47 m3/s，水库具有年调节性能。水库正常蓄水位592.5m，相应库容941万m3，死水位568.5m，死库容67万m3，兴利库容874万m3，总库容1074万m3。水库工程等别为III等，工程规模为中型。 天印水库工程任务为供水、灌溉。水库供水范围为镇远县的都坪、江古和岑巩县的龙田、平庄4个乡镇，设计水平年（2030年）总", "central_accumulative_payment": "中央累计拨付", "central_investment": "中央投资", "local_accumulative_payment": "当前累计拨付", "local_investment": "当前投资", "provincial_investment": "省政府投资", "provincial_loan": "省政府贷款", "total_investment": "总投资", "project_task": "" }]
    for (var i = 0; i < allData.length; i++) {
        createMarker(allData[i]);
    }
    /* $(function () {

        var allData = [[106.630905, 26.674511, "小石", "28y", "170cm", "70kg"], [108.259864, 27.944777, "小王", "28y", "160cm", "50kg"]];
        for (var i = 0; i < allData.length; i++) {
            createMarker(allData[i]);
        }

    }); */
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
            '<div class="panel-detail-title">' + data.plant_name + '</div>',
            '<div class="col-lg-12 panel-item"><div><label>水库规模：</label>',
            '<span id="scale">' + data.scale + '</span></div>',
            ' <div><label>水库等级：</label>',
            '<span id="level">' + data.level + '</span></div>',
            '<div><label>所属单位：</label>',
            '<span id="legal_representative_name">' + data.legal_representative_name + '</span></div>',
            '<div><label>所在位置：</label>',
            '<span id="location">' + data.location + '</span></div>',
            '<div><label>主要功能：</label>',
            '<span id="project_task">' + data.project_task + '</span></div>',

            '<div><label>水库概述：</label>',
            '<span id="overview">' + data.overview + '</span></div>'
        ]
        $("#plant_name").text(data.plant_name);
        $("#item_detail").html(strHtml.join(""));
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
        $('.right-panel').stop().animate({ marginRight: "-400px" }, 300);
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
                    splitLine: {
                        show: true,  //显示分割线
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: '#3a3939',
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

        // echart数据处理

        // 饼图
        var paymentData = [{ value: 1500000, name: '中央累计拨付' }, { value: 2100000, name: '当前累计拨付' }]
        initPieChart("payment_chart", paymentData);
        var investmentData = [{ value: 1500000, name: '省政府投资' }, { value: 2100000, name: '中央投资' }, { value: 3100000, name: '当前投资' }]
        initPieChart("investment_chart", investmentData);
        // 柱状图
        initBarChart("overview_map");
    })
});

