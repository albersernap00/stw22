/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.ejb.timer;

import REST.client.PrecioLuzNowClienteRest;
import stw22.ejb.WebSocket.WebSocketManager;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import stw22.db.GastoEnchufe;
import stw22.db.GastoEnchufeDAO;

import stw22.ejb.Sonoff;

/**
 *
 * @author rober
 */
@Stateless
public class TimerConsultarGasto {
    private static final double POTENCIA_BOMBILLA = 0.000005; // 5MW
    
    @EJB Sonoff sonoff;
    @EJB WebSocketManager ws;
    @EJB GastoEnchufeDAO gastoDB;
    //private double tiempo = gastoDB.obtenerGastoDia().getTiempo();
    private double tiempo = 0;
    
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "*/2", persistent = false)
    public void timer(){       
        double precio = 0.0;
        double gasto = 0.0;        
        if(sonoff.getEstado()){
            PrecioLuzNowClienteRest client = new PrecioLuzNowClienteRest();
                precio = Double.valueOf(client.getPrecioLuzNow());
            client.close();
            System.out.println("[!!!!!] El precio obtenido es " + precio);
            GastoEnchufe g = gastoDB.obtenerGastoDia();
            
            gasto = POTENCIA_BOMBILLA * precio * ( (tiempo)/3600); // Lo obtendrias de la BD
            System.out.println("ESTE GASTO " + gasto);
            
            
            
            //System.out.println("el gasto de hoy es " + g.getGasto());
            if (g == null){
                System.out.println("HE DE ADD GASTo");
                addGasto(gasto);
            }else{    
               
                gastoDB.updateGasto(gasto, (tiempo));
            }
            
            
            ws.sendCoste(String.format("%.15f", gasto));
            
        }      
        
    }
    
    public void addGasto(double gasto){
        GastoEnchufe g = new GastoEnchufe();
        g.setFecha(new Date());
        g.setGasto(gasto);
        g.setTiempo(tiempo);
        gastoDB.create(g);
    }
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "*", persistent = false)
    public void timerSegundos(){ 
        
        if (sonoff.getEstado()){
            if(tiempo == 0){
                GastoEnchufe g = gastoDB.obtenerGastoDia();
                if (g != null){
                    tiempo = g.getTiempo();
                }
            }
            tiempo++;
        }
        
        
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
    
    
    
    
    
    
}
