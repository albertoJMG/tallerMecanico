/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.RollbackFailureException;
import modelo.entidades.Actuacion;
import modelo.entidades.Reparacion;
import modelo.entidades.Profesional;

/**
 *
 * @author alberto
 */
public class ActuacionJpaController implements Serializable {

    public ActuacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actuacion actuacion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparacion reparacion = actuacion.getReparacion();
            if (reparacion != null) {
                reparacion = em.getReference(reparacion.getClass(), reparacion.getId());
                actuacion.setReparacion(reparacion);
            }
            Profesional profesional = actuacion.getProfesional();
            if (profesional != null) {
                profesional = em.getReference(profesional.getClass(), profesional.getId());
                actuacion.setProfesional(profesional);
            }
            em.persist(actuacion);
            if (reparacion != null) {
                reparacion.getActuaciones().add(actuacion);
                reparacion = em.merge(reparacion);
            }
            if (profesional != null) {
                profesional.getActuaciones().add(actuacion);
                profesional = em.merge(profesional);
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

    public void edit(Actuacion actuacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actuacion persistentActuacion = em.find(Actuacion.class, actuacion.getId());
            Reparacion reparacionOld = persistentActuacion.getReparacion();
            Reparacion reparacionNew = actuacion.getReparacion();
            Profesional profesionalOld = persistentActuacion.getProfesional();
            Profesional profesionalNew = actuacion.getProfesional();
            if (reparacionNew != null) {
                reparacionNew = em.getReference(reparacionNew.getClass(), reparacionNew.getId());
                actuacion.setReparacion(reparacionNew);
            }
            if (profesionalNew != null) {
                profesionalNew = em.getReference(profesionalNew.getClass(), profesionalNew.getId());
                actuacion.setProfesional(profesionalNew);
            }
            actuacion = em.merge(actuacion);
            if (reparacionOld != null && !reparacionOld.equals(reparacionNew)) {
                reparacionOld.getActuaciones().remove(actuacion);
                reparacionOld = em.merge(reparacionOld);
            }
            if (reparacionNew != null && !reparacionNew.equals(reparacionOld)) {
                reparacionNew.getActuaciones().add(actuacion);
                reparacionNew = em.merge(reparacionNew);
            }
            if (profesionalOld != null && !profesionalOld.equals(profesionalNew)) {
                profesionalOld.getActuaciones().remove(actuacion);
                profesionalOld = em.merge(profesionalOld);
            }
            if (profesionalNew != null && !profesionalNew.equals(profesionalOld)) {
                profesionalNew.getActuaciones().add(actuacion);
                profesionalNew = em.merge(profesionalNew);
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
                Long id = actuacion.getId();
                if (findActuacion(id) == null) {
                    throw new NonexistentEntityException("The actuacion with id " + id + " no longer exists.");
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
            Actuacion actuacion;
            try {
                actuacion = em.getReference(Actuacion.class, id);
                actuacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actuacion with id " + id + " no longer exists.", enfe);
            }
            Reparacion reparacion = actuacion.getReparacion();
            if (reparacion != null) {
                reparacion.getActuaciones().remove(actuacion);
                reparacion = em.merge(reparacion);
            }
            Profesional profesional = actuacion.getProfesional();
            if (profesional != null) {
                profesional.getActuaciones().remove(actuacion);
                profesional = em.merge(profesional);
            }
            em.remove(actuacion);
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

    public List<Actuacion> findActuacionEntities() {
        return findActuacionEntities(true, -1, -1);
    }

    public List<Actuacion> findActuacionEntities(int maxResults, int firstResult) {
        return findActuacionEntities(false, maxResults, firstResult);
    }

    private List<Actuacion> findActuacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actuacion.class));
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

    public Actuacion findActuacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actuacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getActuacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actuacion> rt = cq.from(Actuacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
