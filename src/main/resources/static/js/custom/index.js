$(function () {

    var getThisUserUrl = "/user/getthisuser"; //拿到当前用户信息的url
    var getThisProjectUrl = "/user/getthisproject";
    var getAllUnreadUrl = "/notification/getallunchecked";
    var saveFilesUrl = '/baseinfo/addfiles';
    var saveBaseInfoUrl = '/baseinfo/savebaseinfo';
    // 提交个人信息
    var savePersonInfoUrl = '/user/personinfosubmit';
    var rtFileTempPath; // 服务器文件暂存地址
    var uploadFileFlag = true;

    var contentDiv = $('#main_content');
    $('#project_monthly_report').click(function () {
        contentDiv.load('reporter/projectmonthlyreport');
    })
    $('#project_months').click(function () {
        contentDiv.load('reporter/projectmonths');
    })
/*    $('#person_info').click(function () {
        contentDiv.load('user/personinfo');
    })*/
    $('#account_config').click(function () {
        contentDiv.load('account/accountconfig');
    })
    $('#notification_entrance').on('click', 'a', function (e) {
        var target = e.currentTarget;
       /* if ('monthly_report_notification' == target.dataset.id){
            contentDiv.load('notification/tonotification');
        }*/
        contentDiv.load('notification/tonotification');
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

    function getpn() {
        console.log($('#plantName'));
    }





    function getThisUser(url) {
        $.getJSON(url, function(data) {
            if (data.code==1002) {
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
                    if (item.object.length <1) {
                        htmlTemp += '';
                    } else {
                        htmlTemp += ' <li>\n' +
                            '                                <a href="#" data-id="'+ item.url +'">\n' +
                            '                                    <div>\n' +
                            '                                        <i class="fa fa-envelope fa-fw"></i> '+ item.type +'待审批\n' +
                            '                                        <span class="pull-right text-muted small">'+ item.timeDiff +'</span>\n' +
                            '                                    </div>\n' +
                            '                                </a>\n' +
                            '                            </li>\n' +
                            '                            <li class="divider"></li>'
                    }
                })
                $('#notification_entrance').append(htmlTemp)
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
                                $('#base_info_modal').modal();
                            } else {
                                $(location).attr("href", "index.html");
                            }
                        }
                    );
                } else if (data.code == 1002) {
                    contentDiv.load('baseinfo/baseinfoshow');
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
        onFinished: function (event, currentIndex)
        {
            getpn();
            var baseInfoVO = {};
            var form = $(this);
            console.log($('#plantName'))
            console.log($('#plantName').val())
            baseInfoVO.plantName = $('#plantName').val();
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
            baseInfoVO.centralInvestment = $('#centralInvestment').val();
            baseInfoVO.localInvestment = $('#localInvestment').val();
            baseInfoVO.centralAccumulativePayment = $('#centralAccumulativePayment').val();
            baseInfoVO.localAccumulativePayment = $('#localAccumulativePayment').val();
            baseInfoVO.provincialLoan = $('#provincialLoan').val();
            baseInfoVO.catchmentArea = $('#catchmentArea').val();
            baseInfoVO.spillway = $('#spillway').val();
            baseInfoVO.irrigatedArea = $('#irrigatedArea').val();
            baseInfoVO.installedCapacity = $('#installedCapacity').val();
            baseInfoVO.livestock = $('#livestock').val();
            baseInfoVO.waterSupplyPopulation = $('#waterSupplyPopulation').val();
            baseInfoVO.provincialInvestment = $('#provincialInvestment').val();
            baseInfoVO.provincialAccumulativePayment = $('#provincialAccumulativePayment').val();
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
        rules : {
            name : {
                required:true,
                minlength:2,
                maxlength:64
            },
            tel : {
                required:true,
                minlength:2,
                maxlength:64
            },
            qq : {
                required:true,
                digits:true
            },
            email : {
                required:true,
                email: true
            },
            id_num : {
                required:true,
                minlength:18,
                maxlength:18
            },
            title : {
                required:true,
                minlength:2,
                maxlength:64
            },
            address : {
                required:true,
                minlength:10,
                maxlength:64
            }
        },
        submitHandler:function(form) {
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
                    contentType : false,
                    processData : false,
                    cache : false,
                    success: function (data) {
                        if (data.code == 1002) {
                            $('#person_info_modal').modal('hide');
                            $('#person_info').click();
                        } else {
                            $('#person_info_rt_msg').text("错误("+data.code+")："+data.msg);
                        }
                    }
                });
            }
        }
    });



    /*配置向导*/
    var enjoyhint_script_data =[
        {
            selector:'#person_menu',
            event:'click',
            description:'请点击用户',
            "skipButton" : {text: "退出"},
        },
        {
            selector:'#person_info',
            event:'custom',
            event_type:'next',
            "nextButton": {text:"下一步"},
            description:'请点击个人设置',
            "skipButton" : {text: "退出"},
        },
        {
            selector:'#menu_1',
            event:'click',
            "skipButton" : {text: "退出"},
            description:'请点击项目储备!',
        },
        {
            selector:'#menu_2',
            event:'click',
            "skipButton" : {text: "退出"},
            description:'请点击基本信息维护!',
        },
        {
            selector:'#base_info',
            event:'custom',
            event_type:'next',
            "nextButton": {text:"下一步"},
            "skipButton" : {text: "退出"},
            description:'请点击项目概况!',
        },
        {
            selector:'#basic_info',
            event:'custom',
            event_type:'next',
            "nextButton": {text:"下一步"},
            "skipButton" : {text: "退出"},
            description:'这是项目基本情况!',
        },
        {
            selector:'#basic_jd',
            event:'custom',
            event_type:'next',
            "nextButton": {text:"下一步"},
            "skipButton" : {text: "退出"},
            description:'这是项目审批情况!',
        }]




    $('#help_page').click(function () {
        enjoyhint_instance = new EnjoyHint({
            onEnd:function(){
                swal({
                    title:"完成!",
                    text:"恭喜你，完成基本配置!",
                    type:"success"
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
    function reconnect(username){
        websocket = new WebSocket('ws://sell01.natapp1.cc/websocket/' + username)
    }
    function dows(username){
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
                    websocket.send("HeartBeat:"+username, "beat");
                }, this.timeout)
            }
        }
        if('WebSocket' in window) {
            websocket = new WebSocket('ws://sell01.natapp1.cc/websocket/' + username)
        }else {
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
            console.log('收到消息：'+event.data);
            if(event.data.startsWith('echo')){
                heartCheck.reset();
            }else{
                var wsMessage = eval("(" + event.data + ")");
                showNotification(wsMessage.title,wsMessage.msg, wsMessage.url);
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
        var $toast = toastr['info'](msg,title); // Wire up an event handler to a button in the toast, if it exists
    }


});

