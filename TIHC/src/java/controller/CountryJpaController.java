/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entities.Country;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Port;
import java.util.ArrayList;
import java.util.List;
import entities.Load;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class CountryJpaController implements Serializable {

    public CountryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Country country) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (country.getPortList() == null) {
            country.setPortList(new ArrayList<Port>());
        }
        if (country.getLoadList() == null) {
            country.setLoadList(new ArrayList<Load>());
        }
        if (country.getLoadList1() == null) {
            country.setLoadList1(new ArrayList<Load>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Port> attachedPortList = new ArrayList<Port>();
            for (Port portListPortToAttach : country.getPortList()) {
                portListPortToAttach = em.getReference(portListPortToAttach.getClass(), portListPortToAttach.getCodPuerto());
                attachedPortList.add(portListPortToAttach);
            }
            country.setPortList(attachedPortList);
            List<Load> attachedLoadList = new ArrayList<Load>();
            for (Load loadListLoadToAttach : country.getLoadList()) {
                loadListLoadToAttach = em.getReference(loadListLoadToAttach.getClass(), loadListLoadToAttach.getCodCarga());
                attachedLoadList.add(loadListLoadToAttach);
            }
            country.setLoadList(attachedLoadList);
            List<Load> attachedLoadList1 = new ArrayList<Load>();
            for (Load loadList1LoadToAttach : country.getLoadList1()) {
                loadList1LoadToAttach = em.getReference(loadList1LoadToAttach.getClass(), loadList1LoadToAttach.getCodCarga());
                attachedLoadList1.add(loadList1LoadToAttach);
            }
            country.setLoadList1(attachedLoadList1);
            em.persist(country);
            for (Port portListPort : country.getPortList()) {
                Country oldCodPaisOfPortListPort = portListPort.getCodPais();
                portListPort.setCodPais(country);
                portListPort = em.merge(portListPort);
                if (oldCodPaisOfPortListPort != null) {
                    oldCodPaisOfPortListPort.getPortList().remove(portListPort);
                    oldCodPaisOfPortListPort = em.merge(oldCodPaisOfPortListPort);
                }
            }
            for (Load loadListLoad : country.getLoadList()) {
                Country oldDestinoOfLoadListLoad = loadListLoad.getDestino();
                loadListLoad.setDestino(country);
                loadListLoad = em.merge(loadListLoad);
                if (oldDestinoOfLoadListLoad != null) {
                    oldDestinoOfLoadListLoad.getLoadList().remove(loadListLoad);
                    oldDestinoOfLoadListLoad = em.merge(oldDestinoOfLoadListLoad);
                }
            }
            for (Load loadList1Load : country.getLoadList1()) {
                Country oldOrigenOfLoadList1Load = loadList1Load.getOrigen();
                loadList1Load.setOrigen(country);
                loadList1Load = em.merge(loadList1Load);
                if (oldOrigenOfLoadList1Load != null) {
                    oldOrigenOfLoadList1Load.getLoadList1().remove(loadList1Load);
                    oldOrigenOfLoadList1Load = em.merge(oldOrigenOfLoadList1Load);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCountry(country.getCodPais()) != null) {
                throw new PreexistingEntityException("Country " + country + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Country country) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Country persistentCountry = em.find(Country.class, country.getCodPais());
            List<Port> portListOld = persistentCountry.getPortList();
            List<Port> portListNew = country.getPortList();
            List<Load> loadListOld = persistentCountry.getLoadList();
            List<Load> loadListNew = country.getLoadList();
            List<Load> loadList1Old = persistentCountry.getLoadList1();
            List<Load> loadList1New = country.getLoadList1();
            List<Port> attachedPortListNew = new ArrayList<Port>();
            for (Port portListNewPortToAttach : portListNew) {
                portListNewPortToAttach = em.getReference(portListNewPortToAttach.getClass(), portListNewPortToAttach.getCodPuerto());
                attachedPortListNew.add(portListNewPortToAttach);
            }
            portListNew = attachedPortListNew;
            country.setPortList(portListNew);
            List<Load> attachedLoadListNew = new ArrayList<Load>();
            for (Load loadListNewLoadToAttach : loadListNew) {
                loadListNewLoadToAttach = em.getReference(loadListNewLoadToAttach.getClass(), loadListNewLoadToAttach.getCodCarga());
                attachedLoadListNew.add(loadListNewLoadToAttach);
            }
            loadListNew = attachedLoadListNew;
            country.setLoadList(loadListNew);
            List<Load> attachedLoadList1New = new ArrayList<Load>();
            for (Load loadList1NewLoadToAttach : loadList1New) {
                loadList1NewLoadToAttach = em.getReference(loadList1NewLoadToAttach.getClass(), loadList1NewLoadToAttach.getCodCarga());
                attachedLoadList1New.add(loadList1NewLoadToAttach);
            }
            loadList1New = attachedLoadList1New;
            country.setLoadList1(loadList1New);
            country = em.merge(country);
            for (Port portListOldPort : portListOld) {
                if (!portListNew.contains(portListOldPort)) {
                    portListOldPort.setCodPais(null);
                    portListOldPort = em.merge(portListOldPort);
                }
            }
            for (Port portListNewPort : portListNew) {
                if (!portListOld.contains(portListNewPort)) {
                    Country oldCodPaisOfPortListNewPort = portListNewPort.getCodPais();
                    portListNewPort.setCodPais(country);
                    portListNewPort = em.merge(portListNewPort);
                    if (oldCodPaisOfPortListNewPort != null && !oldCodPaisOfPortListNewPort.equals(country)) {
                        oldCodPaisOfPortListNewPort.getPortList().remove(portListNewPort);
                        oldCodPaisOfPortListNewPort = em.merge(oldCodPaisOfPortListNewPort);
                    }
                }
            }
            for (Load loadListOldLoad : loadListOld) {
                if (!loadListNew.contains(loadListOldLoad)) {
                    loadListOldLoad.setDestino(null);
                    loadListOldLoad = em.merge(loadListOldLoad);
                }
            }
            for (Load loadListNewLoad : loadListNew) {
                if (!loadListOld.contains(loadListNewLoad)) {
                    Country oldDestinoOfLoadListNewLoad = loadListNewLoad.getDestino();
                    loadListNewLoad.setDestino(country);
                    loadListNewLoad = em.merge(loadListNewLoad);
                    if (oldDestinoOfLoadListNewLoad != null && !oldDestinoOfLoadListNewLoad.equals(country)) {
                        oldDestinoOfLoadListNewLoad.getLoadList().remove(loadListNewLoad);
                        oldDestinoOfLoadListNewLoad = em.merge(oldDestinoOfLoadListNewLoad);
                    }
                }
            }
            for (Load loadList1OldLoad : loadList1Old) {
                if (!loadList1New.contains(loadList1OldLoad)) {
                    loadList1OldLoad.setOrigen(null);
                    loadList1OldLoad = em.merge(loadList1OldLoad);
                }
            }
            for (Load loadList1NewLoad : loadList1New) {
                if (!loadList1Old.contains(loadList1NewLoad)) {
                    Country oldOrigenOfLoadList1NewLoad = loadList1NewLoad.getOrigen();
                    loadList1NewLoad.setOrigen(country);
                    loadList1NewLoad = em.merge(loadList1NewLoad);
                    if (oldOrigenOfLoadList1NewLoad != null && !oldOrigenOfLoadList1NewLoad.equals(country)) {
                        oldOrigenOfLoadList1NewLoad.getLoadList1().remove(loadList1NewLoad);
                        oldOrigenOfLoadList1NewLoad = em.merge(oldOrigenOfLoadList1NewLoad);
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
                Integer id = country.getCodPais();
                if (findCountry(id) == null) {
                    throw new NonexistentEntityException("The country with id " + id + " no longer exists.");
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
            Country country;
            try {
                country = em.getReference(Country.class, id);
                country.getCodPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The country with id " + id + " no longer exists.", enfe);
            }
            List<Port> portList = country.getPortList();
            for (Port portListPort : portList) {
                portListPort.setCodPais(null);
                portListPort = em.merge(portListPort);
            }
            List<Load> loadList = country.getLoadList();
            for (Load loadListLoad : loadList) {
                loadListLoad.setDestino(null);
                loadListLoad = em.merge(loadListLoad);
            }
            List<Load> loadList1 = country.getLoadList1();
            for (Load loadList1Load : loadList1) {
                loadList1Load.setOrigen(null);
                loadList1Load = em.merge(loadList1Load);
            }
            em.remove(country);
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

    public List<Country> findCountryEntities() {
        return findCountryEntities(true, -1, -1);
    }

    public List<Country> findCountryEntities(int maxResults, int firstResult) {
        return findCountryEntities(false, maxResults, firstResult);
    }

    private List<Country> findCountryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Country.class));
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

    public Country findCountry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Country.class, id);
        } finally {
            em.close();
        }
    }

    public int getCountryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Country> rt = cq.from(Country.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
