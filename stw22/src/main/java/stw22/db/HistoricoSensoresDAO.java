/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Robeto Jiménez
 */
@Stateless
public class HistoricoSensoresDAO extends AbstractFacade<HistoricoSensores> {

    @PersistenceContext(unitName = "stw22_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoricoSensoresDAO() {
        super(HistoricoSensores.class);
    }
    
    public int getHistoricoSensoresHora (String _hora, Date fecha){
        List<HistoricoSensores> historico = new ArrayList<>();
        List<HistoricoSensores> historicoDia = new ArrayList<>();
        
        Date hoy = new Date();
        
        
        
        Query query = em.createQuery("SELECT h FROM HistoricoSensores h WHERE h.hora LIKE \"" + _hora + "%\"");
        //query.setParameter("fecha", hoy);
        

        try{
            historico = (List) query.getResultList();
            historico.forEach(element ->{
                if(fecha != null){
                    if (element.getFecha().getDate() == fecha.getDate()){
                        historicoDia.add(element);
                    }
                }else{
                    if (element.getFecha().getDate() == hoy.getDate()){
                        historicoDia.add(element);
                    }
                }
                
            });
            System.out.println("listaHsitorico es: " + historico.toString() + " y su tamanuyo es " + historico.size());
        }catch(Exception e){
            System.out.println("ha habido una buena ecepcion en sensores " + e.getMessage());
        }

        return historicoDia.size();
    }
    
    
    
}
