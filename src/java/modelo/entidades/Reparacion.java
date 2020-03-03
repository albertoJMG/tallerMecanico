/*
 * Entidad Reparación
 * Reparación: Acciones para la reparación del vehiculo. Estas estan divididas a su vez en Actuaciones
 */
package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author alberto
 */
@Entity
@NamedQuery(name="buscarReparacionPendiente", query="select r from Reparacion r where r.fechaTerminacion is null") 
public class Reparacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (length = 250 )
    private String descripcionAveria; //Realizada por el cliente
    @Temporal (TemporalType.DATE)
    private Date fechaSolicitud; //Cuando el cliente pide una reparacion
    @Temporal (TemporalType.DATE)
    private Date fechaTerminacion; //Cuando el Mecanico Jefe cierra una reparacion (este completa o no)
    @ManyToOne
    private Vehiculo vehiculo;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "reparacion")
    private List<Actuacion> actuaciones;
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcionAveria() {
        return descripcionAveria;
    }

    public void setDescripcionAveria(String descripcionAveria) {
        this.descripcionAveria = descripcionAveria;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Actuacion> getActuaciones() {
        return actuaciones;
    }

    public void setActuaciones(List<Actuacion> actuaciones) {
        this.actuaciones = actuaciones;
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
        if (!(object instanceof Reparacion)) {
            return false;
        }
        Reparacion other = (Reparacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Reparacion[ id=" + id + " ]";
    }
    
}
