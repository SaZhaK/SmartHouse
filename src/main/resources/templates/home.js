var buttonWS;

function connectButton() {
	buttonWS = new WebSocket('ws://localhost:8080/button');
	buttonWS.onmessage = function(data){
		onReceiveButton(data.data);
	}
}

function disconnectButton() {
    if (buttonWS != null) {
        buttonWS.close();
    }
    console.log("Disconnected");
}

function startReceivingButton() {
    buttonWS.send("start");
}

function stopReceivingButton() {
    buttonWS.send("stop");
}

function onReceiveButton(message) {
    console.log(" " + message + "");
}

var buttonStart = document.getElementById("buttonStart");
buttonStart.onclick = startReceivingButton;

var buttonStop = document.getElementById("buttonStop");
buttonStop.onclick = stopReceivingButton;

var valueButton = buttonWS.onmessage();

var joyStickWS;

function connectJoyStick() {
	joyStickWS = new WebSocket('ws://localhost:8080/joystick');
	joyStickWS.onmessage = function(data){
		onReceiveJoyStick(data.data);
	}
}

function disconnectJoyStick() {
    if (joyStickWS != null) {
        joyStickWS.close();
    }
    console.log("Disconnected");
}

function startReceivingJoyStick() {
    joyStickWS.send("start");
}

function stopReceivingJoyStick() {
    joyStickWS.send("stop");
}

function onReceiveJoyStick(message) {
    console.log(" " + message + "");
}

window.onload = function() {
    connectButton();
    connectJoyStick();
};

var joystickStart = document.getElementById("joystickStart");
joystickStart.onclick = startReceivingJoyStick;

var joystickStop = document.getElementById("joystickStop");
joystickStop.onclick = stopReceivingJoyStick;

var valueJoyStick = JoyStickWS.onmessage();
