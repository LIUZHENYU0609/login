package com.example.demo.DAO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/** 
* @author liushaomingdev@163.com
* @version 创建时间：2017年8月26日 下午11:14:41 
* 类说明 
*/
public interface TokenDAO {
	String getToken(Long userId);
	Integer insert(@Param("userId") Long userId, @Param("token") String token
            , @Param("expire") Long expire);
	Integer updateToken(@Param("userId") Long userId, @Param("token") String token);
}
