package com.utilities.connectionUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.matrixone.apps.framework.ui.UIUtil;

import matrix.db.Context;
import matrix.util.MatrixException;

public class noCASConnectionUtilities {

//	public static Logger logger  = LogManager.getLogger(noCASConnectionUtilities.class);
	
	
	protected static Context getConnection(String RMI, String UserName, String Password, String Role) throws MatrixException
	{
		
//		Context noCAScontext17x = new Context(":bos", RMI);
		Context noCASContext17x = new Context(RMI);
		
		noCASContext17x.setUser(UserName);
		noCASContext17x.setPassword(Password);
		noCASContext17x.setRole(Role);

		
		noCASContext17x.connect();
		
		System.out.println("noCAScontext17x >>>"+noCASContext17x);
		
//		logger.error("Got Context"+noCASContext17x);
		
		return noCASContext17x;
		
	}
	
	protected static Context getConnection(String RMI, String UserName, String Password) throws MatrixException
	{
		
//		Context noCASContext17x = new Context(":bos", RMI);
		Context noCASContext17x = new Context(RMI);
		
		noCASContext17x.setUser(UserName);
		noCASContext17x.setPassword(Password);
				
		noCASContext17x.connect();
		
		System.out.println("noCAScontext17x >>>"+noCASContext17x);
//		logger.error("Got Context"+noCASContext17x);
		
		return noCASContext17x;
		
	}


/*	public static void main(String[] args) throws Exception
	{
		String RMI = "https://dev1-3dplm-dp.pw.ge.com/dpplminternal-dev2/";
		String UserName = "999901009";
		String Password = "";
		String Role = "";
		
		Context noCAScontext17x = noCASConnectionUtilities.getConnection(RMI, UserName, Password);
		System.out.println("Context Protocol >>"+noCAScontext17x.getProtocol());
		System.out.println("Context  User>>"+noCAScontext17x.getUser());
		
	}*/
}