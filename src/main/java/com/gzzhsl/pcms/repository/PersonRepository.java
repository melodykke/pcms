package com.gzzhsl.pcms.repository;


import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.entity.WechatAuth;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PersonRepository extends JpaRepository<PersonInfo, String> {
    PersonInfo findByUserInfo(UserInfo userInfo);
    PersonInfo findByPersonId(String personId);
    PersonInfo findByWechatAuth(WechatAuth wechatAuth);

    @Modifying
    @Transactional
    @Query("delete from PersonInfo pi where pi.personId = :personId")
    int deleteByPersonId(@Param(value = "personId") String personId);
}
