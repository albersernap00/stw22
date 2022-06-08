/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.mqtt;

import WebSocket.WebSocketManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import stw22.db.HistoricoSensores;
import stw22.timer.Arrancador;

/**
 *
 * @author rober
 */
public class MQTTListener implements MqttCallbackExtended {
    
    
    private WebSocketManager ws;
    
    public MQTTListener(WebSocketManager ws) {
        this.ws = ws;
    }

    @Override
    public void connectComplete(boolean bln, String string) {
        System.out.println("[+] Connection completed");
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("[!] Connection lost " + thrwbl.getMessage());
    }

    @Override
    public void messageArrived(String string, MqttMessage mm)  {
        //Cuando llegue un mensaje quieres comprobar si encender ele nchufe
        // Y ademas mandarlo a la GUI por ws
        // Y si es el sensor de luz/movimiento meterlo en la BD --> JPA de historico?
        if(string.equals(Arrancador.TOPIC_ENCHUFE)){
            ws.sendStatusEnchufe(mm.toString());
            System.out.println("En el mqtt arrived tengo " + mm.toString());
        }else{
            Date fecha;
            String hora;
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

            System.out.println("[!] En message arrived el String string: " + string + " y el mqtt mesae " + mm.toString());
            try{
                JSONObject obj = new JSONObject(mm.toString());
                String accion = "";
                double valorLuz = 0.0;

                int movimiento = 0;
                System.out.println("El valor de obj es " + obj);

                valorLuz = obj.getDouble("luz");
                movimiento = obj.getInt("movimiento");        
                LocalDateTime ahora= LocalDateTime.now();
                fecha = Calendar.getInstance().getTime();            

                HistoricoSensores historico = new HistoricoSensores();
                historico.setFecha(fecha);
                historico.setHora(ahora.getHour() + 2 + ":" + ahora.getMinute() + ":" + ahora.getSecond()); // Zona horaria GMT, espanya es GMT + 2
                historico.setValorSensorLuz(valorLuz);            
                historico.setValorSensorMovimiento(movimiento);            

                ws.addHistoricoLuz(historico);

            }catch(Exception e){
                e.printStackTrace();
                System.out.println("la exce " );
            }
        }
        
               
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
