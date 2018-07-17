package com.springnews.handle;

import com.springnews.exception.NewsException;
import com.springnews.exception.UserException;
import com.springnews.utils.ResultUtil;
import com.springnews.utils.UnifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public UnifyResponse handle(Exception e){
        if (e instanceof UserException) {
            UserException userException = (UserException) e;
            return ResultUtil.error(userException.getCode(), userException.getMessage());
        }
        else if(e instanceof NewsException){
            NewsException newsException = (NewsException) e;
            return ResultUtil.error(newsException.getCode(), newsException.getMessage());
        }
        else {
            logger.error("[ 系统异常 ] {} ", e);
            return ResultUtil.error(999, e.getMessage());
        }
    }

}
