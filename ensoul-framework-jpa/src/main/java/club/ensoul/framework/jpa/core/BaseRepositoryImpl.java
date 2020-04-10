package club.ensoul.framework.jpa.core;

import club.ensoul.framework.core.domain.IEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Slf4j
@NoRepositoryBean
public class BaseRepositoryImpl<T extends IEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    
    private EntityManager entityManager;
    private JpaEntityInformation<T, ?> entityInformation;
    private final PersistenceProvider provider;
    
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }
    
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityManager = em;
        this.entityInformation = entityInformation;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }
    
}
