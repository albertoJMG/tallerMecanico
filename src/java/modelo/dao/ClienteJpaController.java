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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.RollbackFailureException;
import modelo.entidades.Cliente;

/**
 *
 * @author alberto
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        if (cliente.getVehiculos() == null) {
            cliente.setVehiculos(new ArrayList<Vehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vehiculo> attachedVehiculos = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculosVehiculoToAttach : cliente.getVehiculos()) {
                vehiculosVehiculoToAttach = em.getReference(vehiculosVehiculoToAttach.getClass(), vehiculosVehiculoToAttach.getId());
                attachedVehiculos.add(vehiculosVehiculoToAttach);
            }
            cliente.setVehiculos(attachedVehiculos);
            em.persist(cliente);
            for (Vehiculo vehiculosVehiculo : cliente.getVehiculos()) {
                Cliente oldClienteOfVehiculosVehiculo = vehiculosVehiculo.getCliente();
                vehiculosVehiculo.setCliente(cliente);
                vehiculosVehiculo = em.merge(vehiculosVehiculo);
                if (oldClienteOfVehiculosVehiculo != null) {
                    oldClienteOfVehiculosVehiculo.getVehiculos().remove(vehiculosVehiculo);
                    oldClienteOfVehiculosVehiculo = em.merge(oldClienteOfVehiculosVehiculo);
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

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Vehiculo> vehiculosOld = persistentCliente.getVehiculos();
            List<Vehiculo> vehiculosNew = cliente.getVehiculos();
            List<Vehiculo> attachedVehiculosNew = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculosNewVehiculoToAttach : vehiculosNew) {
                vehiculosNewVehiculoToAttach = em.getReference(vehiculosNewVehiculoToAttach.getClass(), vehiculosNewVehiculoToAttach.getId());
                attachedVehiculosNew.add(vehiculosNewVehiculoToAttach);
            }
            vehiculosNew = attachedVehiculosNew;
            cliente.setVehiculos(vehiculosNew);
            cliente = em.merge(cliente);
            for (Vehiculo vehiculosOldVehiculo : vehiculosOld) {
                if (!vehiculosNew.contains(vehiculosOldVehiculo)) {
                    vehiculosOldVehiculo.setCliente(null);
                    vehiculosOldVehiculo = em.merge(vehiculosOldVehiculo);
                }
            }
            for (Vehiculo vehiculosNewVehiculo : vehiculosNew) {
                if (!vehiculosOld.contains(vehiculosNewVehiculo)) {
                    Cliente oldClienteOfVehiculosNewVehiculo = vehiculosNewVehiculo.getCliente();
                    vehiculosNewVehiculo.setCliente(cliente);
                    vehiculosNewVehiculo = em.merge(vehiculosNewVehiculo);
                    if (oldClienteOfVehiculosNewVehiculo != null && !oldClienteOfVehiculosNewVehiculo.equals(cliente)) {
                        oldClienteOfVehiculosNewVehiculo.getVehiculos().remove(vehiculosNewVehiculo);
                        oldClienteOfVehiculosNewVehiculo = em.merge(oldClienteOfVehiculosNewVehiculo);
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
                Long id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Vehiculo> vehiculos = cliente.getVehiculos();
            for (Vehiculo vehiculosVehiculo : vehiculos) {
                vehiculosVehiculo.setCliente(null);
                vehiculosVehiculo = em.merge(vehiculosVehiculo);
            }
            em.remove(cliente);
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

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
