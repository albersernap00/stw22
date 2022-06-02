/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.websocket;



import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author fserna
 */

@ServerEndpoint("/ui")
public class WebSocketManager {


    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    

    public WebSocketManager(){
    }
  
    
    
    
    @OnOpen
    public void onOpen(Session _session){
        sessions.add(_session);
System.out.println("ChirpStack. NEW     websocket session. #: "+sessions.size());    

    } // onOpen

    
    
    
    @OnMessage
    public String onMessage(String message) {
        return null;
    }
    
    @OnClose
    public void onClose(Session _session){
        sessions.remove(_session);
//System.out.println("ChirpStack. REMOVED websocket session. #: "+sessions.size());                
    }
    
    
    public void refreshUI(String _json){
//System.out.println("REFRESH UI: ======================\n");
//System.out.println(_json);
        try {
            for (Session session : sessions) {
                session.getBasicRemote().sendText(_json);
            }
        } catch (IOException ex) {
        }
    }    
}
