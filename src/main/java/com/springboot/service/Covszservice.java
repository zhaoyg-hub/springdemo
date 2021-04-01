package com.springboot.service;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.dao.CovszDao;
import com.springboot.domain.Covsz;

@Service
public class Covszservice {
	@Autowired
	private CovszDao covszDao;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	//向数据库插入一条记录
	 public boolean add(Covsz covszer) {
	        return covszDao.insertcov(covszer)> 0;
	    }
	 
	 //向数据库插入一批记录，当插入数据超过几万时，该种方法会失效报错	 
	 public boolean addbatch(List<Covsz> list) {	      
	        return covszDao.insertbatch(list)>0;
	    }
	 
	 //向数据库插入大量数据，当插入数据超过几万时，该种有效
	 @Transactional
	 public void insertBatch(List<Covsz> list) 
	 {
		    int groupSize = 500;
			int groupNo = list.size() / groupSize;			
			//SqlSession相当于一个数据库连接对象，在一个SqlSession中可以执行多条SQL语句,
			//SqlSession本身是一个接口，提供了很多种操作方法，如insert，select等等，可以直接调用
			SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
			covszDao = sqlSession.getMapper(CovszDao.class);

			if (list.size() <= groupSize) 
			{
				covszDao.insertbatch(list);
				sqlSession.commit();				
				System.out.print("less than 500 records!!");
			} else 
			{
				List<Covsz> subList=null;
				for (int i = 0; i < groupNo; i++)
				{
					subList = list.subList(0, groupSize);
					covszDao.insertbatch(subList);
					list.subList(0, groupSize).clear();
				}
				if (list.size() > 0)
				{
					covszDao.insertbatch(list);
					System.out.print("seccessfull!");
				}
			}
			//flushStatements()起到一种预插入的作用(执行了这行代码之后,要插入的数据会锁定数据库的一行记录,
			//并把数据库默认返回的主键赋值给插入的对象,这样就可以把该对象的主键赋值给其他需要的对象中去了)
			sqlSession.flushStatements();	
			sqlSession.commit();		
	 }
	 
	 
	    //返回满足日期的所有记录
	    public List<Covsz> getfromdaye(String daytimee) {
	        return covszDao.selectfmdaytm(daytimee);
	    }
	 
	    //返回所有记录
	    public List<Covsz> getAll() {
	        return covszDao.selectAllcov();
	    }
	 
	    //修改记录
	    public boolean modify(Covsz covszer) {
	        return covszDao.updatecov(covszer) > 0;
	    }
	 
	    //根据ID删除记录
	    public boolean remove(int id) {
	        return covszDao.deletecov(id)> 0;
	    }     
	    
	    //删除数据库中所有重复的记录
	    public boolean removerepeat() {
	    	return covszDao.delrepeate()>0;
	    }
	    
	    //返回所有不重复ID的记录
	    public List<Covsz> getRepeat() {
	        return covszDao.selectrepeat();
	    }
	    
	    //根据ID查找一条记录
	    public Covsz getOne(int id) {
	        return covszDao.selectcovfmid(id);
	    }

}
