var wsUri = "ws://" + document.location.host + "/stw22/stw";
var webSocket;

var titulo = document.getElementById("prueba");
var provinciaSelect = document.getElementById("provinciaSelect");
var municipioSelect = document.getElementById("municipioSelect");
var eessSelect = document.getElementById("eessSelect");
var pruebaPrecios = document.getElementById("pruebaPrecios");
openSocket();

/**
 * ==================== openSocket =========================================
 * @returns {undefined}
 */
 function openSocket(){
     console.log("1OPENING: "+wsUri);
    // Ensures only one connection is open at a time
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
       closeSocket();
    }
    
    webSocket = new WebSocket(wsUri);

    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function(event){
        if(event.data === undefined){
            return;
        }
        console.log(event.data);
    };

    webSocket.onmessage = function(event){
        var msg = event.data;
        
        console.log("==== "+msg);
        /*var json =  JSON.parse(event.data);
        
        switch (json.cmnd){
            case "provinciasId":                          
                construirProvinciasSelect(json.values);
                //json.provincias.foreach(construirProvinciasSelect);                                
                //console.log(json.provincias)
            
                
        }*/
        if (msg == "hola"){
            titulo.innerHTML = "FELIZ NAVIDAD";
            webSocket.send("adios");
                     
        }       
        
    };

    webSocket.onclose = function(event){
        console.log("Connection Closed");
    };

    webSocket.onerror = function (event){
        console.log("ERROR: "+event.toString());
    };
} //openSocket

     


function construirMunicipiosSelect (value){
    console.log("[!]");
    while (municipioSelect.firstChild){
        municipioSelect.removeChild(municipioSelect.lastChild);
    }
    var emptyField = document.createElement('option');
    emptyField.appendChild(document.createTextNode(''));
    emptyField.value='default';
    municipioSelect.append(emptyField);
    for (var i = 0; i < value.length; i++){
        var opt = document.createElement('option');
        opt.appendChild((document.createTextNode(value[i].b)));
        opt.value=value[i].a;
        municipioSelect.appendChild(opt); 
    }
    console.log("[!]");
    
        
}





function initGrafica(){
    console.log("HEEE");
    graficaPrecios = new google.visualization.LineChart(document.getElementById('graficaPrecios'));
    datosGraficaPrecios = new google.visualization.DataTable();
    datosGraficaPrecios.addColumn('string', 'Time');
    datosGraficaPrecios.addColumn('number', 'Precio Gasoleo A (€)');
    datosGraficaPrecios.addColumn('number', 'Precio Gasolina E95 (€)');
    optionsGraficoGasolina = {
        chart:{title: 'CO2 Live data:'},
        vAxis: {format:'decimal'},        
        curveType: 'function',
        legend: {position:'bottom'}
    };
    graficaPrecios.draw(datosGraficaPrecios, optionsGraficoGasolina);
}

function drawPrices(value){
    
    if (datosGraficaPrecios!==undefined){
        initGrafica();
        console.log("[!!!!] " + value[0].length);
        for (var i = 0; i < value[0].length; i++){
            
         datosGraficaPrecios.addRow([value[0][i].a ,parseFloat(value[0][i].b), parseFloat(value[1][i].b)]);
        
        }
        graficaPrecios.draw(datosGraficaPrecios, optionsGraficoGasolina);
    }
    
    
}


function sendCCAA(value){
    console.log("HAHA " + value);   
    if (value != "default"){
        webSocket.send("{\"cmnd\":\"getProvincias\",\"CCAA\": \"" + value + "\"}");
    }
    
}




/**
 * ==================== closeSocket =========================================
 * @returns {undefined}
 */
function closeSocket(){
    webSocket.close();
}
