package ru.javabegin.backend.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.backend.todo.entity.Category;

import java.util.List;

/**
 * Вы можете уже сразу использовать все методы CRUD (Create, Read, Update, Delete)
 * принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Поиск категорий пользователя (по названию)
     * @param email userEmail
     * @return список категорий
     */
    List<Category> findByUserEmailOrderByTitleAsc(String email);

    @Query("""
            SELECT c FROM Category c WHERE
            (:title IS NULL OR :title=''
            OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND c.user.email=:email
            ORDER BY c.title ASC
            """)
    List<Category> findByTitle(@Param("title") String title, @Param("email") String email);
}
