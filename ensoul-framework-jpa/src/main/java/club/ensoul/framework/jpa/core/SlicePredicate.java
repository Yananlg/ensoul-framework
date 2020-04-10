package club.ensoul.framework.jpa.core;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@FunctionalInterface
public interface SlicePredicate<T> {
    
    List<Predicate> slice(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder);
    
}
