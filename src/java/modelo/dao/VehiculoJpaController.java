/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entidades.Cliente;
import modelo.entidades.Reparacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.RollbackFailureException;
import modelo.entidades.Vehiculo;

/**
 *
 * @author alberto
 */
public class VehiculoJpaController implements Serializable {

    public VehiculoJpaController(EntityManagerFactory emf) {
          this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculo vehiculo) throws RollbackFailureException, Exception {
        if (vehiculo.getReparaciones() == null) {
            vehiculo.setReparaciones(new ArrayList<Reparacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = vehiculo.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                vehiculo.setCliente(cliente);
            }
            List<Reparacion> attachedReparaciones = new ArrayList<Reparacion>();
            for (Reparacion reparacionesReparacionToAttach : vehiculo.getReparaciones()) {
                reparacionesReparacionToAttach = em.getReference(reparacionesReparacionToAttach.getClass(), reparacionesReparacionToAttach.getId());
                attachedReparaciones.add(reparacionesReparacionToAttach);
            }
            vehiculo.setReparaciones(attachedReparaciones);
            em.persist(vehiculo);
            if (cliente != null) {
                cliente.getVehiculos().add(vehiculo);
                cliente = em.merge(cliente);
            }
            for (Reparacion reparacionesReparacion : vehiculo.getReparaciones()) {
                Vehiculo oldVehiculoOfReparacionesReparacion = reparacionesReparacion.getVehiculo();
                reparacionesReparacion.setVehiculo(vehiculo);
                reparacionesReparacion = em.merge(reparacionesReparacion);
                if (oldVehiculoOfReparacionesReparacion != null) {
                    oldVehiculoOfReparacionesReparacion.getReparaciones().remove(reparacionesReparacion);
                    oldVehiculoOfReparacionesReparacion = em.merge(oldVehiculoOfReparacionesReparacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehiculo vehiculo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo persistentVehiculo = em.find(Vehiculo.class, vehiculo.getId());
            Cliente clienteOld = persistentVehiculo.getCliente();
            Cliente clienteNew = vehiculo.getCliente();
            List<Reparacion> reparacionesOld = persistentVehiculo.getReparaciones();
            List<Reparacion> reparacionesNew = vehiculo.getReparaciones();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                vehiculo.setCliente(clienteNew);
            }
            List<Reparacion> attachedReparacionesNew = new ArrayList<Reparacion>();
            for (Reparacion reparacionesNewReparacionToAttach : reparacionesNew) {
                reparacionesNewReparacionToAttach = em.getReference(reparacionesNewReparacionToAttach.getClass(), reparacionesNewReparacionToAttach.getId());
                attachedReparacionesNew.add(reparacionesNewReparacionToAttach);
            }
            reparacionesNew = attachedReparacionesNew;
            vehiculo.setReparaciones(reparacionesNew);
            vehiculo = em.merge(vehiculo);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getVehiculos().remove(vehiculo);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getVehiculos().add(vehiculo);
                clienteNew = em.merge(clienteNew);
            }
            for (Reparacion reparacionesOldReparacion : reparacionesOld) {
                if (!reparacionesNew.contains(reparacionesOldReparacion)) {
                    reparacionesOldReparacion.setVehiculo(null);
                    reparacionesOldReparacion = em.merge(reparacionesOldReparacion);
                }
            }
            for (Reparacion reparacionesNewReparacion : reparacionesNew) {
                if (!reparacionesOld.contains(reparacionesNewReparacion)) {
                    Vehiculo oldVehiculoOfReparacionesNewReparacion = reparacionesNewReparacion.getVehiculo();
                    reparacionesNewReparacion.setVehiculo(vehiculo);
                    reparacionesNewReparacion = em.merge(reparacionesNewReparacion);
                    if (oldVehiculoOfReparacionesNewReparacion != null && !oldVehiculoOfReparacionesNewReparacion.equals(vehiculo)) {
                        oldVehiculoOfReparacionesNewReparacion.getReparaciones().remove(reparacionesNewReparacion);
                        oldVehiculoOfReparacionesNewReparacion = em.merge(oldVehiculoOfReparacionesNewReparacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vehiculo.getId();
                if (findVehiculo(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = vehiculo.getCliente();
            if (cliente != null) {
                cliente.getVehiculos().remove(vehiculo);
                cliente = em.merge(cliente);
            }
            List<Reparacion> reparaciones = vehiculo.getReparaciones();
            for (Reparacion reparacionesReparacion : reparaciones) {
                reparacionesReparacion.setVehiculo(null);
                reparacionesReparacion = em.merge(reparacionesReparacion);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> findVehiculoEntities() {
        return findVehiculoEntities(true, -1, -1);
    }

    public List<Vehiculo> findVehiculoEntities(int maxResults, int firstResult) {
        return findVehiculoEntities(false, maxResults, firstResult);
    }

    private List<Vehiculo> findVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vehiculo findVehiculo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculo> rt = cq.from(Vehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
