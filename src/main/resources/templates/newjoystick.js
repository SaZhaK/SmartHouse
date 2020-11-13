var jswidth=0;
var jsheight=0;
var mnwidth=0;
var mnheight=0;
cx=0;
cy=0;


function inposition()
{
    $("#new-joistik .new-joistmamipulator").animate({left: cx+"px", top: cy+"px"});
}

$(document).ready(function(){
    jswidth=$("#new-joistik").width();
    jsheight=$("#new-joistik").height();
    mnwidth=$("#new-joistik .new-joistmamipulator").width();
    mnheight=$("#new-joistik .new-joistmamipulator").height();

    cx=(jswidth/2)-(mnwidth/2)+joystickValueX;
    cy=(jsheight/2)-(mnheight/2)-joystickValueY;
    inposition();
        
    //$("#joistik .joistmamipulator").draggable({containment:[0-mnwidth/2+300,0-mnheight/2+350,jswidth-mnwidth/2+300,jsheight-mnheight/2+350]});   
})

setInterval(
    function(){    
    $("#ox").val(joystickValueX);
    $("#oy").val(joystickValueY);
    },
1);
    
    