package com.gzzhsl.pcms.repository;


import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonInfo, String> {
    PersonInfo findByUserInfo(UserInfo userInfo);
    PersonInfo findByPersonId(String personId);
}
