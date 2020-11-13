var jswidth=0;
var jsheight=0;
var mnwidth=0;
var mnheight=0;
x=0;
y=0;
cx=0;
cy=0;


function inposition()
{
	$("#joistik .joistmamipulator").animate({left: cx+"px", top: cy+"px"});
}
$(document).ready(function(){
    jswidth=$("#joistik").width();
    jsheight=$("#joistik").height();
    mnwidth=$("#joistik .joistmamipulator").width();
    mnheight=$("#joistik .joistmamipulator").height();

    cx=(jswidth/2)-(mnwidth/2);
    cy=(jsheight/2)-(mnheight/2);
    inposition();
    //$("#joistik .joistmamipulator").mousedown(function(){ inposition();});
    

    $("#joistik .joistmamipulator").mouseup(function(){ inposition();});
    $("#joistik .joistmamipulator").draggable({containment:[0-mnwidth/2+300,0-mnheight/2+350,jswidth-mnwidth/2+300,jsheight-mnheight/2+350]});
    $(document).mouseup(function(){inposition();});
        
})

setInterval(
function(){
var position=$("#joistik .joistmamipulator").position();
x=(cx-position.left)*(-1);	
y=cy-position.top;

$("#ox").val(x);
$("#oy").val(y);
	},
1);
