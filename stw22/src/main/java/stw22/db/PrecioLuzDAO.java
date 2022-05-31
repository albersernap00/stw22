/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
