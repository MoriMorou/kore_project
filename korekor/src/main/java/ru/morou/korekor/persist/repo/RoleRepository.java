package ru.morou.korekor.persist.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.morou.korekor.persist.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("from Role r where name = :name")
    Role findOneByName(String name);
}
