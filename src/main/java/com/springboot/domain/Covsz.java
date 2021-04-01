package com.springboot.domain;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Covsz {
	private Integer covid;
	private Integer covfid;
	private String covdayi;
	private String covdaye;
	private String covdayr;
	private BigDecimal covlon;
	private BigDecimal covlat;
	private String covtype; 		
	private Integer covqua;	
}



