/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;



import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 *
 * @author rober
 */

@ServerEndpoint("/stw")
public class WebSocketManager {
    
    private Set<Session> sessions = new HashSet<Session>(); 
    Gson gson;
    @OnOpen
    public void onOpen(Session _session){
        System.out.println(">>> Session " +_session.getId()+" created");
        sessions.add(_session);
        gson = new Gson();

        broadcastMsg("hola");
    }
    
    
    @OnMessage
    public String onMessage(String message) {
        System.out.println("[!] Recibo === " + message);
        /*JSONObject obj = new JSONObject(message);
        String accion = "";
        String jsonCadena = "";
        int value = -1;
       
        if(obj.has("cmnd")){
            accion = obj.getString("cmnd");
            switch(accion){
                case "getProvincias":
                    value = obj.getInt("CCAA");                    
                    publishMessage(jsonCadena, "provinciasId");       
                break;
                                   
            }
        }*/
        
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
        String json =  "{\"cmnd\": \""+ comando + "\", \"values\": " + cadena  + "}";
        broadcastMsg(json);
    }
    

    
    
}
