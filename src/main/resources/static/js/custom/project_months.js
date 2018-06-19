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
                console.log(data)
            }
        });
    }
})