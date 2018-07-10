package com.springnews.handle;

import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public UnifyResponse handle(Exception e){
        return ResultUtil.error(100, e.getMessage());
    }

}
