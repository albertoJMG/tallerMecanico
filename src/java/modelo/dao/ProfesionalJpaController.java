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
import modelo.entidades.Actuacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.RollbackFailureException;
import modelo.entidades.Profesional;

/**
 *
 * @author alberto
 */
public class ProfesionalJpaController implements Serializable {

    public ProfesionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesional profesional) throws RollbackFailureException, Exception {
        if (profesional.getActuaciones() == null) {
            profesional.setActuaciones(new ArrayList<Actuacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Actuacion> attachedActuaciones = new ArrayList<Actuacion>();
            for (Actuacion actuacionesActuacionToAttach : profesional.getActuaciones()) {
                actuacionesActuacionToAttach = em.getReference(actuacionesActuacionToAttach.getClass(), actuacionesActuacionToAttach.getId());
                attachedActuaciones.add(actuacionesActuacionToAttach);
            }
            profesional.setActuaciones(attachedActuaciones);
            em.persist(profesional);
            for (Actuacion actuacionesActuacion : profesional.getActuaciones()) {
                Profesional oldProfesionalOfActuacionesActuacion = actuacionesActuacion.getProfesional();
                actuacionesActuacion.setProfesional(profesional);
                actuacionesActuacion = em.merge(actuacionesActuacion);
                if (oldProfesionalOfActuacionesActuacion != null) {
                    oldProfesionalOfActuacionesActuacion.getActuaciones().remove(actuacionesActuacion);
                    oldProfesionalOfActuacionesActuacion = em.merge(oldProfesionalOfActuacionesActuacion);
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

    public void edit(Profesional profesional) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesional persistentProfesional = em.find(Profesional.class, profesional.getId());
            List<Actuacion> actuacionesOld = persistentProfesional.getActuaciones();
            List<Actuacion> actuacionesNew = profesional.getActuaciones();
            List<Actuacion> attachedActuacionesNew = new ArrayList<Actuacion>();
            for (Actuacion actuacionesNewActuacionToAttach : actuacionesNew) {
                actuacionesNewActuacionToAttach = em.getReference(actuacionesNewActuacionToAttach.getClass(), actuacionesNewActuacionToAttach.getId());
                attachedActuacionesNew.add(actuacionesNewActuacionToAttach);
            }
            actuacionesNew = attachedActuacionesNew;
            profesional.setActuaciones(actuacionesNew);
            profesional = em.merge(profesional);
            for (Actuacion actuacionesOldActuacion : actuacionesOld) {
                if (!actuacionesNew.contains(actuacionesOldActuacion)) {
                    actuacionesOldActuacion.setProfesional(null);
                    actuacionesOldActuacion = em.merge(actuacionesOldActuacion);
                }
            }
            for (Actuacion actuacionesNewActuacion : actuacionesNew) {
                if (!actuacionesOld.contains(actuacionesNewActuacion)) {
                    Profesional oldProfesionalOfActuacionesNewActuacion = actuacionesNewActuacion.getProfesional();
                    actuacionesNewActuacion.setProfesional(profesional);
                    actuacionesNewActuacion = em.merge(actuacionesNewActuacion);
                    if (oldProfesionalOfActuacionesNewActuacion != null && !oldProfesionalOfActuacionesNewActuacion.equals(profesional)) {
                        oldProfesionalOfActuacionesNewActuacion.getActuaciones().remove(actuacionesNewActuacion);
                        oldProfesionalOfActuacionesNewActuacion = em.merge(oldProfesionalOfActuacionesNewActuacion);
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
                Long id = profesional.getId();
                if (findProfesional(id) == null) {
                    throw new NonexistentEntityException("The profesional with id " + id + " no longer exists.");
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
            Profesional profesional;
            try {
                profesional = em.getReference(Profesional.class, id);
                profesional.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesional with id " + id + " no longer exists.", enfe);
            }
            List<Actuacion> actuaciones = profesional.getActuaciones();
            for (Actuacion actuacionesActuacion : actuaciones) {
                actuacionesActuacion.setProfesional(null);
                actuacionesActuacion = em.merge(actuacionesActuacion);
            }
            em.remove(profesional);
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

    public List<Profesional> findProfesionalEntities() {
        return findProfesionalEntities(true, -1, -1);
    }

    public List<Profesional> findProfesionalEntities(int maxResults, int firstResult) {
        return findProfesionalEntities(false, maxResults, firstResult);
    }

    private List<Profesional> findProfesionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesional.class));
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

    public Profesional findProfesional(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesional.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesional> rt = cq.from(Profesional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
