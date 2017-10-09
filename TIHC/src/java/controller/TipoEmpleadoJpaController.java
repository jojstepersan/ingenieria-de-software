/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entities.TipoEmpleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Tripulante;
import java.util.ArrayList;
import java.util.List;
import entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jojstepersan
 */
public class TipoEmpleadoJpaController implements Serializable {

    public TipoEmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEmpleado tipoEmpleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoEmpleado.getTripulanteList() == null) {
            tipoEmpleado.setTripulanteList(new ArrayList<Tripulante>());
        }
        if (tipoEmpleado.getUsuarioList() == null) {
            tipoEmpleado.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Tripulante> attachedTripulanteList = new ArrayList<Tripulante>();
            for (Tripulante tripulanteListTripulanteToAttach : tipoEmpleado.getTripulanteList()) {
                tripulanteListTripulanteToAttach = em.getReference(tripulanteListTripulanteToAttach.getClass(), tripulanteListTripulanteToAttach.getCodEmpleado());
                attachedTripulanteList.add(tripulanteListTripulanteToAttach);
            }
            tipoEmpleado.setTripulanteList(attachedTripulanteList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tipoEmpleado.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getCodUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tipoEmpleado.setUsuarioList(attachedUsuarioList);
            em.persist(tipoEmpleado);
            for (Tripulante tripulanteListTripulante : tipoEmpleado.getTripulanteList()) {
                TipoEmpleado oldTipoEmpleadoOfTripulanteListTripulante = tripulanteListTripulante.getTipoEmpleado();
                tripulanteListTripulante.setTipoEmpleado(tipoEmpleado);
                tripulanteListTripulante = em.merge(tripulanteListTripulante);
                if (oldTipoEmpleadoOfTripulanteListTripulante != null) {
                    oldTipoEmpleadoOfTripulanteListTripulante.getTripulanteList().remove(tripulanteListTripulante);
                    oldTipoEmpleadoOfTripulanteListTripulante = em.merge(oldTipoEmpleadoOfTripulanteListTripulante);
                }
            }
            for (Usuario usuarioListUsuario : tipoEmpleado.getUsuarioList()) {
                TipoEmpleado oldCodTipoEmpleadoOfUsuarioListUsuario = usuarioListUsuario.getCodTipoEmpleado();
                usuarioListUsuario.setCodTipoEmpleado(tipoEmpleado);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldCodTipoEmpleadoOfUsuarioListUsuario != null) {
                    oldCodTipoEmpleadoOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldCodTipoEmpleadoOfUsuarioListUsuario = em.merge(oldCodTipoEmpleadoOfUsuarioListUsuario);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoEmpleado(tipoEmpleado.getCodTipoEmpleado()) != null) {
                throw new PreexistingEntityException("TipoEmpleado " + tipoEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEmpleado tipoEmpleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoEmpleado persistentTipoEmpleado = em.find(TipoEmpleado.class, tipoEmpleado.getCodTipoEmpleado());
            List<Tripulante> tripulanteListOld = persistentTipoEmpleado.getTripulanteList();
            List<Tripulante> tripulanteListNew = tipoEmpleado.getTripulanteList();
            List<Usuario> usuarioListOld = persistentTipoEmpleado.getUsuarioList();
            List<Usuario> usuarioListNew = tipoEmpleado.getUsuarioList();
            List<Tripulante> attachedTripulanteListNew = new ArrayList<Tripulante>();
            for (Tripulante tripulanteListNewTripulanteToAttach : tripulanteListNew) {
                tripulanteListNewTripulanteToAttach = em.getReference(tripulanteListNewTripulanteToAttach.getClass(), tripulanteListNewTripulanteToAttach.getCodEmpleado());
                attachedTripulanteListNew.add(tripulanteListNewTripulanteToAttach);
            }
            tripulanteListNew = attachedTripulanteListNew;
            tipoEmpleado.setTripulanteList(tripulanteListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getCodUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            tipoEmpleado.setUsuarioList(usuarioListNew);
            tipoEmpleado = em.merge(tipoEmpleado);
            for (Tripulante tripulanteListOldTripulante : tripulanteListOld) {
                if (!tripulanteListNew.contains(tripulanteListOldTripulante)) {
                    tripulanteListOldTripulante.setTipoEmpleado(null);
                    tripulanteListOldTripulante = em.merge(tripulanteListOldTripulante);
                }
            }
            for (Tripulante tripulanteListNewTripulante : tripulanteListNew) {
                if (!tripulanteListOld.contains(tripulanteListNewTripulante)) {
                    TipoEmpleado oldTipoEmpleadoOfTripulanteListNewTripulante = tripulanteListNewTripulante.getTipoEmpleado();
                    tripulanteListNewTripulante.setTipoEmpleado(tipoEmpleado);
                    tripulanteListNewTripulante = em.merge(tripulanteListNewTripulante);
                    if (oldTipoEmpleadoOfTripulanteListNewTripulante != null && !oldTipoEmpleadoOfTripulanteListNewTripulante.equals(tipoEmpleado)) {
                        oldTipoEmpleadoOfTripulanteListNewTripulante.getTripulanteList().remove(tripulanteListNewTripulante);
                        oldTipoEmpleadoOfTripulanteListNewTripulante = em.merge(oldTipoEmpleadoOfTripulanteListNewTripulante);
                    }
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setCodTipoEmpleado(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    TipoEmpleado oldCodTipoEmpleadoOfUsuarioListNewUsuario = usuarioListNewUsuario.getCodTipoEmpleado();
                    usuarioListNewUsuario.setCodTipoEmpleado(tipoEmpleado);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldCodTipoEmpleadoOfUsuarioListNewUsuario != null && !oldCodTipoEmpleadoOfUsuarioListNewUsuario.equals(tipoEmpleado)) {
                        oldCodTipoEmpleadoOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldCodTipoEmpleadoOfUsuarioListNewUsuario = em.merge(oldCodTipoEmpleadoOfUsuarioListNewUsuario);
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
                Integer id = tipoEmpleado.getCodTipoEmpleado();
                if (findTipoEmpleado(id) == null) {
                    throw new NonexistentEntityException("The tipoEmpleado with id " + id + " no longer exists.");
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
            TipoEmpleado tipoEmpleado;
            try {
                tipoEmpleado = em.getReference(TipoEmpleado.class, id);
                tipoEmpleado.getCodTipoEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEmpleado with id " + id + " no longer exists.", enfe);
            }
            List<Tripulante> tripulanteList = tipoEmpleado.getTripulanteList();
            for (Tripulante tripulanteListTripulante : tripulanteList) {
                tripulanteListTripulante.setTipoEmpleado(null);
                tripulanteListTripulante = em.merge(tripulanteListTripulante);
            }
            List<Usuario> usuarioList = tipoEmpleado.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setCodTipoEmpleado(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(tipoEmpleado);
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

    public List<TipoEmpleado> findTipoEmpleadoEntities() {
        return findTipoEmpleadoEntities(true, -1, -1);
    }

    public List<TipoEmpleado> findTipoEmpleadoEntities(int maxResults, int firstResult) {
        return findTipoEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<TipoEmpleado> findTipoEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEmpleado.class));
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

    public TipoEmpleado findTipoEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEmpleado> rt = cq.from(TipoEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
