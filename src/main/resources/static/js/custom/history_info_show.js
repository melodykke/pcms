$(function () {

    getHistoryStatistic("monthlyreport/gethistorystatistic");

    function getHistoryStatistic(url) {

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            beforeSend:function () {
                completeLoading();
            },
            success: function (data) {
                var historyMonthlyStatistic = data.data;
                if (historyMonthlyStatistic.state == 0) {
                    $('#state_bar').html('<div style="width: 50%;" class="progress-bar"></div>')
                    $('#state_msg').html("等待审批")
                } else if (historyMonthlyStatistic.state == 1) {
                    $('#state_bar').html('<div style="width: 100%;" class="progress-bar"></div>')
                    $('#state_msg').html("审批通过")
                    $('#history_monthly_statistic_approval_div').remove();
                } else if (historyMonthlyStatistic.state == -1) {
                    $('#state_bar').html('<div style="width: 100%;" class="progress-bar"></div>')
                    $('#state_msg').html("审批未通过")
                    $('#history_monthly_statistic_approval_btn').remove();
                }
                $('#historyCivilEngineering').val(historyMonthlyStatistic.historyCivilEngineering);
                $('#historyMetalMechanism').val(historyMonthlyStatistic.historyMetalMechanism);
                $('#historyTemporaryWork').val(historyMonthlyStatistic.historyTemporaryWork);
                $('#historyResettlementArrangement').val(historyMonthlyStatistic.historyResettlementArrangement);
                $('#historyWaterConservation').val(historyMonthlyStatistic.historyWaterConservation);
                $('#historyIndependentCost').val(historyMonthlyStatistic.historyIndependentCost);
                $('#historyElectromechanicalEquipment').val(historyMonthlyStatistic.historyElectromechanicalEquipment);
                $('#historyEnvironmentalProtection').val(historyMonthlyStatistic.historyEnvironmentalProtection);
                $('#historyOtherCost').val(historyMonthlyStatistic.historyOtherCost);
                $('#historySourceCentralInvestment').val(historyMonthlyStatistic.historySourceCentralInvestment);
                $('#historyAvailableCentralInvestment').val(historyMonthlyStatistic.historyAvailableCentralInvestment);
                $('#historySourceProvincialInvestment').val(historyMonthlyStatistic.historySourceProvincialInvestment);
                $('#historyAvailableProvincialInvestment').val(historyMonthlyStatistic.historyAvailableProvincialInvestment);
                $('#historySourceLocalInvestment').val(historyMonthlyStatistic.historySourceLocalInvestment);
                $('#historyAvailableLocalInvestment').val(historyMonthlyStatistic.historyAvailableLocalInvestment);
                $('#historyOpenDug').val(historyMonthlyStatistic.historyOpenDug);
                $('#historyConcrete').val(historyMonthlyStatistic.historyConcrete);
                $('#historyRebar').val(historyMonthlyStatistic.historyRebar);
                $('#historyHoleDug').val(historyMonthlyStatistic.historyHoleDug);
                $('#historyGrout').val(historyMonthlyStatistic.historyGrout);
                $('#historyLabourForce').val(historyMonthlyStatistic.historyLabourForce);
                $('#historyBackfill').val(historyMonthlyStatistic.historyBackfill);
                $('#historyMasonry').val(historyMonthlyStatistic.historyMasonry);
                $('#historyLabourForce').val(historyMonthlyStatistic.historyLabourForce);
                $('#historyLabourForce').val(historyMonthlyStatistic.historyLabourForce);
                $('#historyLabourForce').val(historyMonthlyStatistic.historyLabourForce);
                $('#historyLabourForce').val(historyMonthlyStatistic.historyLabourForce);
            },
            complete: function () {
                $("#loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.msg);
            }
        });
    }

    $("[name='monthly-history-statistic-checkbox']").bootstrapSwitch({
        onText : "拒绝",
        offText : "通过",
        onColor : "danger",
        offColor : "success",
        size : "large",
        onSwitchChange : function() {
            var checkedOfAll=$("#monthly_history_statistic_checkbox").prop("checked");
            if (checkedOfAll==false){
                $('#monthly_history_statistic_approve_input').hide()
            }
            else {
                $('#monthly_history_statistic_approve_input').show()
                $('#monthly_history_statistic_area').text('')
            }
        }
    });
    $('#history_info_approve_submit').click(function () {
        var switchState = $("#monthly_history_statistic_checkbox").prop("checked");
        var checkinfo = $('#monthly_history_statistic_area').val();
        $.ajax({
            url: "monthlyreport/approvehistorymonthlystatistic",
            type: "POST",
            data: JSON.stringify({"switchState": switchState, "checkinfo": checkinfo}),
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                if (data.code == 1002 || data.code == 1003) {
                    $('#history_statistic_check_div').html('');
                    $('#history_statistic_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 成 功</h1></div> <div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                } else {
                    $('#history_statistic_check_div').html('');
                    $('#history_statistic_check_div').html('<div class="modal-header"><h1 class="modal-title">操 作 出 错</h1></div>   <div class="modal-body">\n' +
                        '                <div class="form-group animated fadeIn" ><label style="font-size: 15px;">'+ data.msg +'</label></div>\n' +
                        '            </div><div class="modal-footer">\n' +
                        '                <button type="button" id="check_result_confirm_btn" class="btn btn-white" data-dismiss="modal">确定</button>\n' +
                        '            </div>');
                }
            }
        });

    });

    $('#history_info_repeat').click(function () {
            $('#main_content', parent.document).load('monthlyreport/tomonthshistory');
        }
    );
    $('#history_statistic_check_div').on("click", '#check_result_confirm_btn', function () {
        $('#main_content', parent.document).load('monthlyreport/tomonthshistoryshow');
    })
})