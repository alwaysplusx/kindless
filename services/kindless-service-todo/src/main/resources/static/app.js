var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#todo").html("");
}

function connect() {
    var socket = new SockJS('/todo-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        token: 'this is connect token'
    }, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/connected', function (resp) {
            console.log('todo ping was connected', resp)
        });
        stompClient.subscribe('/topic/disconnected', function (resp) {
            console.log('todo ping was disconnected', resp)
            disconnect()
        })
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function ping() {
    var name = $("#name").val()
    stompClient.send("/app/ping", {}, name)
    stompClient.subscribe("/user/" + name + "/message", function (resp) {
        console.log("handle user message", resp)
        showTodo(resp.body)
    })
}

function unping() {
    stompClient.send("/app/unping", {}, $("#name").val())
}

function sendMessage() {
    stompClient.send("/app/message", {}, $("#name").val())
}

function showTodo(message) {
    $("#todo").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        ping();
    });
    $("#sendMessage").click(function () {
        sendMessage();
    })
});

