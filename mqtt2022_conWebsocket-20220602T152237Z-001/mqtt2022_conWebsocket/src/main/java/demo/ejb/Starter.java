/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.ejb;

import demo.mqtt.MQTTManager;
import demo.mqtt.MqttListener;
import demo.mqtt.Topic;
import demo.websocket.WebSocketManager;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author uzffs
 */
@Singleton
@Startup
public class Starter {

    private MQTTManager mqtt = null;
    private MqttListener mqttListener;

    private WebSocketManager websocket = null;

    @PostConstruct
    public void init() {
        System.out.println("=================== Booting");

        websocket = new WebSocketManager();

        mqttListener = new MqttListener(this);
        mqtt = new MQTTManager("tcp://155.210.71.106", "stw", "stweb22");
        mqtt.addTopicToSubscribe(Topic.TOPIC_RPI_SENSORS);
        mqtt.addTopicToSubscribe(Topic.TOPIC_SONOFF_STAT_POWER);
        mqtt.subscribe(mqttListener);
    }

    @PreDestroy
    public void close() {
        websocket = null;
        if (mqtt != null) {
            mqtt.close();
        }

        System.out.println("demo says bye.");
    }

    /* 
        -   _rx es el json que recibimos de MQTT
     */
    public void onUplink(String _topic, String _rxJson) {
        System.out.println("TOPIC: " + _topic + "\t" + _rxJson);

        this.notifyUIWeb(_rxJson);

    } // onUplink

    public void notifyUIWeb(String _json) {
        if (this.websocket != null) {
            this.websocket.refreshUI(_json);
        } else {
            websocket = new WebSocketManager();
            this.websocket.refreshUI(_json);
        }
    }

    public MQTTManager getMqtt() {
        return mqtt;
    }

    public void resubscribe() {
        mqtt.subscribe(mqttListener);
    }

}
