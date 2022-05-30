/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rober
 */
@Stateless
public class UsuarioDAO extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "stw22_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

    public UsuarioDAO() {
        super(Usuario.class);
    }
    
}
