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
import entities.State;
import entities.crewman;
import java.util.ArrayList;
import java.util.List;
import entities.Load;
import entities.Ship;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class ShipJpaController implements Serializable {

    public ShipJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ship ship) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (ship.getCrewmanList() == null) {
            ship.setCrewmanList(new ArrayList<crewman>());
        }
        if (ship.getLoadList() == null) {
            ship.setLoadList(new ArrayList<Load>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            State codEstado = ship.getCodEstado();
            if (codEstado != null) {
                codEstado = em.getReference(codEstado.getClass(), codEstado.getCodEstado());
                ship.setCodEstado(codEstado);
            }
            List<crewman> attachedCrewmanList = new ArrayList<crewman>();
            for (crewman crewmanListcrewmanToAttach : ship.getCrewmanList()) {
                crewmanListcrewmanToAttach = em.getReference(crewmanListcrewmanToAttach.getClass(), crewmanListcrewmanToAttach.getCodEmpleado());
                attachedCrewmanList.add(crewmanListcrewmanToAttach);
            }
            ship.setCrewmanList(attachedCrewmanList);
            List<Load> attachedLoadList = new ArrayList<Load>();
            for (Load loadListLoadToAttach : ship.getLoadList()) {
                loadListLoadToAttach = em.getReference(loadListLoadToAttach.getClass(), loadListLoadToAttach.getCodCarga());
                attachedLoadList.add(loadListLoadToAttach);
            }
            ship.setLoadList(attachedLoadList);
            em.persist(ship);
            if (codEstado != null) {
                codEstado.getShipList().add(ship);
                codEstado = em.merge(codEstado);
            }
            for (crewman crewmanListcrewman : ship.getCrewmanList()) {
                Ship oldCodBarcoOfCrewmanListcrewman = crewmanListcrewman.getCodBarco();
                crewmanListcrewman.setCodBarco(ship);
                crewmanListcrewman = em.merge(crewmanListcrewman);
                if (oldCodBarcoOfCrewmanListcrewman != null) {
                    oldCodBarcoOfCrewmanListcrewman.getCrewmanList().remove(crewmanListcrewman);
                    oldCodBarcoOfCrewmanListcrewman = em.merge(oldCodBarcoOfCrewmanListcrewman);
                }
            }
            for (Load loadListLoad : ship.getLoadList()) {
                Ship oldCodBarcoOfLoadListLoad = loadListLoad.getCodBarco();
                loadListLoad.setCodBarco(ship);
                loadListLoad = em.merge(loadListLoad);
                if (oldCodBarcoOfLoadListLoad != null) {
                    oldCodBarcoOfLoadListLoad.getLoadList().remove(loadListLoad);
                    oldCodBarcoOfLoadListLoad = em.merge(oldCodBarcoOfLoadListLoad);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findShip(ship.getCodBarco()) != null) {
                throw new PreexistingEntityException("Ship " + ship + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ship ship) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ship persistentShip = em.find(Ship.class, ship.getCodBarco());
            State codEstadoOld = persistentShip.getCodEstado();
            State codEstadoNew = ship.getCodEstado();
            List<crewman> crewmanListOld = persistentShip.getCrewmanList();
            List<crewman> crewmanListNew = ship.getCrewmanList();
            List<Load> loadListOld = persistentShip.getLoadList();
            List<Load> loadListNew = ship.getLoadList();
            if (codEstadoNew != null) {
                codEstadoNew = em.getReference(codEstadoNew.getClass(), codEstadoNew.getCodEstado());
                ship.setCodEstado(codEstadoNew);
            }
            List<crewman> attachedCrewmanListNew = new ArrayList<crewman>();
            for (crewman crewmanListNewcrewmanToAttach : crewmanListNew) {
                crewmanListNewcrewmanToAttach = em.getReference(crewmanListNewcrewmanToAttach.getClass(), crewmanListNewcrewmanToAttach.getCodEmpleado());
                attachedCrewmanListNew.add(crewmanListNewcrewmanToAttach);
            }
            crewmanListNew = attachedCrewmanListNew;
            ship.setCrewmanList(crewmanListNew);
            List<Load> attachedLoadListNew = new ArrayList<Load>();
            for (Load loadListNewLoadToAttach : loadListNew) {
                loadListNewLoadToAttach = em.getReference(loadListNewLoadToAttach.getClass(), loadListNewLoadToAttach.getCodCarga());
                attachedLoadListNew.add(loadListNewLoadToAttach);
            }
            loadListNew = attachedLoadListNew;
            ship.setLoadList(loadListNew);
            ship = em.merge(ship);
            if (codEstadoOld != null && !codEstadoOld.equals(codEstadoNew)) {
                codEstadoOld.getShipList().remove(ship);
                codEstadoOld = em.merge(codEstadoOld);
            }
            if (codEstadoNew != null && !codEstadoNew.equals(codEstadoOld)) {
                codEstadoNew.getShipList().add(ship);
                codEstadoNew = em.merge(codEstadoNew);
            }
            for (crewman crewmanListOldcrewman : crewmanListOld) {
                if (!crewmanListNew.contains(crewmanListOldcrewman)) {
                    crewmanListOldcrewman.setCodBarco(null);
                    crewmanListOldcrewman = em.merge(crewmanListOldcrewman);
                }
            }
            for (crewman crewmanListNewcrewman : crewmanListNew) {
                if (!crewmanListOld.contains(crewmanListNewcrewman)) {
                    Ship oldCodBarcoOfCrewmanListNewcrewman = crewmanListNewcrewman.getCodBarco();
                    crewmanListNewcrewman.setCodBarco(ship);
                    crewmanListNewcrewman = em.merge(crewmanListNewcrewman);
                    if (oldCodBarcoOfCrewmanListNewcrewman != null && !oldCodBarcoOfCrewmanListNewcrewman.equals(ship)) {
                        oldCodBarcoOfCrewmanListNewcrewman.getCrewmanList().remove(crewmanListNewcrewman);
                        oldCodBarcoOfCrewmanListNewcrewman = em.merge(oldCodBarcoOfCrewmanListNewcrewman);
                    }
                }
            }
            for (Load loadListOldLoad : loadListOld) {
                if (!loadListNew.contains(loadListOldLoad)) {
                    loadListOldLoad.setCodBarco(null);
                    loadListOldLoad = em.merge(loadListOldLoad);
                }
            }
            for (Load loadListNewLoad : loadListNew) {
                if (!loadListOld.contains(loadListNewLoad)) {
                    Ship oldCodBarcoOfLoadListNewLoad = loadListNewLoad.getCodBarco();
                    loadListNewLoad.setCodBarco(ship);
                    loadListNewLoad = em.merge(loadListNewLoad);
                    if (oldCodBarcoOfLoadListNewLoad != null && !oldCodBarcoOfLoadListNewLoad.equals(ship)) {
                        oldCodBarcoOfLoadListNewLoad.getLoadList().remove(loadListNewLoad);
                        oldCodBarcoOfLoadListNewLoad = em.merge(oldCodBarcoOfLoadListNewLoad);
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
                Integer id = ship.getCodBarco();
                if (findShip(id) == null) {
                    throw new NonexistentEntityException("The ship with id " + id + " no longer exists.");
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
            Ship ship;
            try {
                ship = em.getReference(Ship.class, id);
                ship.getCodBarco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ship with id " + id + " no longer exists.", enfe);
            }
            State codEstado = ship.getCodEstado();
            if (codEstado != null) {
                codEstado.getShipList().remove(ship);
                codEstado = em.merge(codEstado);
            }
            List<crewman> crewmanList = ship.getCrewmanList();
            for (crewman crewmanListcrewman : crewmanList) {
                crewmanListcrewman.setCodBarco(null);
                crewmanListcrewman = em.merge(crewmanListcrewman);
            }
            List<Load> loadList = ship.getLoadList();
            for (Load loadListLoad : loadList) {
                loadListLoad.setCodBarco(null);
                loadListLoad = em.merge(loadListLoad);
            }
            em.remove(ship);
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

    public List<Ship> findShipEntities() {
        return findShipEntities(true, -1, -1);
    }

    public List<Ship> findShipEntities(int maxResults, int firstResult) {
        return findShipEntities(false, maxResults, firstResult);
    }

    private List<Ship> findShipEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ship.class));
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

    public Ship findShip(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ship.class, id);
        } finally {
            em.close();
        }
    }

    public int getShipCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ship> rt = cq.from(Ship.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
