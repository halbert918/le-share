package com.le.share.config;

import com.le.share.common.enums.LeResultEnum;
import com.le.share.exception.LeShareException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by yinbohe.
 * Date 2019/8/20
 * Description basePackages限定扫描包，防止swagger无法访问
 */
@RestControllerAdvice(basePackages = "com.le.share.controller")
public class ControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class clazz) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class clazz,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (obj instanceof EntityBody) {
            return obj;
        }

        return new EntityBody(LeResultEnum.EXECUTE_SUCCESS.getErrorCode(), LeResultEnum.EXECUTE_SUCCESS.getMessage(), obj);
    }

    /**
     * 异常处理.
     *
     * @param ex Exception
     * @return ResponseEntity
     */
    @ExceptionHandler( {Exception.class})
    public ResponseEntity<?> errorHandler(Exception ex) {
        if (ex instanceof LeShareException) {
            LeResultEnum resultEnum = ((LeShareException) ex).getResultEnum();
            return new ResponseEntity<>(new EntityBody(resultEnum.getErrorCode(), resultEnum.getMessage()),
                    HttpStatus.OK);
        }

        if (ex instanceof IllegalArgumentException) {
            return new ResponseEntity<>(new EntityBody(LeResultEnum.ILLEGAL_ARGUMENTS.code(), ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            for (final FieldError error : ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()) {
                return new ResponseEntity<>(new EntityBody(LeResultEnum.UN_KNOWN_EXCEPTION.getErrorCode(),
                        error.getDefaultMessage()), HttpStatus.OK);
            }
        }
        ex.printStackTrace();
        return new ResponseEntity<>(new EntityBody(LeResultEnum.UN_KNOWN_EXCEPTION.getErrorCode(),
                ex.getMessage()), HttpStatus.OK);
    }

}