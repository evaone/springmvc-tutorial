package com.cloudy.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import com.ibeifeng.hiveF.ParseArgs;
import com.ibeifeng.hiveF.Utils;

public class Hive2Mysql {

	public Hive2Mysql(String propertyName){
		
	}
	
	Properties prop=new Properties();
	
	public void init(String propertyName) throws Exception{
		InputStream stream=new FileInputStream(propertyName);
		prop.load(stream);
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length<1){
			System.out.println("pls set propertyName;");
			System.exit(1);
		}
		
		args=new String[3];
		args[0]="c:/aa.property";
		args[1]="-date";
		args[2]="2015-01-01";
		
		String propertyName=args[0];
		//首个参数以外的其他参数，存到Map里
		ParseArgs parse=new ParseArgs(args);
		
		Hive2Mysql h2m=new Hive2Mysql(propertyName);
		
		System.out.println(h2m.prop.get("Hive_sql"));
		System.out.println(h2m.prop.get("Mysql_table"));
		String hive_sql=h2m.prop.getProperty("Hive_sql").toString();
		hive_sql=Utils.parse(hive_sql, parse.getMap());
		
		String mysql_talbe=h2m.prop.get("Mysql_table").toString();
		String mysql_columns = h2m.prop.get("mysql_columns").toString() ;
		String mysql_delete = h2m.prop.get("mysql_delete").toString();
		mysql_delete=Utils.parse(mysql_delete, parse.getMap());
		
	   String mysql_sql="insert into"+mysql_talbe+"("+mysql_columns+") values(";
	   
	   Connection mysqlCon=MyConnection.getMysqlInstance();
	   Connection myHiveCon=MyConnection.getHiveInstance();
	   
	   Statement stHive=myHiveCon.createStatement();
	   ResultSet rsHive=stHive.executeQuery(hive_sql);
	   
	   Statement stMysql=mysqlCon.createStatement();
	   stMysql.execute(mysql_delete);
	   
	   int len=hive_sql.split("from")[0].split("select")[1].trim().split(",").length;
	   System.out.println(len);
	   String value="";
	   while(rsHive.next()){
		   for(int i=1;i<=len;i++){
			   value+="'"+rsHive.getString(i)+"'";
		   }
		   value=value.substring(0,value.length()-1);
		   mysql_sql=mysql_sql+value+")";
		   System.out.println(value);
		   System.out.println(mysql_sql);
		   
		   stMysql.execute(mysql_sql);
		   
		   value="";
		   mysql_sql="insert into"+mysql_talbe+"("+mysql_columns+") values(";
	   }
	   
	   rsHive.close();
	   stHive.close();
	   stMysql.close();
	   mysqlCon.close();
	   myHiveCon.close();

	}
}
