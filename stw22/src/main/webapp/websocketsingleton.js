var wsUri = "ws://" + document.location.host + "/stw22/stw/singleton";
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
        var json =  JSON.parse(event.data);
        
        switch (json.cmnd){
            case "datePrecioLuzResult":                          
                console.log(json.values);
                //parsearValoresPrecioLuz(json.values);
                console.log("ENTRO en el case");
                drawPrices(json.values);
            break;
                
        }
            
        
    };

    webSocket.onclose = function(event){
        console.log("Connection Closed");
    };

    webSocket.onerror = function (event){
        console.log("ERROR: "+event.toString());
    };
} //openSocket


function parsearValoresPrecioLuz(value){
    if (value){
        value.forEach(element =>{
            console.log("heee " + element.id + " y la hora es " + element.hora);
            console.log("haa");
        });
    }else{
        window.alert("No hay información disponibles para la fecha seleccionada");
    }        
    
}

function enviarFecha(){
    var fechaSelected = document.getElementById("datePrecioLuz").value;
    console.log("[!!] estoy en enviar fecha : " + fechaSelected);
    webSocket.send("{ \"cmnd\": " + "\"datePrecioLuz\", \"Fecha\": \"" + fechaSelected  + "\" }");
    
}


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
    datosGraficaPrecios.addColumn('string', 'Hora');
    datosGraficaPrecios.addColumn('number', 'Precio Luz (€/MWh)');    
    optionsGraficoLuz = {
        chart:{title: 'Precio Luz'},
        vAxis: {format:'decimal'},        
        curveType: 'function',
        legend: {position:'bottom'}
    };
    graficaPrecios.draw(datosGraficaPrecios, optionsGraficoLuz);
}

function drawPrices(value){
    
    if (datosGraficaPrecios!==undefined){
        initGrafica();        
        if (value){
            value.forEach(element =>{
                console.log("heee " + element.id + " y la hora es " + element.hora);            
                datosGraficaPrecios.addRow([element.hora , element.precio]);
            });   
            if (value.length == 0){
                window.alert("No hay información disponible para la fecha seleccionada");
                console.log("AAA");
            }
            graficaPrecios.draw(datosGraficaPrecios, optionsGraficoLuz);
        }
        /*for (var i = 0; i < value[0].length; i++){
            
         datosGraficaPrecios.addRow([value[0][i].a ,parseFloat(value[0][i].b), parseFloat(value[1][i].b)]);
        
        }*/
        
    }
    
    
}




/**
 * ==================== closeSocket =========================================
 * @returns {undefined}
 */
function closeSocket(){
    webSocket.close();
}
