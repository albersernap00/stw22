/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Robeto Jiménez
 */
@Entity
public class HistoricoSensores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date fecha;
    private String hora;
    private double valorSensorLuz; // valor lux de la luz
    private int valorSensorMovimiento; // 1 movimniento 0 no movimiento

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getValorSensorLuz() {
        return valorSensorLuz;
    }

    public void setValorSensorLuz(double valorSensorLuz) {
        this.valorSensorLuz = valorSensorLuz;
    }

    public int getValorSensorMovimiento() {
        return valorSensorMovimiento;
    }

    public void setValorSensorMovimiento(int valorSensorMovimiento) {
        this.valorSensorMovimiento = valorSensorMovimiento;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoSensores)) {
            return false;
        }
        HistoricoSensores other = (HistoricoSensores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HistoricoSensores{" + "id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", valorSensorLuz=" + valorSensorLuz + ", valorSensorMovimiento=" + valorSensorMovimiento + '}';
    }

    
    
}
