$(function () {
    var getProjectMonthsUrl = '/monthlyreport/getmonthlyreports';
    getProjectMonthsReports();
    // 获取特定工程下特定年份
    function getProjectMonthsReports() {
        $.ajax({
            url: getProjectMonthsUrl,
            type: 'POST',
            data: JSON.stringify({"year": null}),
            contentType : "application/json",
            dataType: "json",
            cache : false,
            success: function (data) {
                var projectMonths = data.data;
                $('#projectMonthsDiv').html('');
                var tempHtml = '';
                if (projectMonths == null) {projectMonths = []}
                projectMonths.map(function (item, index) {
                    var state = '';
                    if (item.state == 0) {
                        state = '未审核';
                    } else if (item.state == 1) {
                        state = '已审核';
                    } else {
                        state = '未知状态';
                    }
                    tempHtml += '<div class="col-md-3">\n' +
                    '                <div class="ibox">\n' +
                    '                    <div class="ibox-content product-box">\n' +
                    '\n' +
                    '                         <div class="product-imitation" style="background:url(' + ((item.thumbnailUrl==null)? "#":item.thumbnailUrl) + ');background-size:cover;">\n' +
                    '                        </div>\n' +
                    '                        <div class="product-desc">\n' +
                    '                                <span class="product-price">\n' +
                    '                                    '+ item.month +'月\n' +
                    '                                </span>\n' +
                    '                            <p class="text-muted">填报人：<strong>'+ item.submitter +'</strong></p>\n' +
                    '                            <p class="text-muted">月报审核状态：<span class="label label-primary">'+ state +'</span></p>\n' +
                    '                            <div class="col-sm-offset-9 m-t text-righ">\n' +
                    '\n' +
                    '                                <a href="#" class="btn btn-xs btn-outline btn-primary" data-id="'+ item.projectMonthlyReportId +'">详情 <i class="fa fa-long-arrow-right"></i> </a>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '            </div>';
                })
                $('#projectMonthsDiv').append(tempHtml);
            }
        });
    }

    $('#projectMonthsDiv').on('click', 'a', function (e) {
        completeLoading();
        var target = $(e.currentTarget);
        var projectMonthlyReportId = e.currentTarget.dataset.id;
        $('#main_content', parent.document).load('monthlyreport/projectmonthlyreportshow?projectMonthlyReportId='+projectMonthlyReportId);

    });
    // 加载状态为complete时移除loading效果
    if (document.readyState == "complete") {
        document.getElementById('main_loading').style.display = "none";
    }
    else {
        document.getElementById('main_loading').style.display = "block";
    }

})