package vaco.built.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vaco.built.interview.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>  {
}
