/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.timer;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * java beans => sessions bean 
 * @author rober
 */
@Singleton
@Startup
public class Arrancador {
    
    @EJB TimerPrecioLuz timer;
    public static Gson gson;
    @PostConstruct
    public void init(){
        gson = new Gson();
        //timer.obtenerPrecioLuz();
        //crearServicio(NOMBRE_SERVICIO, StatusCode.BOOTING, getIp());
        System.out.println("====== LA IP ES ");
        //Stockk_Services_Directory clientService = new Stockk_Services_Directory();
        //clientService.registerService(servicio);
        //clientService.close();
        
    }
    
    @PreDestroy
    public void bye(){
        System.out.println("====== DOWN LA IP ES " );
                
    }
    
    

    
    
}

