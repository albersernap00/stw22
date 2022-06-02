/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.mqtt;

import demo.ejb.Starter;
import java.util.Date;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author fserna
 */

public class MqttListener implements MqttCallbackExtended{   //MqttCallbackExtended
    private Starter masterBean;
    
    public MqttListener(Starter _master){
        this.masterBean = _master;
    }
    
    
    
    @Override
    public void messageArrived(String _topic, MqttMessage _mm) throws Exception {
        String msg = _mm.toString();
//System.out.println(">>>>>>"+msg);        
        if (_topic.contains("/rpi/meteo")){
            masterBean.onUplink(_topic, msg);
        }else{
            String json = "{\"type\":\"sonoff\", \"msg\":\""+msg+"\"}";
            masterBean.onUplink(_topic, json);
        }

    } //messageArrived

    
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("lorawanPanel     ======> MqttListener   --- CONNECTION-LOST >  --- "+new Date());
        System.out.println(thrwbl.getMessage());
        System.out.println("===========================================================");
    }

    
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
      //  System.out.println("Geigerr 04a ======> Geigerr.MqttListener  --------DELIVERY COMPLETE--------");
    }
    

    @Override
    public void connectComplete(boolean _reconnect, String _serverURI) {
        System.out.println("lorawanPanel --> MQTT Reconnect: "+_reconnect+"\t"+_serverURI);
        masterBean.resubscribe();
    }
}
