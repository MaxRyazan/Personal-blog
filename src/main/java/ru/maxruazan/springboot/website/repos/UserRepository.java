package ru.maxruazan.springboot.website.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxruazan.springboot.website.models.MyUser;


@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByEmail(String email);


    @Override
    <S extends MyUser> S saveAndFlush(S entity);
}
