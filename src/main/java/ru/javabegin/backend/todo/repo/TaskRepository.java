package ru.javabegin.backend.todo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.backend.todo.entity.Task;

import java.util.Date;
import java.util.List;

/**
 * Принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Учитываем, что параметр может быть null или пустым.
     * (:param IS NULL OR table.field=:param) Ищем по всем переданным параметрам (пустые параметры учитываться не будут).
     * (table.field.field=:param) Показываем задачи только определенного пользователя, а не все
     * @param title название задачи
     * @param completed завершена задача или нет
     * @param priorityId универсальный идентификатор приоритета задачи
     * @param categoryId универсальный идентификатор категории задачи
     * @param email почта пользователя
     * @param dateFrom дата начала периода поиска
     * @param dateTo дата окончания периода поиска
     * @param pageable настройки: размер страницы, сколько данных на странице показать, сколько всего элементов найдено
     * @return список задач постранично
     */
    @Query("""
            SELECT t FROM Task t WHERE
            (:title IS NULL OR :title='' OR LOWER(t.title) LIKE LOWER(concat('%', :title, '%'))) AND
            (:completed IS NULL OR t.completed=:completed) AND
            (:priorityId IS NULL OR t.priority.id=:priorityId) AND
            (:categoryId IS NULL OR t.category.id=:categoryId) AND
            ((cast(:dateFrom AS TIMESTAMP) IS NULL OR t.taskDate>=:dateFrom) AND
            (cast(:dateTo AS TIMESTAMP ) IS NULL  OR t.taskDate<=:dateTo)) AND
            (t.user.email=:email)
            """)
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Boolean completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId,
                            @Param("email") String email,
                            @Param("dateFrom") Date dateFrom,
                            @Param("dateTo") Date dateTo,
                            Pageable pageable
                            );

    /**
     * Поиск всех задач конкретного пользователя
     * @param email почта пользователя
     * @return список всех задач пользователя.
     */
    List<Task> findByUserEmailOrderByTitleAsc(String email);

}
