package com.utilities.dataProcessingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.framework.ui.UIUtil;

import matrix.util.StringList;

public class processPPandDPInfoForLinks {
	
	private static final String LocalRMI="http://gczc51035d4e.logon.ds.ge.com:8070/internal";  //Local noCAS
	
	private static final String Dev1CAS="https://dev1-3dplm-dp.pw.ge.com/dpplm/common/emxNavigator.jsp";  //DEV1 CAS
	private static final String Dev2CAS="https://dev1-3dplm-dp.pw.ge.com/dpplm-dev2/common/emxNavigator.jsp";  //DEV2 CAS
	private static final String Qa1CAS="https://qa1-3dplm-dp.pw.ge.com/dpplm/common/emxNavigator.jsp";  //QA1 CAS
	
	private static final String ObjectLink = "?objectId=";

	
	public static List<Map> processInfoForLinks(List<Map<?,?>> mlPPList, List<Map<?,?>> mlDPList, String Env)throws Exception
	{
		List<Map> mlReturn = new ArrayList<Map>();
		
		String strEnvUrl = getEnvUrl(Env);
		String strObjUrl = "";
		
		String strPPType = "";
		String strPPName = "";
		String strPPRevision = "";
		String strPPPartNumber = "";
		String strPPDescription = "";
		String strPPOwner = "";
		String strPPState = "";
		String strPPUsage = "";
		String strLastVersion = "";
		String strPPID = "";
		
		String strRefDocIdFromPath = "";
		StringList slRefDocPIDFromPaths = new StringList();
		StringList slRefDocsPIDs = new StringList();
		StringList slRefDocPID = new StringList();
		
		String sDPPID = "";
		String sDPOID = "";
		String sDPType = "";
		String sDPName = "";
		String sDPRevision = "";
		String sDPState = "";

		Map<?, ?> mTemp = null;
		Map<?, ?> mTemp1 = null;
		
		Map<Integer, String> mReorderedMap = new HashMap<Integer, String>();
		int iCounter = 0;
		
		if(mlPPList != null && mlPPList.size()>0 && UIUtil.isNotNullAndNotEmpty(strEnvUrl))
		{
			for(int i=0; i<mlPPList.size(); i++)
			{
				mReorderedMap = new HashMap<Integer, String>();
				iCounter = 0;
				strObjUrl = "";

				mTemp = (HashMap<?, ?>)mlPPList.get(i);
				
				strPPType = (String)mTemp.get(DomainConstants.SELECT_TYPE);
				strPPName = (String)mTemp.get(DomainConstants.SELECT_NAME);
				strPPRevision = (String)mTemp.get(DomainConstants.SELECT_REVISION);
				strPPPartNumber = (String)mTemp.get("attribute[PLMEntity.V_Name].value");
				strPPDescription = (String)mTemp.get("attribute[PLMEntity.V_description].value");
				strPPOwner = (String)mTemp.get(DomainConstants.SELECT_OWNER);
				strPPState = (String)mTemp.get(DomainConstants.SELECT_CURRENT);
				strPPUsage = (String)mTemp.get("attribute[PLMEntity.V_usage].value");
				strPPID = (String)mTemp.get(DomainConstants.SELECT_ID);
				
				strLastVersion = (String)mTemp.get("attribute[PLMReference.V_isLastVersion].value");
				
				if("TRUE".equalsIgnoreCase(strLastVersion) && "RELEASED".equalsIgnoreCase(strPPState)) //Get only Latest Released revisions
				{
					strObjUrl = strEnvUrl+ObjectLink+strPPID;
					
					mReorderedMap.put(iCounter, strPPType);
					iCounter++;
					mReorderedMap.put(iCounter, strPPName);
					iCounter++;
					mReorderedMap.put(iCounter, strPPRevision);
					iCounter++;
					mReorderedMap.put(iCounter, strPPPartNumber);
					iCounter++;
					mReorderedMap.put(iCounter, strPPDescription);
					iCounter++;
					mReorderedMap.put(iCounter, strPPOwner);
					iCounter++;
					mReorderedMap.put(iCounter, strPPUsage);
					iCounter++;
					mReorderedMap.put(iCounter, strPPState);
					iCounter++;
					mReorderedMap.put(iCounter, strObjUrl);
					iCounter++;
					strRefDocIdFromPath = (String)mTemp.get("from[VPLMrel/PLMConnection/V_Owner].to.paths[SemanticRelation].path.attribute[LastPIDAndRole].value");
				
					if(UIUtil.isNotNullAndNotEmpty(strRefDocIdFromPath))
					{
						String sRefDocPID = "";
						if(strRefDocIdFromPath.contains("")) //Contains more than one Reference Document
						{
							slRefDocPIDFromPaths = FrameworkUtil.split(strRefDocIdFromPath, "");
							
							for(int l=0; l<slRefDocPIDFromPaths.size(); l++)
							{
								slRefDocsPIDs = FrameworkUtil.split((String)slRefDocPIDFromPaths.get(l), "|");
								sRefDocPID = (String)slRefDocsPIDs.get(0);
								
								if(UIUtil.isNotNullAndNotEmpty(sRefDocPID)) //PP has atleast one RefDoc
								{
									if(mlDPList!=null && mlDPList.size()>0)
									{
										for(int j=0; j<mlDPList.size(); j++)
										{
											mTemp1 = (HashMap<?, ?>)mlDPList.get(j);
											sDPPID = (String)mTemp1.get("DP-PID");
											
											if(UIUtil.isNotNullAndNotEmpty(sDPPID)) //Safety check
											{
												if(sRefDocPID.equals(sDPPID))
												{
													strObjUrl = strEnvUrl;
													
													sDPType = (String)mTemp1.get(DomainConstants.SELECT_TYPE);
													sDPName = (String)mTemp1.get(DomainConstants.SELECT_NAME);
													sDPRevision = (String)mTemp1.get(DomainConstants.SELECT_REVISION);
													sDPState = (String)mTemp1.get(DomainConstants.SELECT_CURRENT);
				
													mReorderedMap.put(iCounter, sDPType);
													iCounter++;
													mReorderedMap.put(iCounter, sDPName);
													iCounter++;
													mReorderedMap.put(iCounter, sDPRevision);
													iCounter++;
													mReorderedMap.put(iCounter, sDPState);
													iCounter++;
													
													sDPOID = (String)mTemp1.get("DP-OID");
													strObjUrl = strObjUrl + ObjectLink + sDPOID;
													
													mReorderedMap.put(iCounter, strObjUrl);
													iCounter++;
												}
											}
										}
									}
								}
							}
						}
						else //Contains only one Reference Document
						{
							slRefDocPID = FrameworkUtil.split(strRefDocIdFromPath, "|");
							sRefDocPID = (String)slRefDocPID.get(0);
							
							if(UIUtil.isNotNullAndNotEmpty(sRefDocPID)) //PP has atleast one RefDoc
							{
								if(mlDPList!=null && mlDPList.size()>0)
								{
									for(int j=0; j<mlDPList.size(); j++)
									{
										mTemp1 = (HashMap<?, ?>)mlDPList.get(j);
										sDPPID = (String)mTemp1.get("DP-PID");
										
										if(UIUtil.isNotNullAndNotEmpty(sDPPID)) //Safety check
										{
											if(sRefDocPID.equals(sDPPID))
											{
												strObjUrl = strEnvUrl;
												
												sDPType = (String)mTemp1.get(DomainConstants.SELECT_TYPE);
												sDPName = (String)mTemp1.get(DomainConstants.SELECT_NAME);
												sDPRevision = (String)mTemp1.get(DomainConstants.SELECT_REVISION);
												sDPState = (String)mTemp1.get(DomainConstants.SELECT_CURRENT);
			
												mReorderedMap.put(iCounter, sDPType);
												iCounter++;
												mReorderedMap.put(iCounter, sDPName);
												iCounter++;
												mReorderedMap.put(iCounter, sDPRevision);
												iCounter++;
												mReorderedMap.put(iCounter, sDPState);
												iCounter++;
												
												sDPOID = (String)mTemp1.get("DP-OID");
												strObjUrl = strObjUrl + ObjectLink + sDPOID;
												
												mReorderedMap.put(iCounter, strObjUrl);
												iCounter++;
											}
										}
									}
								}
							}
						}
					}
				}
				mlReturn.add(mReorderedMap);
			}
		}
		System.out.println("mlReturn >>"+mlReturn);
		return mlReturn;
	}
	
	public static String getEnvUrl(String Env) throws Exception
	{
		
		String Env1 = "";
		
			switch(Env)
			{
				case "Local":
				Env1=LocalRMI;
				break;
				
				case "Dev1":
				Env1=Dev1CAS;
				break;
				
				case "Dev2":
				Env1=Dev2CAS;
				break;
				
				case "Qa1":
				Env1=Qa1CAS;
				break;
				
				default:
				Env1="";
			}
			return Env1;
	}
}
