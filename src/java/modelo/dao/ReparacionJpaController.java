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
import modelo.entidades.Vehiculo;
import modelo.entidades.Actuacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.RollbackFailureException;
import modelo.entidades.Reparacion;

/**
 *
 * @author alberto
 */
public class ReparacionJpaController implements Serializable {

    public ReparacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reparacion reparacion) throws RollbackFailureException, Exception {
        if (reparacion.getActuaciones() == null) {
            reparacion.setActuaciones(new ArrayList<Actuacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo vehiculo = reparacion.getVehiculo();
            if (vehiculo != null) {
                vehiculo = em.getReference(vehiculo.getClass(), vehiculo.getId());
                reparacion.setVehiculo(vehiculo);
            }
            List<Actuacion> attachedActuaciones = new ArrayList<Actuacion>();
            for (Actuacion actuacionesActuacionToAttach : reparacion.getActuaciones()) {
                actuacionesActuacionToAttach = em.getReference(actuacionesActuacionToAttach.getClass(), actuacionesActuacionToAttach.getId());
                attachedActuaciones.add(actuacionesActuacionToAttach);
            }
            reparacion.setActuaciones(attachedActuaciones);
            em.persist(reparacion);
            if (vehiculo != null) {
                vehiculo.getReparaciones().add(reparacion);
                vehiculo = em.merge(vehiculo);
            }
            for (Actuacion actuacionesActuacion : reparacion.getActuaciones()) {
                Reparacion oldReparacionOfActuacionesActuacion = actuacionesActuacion.getReparacion();
                actuacionesActuacion.setReparacion(reparacion);
                actuacionesActuacion = em.merge(actuacionesActuacion);
                if (oldReparacionOfActuacionesActuacion != null) {
                    oldReparacionOfActuacionesActuacion.getActuaciones().remove(actuacionesActuacion);
                    oldReparacionOfActuacionesActuacion = em.merge(oldReparacionOfActuacionesActuacion);
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

    public void edit(Reparacion reparacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparacion persistentReparacion = em.find(Reparacion.class, reparacion.getId());
            Vehiculo vehiculoOld = persistentReparacion.getVehiculo();
            Vehiculo vehiculoNew = reparacion.getVehiculo();
            List<Actuacion> actuacionesOld = persistentReparacion.getActuaciones();
            List<Actuacion> actuacionesNew = reparacion.getActuaciones();
            if (vehiculoNew != null) {
                vehiculoNew = em.getReference(vehiculoNew.getClass(), vehiculoNew.getId());
                reparacion.setVehiculo(vehiculoNew);
            }
            List<Actuacion> attachedActuacionesNew = new ArrayList<Actuacion>();
            for (Actuacion actuacionesNewActuacionToAttach : actuacionesNew) {
                actuacionesNewActuacionToAttach = em.getReference(actuacionesNewActuacionToAttach.getClass(), actuacionesNewActuacionToAttach.getId());
                attachedActuacionesNew.add(actuacionesNewActuacionToAttach);
            }
            actuacionesNew = attachedActuacionesNew;
            reparacion.setActuaciones(actuacionesNew);
            reparacion = em.merge(reparacion);
            if (vehiculoOld != null && !vehiculoOld.equals(vehiculoNew)) {
                vehiculoOld.getReparaciones().remove(reparacion);
                vehiculoOld = em.merge(vehiculoOld);
            }
            if (vehiculoNew != null && !vehiculoNew.equals(vehiculoOld)) {
                vehiculoNew.getReparaciones().add(reparacion);
                vehiculoNew = em.merge(vehiculoNew);
            }
            for (Actuacion actuacionesOldActuacion : actuacionesOld) {
                if (!actuacionesNew.contains(actuacionesOldActuacion)) {
                    actuacionesOldActuacion.setReparacion(null);
                    actuacionesOldActuacion = em.merge(actuacionesOldActuacion);
                }
            }
            for (Actuacion actuacionesNewActuacion : actuacionesNew) {
                if (!actuacionesOld.contains(actuacionesNewActuacion)) {
                    Reparacion oldReparacionOfActuacionesNewActuacion = actuacionesNewActuacion.getReparacion();
                    actuacionesNewActuacion.setReparacion(reparacion);
                    actuacionesNewActuacion = em.merge(actuacionesNewActuacion);
                    if (oldReparacionOfActuacionesNewActuacion != null && !oldReparacionOfActuacionesNewActuacion.equals(reparacion)) {
                        oldReparacionOfActuacionesNewActuacion.getActuaciones().remove(actuacionesNewActuacion);
                        oldReparacionOfActuacionesNewActuacion = em.merge(oldReparacionOfActuacionesNewActuacion);
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
                Long id = reparacion.getId();
                if (findReparacion(id) == null) {
                    throw new NonexistentEntityException("The reparacion with id " + id + " no longer exists.");
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
            Reparacion reparacion;
            try {
                reparacion = em.getReference(Reparacion.class, id);
                reparacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reparacion with id " + id + " no longer exists.", enfe);
            }
            Vehiculo vehiculo = reparacion.getVehiculo();
            if (vehiculo != null) {
                vehiculo.getReparaciones().remove(reparacion);
                vehiculo = em.merge(vehiculo);
            }
            List<Actuacion> actuaciones = reparacion.getActuaciones();
            for (Actuacion actuacionesActuacion : actuaciones) {
                actuacionesActuacion.setReparacion(null);
                actuacionesActuacion = em.merge(actuacionesActuacion);
            }
            em.remove(reparacion);
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

    public List<Reparacion> findReparacionEntities() {
        return findReparacionEntities(true, -1, -1);
    }

    public List<Reparacion> findReparacionEntities(int maxResults, int firstResult) {
        return findReparacionEntities(false, maxResults, firstResult);
    }

    private List<Reparacion> findReparacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reparacion.class));
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

    public Reparacion findReparacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reparacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReparacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reparacion> rt = cq.from(Reparacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
