package ru.maxruazan.springboot.website.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.maxruazan.springboot.website.models.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
     Post findById(long id);
}
