package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class GenericDao<E> implements AutoCloseable {

    private static final EntityManagerFactory emf;

    static {
        try {
            emf=Persistence.createEntityManagerFactory("gerenciador-usuarios");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar o EntityManagerFactory: " + e.getMessage(), e);
        }
    }

    private final EntityManager em;
    private final Class<E> type;

    public GenericDao(Class<E> entidade) {
        this.type=entidade;
        em=emf.createEntityManager();
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public void begin() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    public void end() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    public void create(E entity) {
        em.persist(entity);
    }

    public void update(E entity) {
        em.merge(entity);
    }

    public void delete(long id) {
        E entidade=findById(id);
        if (entidade != null) {
            em.remove(em.contains(entidade) ? entidade : em.merge(entidade));
        }
    }

    public E findById(Object id) {
        return em.find(type, id);
    }

    public List findAll() {
        Query query=em.createQuery("FROM " + type.getSimpleName());
        return query.getResultList();
    }
}
