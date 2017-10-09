/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Ship;
import entities.State;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class StateJpaController implements Serializable {

    public StateJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(State state) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (state.getShipList() == null) {
            state.setShipList(new ArrayList<Ship>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Ship> attachedShipList = new ArrayList<Ship>();
            for (Ship shipListShipToAttach : state.getShipList()) {
                shipListShipToAttach = em.getReference(shipListShipToAttach.getClass(), shipListShipToAttach.getCodBarco());
                attachedShipList.add(shipListShipToAttach);
            }
            state.setShipList(attachedShipList);
            em.persist(state);
            for (Ship shipListShip : state.getShipList()) {
                State oldCodEstadoOfShipListShip = shipListShip.getCodEstado();
                shipListShip.setCodEstado(state);
                shipListShip = em.merge(shipListShip);
                if (oldCodEstadoOfShipListShip != null) {
                    oldCodEstadoOfShipListShip.getShipList().remove(shipListShip);
                    oldCodEstadoOfShipListShip = em.merge(oldCodEstadoOfShipListShip);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findState(state.getCodEstado()) != null) {
                throw new PreexistingEntityException("State " + state + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(State state) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            State persistentState = em.find(State.class, state.getCodEstado());
            List<Ship> shipListOld = persistentState.getShipList();
            List<Ship> shipListNew = state.getShipList();
            List<Ship> attachedShipListNew = new ArrayList<Ship>();
            for (Ship shipListNewShipToAttach : shipListNew) {
                shipListNewShipToAttach = em.getReference(shipListNewShipToAttach.getClass(), shipListNewShipToAttach.getCodBarco());
                attachedShipListNew.add(shipListNewShipToAttach);
            }
            shipListNew = attachedShipListNew;
            state.setShipList(shipListNew);
            state = em.merge(state);
            for (Ship shipListOldShip : shipListOld) {
                if (!shipListNew.contains(shipListOldShip)) {
                    shipListOldShip.setCodEstado(null);
                    shipListOldShip = em.merge(shipListOldShip);
                }
            }
            for (Ship shipListNewShip : shipListNew) {
                if (!shipListOld.contains(shipListNewShip)) {
                    State oldCodEstadoOfShipListNewShip = shipListNewShip.getCodEstado();
                    shipListNewShip.setCodEstado(state);
                    shipListNewShip = em.merge(shipListNewShip);
                    if (oldCodEstadoOfShipListNewShip != null && !oldCodEstadoOfShipListNewShip.equals(state)) {
                        oldCodEstadoOfShipListNewShip.getShipList().remove(shipListNewShip);
                        oldCodEstadoOfShipListNewShip = em.merge(oldCodEstadoOfShipListNewShip);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = state.getCodEstado();
                if (findState(id) == null) {
                    throw new NonexistentEntityException("The state with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            State state;
            try {
                state = em.getReference(State.class, id);
                state.getCodEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The state with id " + id + " no longer exists.", enfe);
            }
            List<Ship> shipList = state.getShipList();
            for (Ship shipListShip : shipList) {
                shipListShip.setCodEstado(null);
                shipListShip = em.merge(shipListShip);
            }
            em.remove(state);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public List<State> findStateEntities() {
        return findStateEntities(true, -1, -1);
    }

    public List<State> findStateEntities(int maxResults, int firstResult) {
        return findStateEntities(false, maxResults, firstResult);
    }

    private List<State> findStateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(State.class));
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

    public State findState(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(State.class, id);
        } finally {
            em.close();
        }
    }

    public int getStateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<State> rt = cq.from(State.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
