$(function () {
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://localhost:8080/webSocket');
    } else {
        alert('该浏览器不支持WS！');
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    }
    websocket.onclose = function (event) {
        console.log('关闭连接');
    }
    websocket.onmessage = function (event) {
        console.log('收到消息：' + event.data);
        // 弹窗
        showNotification(event.data,'','')
    }
    websocket.onerror = function () {
        alert('websocket通信错误！');
    }
    window.onbeforeunload = function () {
        websocket.close();
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
            alert('You can perform some custom action after a toast goes away');
        };
        var $toast = toastr['success'](msg,title); // Wire up an event handler to a button in the toast, if it exists
    }



});

