package ru.morou.korekor.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.morou.korekor.persist.model.User;


/**
 * JpaRepository расширяет PagingAndSortingRepository который, в свою очередь, расширяет CrudRepository.
 * 1. CrudRepository основном предоставляет функции CRUD.
 * 2. PagingAndSortingRepository предоставляет методы для разбиения на страницы и сортировки записей.
 * 3. JpaRepository предоставляет некоторые связанные с JPA методы, такие как очистка контекста постоянства и удаление записей в пакете.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @Querty (данные; запрос; [заголовки])
     * @param userName
     * @return
     */
    @Query("from User u join fetch u.roles r where u.userName = :userName")
    User findOneByUserName(String userName);
}
