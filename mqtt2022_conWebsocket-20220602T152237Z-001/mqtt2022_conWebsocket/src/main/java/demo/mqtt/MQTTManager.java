/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.mqtt;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author fserna
 */

public class MQTTManager {

    private MqttClient mqttClient;
    private MemoryPersistence persistence = new MemoryPersistence();

    private String broker;
    private String user;
    private String password;
    
    private List<String> topicsToSubscribe = new ArrayList();  // topics to subscribe
    
    
    
    
    
    public MQTTManager(String _broker, String _user, String _password){
        this.broker     = _broker;
        this.user       = _user;
        this.password   = _password;
        this.connectToBroker(broker, user, password);
    }


    
    public void close(){ 
        try {
            if (mqttClient!=null){
                if (mqttClient.isConnected()){
                    mqttClient.disconnect();
                }
                mqttClient.close();
            }
System.out.println ("lorawanPanel 99  ======> lorawanPanel.MQTTManager feels DESTROYED");
        } catch (Exception ex) {
        }
 }

    
   
//    @PrePassivate
//    public void saveStateBecauseImLeaving(){
//        System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager --------- saveStateBecauseImLeaving...");        
//    }
    

    
//    @PostActivate
//    public void helloImBack(){
//       System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager --------- helloImBack...");   
//    }
        
    
    
    public void addTopicToSubscribe(String _topic){
        this.topicsToSubscribe.add(_topic);
    }
    
    public List<String> getTopicsSubscribed(){
        return this.topicsToSubscribe;
    }
    
    
    
    private void connectToBroker (String _broker, String _user, String _password){
        if (mqttClient == null){
            try {
                String randomId = "fs_"+String.valueOf(Math.random()).substring(2, 12);
                mqttClient = new MqttClient (_broker, randomId, persistence);
System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.connectingToBroker: '"+_broker+"' with ID: '"+randomId+"'");
            } catch (MqttException ex) {
                System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.connectToBroker:   mqttClient NULL no puede crearse.");
            }
        }
        
        if (mqttClient != null){
            try {
                if ( ! mqttClient.isConnected()){
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setUserName(_user);
                    options.setPassword(_password.toCharArray());
                    options.setAutomaticReconnect(true);
                    options.setCleanSession(false);
                    //options.setKeepAliveInterval(???); 
                    mqttClient.connect(options);
                    if (mqttClient.isConnected()){
System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.connectingToBroker: '"+_broker+"\tOK!");
                    }else{
System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.connectToBroker:  *** ERROR: Session NOT established***");                    
                    }
                }
            } catch (MqttException ex) {
System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.connectToBroker:   IMPOSIBLE CONECTAR a broker.");
                ex.printStackTrace();
            }
        }
    }// connectToBroker
    
    
    
    
    
    /**
     * Subscribes to the topics stored in the list "topicsToSubscribe"
     * @param _callback 
     */
    public void subscribe(MqttCallback _callback){
        for (String t: topicsToSubscribe){
            subscribe (t, _callback);
        }
    }
    
    
    
    private void subscribe(String _topic, MqttCallback _callback){
        if ((mqttClient!=null)&&(mqttClient.isConnected())){
            mqttClient.setCallback(_callback);
            try {
                mqttClient.subscribe(_topic);
                System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.SUBSCRIBE to: '"+_topic+"'");
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    

    public void publish(String _topic, String _msg) {
        publish (_topic, _msg, false, 1);
    }
    

    
    public void publish(String _topic, String _msg, boolean _retained, int _qos) {
        try {
            MqttMessage mOut = new MqttMessage(_msg.getBytes());
            mOut.setQos(_qos);
            mOut.setRetained(_retained);
            if ((mqttClient!=null)&&(mqttClient.isConnected())){
                mqttClient.publish(_topic, mOut); 
                System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.Publish ===> "+_topic+" -> "+mOut);
            }else{
                System.out.println("lorawanPanel     ======> lorawanPanel.MQTTManager.Publish =====ERR====> mqttClient es NULL o NOT CONNECTED");
            }
        } catch (MqttException ex) {
          ex.printStackTrace();
        }
    }

    

}
