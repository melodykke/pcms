$(function () {
/*    var interval = setInterval(function () {
        $.ajax({
            url: "wechatlogin/wechatloginstate",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code == 1002) {
                    if (data.data == true) {
                        window.location.href = '/index';
                        clearInterval(interval);
                    }
                }
            }
        })
    }, 3000);*/
    var websocket = null;
    console.log(111111)
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://sell01.natapp1.cc/wechatloginwebsocket')
    } else {
        alert('该浏览器不支持ws！');
    }
    websocket.onopen = function (event) {
        console.log('wechatlogin建立连接');
    };
    websocket.onclose = function (event) {
        console.log('wechatlogin连接关闭')
    };
    websocket.onmessage = function (event) {
        console.log('收到消息：' + event.data);
        var jsonData = eval("(" + event.data + ")");
        window.location.href = '/wechatlogin/wechatauthlogin?openId='+jsonData.data;

    };
    websocket.onerror = function (event) {
    };
    websocket.onbeforeunload = function () {
        websocket.close();
    }

});