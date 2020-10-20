var ws;

function connect() {
	ws = new WebSocket('ws://localhost:8080/ws');
	ws.onmessage = function(data){
		onReceive(data.data);
	}
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    console.log("Disconnected");
}

function startReceiving() {
    ws.send("start");
}

function stopReceiving() {
    ws.send("stop");
}

function onReceive(message) {
    console.log(" " + message + "");
}

window.onload = function() {
  connect();
};

var startButton = document.getElementById("startButton");
startButton.onclick = startReceiving;

var stopButton = document.getElementById("stopButton");
stopButton.onclick = stopReceiving;