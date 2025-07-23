package com.mafei.laboratory.commons.exception;

import com.mafei.laboratory.commons.utils.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author wutangsheng
 * @create 2021-02-10 21:12
 * @info 统一全局异常，出处 <a herf="https://gitee.com/elunez/eladmin">eladmin</a>
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {
    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleException(Throwable e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * 处理token异常
     *
     * @return
     */
    public ResponseEntity<ApiError> noPermissionException() {
        return null;
    }


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getStatus(), e.getMessage()));
    }

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public ResponseEntity<ApiError> entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        return buildResponseEntity(ApiError.error(NOT_FOUND.value(), e.getMessage()));
    }


    /**
     * 统一封装返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
