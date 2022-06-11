/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

/**
 *
 * @author rober
 */
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Usuario
 */
@WebListener
public class OyenteSesiones implements HttpSessionListener {
    
    
    @Override
    public void sessionCreated(HttpSessionEvent _se) {
        System.out.println("=== Sesión creada ====== "+_se.getSession().getId());
        _se.getSession().setAttribute("misCojones", "MISCO");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent _se) {
        System.out.println("*** Sesión destroyed *** "+_se.getSession().getId());
        
        HttpSession session  = _se.getSession();
        ServletContext application = session.getServletContext();
        
        String nombre = (String) session.getAttribute("nombre");
        if (nombre !=null){
            List<String>nombresConectados = (List<String>)application.getAttribute("usuariosConectados");
            if (nombresConectados!=null){
                nombresConectados.remove(nombre);
                application.setAttribute("usuariosConectados", nombresConectados);
                System.out.println("\tUsuario: '"+nombre+"' ha sido eliminado. Quedan: "+nombresConectados.size());
            }
        }
    }

   
}
