package com.utilities.connectionUtils;

import com.matrixone.apps.framework.ui.UIUtil;

import matrix.db.Context;

public class connectToEnv {
	
	private static final String LocalRMI="http://gczc51035d4e.logon.ds.ge.com:8070/internal";  //Local noCAS
	
	private static final String Dev1RMI="http://asxd3612a-1.pw.ge.com:7000/dpplminternal/";  //DEV1 noCAS
	private static final String Dev2RMI="http://asxd3650a-2.pw.ge.com:7000/dpplminternal-dev2/";  //DEV2 noCAS
	private static final String Qa1RMI="https://qa1-3dplm-dp.pw.ge.com/dpplminternal/";  //QA1 noCAS
	
	private static final String EngAuthor="ctx::VPLMCreator.General Electric.Engineering";
	private static final String EngContributor="ctx::VPLMExperimenter.General Electric.Engineering";
	private static final String EngOwner="ctx::VPLMProjectAdministrator.General Electric.Engineering";
	private static final String EngLeader="ctx::VPLMProjectLeader.General Electric.Engineering";
	private static final String EngReader="ctx::VPLMViewer.General Electric.Engineering";
	
	private static final String StdPrtsAuthor="ctx::VPLMCreator.General Electric.Standard Parts";
	private static final String StdPrtsContributor="ctx::VPLMExperimenter.General Electric.Standard Parts";
	private static final String StdPrtsOwner="ctx::VPLMProjectAdministrator.General Electric.Standard Parts";
	private static final String StdPrtsLeader="ctx::VPLMProjectLeader.General Electric.Standard Parts";
	private static final String StdPrtsReader="ctx::VPLMViewer.General Electric.Standard Parts";

	private static final String GscmAuthor="ctx::VPLMCreator.General Electric.GSCM";
	private static final String GscmContributor="ctx::VPLMExperimenter.General Electric.GSCM";
	private static final String GscmOwner="ctx::VPLMProjectAdministrator.General Electric.GSCM";
	private static final String GscmLeader="ctx::VPLMProjectLeader.General Electric.GSCM";
	private static final String GscmReader="ctx::VPLMViewer.General Electric.GSCM";

	
	public static Context get17xNoCasContext(String UserName, String Pwd, String Role, String RMI) throws Exception
	{
		System.out.println("Getting 17x Context");
		
		Context context17x = null;
		
		String Role1 = getRole(Role);
		String RMI1 = getRMI(RMI);
		
		if(UIUtil.isNotNullAndNotEmpty(RMI1))
		{
			if(UIUtil.isNullOrEmpty(Role1))
			{
				context17x = noCASConnectionUtilities.getConnection(RMI1, UserName, Pwd);
			}
			
			else if(!(UIUtil.isNullOrEmpty(Role1)))
			{
				context17x = noCASConnectionUtilities.getConnection(RMI1, UserName, Pwd, Role1);
			}
		}
		
		return context17x;
	}
	
	private static String getRole(String Role)
	{
		String Role1 = "";
		
		switch(Role)
		{
			case "EngAuthor":
			Role1 = EngAuthor;
			break;

			case "EngContributor":
			Role1 = EngContributor;
			break;
			
			case "EngOwner":
			Role1 = EngOwner;
			break;
		
			case "EngLeader":
			Role1 = EngLeader;
			break;
			
			case "EngReader":
			Role1 = EngReader;
			break;
			
			case "StdPrtsAuthor":
			Role1 = StdPrtsAuthor;
			break;
			
			case "StdPrtsContributor":
			Role1 = StdPrtsContributor;
			break;
			
			case "StdPrtsOwner":
			Role1 = StdPrtsOwner;
			break;
			
			case "StdPrtsLeader":
			Role1 = StdPrtsLeader;
			break;
			
			case "StdPrtsReader":
			Role1 = StdPrtsReader;
			break;
			
			case "GscmAuthor":
			Role1 = GscmAuthor;
			break;
			
			case "GscmContributor":
			Role1 = GscmContributor;
			break;
			
			case "GscmOwner":
			Role1 = GscmOwner;
			break;
				
			case "GscmLeader":
			Role1 = GscmLeader;
			break;
			
			case "GscmReader":
			Role1 = GscmReader;
			break;
			
			case "LocalEngLeader":
			Role1 = "ctx::VPLMProjectLeader.Company Name.Engineering";
			break;
			
			default:
			Role1 = "";	
		}
		
		return Role1;
	}

	private static String getRMI(String RMI)
	{
		String RMI1 = "";
	
		switch(RMI)
		{
			case "Local":
			RMI1=LocalRMI;
			break;
			
			case "Dev1":
			RMI1=Dev1RMI;
			break;
			
			case "Dev2":
			RMI1=Dev2RMI;
			break;
			
			case "Qa1":
			RMI1=Qa1RMI;
			break;
			
			default:
			RMI1="";
		
		}
		
		return RMI1;
	}
}
