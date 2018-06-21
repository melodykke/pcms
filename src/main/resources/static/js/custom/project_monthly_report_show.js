$(function () {
    // 从URL里获取projectMonthlyReport pId参数的值
    var pId = getQueryString('pId');
    alert(pId)
    $('#last_month').click( function(){
        $.ajax({
            url: 'http://www.baidu.com',
            type: 'POST',
            data: {month:123},
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                if (data.success){
                    // ...
                }
            },
            complete: function () {
                $("loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.responseText);
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
        $.ajax({
            url: 'http://www.baidu.com',
            type: 'POST',
            data: {month:123},
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();
            },
            success: function (data) {
                if (data.success){
                    // ...
                }
            },
            complete: function () {
                $("loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.responseText);
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
            url: 'http://www.baidu.com',
            type: 'POST',
            data: {month:123},
            contentType: 'application/json',
            beforeSend:function () {
                $('#loading').show();


            },
            success: function (data) {
                if (data.success){
                    // ...
                }
            },
            complete: function () {
                $("loading").hide();
            },
            error: function (data) {
                console.info("error: " + data.responseText);
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

})