package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface WeChatLoginService {
    ResultVO doWeChatBinding(String openId, String username, String password);
}
