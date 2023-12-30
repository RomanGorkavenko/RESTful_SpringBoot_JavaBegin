package ru.javabegin.backend.todo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javabegin.backend.todo.entity.Category;
import ru.javabegin.backend.todo.repo.CategoryRepository;

import java.util.List;

/**
 * Всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
 * что мало методов или это все можно реализовать сразу в контроллере
 * Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
 */
@Service
// Все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе выполняются несколько SQL запросов
// и возникнет исключение - то все выполненные операции откатятся (Rollback)
@Transactional
public class CategoryService {

    /**
     * Работает встроенный механизм DI из Spring,
     * который при старте приложения подставит в эту переменную нужные класс-реализацию.
     */
    private final CategoryRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Category> findAll(String email) {
        return repository.findByUserEmailOrderByTitleAsc(email);
    }

    public Category add(Category category) {
        // метод save обновляет или создает новый объект, если его не было.
        return repository.save(category);
    }

    public Category update(Category category) {
        // save работает как на добавление, так и на обновление
        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String text, String email) {
        return repository.findByTitle(text, email);
    }
}
