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
import javax.persistence.Query;

/**
 *
 * @author Robeto Jiménez
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
    
    public boolean createIfNotExists(Usuario entity){
        
        System.out.println("[!!] Entro en el create if not exists el nombreQ le paso es " + entity.getNombreUsuario());
        List<Usuario> lista = findAll();
        boolean existeUsuario = false;
        System.out.println("[!!] Después del findall");
        if (lista != null){
           for (Usuario user: lista) {
               if (user.getNombreUsuario() == null){
                   System.out.println("Tengo q borrar uno");
                   
                   super.remove(user);
               }else{
                if (user.getNombreUsuario().equals(entity.getNombreUsuario())){
                    existeUsuario = true;
                }   
               }
               
               
                
            } 
        }
        
        System.out.println("[!!] Después del for");
        if (!existeUsuario){
            super.create(entity);
            return true;
        }
        return false;
    }

    public UsuarioDAO() {
        super(Usuario.class);
    }
    
}
