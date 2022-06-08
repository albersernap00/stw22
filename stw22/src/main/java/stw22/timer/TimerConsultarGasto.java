/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.timer;

import REST.client.PrecioLuzNowClienteRest;
import WebSocket.WebSocketManager;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import stw22.ejb.Sonoff;

/**
 *
 * @author rober
 */
@Stateless
public class TimerConsultarGasto {
    private static final double POTENCIA_BOMBILLA = 0.000005; // 5MW
    private double tiempo = 0;
    @EJB Sonoff sonoff;
    @EJB WebSocketManager ws;
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "*/2", persistent = false)
    public void timer(){       
        double precio = 0.0;
        double gasto = 0.0;
        if(sonoff.getEstado()){
            PrecioLuzNowClienteRest client = new PrecioLuzNowClienteRest();
                precio = Double.valueOf(client.getPrecioLuzNow());
            client.close();
            System.out.println("[!!!!!] El precio obtenido es " + precio);
            double t = tiempo/3600.0;
            System.out.println(t);
            gasto = POTENCIA_BOMBILLA * precio * (tiempo/3600); // Lo obtendrias de la BD
            System.out.println("ESTE GASTO " + gasto);
            ws.sendCoste(String.format("%.15f", gasto));
        }      
        
    }
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "*", persistent = false)
    public void timerSegundos(){ 
        if (sonoff.getEstado()){
            tiempo++;
        }
        
    }
    
    
}
