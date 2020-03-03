/*
 * Clase TALLER
 * JavaBeans de acceso al modelo
 */
package modelo;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.dao.ActuacionJpaController;
import modelo.dao.ClienteJpaController;
import modelo.dao.ProfesionalJpaController;
import modelo.dao.ReparacionJpaController;
import modelo.dao.VehiculoJpaController;
import modelo.entidades.Actuacion;
import modelo.entidades.Cliente;
import modelo.entidades.Profesional;
import modelo.entidades.Reparacion;
import modelo.entidades.Vehiculo;

/**
 *
 * @author alberto
 */
public class Taller {

    public static final String PU = "TallerMecanicoPU";

    // Metodos que controlan el acceso del Cliente
    public Cliente loginC(String login, String password) {
        Cliente c = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("buscarPorLoginC");
        consulta.setParameter("nombreUsuario", login);
        List<Cliente> resultado = consulta.getResultList();
        if (resultado.size() > 0) {
            if (resultado.get(0).getPass().equals(password)) {
                c = resultado.get(0);
            }
        }
        em.close();
        return c;
    }

    // Metodos que controlan el acceso de los profesionales
    public Profesional loginP(String login, String password) {
        Profesional p = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("buscarPorLoginP");
        consulta.setParameter("nombreUsuario", login);
        List<Profesional> resultado = consulta.getResultList();
        if (resultado.size() > 0) {
            if (resultado.get(0).getPass().equals(password)) {
                p = resultado.get(0);
            }
        }
        em.close();
        return p;
    }

    // Metodos de creacion de las distintas entidades
    public void crearCliente(Cliente c) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        try {
            ejc.create(c);
        } catch (Exception ex) {
            System.err.println("Error al crear al cliente: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }

    }

    public void crearProfesional(Profesional p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProfesionalJpaController ejc = new ProfesionalJpaController(emf);
        try {
            ejc.create(p);
        } catch (Exception ex) {
            System.err.println("Error al crear al profesional: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }

    }

    public void crearVehiculo(Vehiculo v) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        VehiculoJpaController ejc = new VehiculoJpaController(emf);
        try {
            ejc.create(v);
        } catch (Exception ex) {
            System.err.println("Error al crear al vehiculo: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }

    }

    public void crearReparacion(Reparacion r) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ReparacionJpaController ejc = new ReparacionJpaController(emf);
        try {
            ejc.create(r);
        } catch (Exception ex) {
            System.err.println("Error al crear la reparacion: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }

    }

    public void crearActuacion(Actuacion a) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ActuacionJpaController ejc = new ActuacionJpaController(emf);
        try {
            ejc.create(a);
        } catch (Exception ex) {
            System.err.println("Error al crear la actuacion: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }

    }

    //Metodos de busqueda de las distintas entidades
    public Cliente buscarCliente(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        Cliente clie = ejc.findCliente(id);
        return clie;
    }

    public Vehiculo buscarVehiculo(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        VehiculoJpaController ejc = new VehiculoJpaController(emf);
        Vehiculo v = ejc.findVehiculo(id);
        return v;
    }

    public Profesional buscarProfesional(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProfesionalJpaController ejc = new ProfesionalJpaController(emf);
        Profesional emp = ejc.findProfesional(id);
        return emp;
    }

    public Reparacion buscarReparacion(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ReparacionJpaController ejc = new ReparacionJpaController(emf);
        Reparacion rep = ejc.findReparacion(id);
        return rep;
    }

    public Actuacion buscarActuacion(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ActuacionJpaController ejc = new ActuacionJpaController(emf);
        Actuacion a = ejc.findActuacion(id);
        return a;
    }
    
    public boolean buscarClientePorUsuario(String nombreUsuario) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        boolean existe = false;
        List<Cliente> clientes = ejc.findClienteEntities();
        for(int i = 0; i<clientes.size() && !existe ; i++){
            if(clientes.get(i).getNombreUsuario().equals(nombreUsuario)){
                existe = true;
            }
        }
        return existe;
    }
    
    public boolean buscarProfesionalPorUsuario(String nombreUsuario) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProfesionalJpaController ejc = new ProfesionalJpaController(emf);
        boolean existe = false;
        List<Profesional> profesionales = ejc.findProfesionalEntities();
        for(int i = 0; i<profesionales.size() && !existe ; i++){
            if(profesionales.get(i).getNombreUsuario().equals(nombreUsuario)){
                existe = true;
            }
        }
        return existe;
    }

    //Metodos para la obtencion de las listas de las distintas entidades -------------------------> Crear metodo para la obtencion de reparaciones finalizadas
    public List<Cliente> getClientes() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        List<Cliente> clientes = ejc.findClienteEntities();
        return clientes;
    }

    public List<Reparacion> getReparaciones() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ReparacionJpaController ejc = new ReparacionJpaController(emf);
        List<Reparacion> reparaciones = ejc.findReparacionEntities();
        return reparaciones;
    }

    public List<Reparacion> getReparacionesEnCurso() {
        List<Reparacion> r = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("buscarReparacionPendiente");
        r = consulta.getResultList();
        em.close();
        return r;
    }

    public List<Reparacion> getReparacionesPorFecha(String fecha) {
        List<Reparacion> r = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT r FROM Reparacion r WHERE r.fechaTerminacion = '"+fecha+"'";
        Query query = em.createQuery(jpql);
        r = query.getResultList();
        em.close();
        return r;
    }

    public List<Profesional> getProfesionales() {
        List<Profesional> p = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT p FROM Profesional p WHERE p.tipo like 'mecanico%'";
        Query query = em.createQuery(jpql);
        p = query.getResultList();
        em.close();
        return p;
    }

    public List<Profesional> getProfesionalesActivos() {
        List<Profesional> p = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT p FROM Profesional p WHERE p.activo = 'T'";
        Query query = em.createQuery(jpql);
        //consulta.setParameter("nombreUsuario", login);
        p = query.getResultList();
        em.close();
        return p;
    }
    
    public List<Actuacion> getTodasActuaciones() {
        List<Actuacion> a = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT a FROM Actuacion a";
        Query query = em.createQuery(jpql);
        a = query.getResultList();
        em.close();
        return a;
    }

    //Metodos de actualizacion de las entidades
    public void bajaProfesional(Profesional p) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProfesionalJpaController ejc = new ProfesionalJpaController(emf);
        ejc.edit(p);
    }

    public void actualizarProfesional(Profesional p) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProfesionalJpaController ejc = new ProfesionalJpaController(emf);
        ejc.edit(p);
    }

    public void actualizarReparacion(Reparacion r) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ReparacionJpaController ejc = new ReparacionJpaController(emf);
        ejc.edit(r);
    }

    public void actualizarActuacion(Actuacion a) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ActuacionJpaController ejc = new ActuacionJpaController(emf);
        ejc.edit(a);
    }
    
    public void actualizarVehiculo(Vehiculo v) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        VehiculoJpaController ejc = new VehiculoJpaController(emf);
        ejc.edit(v);
    }
    
    public void actualizarCliente(Cliente c) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        ejc.edit(c);
    }

}
