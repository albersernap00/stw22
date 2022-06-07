/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author rober
 */
public class MQTTManager {
    
    public static final int QOS = 0;
    
    private MqttClient mqttClient;
    private MemoryPersistence persistence = new MemoryPersistence();
    
    
    private String broker;
    
    /*Para el caso del broker local en casa no hace falta contrasenya*/
    private String user;
    private String password;
    private List<String> topicsToSubscribe = new ArrayList(); // Topics a los que te quieres suscribir

    
    public MQTTManager(String broker, String user, String password) {
        this.broker = broker;
        this.user = user;
        this.password = password;
        this.connectBroker(user, password, broker);
    }
    
    
    public void anyadirTopicSuscribe(String _topic){
        topicsToSubscribe.add(_topic);
    }
    
    public void subscribe(MqttCallback _callback){
        for (String _topic: topicsToSubscribe){
            
                if ((mqttClient!=null)&&(mqttClient.isConnected())){
                mqttClient.setCallback(_callback);
                
                try {
                    mqttClient.subscribe(_topic);
                    System.out.println("[!!] Suscrito a " + _topic);
                } catch (MqttException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

/*
    El valor de QoS se establece por defecto a 1
*/    
public void publish(String _topic, String _msg, boolean retain){
    if (mqttClient != null && mqttClient.isConnected()){
        try {
            MqttMessage mensajeSalida = new MqttMessage(_msg.getBytes());
            mensajeSalida.setQos(QOS);
            mensajeSalida.setRetained(retain);
            mqttClient.publish(_topic, mensajeSalida);
            System.out.println("[+] Publicado mensaje " + _msg + " sobre el topic " + _topic);
        } catch (Exception ex) {
            System.out.println("[!] Excepcion publicando mensaje " + _msg + " sobre el topic " + _topic);
        }
    }
    
}    
   
    public boolean connectBroker(String _user, String _password, String _broker){
        if(mqttClient == null){
            try {
                 mqttClient = new MqttClient(_broker, "ra", persistence);
            
            } catch (MqttException ex) {
                Logger.getLogger(MQTTManager.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        if (mqttClient != null){
            
            if (!mqttClient.isConnected()){
                try {
                    MqttConnectOptions mqttOptions = new MqttConnectOptions();
                    mqttOptions.setUserName(_user);
                    mqttOptions.setPassword(_password.toCharArray());
                    mqttOptions.setAutomaticReconnect(true);
                    mqttOptions.setCleanSession(false);
                    
                    mqttClient.connect(mqttOptions);
                } catch (MqttException ex) {
                    Logger.getLogger(MQTTManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return mqttClient.isConnected();
        }
        
        return false;
    }

    public void close() {
        if (mqttClient != null && mqttClient.isConnected()){
            try {
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException ex) {
                Logger.getLogger(MQTTManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
