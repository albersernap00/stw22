/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rober
 */
@Stateless
public class PrecioLuzDAO extends AbstractFacade<PrecioLuz> {

    @PersistenceContext(unitName = "stw22_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrecioLuzDAO() {
        super(PrecioLuz.class);
    }
    
        
    public boolean createIfNotExists(PrecioLuz entity){
        System.out.println("[!!] Antes del if not exists");
        List<PrecioLuz> lista = findAll();
        System.out.println("[!!] Despues del if not exists");
        boolean existePrecio = false;
        if (lista != null){
            for (PrecioLuz precio: lista) {
                if (precio.getFecha().equals(entity.getFecha()) && precio.getHora().equals(entity.getHora())){
                    existePrecio = true;
                }
            }
        }
        
        
        if (!existePrecio){
            super.create(entity);
            return true;
        }
        return false;
    }
    
    public List<PrecioLuz> obtenerPreciosDia(Date date){
        
        Query query = em.createQuery("SELECT p FROM PrecioLuz p WHERE p.fecha=:fecha ORDER BY p.hora ASC");
        query.setParameter("fecha", date);
        
        List<PrecioLuz> lista = query.getResultList();
        
        return lista;
    }
    
    public double obtenerPreciosNow(){
        Date hoy = new Date();
        int hora = hoy.getHours() + 2;
        
        List<PrecioLuz> lista = super.findAll();
        
        String horaSelect = getHoraSelect(hora);
        System.out.println("[!!!!!!!!!!!!] La hora en cadena es '" + horaSelect + "'");
        for(PrecioLuz p: lista){
            System.out.println("La hora a comprarar es '" + p.getHora() + "' y '" + horaSelect + "'");
            System.out.println("La fecha a comprarar es '" + p.getFecha().getDate() + "' y '" + hoy.getDate() + "'");
            if (p.getFecha().getDate() == hoy.getDate() && p.getHora().equals(horaSelect)){
                return p.getPrecio();
            }
        }       
        return 0.0;
    }

    private String getHoraSelect(int hora) {
        String horaReturn = "";
        if(hora < 9){
            horaReturn = "0" + hora + "-0" + (hora + 1);                        
        }else if (hora == 9){
            horaReturn = "0" + hora + "-10";
        }else{
            horaReturn = hora + "-" + (hora + 1);
        }
        return horaReturn;
    }
    
}
