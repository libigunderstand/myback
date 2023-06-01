package com.rzon.myback.error;

import com.rzon.myback.model.ResponseData;
import com.rzon.myback.model.Result;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    //    处理自定义异常
    @ExceptionHandler(value = BaseException.class)
    public Result baseExceptionHandler(BaseException e) {
        return ResponseData.error(e.getMessage(), e.getCode());
    }

    //    处理空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public Result exceptionHandler(NullPointerException e) {
        return ResponseData.error(e.getMessage(), BaseErrorEnum.BODY_NOT_MATCH.getCode());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException e) {
        StringBuilder msg = new StringBuilder();
        if(e != null){
            BindingResult br = e.getBindingResult();
            for(ObjectError err:br.getAllErrors()) {
                msg.append(err.getDefaultMessage()).append("！");
            }
        }
        return ResponseData.error(String.valueOf(msg), BaseErrorEnum.INTERNAL_SERVER_ERROR.getCode());
    }

    @ExceptionHandler(value = BindException.class)
    public Result exceptionHandler(BindException e) {
        StringBuilder msg = new StringBuilder();
        if(e != null){
            BindingResult br = e.getBindingResult();
            for(ObjectError err:br.getAllErrors()) {
                msg.append(err.getDefaultMessage()).append("！");
            }
        }
        return ResponseData.error(String.valueOf(msg), BaseErrorEnum.INTERNAL_SERVER_ERROR.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        return ResponseData.error(e.getMessage(), BaseErrorEnum.REQUEST_METHOD_SUPPORT_ERROR.getCode());
    }
}
