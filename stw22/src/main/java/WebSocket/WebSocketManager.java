/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import com.google.gson.Gson;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import stw22.db.HistoricoSensores;
import stw22.db.HistoricoSensoresDAO;
import stw22.db.PrecioLuz;
import stw22.db.PrecioLuzDAO;
import stw22.ejb.Sonoff;
import stw22.timer.Arrancador;


/**
 *
 * @author rober
 */

@ServerEndpoint("/stw")
@Singleton
public class WebSocketManager {
    @EJB PrecioLuzDAO preciosDB; 
    @EJB HistoricoSensoresDAO historicoDB;
    @EJB Sonoff sonoff;
    @EJB Arrancador starter;
    
    private Set<Session> sessions = new HashSet<Session>(); 
    
    Gson gson = new Gson();
    @OnOpen
    public void onOpen(Session _session){
        System.out.println(">>> Session " +_session.getId()+" created");
        sessions.add(_session);
        
    }

    public WebSocketManager() {
    }
    
    @OnMessage
    public String onMessage(Session sesion, String message) {
        System.out.println("[!] Recibo ===  de la sesion "+ sesion.getId() + " el mensaje " + message);
        JSONObject obj = new JSONObject(message);
        String accion = "";
        String jsonCadena = "";
        String valueFecha = "";
        
        Date date;
        if(obj.has("cmnd")){
            try {
                accion = obj.getString("cmnd");
                switch(accion){
                    case "datePrecioLuz":
                        valueFecha = obj.getString("Fecha");
                        System.out.println("[+] La fecha que recibo es " + valueFecha);
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(valueFecha);
                        List<PrecioLuz> listaValores = preciosDB.obtenerPreciosDia(date);                        
                        sendMessageSession(gson.toJson(listaValores,List.class), "datePrecioLuzResult", sesion);
                    break;
                    case "dateGraficaBarras":
                        valueFecha = obj.getString("Fecha");
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(valueFecha);
                        System.out.println("[+] La fecha que recibo en las barras es " + valueFecha);
                        sendDataSensores(date, "datosSensoresResultFecha");
                    break;
                    case "updateSonoff":
                        boolean estadoSonoff = obj.getBoolean("Value");
                        sonoff.setEstado(estadoSonoff); // y publish del mqtt
                        //publishMessage(String.valueOf(sonoff.getEstado()), "enchufe");
                        if (estadoSonoff){
                            starter.getMqtt().publish("/stw/stwAR/cmnd/POWER","1" , false);
                        }else{
                            starter.getMqtt().publish("/stw/stwAR/cmnd/POWER","0" , false);
                        }
                        
                        
                    break;
                    
                        
                }
            } catch (ParseException ex) {
                Logger.getLogger(WebSocketManager.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("[!] Excepcion parseando la fecha en WebSocketManager");
            }
        }
        
        return message;
    }
    

     
    
    /**
     * Envía el texto "_msg" a todas las sesiones existentes (broadcast)
     * @param _msg 
     */
    public void broadcastMsg(String _msg){
        try {                      
            for (Session session : sessions) {                
                session.getBasicRemote().sendText(_msg);
            }            
        } catch (IOException ex) {            
            Logger.getLogger(WebSocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void publishMessage(String cadena, String comando){
        String json =  "{\"cmnd\": \""+ comando + "\", \"values\": " + cadena  + " }";
        System.out.println("El JSON q se manda es " + json);
        broadcastMsg(json);
    }
    
    private void sendMessageSession(String cadena, String comando, Session sesion){
        try {
            String json =  "{\"cmnd\": \""+ comando + "\", \"values\": " + cadena  + " }";
            System.out.println("El JSON q se manda es " + json);
            sesion.getBasicRemote().sendText(json);
        } catch (IOException ex) {
            Logger.getLogger(WebSocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addHistoricoLuz(HistoricoSensores _sensor){
        historicoDB.create(_sensor);   
        sendDataSensores(null, "datosSensoresResult"); // Si mandas null es la fecha actual
    }
    
    public void sendDataSensores(Date fecha, String comando){
        try{
            
            String listaJson = "[";
            for (int i = 0; i < 24; i++) {
                int numVeces = 0;
                if (i < 10){
                    numVeces = historicoDB.getHistoricoSensoresHora("0" + String.valueOf(i), fecha);
                }else{
                    numVeces = historicoDB.getHistoricoSensoresHora(String.valueOf(i), fecha);
                }
                
                if(i == 23){
                    listaJson += "{\"hora\": \"" + i + "\", \"value\": \""+numVeces + "\"}";
                }else{
                    listaJson += "{\"hora\": \"" + i + "\", \"value\": \""+numVeces + "\"},";
                }
                
                
                
            }
            
            listaJson +="]";
            publishMessage(listaJson, comando);
        }catch(Exception e){
            System.out.println("Excepcion add " + e.toString());
            e.printStackTrace();
        }
    }

    public void sendStatusEnchufe(String mensaje) {
        publishMessage(mensaje, "enchufe");
    }

    public void switchEstadoEnchufe(boolean estado) {
        if (sonoff.getEstado() != estado){
            if (estado){
                starter.getMqtt().publish("/stw/stwAR/cmnd/POWER","1" , estado);
            }else{
                starter.getMqtt().publish("/stw/stwAR/cmnd/POWER","0" , estado);
            }
            sonoff.setEstado(estado);
        }
        
    }

    public void sendCoste(String precio) {
        System.out.println("[!] El precio que me llega es " + precio);
        
        publishMessage(precio, "updateGasto");
    }
    

    
    
}
