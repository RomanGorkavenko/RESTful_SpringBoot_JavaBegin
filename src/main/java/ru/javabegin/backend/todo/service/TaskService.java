package ru.javabegin.backend.todo.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.javabegin.backend.todo.entity.Task;
import ru.javabegin.backend.todo.repo.TaskRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll(String email) {
        return repository.findByUserEmailOrderByTitleAsc(email);
    }

    public Task add(Task task) {
        return repository.save(task);
    }

    public Task update(Task task) {
        return repository.save(task);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<Task> findByParams(String title, Boolean completed, Long priorityId, Long categoryId, String email,
                                   Date dateFrom, Date dateTo, PageRequest paging) {
        return repository.findByParams(title, completed, priorityId, categoryId, email, dateFrom, dateTo, paging);
    }

    public Task findById(Long id) {
        return repository.findById(id).orElseThrow(); // т.к. возвращается Optional - можно получить объект методом get()
    }

}
