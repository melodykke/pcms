$(function () {
    var getprojectmonthlyreportbyprojectmonthlyreportidurl = "monthlyreport/getprojectmonthlyreportbyprojectmonthlyreportid"; // 依靠内部pId获取月报
    var getprojectmonthlyreportshowbytimeUrl = '/monthlyreport/getprojectmonthlyreportshowbytime'; // 根据时间区间获取月报

    getProjectMonthlyReport();

    //  增加一个可以藏起来PID的页面元素 TODO


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


    $('#mr_table').click(function () {
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
                console.log(data)
                if (data.code == 1002){
                    $('#data_table_modal').modal();
                    $('.dataTables-example').DataTable({
                        bFilter: false,    //去掉搜索框
                        retrieve: true,
                        destroy:true,
                        bInfo:false,       //去掉显示信息
                        data:data.data,
                        paging: false,
                        ordering:false,
                        // autoWidth:auto,
                        lengthChange: false,
                        responsive: true,
                        dom: '<"html5buttons"B>lTfgitp',
                        buttons: [
                            { extend: 'copy'},
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
        })
    })

    $('#project_monthly_report_content_div').mouseenter(function () {
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

    });

    function refreshContents(data){
        $('#plantName').text(data.data.plantName);
        $('#year_tag').text(data.data.year);
        $('#month').text(data.data.month+' 月');
        data.data.state == 0 ? $('#state').text("待审核") :  $('#state').text("已审核");
        $('#submitter').text(data.data.submitter);
        $('#submitTime').text(data.data.createTime);
        data.data.state == 0 ? $('#state_bar').css("width", "50%") : $('#state_bar').css("width", "100%");
        data.data.state == 0 ? $('#state_msg').text("等待上级审批") : $('#state_msg').text("审核通过");
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
                '                                                                                            <a type="button" class="btn-primary pull-right" href="/download/downloadFile?fileId='+item.imgAddr+'">下载</a>\n' +
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
        var checkedOfAll=$("#my-checkbox").prop("checked");
        var checkinfo=$('#approve_area').val();
        alert(checkedOfAll)
        alert(checkinfo)
    })



})