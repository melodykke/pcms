$(function () {
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
        $('#animation_box').addClass('fadeInDown');

        setTimeout(function(){
            $('#animation_box').removeAttr('class').attr('class', '');
        }, 500);

        return false;
    });

    $('.dataTables-example').DataTable({
        bFilter: false,    //去掉搜索框
        bInfo:false,       //去掉显示信息

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


})