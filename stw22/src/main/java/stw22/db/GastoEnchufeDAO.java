/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rober
 */
@Stateless
public class GastoEnchufeDAO extends AbstractFacade<GastoEnchufe> {

    @PersistenceContext(unitName = "stw22_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GastoEnchufeDAO() {
        super(GastoEnchufe.class);
    }
    
    public GastoEnchufe obtenerGastoDia(){
        Date hoy = new Date();
        GastoEnchufe gasto = null;        
        List<GastoEnchufe> lista = super.findAll();
        for(GastoEnchufe g: lista){
            if(g.getFecha().getDate() == hoy.getDate() && g.getFecha().getMonth() == hoy.getMonth()){
                gasto = g;
            }
        }
                                        
        return gasto;
        
    }
    
    public void updateGasto(double gasto, double tiempo){
        Date hoy = new Date();
        List<GastoEnchufe> lista = super.findAll();
        for(GastoEnchufe g: lista){
            if(g.getFecha().getDate() == hoy.getDate() && g.getFecha().getMonth() == hoy.getMonth()){
                g.setGasto(gasto);
                g.setTiempo(tiempo);
                super.edit(g);
            }
        }
    }
    
    public void removeAll(){
        for (GastoEnchufe g: super.findAll()){
            super.remove(g);
        }
    }
    
}
