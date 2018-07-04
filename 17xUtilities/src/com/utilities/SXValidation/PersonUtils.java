package com.utilities.SXValidation;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.matrixone.apps.domain.util.MqlUtil;

import matrix.db.Context;
import matrix.db.MQLCommand;

public class PersonUtils {
	
	private static Date date = new Date(System.currentTimeMillis());
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	private static String strDate = formatter.format(date);
	
	private static final String ReportDir = "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\";
	
	private static String PersonReportWorkBookName = "SX-Validation-Env-PersonReport" + "-" + strDate;
	private static String[] PersonReportcolumns = {"SSO", "First Name", "Last Name", "Role In Collab Space", "Site", "Active / Inactive"};

	public static void getPersonInfo(Context context) throws Exception
	{
		MQLCommand mql = new MQLCommand();
		
		String sMql = "";
	}
	

}
