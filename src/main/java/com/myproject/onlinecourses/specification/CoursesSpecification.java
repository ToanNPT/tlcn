package com.myproject.onlinecourses.specification;

import com.myproject.onlinecourses.dto.SearchCriteria;
import com.myproject.onlinecourses.entity.Category;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.entity.Course_;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.CategoryRepository;
import com.myproject.onlinecourses.utils.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoursesSpecification implements Specification<Course> {
    private List<SearchCriteria> list;

    public CoursesSpecification(List<SearchCriteria> list){
        this.list = list;
    }


    public CategoryRepository categoryRepo;

    public CoursesSpecification(CategoryRepository categoryRepo){
        this.list = new ArrayList<>();
        this.categoryRepo  =categoryRepo;
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();
        for(SearchCriteria criteria : list){
            if(criteria.getOperation().equals(SearchOperation.EQUAL)){
                if(criteria.getKey().equals(Course_.CATEGORY)){
                    String[] arr = criteria.getValue().toString().split(",");
                    for(String str : arr){
                        Optional<Category> c = categoryRepo.findById(str.trim().toLowerCase());
                        if(!c.isPresent())
                            throw new NotFoundException("category doesn't not match");
                        orPredicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), c.get()));
                    }
                }
                else
                    predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
            }
            else if(criteria.getOperation().equals(SearchOperation.GREATER_THAN)){

                predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if(criteria.getOperation().equals(SearchOperation.LESS_THAN)){
                predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if(criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if(criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            }
            else if(criteria.getOperation().equals(SearchOperation.NOT_EQUAL)){
                predicates.add(criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
            }
            else if(criteria.getOperation().equals(SearchOperation.MATCH)){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%" ));
            }
            else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase()));
            }
            else if(criteria.getOperation().equals(SearchOperation.IN)){
                predicates.add(criteriaBuilder.in(root.get(criteria.getKey())).value(criteria.getValue()));
            }
            else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                predicates.add(criteriaBuilder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            }
            else if(criteria.getOperation().equals(SearchOperation.BETWEEN)){
                String[] arr = criteria.getValue().toString().split(",");
                double min = Double.parseDouble(arr[0]);
                double max = Double.parseDouble(arr[1]);
                predicates.add(criteriaBuilder.between(root.get(criteria.getKey()), min, max));
            }
        }
        if(orPredicates.size() != 0){
            Predicate or = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
            predicates.add(or);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
