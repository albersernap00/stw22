/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public Usuario checkAutenticacion (String _login, String _pwd){
        Usuario usuario = null;
        System.out.println("Los params que me llegan son " + _login + " y " + _pwd);
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario=:login AND u.password=:pwd");
        query.setParameter("login", _login);
        query.setParameter("pwd", _pwd);

        try{
            usuario = (Usuario) query.getSingleResult();
            System.out.println("usuario es: " + usuario.toString());
        }catch(Exception e){
            System.out.println("ha habido una buena ecepcion " + e.getMessage());
        }

        return usuario;
    }

    public UsuarioDAO() {
        super(Usuario.class);
    }
    
}
