package com.cloudy.tools;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String string="select huodong,pv,uv from rpt.rpt_sale_daily limit 20";
		System.out.println(string.split("from")[0].split("select")[1].trim().split(",").length);

	}

}
