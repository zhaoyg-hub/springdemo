package com.springboot.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.springboot.domain.Covsz;
import com.springboot.service.Covszservice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
//@RestController
public class UserController{
	
	@Autowired
	//private Cityservice cityservice;	
	private Covszservice covszService;
	
	//springboot test
/*
	@RequestMapping("/hello")
	 @ResponseBody
	 public String hello()
	 {
		 return"Hello World ggdghtthsgsADGHH!";
	 }
*/
	
	 //主页面，返回显示地图的静态页面
	@RequestMapping("/covsz")
	public String covszer() {
		return "MapboxDemo.html";
	}
	
	 //读取数据库中的全部数据，往前端传递
	//asfasadg打发点
	 @ResponseBody   //这个注解就是把数据以JSON格式传走
	 @RequestMapping("/getall")
	 public List<Covsz> getAll()
	 {
		 List<Covsz> list=new ArrayList<>();			 
		 //covszService.removerepeat();   //删除数据库中重复的记录
		 //list=covszService.getRepeat();  //返回所有重复的记录
		 list=covszService.getAll();    //返回去掉重复的记录
		 
		 return list;
	 }
	 
	 //按照发病日期查询
	 @ResponseBody   
	 @RequestMapping("/getdatafromdaye")
	 public List<Covsz> getdatafromDaye(String daye)
	 {
		 List<Covsz> list=new ArrayList<>();			 
		 list=covszService.getfromdaye("2020-01-23");
		 return list;
	 }
	 
	 
	 //数据库管理，添加数据页面
		@RequestMapping("/addTxt")
		public String addtxtfile() {
			return "readTxt.html";
		}		
		
		
		//接受前端json字符串存入数据库
		@ResponseBody		
		@RequestMapping("/savetodb") 		
		public void receivestr(@RequestBody String params) 
		{
			//使用JSONArray解释json字符串
			JSONArray mapArray = JSONArray.fromObject(params);
			System.out.println(mapArray.get(0));				 
			//将数据存入数据库
			long time1 = System.currentTimeMillis();
			
			if(mapArray.size()>0) {
				for(int i=0; i<mapArray.size(); i++) {
					JSONObject object= (JSONObject) mapArray.get(i);									
					Covsz sz=new Covsz();					
					int tid=Integer.parseInt((String) object.get("id"));
					int tfid=Integer.parseInt((String) object.get("fid"));
					int tqua=Integer.parseInt((String) object.get("qua"));
					BigDecimal tlon=new BigDecimal((String) object.get("lon"));
					BigDecimal tlat=new BigDecimal((String) object.get("lat"));																		
					
					sz.setCovid(tid);
					sz.setCovfid(tfid);
					sz.setCovqua(tqua);
					sz.setCovdayi((String) object.get("day_i"));
					sz.setCovdaye((String) object.get("day_e"));
					sz.setCovdayr((String) object.get("day_r"));
					sz.setCovlon(tlon);
					sz.setCovlat(tlat);					
					sz.setCovtype((String) object.get("tpye"));
					
					covszService.add(sz);
				}
			}
			
			
			long time2 = System.currentTimeMillis();
			System.out.println("插入数据库耗时"+(time2-time1));

		}
		
		
		//批量插入数据库,方法一，  
		@ResponseBody		
		@RequestMapping("/multinsert") 		
		public void mulinset(@RequestBody String params) 
		{
			//使用JSONArray解释json字符串
			JSONArray mapArray = JSONArray.fromObject(params);
			System.out.println(mapArray.get(0));				 
			//将数据存入数据库
			long time1 = System.currentTimeMillis();
			
			List<Covsz> covList = new ArrayList<>();
			//数据插入操作
			if(mapArray.size()>0) {
				for(int i=0; i<mapArray.size(); i++) {

					
					JSONObject object= (JSONObject) mapArray.get(i);									
					Covsz sz=new Covsz();						
					int tfid=Integer.parseInt((String) object.getString("fid"));					
					BigDecimal tlon=new BigDecimal((String) object.getString("lng"));
					BigDecimal tlat=new BigDecimal((String) object.getString("lat"));																		
					
					sz.setCovid(0);
					sz.setCovfid(tfid);
					sz.setCovqua(0);
					sz.setCovdayi("2020-07-13");
					sz.setCovdaye("2020-07-13");
					sz.setCovdayr("2020-07-13");
					sz.setCovlon(tlon);
					sz.setCovlat(tlat);					
					sz.setCovtype("import");
					covList.add(sz);	
				}
				covszService.addbatch(covList);
			}
			
					
			long time2 = System.currentTimeMillis();
			System.out.println("插入数据库耗时"+(time2-time1)+"毫秒");

		}
	
		//批量插入数据库，方法二
		@ResponseBody		
		@RequestMapping("/insertbatch") 		
		public void insertbatch(@RequestBody String params) 
		{
			//使用JSONArray解释json字符串
			JSONArray mapArray = JSONArray.fromObject(params);
			if(mapArray==null || mapArray.isEmpty()) {
				System.out.print("upload data fail!");
				return;
			}else {
				System.out.println(mapArray.get(0));
				System.out.println("上传数据大小："+mapArray.size());
			}
							 
			//将数据存入数据库
			long time1 = System.currentTimeMillis();
			
			List<Covsz> covList = new ArrayList<>();
			//数据插入操作
			if(mapArray.size()>0) {
				for(int i=0; i<mapArray.size(); i++) {
					JSONObject object= (JSONObject) mapArray.get(i);									
					Covsz sz=new Covsz();																		
					
					//数据库插入时要注意json文件的格式名称等
					int tfid=Integer.parseInt((String) object.getString("fid"));						
					BigDecimal tlon=new BigDecimal((String) object.getString("lng"));
					BigDecimal tlat=new BigDecimal((String) object.getString("lat"));	
					
					sz.setCovid(0);
					sz.setCovfid(tfid);
					sz.setCovqua(0);
					sz.setCovdayi("2020-07-13");
					sz.setCovdaye("2020-07-13");
					sz.setCovdayr("2020-07-13");
					sz.setCovlon(tlon);
					sz.setCovlat(tlat);					
					sz.setCovtype("import");
					covList.add(sz);	
				}				
			}
			
			covszService.insertBatch(covList);
			
			long time2 = System.currentTimeMillis();
			System.out.println("插入数据库耗时："+(time2-time1)+"毫秒!!!!");
		}	
		
		
	
		
		
		
		
}