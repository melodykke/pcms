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
    }
    websocket.onerror = function () {
        alert('websocket通信错误！');
    }
    window.onbeforeunload = function () {
        websocket.close();
    }
});

