package com.ssafy.la.mail.model.dao;

import com.ssafy.la.mail.model.dto.MailTokenVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailMapper {

    void insertToken(MailTokenVo mailTokenVo);
    
    String verifyToken(String verifytoken);

    String verifyToken(String verifytoken);

    void deleteToken(String verifytoken);

}
