/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.mqtt;

/**
 *
 * @author fserna
 */
public class Topic {

    // to subscribe
    public static final String TOPIC_RPI_SENSORS       = "/stw/fs/rpi/meteo";
    public static final String TOPIC_SONOFF_STAT_POWER = "/stw/fs/sonoffRF/stat/POWER";

    // to publish
    public static final String TOPIC_SONOFF_CMND_POWER  = "/stw/fs/sonoffRF/cmnd/POWER";
    
    // topic de testamento
    public static final String TOPIC_LASTWILL  = "/stw/thatsAllFolks";
    
    
}
