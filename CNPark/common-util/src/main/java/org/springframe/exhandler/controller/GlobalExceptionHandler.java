package org.springframe.exhandler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframe.constans.HttpStatus;
import org.springframe.exhandler.exception.BaseException;
import org.springframe.util.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常处理
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    /*@ExceptionHandler(value = BaseException.class)
    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        return e.getMessage();
    }*/

    @ExceptionHandler
    public ResponseEntity defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());

        if (e instanceof IllegalArgumentException || e instanceof MethodArgumentTypeMismatchException){
            //400 非法请求参数
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        } else if (e instanceof AccessDeniedException){
            //401
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        } else if (e instanceof NoHandlerFoundException){
            //404
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } else if (e instanceof BaseException){
            //服务器异常
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (logger.isErrorEnabled()){
            logger.error("系统异常! {}", e);
        }
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
