package ru.javabegin.backend.todo.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Возможные значения, по которым можно искать задачи + значения сортировки.
 * Такие же названия должны быть у объекта на frontend
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSearchValues {

    //region Поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;
    private String email;
    //endregion

    //region для задания периода по датам
    private Date dateFrom;
    private Date dateTo;
    //endregion

    //region постраничность
    private Integer pageNumber;
    private Integer pageSize;
    //endregion

    //region сортировка
    private String sortColumn;
    private String sortDirection;
    //endregion

}
