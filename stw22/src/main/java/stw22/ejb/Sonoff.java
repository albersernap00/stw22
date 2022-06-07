/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.ejb;

import javax.ejb.Stateless;

/**
 *
 * @author rober
 */
@Stateless
public class Sonoff {
    private Boolean estado = false;

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean _estado) {
        this.estado = _estado;
    }
}
