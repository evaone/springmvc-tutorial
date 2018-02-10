package com.ibeifeng.hiveF;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		args = new String[3];
		args[0]="e:/3-20.sql";
		args[1]="-date";
		args[2]="2013-01-01";
		
		ParseArgs parse=new ParseArgs(args);
		String sql=Utils.getSql(new File(args[0]));
		String str=Utils.parse(sql, parse.getMap());
		System.out.println(str);

	}

}
