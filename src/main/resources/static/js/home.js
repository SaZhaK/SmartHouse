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
    console.log(" " + message + " ");
    var valueButton = message;

    if (valueButton == 0) {
        let elem = document.getElementById('button-light');
        elem.className = "button-light-active";
    }
    if (valueButton == 1) {
        let elem = document.getElementById('button-light');
        elem.className = "button-light";
    }
}

var buttonStart = document.getElementById("buttonStart");
buttonStart.onclick = startReceivingButton;

var buttonStop = document.getElementById("buttonStop");
buttonStop.onclick = stopReceivingButton;



var jswidth=0;
var jsheight=0;
var mnwidth=0;
var mnheight=0;
var cx=0;
var cy=0;


function inposition() {
    $("#new-joistik .new-joistmamipulator").animate({left: cx+"px", top: cy+"px"});
}

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
    jswidth=$("#new-joistik").width();
    jsheight=$("#new-joistik").height();
    mnwidth=$("#new-joistik .new-joistmamipulator").width();
    mnheight=$("#new-joistik .new-joistmamipulator").height();

    if (message == 11) {
        cx = 0;
        cy = 0;
    } else if (message == 12) {
        cx = 60;
        cy = 0;
    }  else if (message == 13) {
        cx = 140;
        cy = 0;
    } else if (message == 21) {
        cx = 0;
        cy = 60;
    } else if (message == 22) {
        cx = 60;
        cy = 60;
    } else if (message == 23) {
        cx = 140;
        cy = 60;
    }  else if (message == 31) {
        cx = 0;
        cy = 140;
    } else if (message == 32) {
        cx = 60;
        cy = 140;
    } else if (message == 33) {
        cx = 140;
        cy = 140;
    }

    inposition();
}

var joystickStart = document.getElementById("joystickStart");
joystickStart.onclick = startReceivingJoyStick;

var joystickStop = document.getElementById("joystickStop");
joystickStop.onclick = stopReceivingJoyStick;



var soundWS;

function connectSound() {
	soundWS = new WebSocket('ws://localhost:8080/sound');
	soundWS.onmessage = function(data){
		onReceiveSound(data.data);
	}
}

function disconnectSound() {
    if (soundWS != null) {
        soundWS.close();
    }
    console.log("Disconnected");
}

function sound() {
    soundWS.send("sound");
}

function onReceiveSound(message) {
    console.log(message);
}

function buttonSoundOnclick() {
    sound();
    buttonSound.className = (buttonSound.className == 'voice-button' ? 'voice-button-active' : 'voice-button');
}

var buttonSound = document.getElementById("buttonSound");
buttonSound.onclick = buttonSoundOnclick; 

window.onload = function() {
    connectButton();
    connectJoyStick();
    connectSound();
};



var redWS;

function connectRed() {
	redWS = new WebSocket('ws://localhost:8080/red');
	redWS.onmessage = function(data){
		onReceiveRed(data.data);
	}
}

function disconnectRed() {
    if (redWS != null) {
        redWS.close();
    }
    console.log("Disconnected");
}

function red() {
    redWS.send("red");
}

function onReceiveRed(message) {
    console.log(message);
}

function sleep(ms) {
    ms += new Date().getTime();
    while (new Date() < ms){}
}

function buttonRedOnclick() {
    red();
    buttonRed.className = ( buttonRed.className == 'red-button-active' ? 'red-button' : 'red-button-active');
}

var buttonRed = document.getElementById("buttonRed");
buttonRed.onclick = buttonRedOnclick;



var greenWS;

function connectGreen() {
	greenWS = new WebSocket('ws://localhost:8080/green');
	greenWS.onmessage = function(data){
		onReceiveGreen(data.data);
	}
}

function disconnectSound() {
    if (greenWS != null) {
        greenWS.close();
    }
    console.log("Disconnected");
}

function green() {
    greenWS.send("green");
}

function onReceiveGreen(message) {
    console.log(message);
}

function buttonGreenOnclick() {
    green();
    buttonGreen.className = ( buttonGreen.className == 'green-button-active' ? 'green-button' : 'green-button-active');
}

var buttonGreen = document.getElementById("buttonGreen");
buttonGreen.onclick = buttonGreenOnclick;



var blueWS;

function connectBlue() {
	blueWS = new WebSocket('ws://localhost:8080/blue');
	blueWS.onmessage = function(data){
		onReceiveBlue(data.data);
	}
}

function disconnectBlue() {
    if (blueWS != null) {
        blueWS.close();
    }
    console.log("Disconnected");
}

function blue() {
    blueWS.send("blue");
}

function onReceiveBlue(message) {
    console.log(message);
}

function buttonBlueOnclick() {
    blue();
    buttonBlue.className = ( buttonBlue.className == 'blue-button-active' ? 'blue-button' : 'blue-button-active');
}

var buttonBlue = document.getElementById("buttonBlue");
buttonBlue.onclick = buttonBlueOnclick;



var fanWS;

function connectFan() {
	fanWS = new WebSocket('ws://localhost:8080/fan');
	fanWS.onmessage = function(data){
		onReceiveFan(data.data);
	}
}

function disconnectFan() {
    if (fanWS != null) {
        fanWS.close();
    }
    console.log("Disconnected");
}

function fan() {
    fanWS.send("fan");
}

function onReceiveFan(message) {
    console.log(message);
}

function buttonFanOnclick() {
    fan();
}

var buttonFan = document.getElementById("buttonFan");
buttonFan.onclick = buttonFanOnclick;



window.onload = function() {
    connectButton();
    connectJoyStick();

    connectSound();
    connectRed();
    connectGreen();
    connectBlue();

    connectAngle();

    connectFan();
};