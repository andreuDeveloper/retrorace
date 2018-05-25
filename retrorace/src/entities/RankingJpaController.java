/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Usuario
 */
public class RankingJpaController implements Serializable {

    public RankingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ranking ranking) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ranking);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ranking ranking) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ranking = em.merge(ranking);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ranking.getId();
                if (findRanking(id) == null) {
                    throw new NonexistentEntityException("The ranking with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ranking ranking;
            try {
                ranking = em.getReference(Ranking.class, id);
                ranking.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ranking with id " + id + " no longer exists.", enfe);
            }
            em.remove(ranking);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ranking> findRankingEntities() {
        return findRankingEntities(true, -1, -1);
    }

    public List<Ranking> findRankingEntities(int maxResults, int firstResult) {
        return findRankingEntities(false, maxResults, firstResult);
    }

    private List<Ranking> findRankingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ranking.class));
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

    public Ranking findRanking(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ranking.class, id);
        } finally {
            em.close();
        }
    }

    public int getRankingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ranking> rt = cq.from(Ranking.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Ranking> getGlobalRankingInMap(int idMap){
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Ranking.findByIdMap").setParameter("idMap", idMap).setMaxResults(10).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Ranking> getPersonalRankingInMap(int idMap,String username){
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Ranking.findPersonalRecordInMap").setParameter("idMap", idMap).setParameter("username", username).setMaxResults(10).getResultList();
        } finally {
            em.close();
        }
    }
    
}
