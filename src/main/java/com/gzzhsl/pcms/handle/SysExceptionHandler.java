package com.gzzhsl.pcms.handle;


import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class SysExceptionHandler {

    @ExceptionHandler({SysException.class})
    @ResponseBody
    public ResultVO sysExceptionHandle(Exception e){
        if(e instanceof SysException){
            log.info("【系统异常】" + e);
            return ResultUtil.failed(((SysException)e).getCode(), e.getMessage());
        } else{
            log.info("【未知异常】" + e);
            return ResultUtil.failed();
        }
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public ResultVO unauthrizedHandle(Exception e) {
        if (e instanceof UnauthorizedException) {
            log.info("【未授权异常】" + e);
            return ResultUtil.failed(SysEnum.NO_AUTHORIZATION_ERROR);
        } else {
            log.info("【未授权引起的未知异常】" + e);
            return ResultUtil.failed();
        }
    }
}
