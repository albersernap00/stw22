/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.mqtt;

import WebSocket.WebSocketManager;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        //Cuando llegue un mensaje quieres comprobar si encender ele nchufe
        // Y ademas mandarlo a la GUI por ws
        // Y si es el sensor de luz/movimiento meterlo en la BD --> JPA de historico?
        System.out.println("[!] En message arrived el String string: " + string + " y el mqtt mesae " + mm.toString());
        ws.broadcastMsg(mm.toString());        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
