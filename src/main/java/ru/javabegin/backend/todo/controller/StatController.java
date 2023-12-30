package ru.javabegin.backend.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.backend.todo.entity.Stat;
import ru.javabegin.backend.todo.entity.User;
import ru.javabegin.backend.todo.service.StatService;

@RestController
public class StatController {

    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    /**
     * Для статистика всгда получаем только одну строку с id=1 (согласно таблице БД).
     * Можно не использовать ResponseEntity, а просто вернуть коллекцию, код все равно будет 200 ОК
     * @param email {@link User#getEmail()}
     * @return статус запроса и объект {@link Stat}
     */
    @PostMapping("/stat")
    public ResponseEntity<Stat> findByEmail(@RequestBody String email) {
        return ResponseEntity.ok(statService.findStat(email));
    }

}
