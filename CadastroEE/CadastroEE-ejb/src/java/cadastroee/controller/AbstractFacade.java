/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroee.controller;

import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) { getEntityManager().persist(entity); }

    public void edit(T entity) { getEntityManager().merge(entity); }

    public void remove(T entity) { getEntityManager().remove(getEntityManager().merge(entity)); }

    public T find(Object id) { return getEntityManager().find(entityClass, id); }

    public List<T> findAll() {
        var cb = getEntityManager().getCriteriaBuilder();
        var cq = cb.createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
}