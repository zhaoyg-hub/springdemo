package com.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.springboot.domain.Covsz;


@Mapper
@Component
public interface CovszDao {
	int insertcov(Covsz covser);    //插入一条记录	
	int insertbatch(List<Covsz> list);    //插入一个list序列
	
	List<Covsz> selectfmdaytm(String daytime);  //按day_e日期查询
	List<Covsz> selectAllcov();    //查询表中所有记录，过滤掉重复的
	int deletecov(Integer covid);  //根据ID删除记录
	int updatecov(Covsz covszer);   //更新记录
	List<Covsz> selectrepeat();    //由于添加数据库产生重复记录，查询重复的所有记录
	Covsz selectcovfmid(Integer covid);       //按ID查找
	int delrepeate();        //删除数据库中重复的记录

}
