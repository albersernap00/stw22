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


/**
 *
 * @author rober
 */

@ServerEndpoint("/stw")
@Singleton
public class WebSocketManager {
    @EJB PrecioLuzDAO preciosDB; 
    @EJB HistoricoSensoresDAO historicoDB;
    
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
       
        if(obj.has("cmnd")){
            try {
                accion = obj.getString("cmnd");
                switch(accion){
                    case "datePrecioLuz":
                        valueFecha = obj.getString("Fecha");
                        System.out.println("[+] La fecha que recibo es " + valueFecha);
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(valueFecha);
                        List<PrecioLuz> listaValores = preciosDB.obtenerPreciosDia(date);                        
                        sendMessageSession(gson.toJson(listaValores,List.class), "datePrecioLuzResult", sesion);
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
        sendDataSensores();
    }
    
    public void sendDataSensores(){
        try{
            
            String listaJson = "[";
            for (int i = 0; i < 24; i++) {
                int numVeces = 0;
                if (i < 10){
                    numVeces = historicoDB.getHistoricoSensoresHora("0" + String.valueOf(i));
                }else{
                    numVeces = historicoDB.getHistoricoSensoresHora(String.valueOf(i));
                }
                
                if(i == 23){
                    listaJson += "{\"hora\": \"" + i + "\", \"value\": \""+numVeces + "\"}";
                }else if(i == 0){
                    listaJson += "{\"hora\": \"" + "24" + "\", \"value\": \""+numVeces + "\"},";
                }else{
                    listaJson += "{\"hora\": \"" + i + "\", \"value\": \""+numVeces + "\"},";
                }
                
                
                
            }
            
            listaJson +="]";
            publishMessage(listaJson, "datosSensoresResult");
        }catch(Exception e){
            System.out.println("Excepcion add " + e.toString());
            e.printStackTrace();
        }
    }
    

    
    
}
