$(function () {
    var getprojectmonthlyreportbyprojectmonthlyreportidurl = "monthlyreport/getprojectmonthlyreportbyprojectmonthlyreportid"; // 依靠内部pId获取月报
    var getprojectmonthlyreportshowbytimeUrl = '/monthlyreport/getprojectmonthlyreportshowbytime'; // 根据时间区间获取月报

    getProjectMonthlyReport();

    //  增加一个可以藏起来PID的页面元素 TODO

    var datatable = [
        ["1","计划总投资","万元","","","对应此处"],
        ["2","本年计划投资","万元","","对应此处",""],
        ["3","完成投资","","本月完成投资","本年完成投资","开工累计完成投资"],
        ["3.1","  按概算构成分","","","",""],
        ["3.1.1","   建筑工程","万元","","","",],
        ["3.1.2","   金属机构设备及安装工程","万元","","",""],
        ["3.1.3","   机电设备及安装工程","万元","","",""],
        ["3.1.4","   施工临时工程","万元","","",""],
        ["3.1.5","   独立费用","万元","","",""],
        ["3.1.6","   征地及移民投资","万元","","",""],
        ["3.1.7","   水土保持工程","万元","","",""],
        ["3.1.8","   环境保护工程","万元","","",""],
        ["3.1.9","   其他","万元","","","",],
        ["3.2","  按资金来源分","","本月完成投资","本年完成投资","开工累计完成投资"],
        ["3.2.1","    中央投资","万元","","",""],
        ["3.2.2","    省级投资","万元","","",""],
        ["3.2.3","    市县投资","万元","","",""],
        ["4","到位资金","","本月到位资金","本年到位资金","开工累计到位资金"],
        ["4.1","    中央投资","万元","","",""],
        ["4.2","    省级投资","万元","","",""],
        ["4.3","    市县投资","万元","","",""],
        ["5","已完成工程量","","本月完成工程量","本年完成工程量","开工累计完成工程量"],
        ["5.1","    土石方明挖","万立方米","","",""],
        ["5.2","    土石方洞挖","万立方米","","",""],
        ["5.3","    土石方回填","万立方米","","",""],
        ["5.4","    砌石","万立方米","","",""],
        ["5.5","    混凝土","万立方米","","",""],
        ["5.6","    钢筋","吨","","",""],
        ["5.7","    灌浆","米","","",""],
        ["6","  劳动力投入","","本月投入","本年投入","开工累计投入"],
        ["6.1","   投入劳动力","万工日","","",""]
    ];

    function getProjectMonthlyReport() {
        $.ajax({
            url: getprojectmonthlyreportbyprojectmonthlyreportidurl,
            type: 'POST',
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                refreshContents(data);
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });
    }



    $('#last_month').click( function(){
        var lastMonth = $('#year_tag').text()+'-0'+ (parseInt($('#month').text())-1);
        $.ajax({
            url: getprojectmonthlyreportshowbytimeUrl,
            type: 'POST',
            data: JSON.stringify({"time":lastMonth}),
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                if (data.code == 1002) {
                    refreshContents(data);
                }
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });

        $('#animation_box').removeAttr('class').attr('class', '');
        var animation = $(this).attr("data-animation");
        $('#animation_box').addClass('animated');
        $('#animation_box').addClass(animation);
        setTimeout(function(){
            $('#animation_box').removeAttr('class').attr('class', '');
        }, 500);

        // $('#animation_box').delay(2000).removeAttr('class').attr('class', '');
        return false;
    });
    $('#next_month').click( function(){
        var lastMonth = $('#year_tag').text()+'-0'+ (parseInt($('#month').text())+1);
        $.ajax({
            url: getprojectmonthlyreportshowbytimeUrl,
            type: 'POST',
            data: JSON.stringify({"time":lastMonth}),
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                if (data.code == 1002) {
                    refreshContents(data);
                }
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });

        $('#animation_box').removeAttr('class').attr('class', '');
        var animation = $(this).attr("data-animation");
        $('#animation_box').addClass('animated');
        $('#animation_box').addClass(animation);

        setTimeout(function(){
            $('#animation_box').removeAttr('class').attr('class', '');
        }, 500);

        return false;
    });

    $('#mr_show_date .input-group.date').datepicker({
        language: "zh-CN",
        format: 'yyyy-mm',
        minViewMode: 1,
        keyboardNavigation: false,
        forceParse: false,
        forceParse: false,
        autoclose: true,
        todayHighlight: true
    }).on('changeDate',function () {
        var time=$('#input_time').val();
        $.ajax({
            url: getprojectmonthlyreportshowbytimeUrl,
            type: 'POST',
            data: JSON.stringify({"time":time}),
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                if (data.code == 1002) {
                    refreshContents(data);
                }
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });

        $('#animation_box').removeAttr('class').attr('class', '');
        $('#animation_box').addClass('animated');
        $('#animation_box').addClass('shake');

        setTimeout(function(){
            $('#animation_box').removeAttr('class').attr('class', '');
        }, 500);

        return false;
    });


/*    $('#mr_table').click(function () {

    })*/

   /* $('#project_monthly_report_content_div').mouseenter(function () {
        window.onmousewheel=function(){
            return false
        };
    });
    $('#project_monthly_report_content_div').mouseleave(function () {
        window.onmousewheel=function(){
            return true
        };
    });
    $('#project_monthly_report_content_div').mousewheel(function (e, delta) {
        if (delta > 0) {
            $('#last_month').trigger("click");
        }else {
            $('#next_month').trigger("click");
        }
    });*/

    function refreshContents(data){
        $('#plantName').text(data.data.plantName);
        $('#year_tag').text(data.data.year);
        $('#month').text(data.data.month+' 月');
        if (data.data.state == 0) {
            $('#state').text("待审核")
        } else if (data.data.state == 1) {
            $('#state').text("已审核");
        } else if (data.data.state == -1) {
            $('#state').text("审核未通过")
        }
        if (data.data.state == 1) {
            $('#check_btn').html('<span class="label label-primary"><i class="fa fa-check"></i> 已审批通过</span>');
        } else if (data.data.state == -1) {
            $('#check_btn').html('<span class="label label-danger"><i class="fa fa-times"></i> 已拒绝</span>');
        } else {
            $('#check_btn').html('<a id="" class="btn btn-danger btn-facebook animation_select" data-toggle="modal" data-target="#approve_modal">\n' +
                '                审批\n' +
                '                </a>');
        }
        $('#submitter').text(data.data.submitter);
        $('#submitTime').text(data.data.createTime);
        data.data.state == 0 ? $('#state_bar').css("width", "50%") : $('#state_bar').css("width", "100%");
        if (data.data.state == 0) {
            $('#state_msg').text("等待上级审批")
        } else if (data.data.state == 1) {
            $('#state_msg').text("已通过")
        } else if (data.data.state == -1) {
            $('#state_msg').text("未通过")
        }
        if (data.data.state == -1) {
            $('#tabbtn').append('');
        } else {
            $('#tabbtn').append('<li class=""><a data-toggle="modal" id="mr_table"> <i class="fa fa-pie-chart"></i>月报表</a></li>')
            $('#tabbtn').on('click', '#mr_table', function (e) {
                var  labelItemListDataTable = $('.dataTables-example').dataTable();
                labelItemListDataTable.fnClearTable();
                labelItemListDataTable.fnDestroy();
                $.ajax({
                    url: 'monthlyreport/getmonthlyreportexcelbyprojectid',
                    type: 'GET',
                    data: {projectMonthlyReportId: $('#plantName').attr('projectMonthlyReportId')
                        ,currentDate: $('#year_tag').text()+'-0'+ (parseInt($('#month').text()))},
                    contentType: 'application/json',
                    beforeSend:function () {
                        $('#loading').show();
                    },
                    success: function (data) {
                        if (data.code == 1002){
                            $('#data_table_modal').modal();
                            $('.dataTables-example').DataTable({
                                bFilter: false,    //去掉搜索框
                                retrieve: true,
                                destroy:true,
                                bInfo:false,       //去掉显示信息
                                data:datatable,
                                paging: false,
                                ordering:false,
                                bAutoWidth:true,
                                lengthChange: false,
                                responsive: true,
                                dom: '<"html5buttons"B>lTfgitp',
                                buttons: [
                                    {extend: 'copy'},
                                    {extend: 'excel', title: 'ExampleFile'},
                                    {extend: 'print',
                                        customize: function (win){
                                            $(win.document.body).addClass('white-bg');
                                            $(win.document.body).css('font-size', '10px');

                                            $(win.document.body).find('table')
                                                .addClass('compact')
                                                .css('font-size', 'inherit');
                                        }
                                    }
                                ]
                            });
                        }
                    },
                    complete: function () {
                        $("#loading").hide();
                    },
                    error: function (data) {
                        console.info("error: " + data.msg);
                    }
                });
                $('#data_table_modal').modal('show');
                $('#dismiss_modal').click(function () {
                    $('#data_table_modal').modal('hide');
                })
            })
        }

        $('#civilEngineering').text(data.data.civilEngineering);
        $('#electromechanicalEquipment').text(data.data.electromechanicalEquipment);
        $('#metalMechanism').text(data.data.metalMechanism);
        $('#temporaryWork').text(data.data.temporaryWork);
        $('#independentCost').text(data.data.independentCost);
        $('#reserveFunds').text(data.data.reserveFunds);
        $('#resettlementArrangement').text(data.data.resettlementArrangement);
        $('#waterConservation').text(data.data.waterConservation);
        $('#environmentalProtection').text(data.data.environmentalProtection);
        $('#otherCost').text(data.data.otherCost);
        $('#openDug').text(data.data.openDug);
        $('#holeDug').text(data.data.holeDug);
        $('#backfill').text(data.data.backfill);
        $('#grout').text(data.data.grout);
        $('#masonry').text(data.data.masonry);
        $('#concrete').text(data.data.concrete);
        $('#rebar').text(data.data.rebar);
        $('#report_year').text(data.data.year);
        $('#report_month').text(data.data.month);
        $('#labourForce').text(data.data.labourForce);
        $('#constructionContent').text(data.data.constructionContent);
        $('#remark').text(data.data.remark);
        $('#visualProgress').text(data.data.visualProgress);
        $('#difficulty').text(data.data.difficulty);
        $('#measure').text(data.data.measure);
        $('#suggestion').text(data.data.suggestion);
        $('#plantName').attr('projectMonthlyReportId', data.data.projectMonthlyReportId);
        var projectMonthlyReportImgVOList = data.data.projectMonthlyReportImgVOList;
        var file_display_html = '';
        projectMonthlyReportImgVOList.map(function (item, index) {
            file_display_html += '<div class="file-box">\n' +
                '                                                                                    <div class="file">\n' +
                '                                                                                        <span class="corner"></span>\n' +
                '                                                                                        <div class="image" style="background:url(' + item.thumbnailAddr + ');background-size:cover;">\n' +
                '                                                                                        </div>\n' +
                '                                                                                        <div class="file-name">\n' +
                '                                                                                            文件\n' +
                '                                                                                            <br/>\n' +
                '                                                                                            <small>'+ item.createTime +'</small>\n' +
                '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/monthlyreportfile?fileId='+item.imgAddr+'">下载</a>\n' +
                '                                                                                        </div>\n' +
                '                                                                                    </div>\n' +
                '                                                                                </div>'
        });
        $('#files_display_div').html("");
        $('#files_display_div').html(file_display_html);
    }


    $("[name='my-checkbox']").bootstrapSwitch({
        onText : "拒绝",
        offText : "通过",
        onColor : "danger",
        offColor : "success",
        size : "large",
        onSwitchChange : function() {
            var checkedOfAll=$("#my-checkbox").prop("checked");
            if (checkedOfAll==false){
                $('#approve_input').hide()
            }
            else {
                $('#approve_input').show()
            }
        }
    });

    $('#submit').click(function () {
        var switchState = $("#my-checkbox").prop("checked");  // true: 按钮为通过 false：按钮通过
        var checkinfo = $('#approve_area').val();
        var projectMonthlyReportId = $('#plantName').attr("projectmonthlyreportid");
        $.ajax({
            url: "monthlyreport/approvemonthlyreport",
            type: 'POST',
            data: JSON.stringify({"switchState":switchState, "checkinfo":checkinfo, "projectMonthlyReportId":projectMonthlyReportId}),
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                if (data.code == 1002) {
                    $('#monthly_report_check_div').html('');
                    $('#monthly_report_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 成 功</h1></div> <div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                } else {
                    $('#monthly_report_check_div').html('');
                    $('#monthly_report_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
                        '                <div class="form-group animated fadeIn" ><label style="font-size: 15px;">'+ data.msg +'</label></div>\n' +
                        '            </div><div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                }
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });
    })
    $('#monthly_report_check_div').on('click', '#check_result_confirm_btn', function (e) {
        $('#main_content', parent.document).load('reporter/projectmonths');
    })

})