package ru.maxruazan.springboot.website.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxruazan.springboot.website.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
