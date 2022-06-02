/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var wsUri = "ws://" + document.location.host + "/mqtt2022_conWebsocket/ui";
var webSocket;

openSocket();
setInterval(showQueHoraEs,1000);




 function openSocket(){
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
       closeSocket();
    }

    webSocket = new WebSocket(wsUri);
    
    webSocket.onopen = function(event){
        if(event.data === undefined)
            return;
    };

    webSocket.onmessage = function(event){
//console.log("MSG>>>>>>>>>>>>>>> "+event.data);
        var str = event.data;
        str = str.replace(/'/g, '"');
//console.log("STR>>>>>>>>>>>>>>> "+str);
        var json = JSON.parse(str);
        
        var status;
        if (json.type==="sonoff"){
            status = json.msg;
//console.log("SONOFF STATUS: "+status);
            var colorStatus = "red";
            var txtStatus = "APAGADO";
            document.getElementById("botonON").disabled = false;
            document.getElementById("botonOFF").disabled = true;
            if (status==="ON"){
                colorStatus = "yellowgreen";
                txtStatus = "ENCENDIDO";
                document.getElementById("botonON").disabled = true;
                document.getElementById("botonOFF").disabled = false;

            }
            document.getElementById("canvas").style.backgroundColor = colorStatus;
            document.getElementById("estado").innerHTML = txtStatus;
        }else{
            
//console.log("RPi===> "+str);
            json = JSON.parse(str);

            document.getElementById("rpiTime").innerHTML = getDDMMYYYY(json.ms)+" @ "+getHHMMSS(json.ms);
            document.getElementById("rpiTemp").innerHTML = json.temp+" ºC";
            document.getElementById("rpiPress").innerHTML = json.press+" ºC";
            document.getElementById("rpiTempCpu").innerHTML = json.tempCpu+" ºC";
        }

    };

    webSocket.onclose = function(event){
    };

    webSocket.onerror = function (event){
    };
} //openSocket
           
                

//function sendText(){
//    //var text = messageInput.value;
//    webSocket.send(messageInput.value);
//}

function closeSocket(){
    webSocket.close();
}








function showQueHoraEs(){
    var ahora = new Date().getTime();
    document.getElementById("queHoraEs").innerHTML = getDDMMYYYY(ahora)+" @ "+getHHMMSS(ahora);
    
}




function setMs(_ms){
    msLastRX = _ms;
}

     
function getHHMMSS(_ms){
    var date = new Date(_ms);
    var txt = "";
    
    if (date.getHours()<10){
        txt= "0";
    }
    txt = txt + date.getHours()+":";
    if (date.getMinutes()<10){
        txt = txt + "0";
    }
    txt = txt + date.getMinutes()+":";
    if (date.getSeconds()<10){
        txt = txt + "0";
    }
    txt = txt + date.getSeconds();
  
    return txt;
}

    
function getDDMMYYYY(_ms){
    var date = new Date(_ms);
    var txt = "";
    
    if (date.getDate()<10){
        txt = "0";
    }
    txt = txt + date.getDate()+"/";
    if ((date.getMonth()+1)<10){
        txt = txt +"0";
    }
    txt = txt + (date.getMonth()+1)+"/"+date.getFullYear();
    return txt;
}


