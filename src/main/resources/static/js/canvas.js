//ПОДЛОЖКА
var c = document.getElementById("canvas");
var ctx = c.getContext("2d");
ctx.beginPath();
ctx.arc(150, 150, 100, 1 * Math.PI, 2 * Math.PI);
ctx.strokeStyle = '#FFF5EE';
ctx.fillStyle = "#FFF5EE";
ctx.shadowColor = 'rgba(0,0,0,0.5)';
ctx.shadowBlur = 15;
ctx.stroke();
ctx.fill();

var c = document.getElementById("ball");
var ctx = c.getContext("2d");

var c = document.getElementById("line");
var ctx2 = c.getContext("2d");

var linex;
var liney;

//ШАР
function drawBall() {
    ctx.beginPath();
    ctx.arc(50, 150, 15, 0, Math.PI*2);
    ctx.fillStyle = "#FFF5EE";
    ctx.shadowColor = 'rgba(0,0,0,0.8)';
    ctx.shadowBlur = 15;
    ctx.fill();
    ctx.closePath();
}

//ОТРИСОВЫВАНИЕ РУЧКИ ПО ПОЗИЦИИ ШАРА
function drawLine() {
    ctx2.clearRect(0, 0, canvas.width, canvas.height);
    ctx2.lineWidth = 3;
    ctx2.beginPath();
    ctx2.lineTo(150,150);
    ctx2.lineTo(linex, liney);
    ctx2.closePath();
    ctx2.strokeStyle = '#696969';
    ctx2.stroke();   
}


var angleWS;

function connectAngle() {
	angleWS = new WebSocket('ws://localhost:8080/angle');
	angleWS.onmessage = function(data){
		onReceiveAngle(data.data);
	}
}

function disconnectAngle() {
    if (angleWS != null) {
        angleWS.close();
    }
    console.log("Disconnected");
}

function onReceiveAngle(message) {
    console.log(message);
}


//ВЫСЧИТЫВАЕМ УГОЛ (В ГРАДУСАХ)
var angle=0;
function findAngle() {
    angleRad= Math.acos((150-linex) / Math.sqrt((150-liney)*(150-liney)+(150-linex)*(150-linex)));
    angle = angleRad * 180 / Math.PI;
}

drawBall();
var ball = document.getElementById('ball');

//ОТСЛЕЖИВАНИЕ ПЕРЕМЕЩЕНИЯ ШАРА
ball.onmousedown = function(e) { 

  ball.style.position = 'absolute';
  moveAt(e);
  document.body.appendChild(ball);
  ball.style.zIndex = 1000; 

  function moveAt(e) {
    ball.style.left = e.pageX - ball.offsetWidth / 2 + 'px';
    ball.style.top = e.pageY - ball.offsetHeight / 2 + 'px'; 
  }

  document.onmousemove = function(e) {
    moveAt(e);
    linex=e.pageX-665;
    liney=e.pageY-350;
    drawLine();
    findAngle();
  }

  ball.onmouseup = function() {
    document.onmousemove = null;
    ball.onmouseup = null;

    angleWS.send("angle " + angle)
  }
}