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
        var json =  JSON.parse(event.data);
        
        switch (json.cmnd){
            case "datePrecioLuzResult":                          
                console.log(json.values);
                //parsearValoresPrecioLuz(json.values);
                console.log("ENTRO en el case");
                drawPrices(json.values);
            break;
            case "datosSensoresResult": //este viene del MQTT
                checkDrawPricesBarras(json.values);
            break;
            case "datosSensoresResultFecha":
                drawPricesBarras(json.values);
            break;
            case "enchufe":
                var value = json.values;
                console.log("AAA q pasa el value es " + value);
                var estado = false;
                var colorStatus = "red";

                if (value === 1){

                    document.getElementById("botonON").disabled = true;
                    document.getElementById("botonOFF").disabled = false;
                    estado = true;
                    document.getElementById("estado").innerHTML = "ENCENCDIDO";
                    colorStatus = "yellowgreen";
                }else{        
                    document.getElementById("botonON").disabled = false;
                    document.getElementById("botonOFF").disabled = true;
                    estado = false;
                    document.getElementById("estado").innerHTML = "APAGADO";        
                }
                document.getElementById("canvas").style.backgroundColor = colorStatus;
                /*console.log("me ha llegao : " + json.values);
                var status = json.values;
                var colorStatus = "red";
                var txtStatus = "APAGADO";
                document.getElementById("botonON").disabled = false;
                document.getElementById("botonOFF").disabled = true;
                console.log("EL JSON VALUES ES " + json.values);
                if ((status==="ON")||(status==="1") ||(status === true)){
                    console.log("ESTOY AQUI DENTRO");
                    colorStatus = "yellowgreen";
                    txtStatus = "ENCENDIDO";
                    document.getElementById("botonON").disabled = true;
                    document.getElementById("botonOFF").disabled = false;
                }
                document.getElementById("canvas").style.backgroundColor = colorStatus;
                document.getElementById("estado").innerHTML = txtStatus;*/
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
function enviarFechaHistorico(){
    var fechaSelected = document.getElementById("dateGraficaBarras").value;
    console.log("[!!] estoy en enviar fecha : " + fechaSelected);
    webSocket.send("{ \"cmnd\": " + "\"dateGraficaBarras\", \"Fecha\": \"" + fechaSelected  + "\" }");
    
}

function pruebaSwitch(value){
    console.log("AAA q pasa el value es " + value);
    var estado = false;
    var colorStatus = "red";
    
    if (value == 1){
        
        document.getElementById("botonON").disabled = true;
        document.getElementById("botonOFF").disabled = false;
        estado = true;
        document.getElementById("estado").innerHTML = "ENCENCDIDO";
        colorStatus = "yellowgreen";
    }else{        
        document.getElementById("botonON").disabled = false;
        document.getElementById("botonOFF").disabled = true;
        estado = false;
        document.getElementById("estado").innerHTML = "APAGADO";        
    }
    document.getElementById("canvas").style.backgroundColor = colorStatus;
    webSocket.send("{ \"cmnd\": " + "\"updateSonoff\", \"Value\": " + estado  + " }");
    
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

function initGraficaBarras() {
      var data = google.visualization.arrayToDataTable([
        ['Hora', 'Numero veces']
      ]);
      var materialChart = new google.charts.Bar(document.getElementById('graficaBarras'));
      var materialOptions = {
        chart: {
          title: 'Número de veces enchufe enciende'
        },
        hAxis: {
          title: 'Horas',
          minValue: 0,
        },
        vAxis: {
          title: 'Veces'
        },
        bars: 'horizontal'
      };
      
      materialChart.draw(data, materialOptions);
}

//Se le llama cuando venga del websocket
function checkDrawPricesBarras(value){
    var fechaSelected = document.getElementById("dateGraficaBarras").value;
    today = new Date();    
    fechaSel = new Date(fechaSelected);    
    
    if (fechaSel.getDate() == today.getDate()){
        drawPricesBarras(value);
    }
}

function drawPricesBarras(value) {
    var materialChart = new google.charts.Bar(document.getElementById('graficaBarras'));
    
    if (materialChart!==undefined){
        initGraficaBarras();  
        if (value){
            var data = google.visualization.arrayToDataTable([
                ['Hora', 'Numero veces']
            ]);
            var dataArray = [
                ['Hora', 'Numero veces'],
                [' ',0],                
            ];
            value.forEach(element =>{
                console.log("Concateno " +element.hora + " y " + element.value);
                valor = parseInt(element.hora) + 1;
                if (element.hora < 10){
                    
                    dataArray.push(['0' + element.hora + '- 0' + valor , element.value]);
                }else {
                    dataArray.push([element.hora + "-" + valor, element.value]);
                }
                
                
            });
            data = google.visualization.arrayToDataTable(dataArray);
            //var dataGoogle = google.visualization.arrayToDataTable(data);
            /*dataGoogle = google.visualization.arrayToDataTable([
                ['Hora', 'Numero veces'],
                ['00-01', 2],
                ['00-02', 0],
                ['00-03', 2],
            ]);*/
            var materialOptions = {
                chart: {
                  title: 'Número de veces enchufe enciende',
                  
                },
                hAxis: {
                  title: 'Horas',
                  minValue: 0,
                },
                vAxis: {
                  title: 'Veces',                 
                },
                bars: 'horizontal',
                width:800,
                height:800
            };           
            materialChart.draw(data, materialOptions);
            console.log("TERMINO DE PINTAR");
        }
    }
        
      

     
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
        
    }
    
    
}




/**
 * ==================== closeSocket =========================================
 * @returns {undefined}
 */
function closeSocket(){
    webSocket.close();
}
