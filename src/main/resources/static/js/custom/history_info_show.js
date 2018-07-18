$(function () {
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
                $('#approve_area').text('')
            }
        }
    });
    $('#history_info_approve_submit').click(function () {
        var checkedOfAll=$("#my-checkbox").prop("checked");
        var checkinfo=$('#approve_area').val();
        alert(checkedOfAll+checkinfo)
    })

    $('#history_info_repeat').click(function () {
            $('#main-page').load('history_info_new.html');
        }
    );
})