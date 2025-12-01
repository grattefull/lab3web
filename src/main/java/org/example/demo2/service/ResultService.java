package org.example.demo2.service;

import org.example.demo2.entity.ResultEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ResultService {

    @PersistenceContext(unitName = "labPU")
    private EntityManager em;

    public void save(ResultEntity e) {
        em.persist(e);
    }

    public List<ResultEntity> findBySession(String sessionId) {
        return em.createQuery("select r from ResultEntity r " + "where r.sessionId = :sid order by r.id desc", ResultEntity.class)
                .setParameter("sid", sessionId)
                .setMaxResults(200)
                .getResultList();
    }

    public void deleteBySession(String sessionId) {
        em.createQuery("delete from ResultEntity r where r.sessionId = :sid")
                .setParameter("sid", sessionId)
                .executeUpdate();
    }
}
