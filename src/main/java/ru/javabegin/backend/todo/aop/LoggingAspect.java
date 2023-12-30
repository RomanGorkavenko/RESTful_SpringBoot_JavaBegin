package ru.javabegin.backend.todo.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log
public class LoggingAspect {

    /**
     * Аспект будет выполняться для всех методов из пакета контроллеров.
     * Аннотация @Around(когда будет выполняться "*"-все (методы) из пакета - "ru.javabegin.backend.todo.controller"
     * "..*" - и всех входящих в него, "(..)" - фильтрация(название метода, его параметры и т.д.) - в этом примере
     * не используется.)
     * {@link MethodSignature} - похоже на механизм reflection для получения информации о методе, поле, классе и т.д.
     * {@link ProceedingJoinPoint#proceed()} - вызывает метод или переходит к следующей рекомендации.
     * @param proceedingJoinPoint - ссылка на текущий метод который выполняется.
     * @return вызываем полученный метод, без возвращения вызова метода, метод не выполнится.
     * @throws Throwable исключение если вызванный процесс выдает что-либо
     */
    @Around("execution(* ru.javabegin.backend.todo.controller..*(..))")
    public Object profileControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // получить информацию о том, какой класс и метод выполняется
        String className = methodSignature
                .getDeclaringType()
                .getSimpleName();
        String methodName = methodSignature.getName();

        log.info("-------- Executing "+ className + "." + methodName + "   ----------- ");

        StopWatch countdown = new StopWatch();

        //  засекаем время
        countdown.start();
        // выполняем сам метод
        Object result = proceedingJoinPoint.proceed();
        countdown.stop();

        log.info("-------- Execution time of " + className + "." + methodName +
                " :: " + countdown.getTotalTimeMillis() + " ms");

        return result;

    }

}
