/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.timer;

import WebSocket.WebSocketManager;
import com.google.gson.Gson;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import stw22.db.HistoricoSensores;
import stw22.db.HistoricoSensoresDAO;
import stw22.mqtt.MQTTListener;
import stw22.mqtt.MQTTManager;

/**
 * java beans => sessions bean 
 * @author rober
 */
@Singleton
@Startup
public class Arrancador {
    
    @EJB TimerPrecioLuz timer;
    @EJB WebSocketManager ws;
    @EJB HistoricoSensoresDAO hs;
    public static final String TOPIC_SENSOR_LUZ = "/stw/stwAR/sensores/luz";   
    public static final String TOPIC_SENSOR_MOVIMIENTO = "/stw/stwAR/sensores/movimiento";
    
    public static final String HOST = "tcp://192.168.1.68";
    public static final String USER = "";
    public static final String PASSWORD = "";
    
    
    
    private MQTTListener mqttListener;
    private MQTTManager mqttManager;
    //private WebSocketManager ws; // TODO cambiarlo por el que sea singleton
    public static Gson gson;
    
    
    @PostConstruct
    public void init(){
        gson = new Gson();             
        mqttListener = new MQTTListener(ws);
        mqttManager = new MQTTManager(HOST, USER, PASSWORD);       
        
        mqttManager.anyadirTopicSuscribe(TOPIC_SENSOR_LUZ);
        mqttManager.anyadirTopicSuscribe(TOPIC_SENSOR_MOVIMIENTO);
        
        mqttManager.subscribe(mqttListener);
        System.out.println("[!] EMPIEZA LO BUENOI");
        
        System.out.println("sssssss"  + hs.findAll());
        System.out.println("EL TAMANYU es " + hs.getHistoricoSensoresHora("22"));
        
        
        System.out.println("[!] TERMINA LO BUENO");
        
    }
    
    @PreDestroy
    public void bye(){
        ws = null;
        if (mqttManager != null){
            mqttManager.close();
        }
                
    }
    
    

    
    
}

