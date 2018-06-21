package com.utilities.SXValidation;

import com.matrixone.apps.domain.util.ContextUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.domain.util.MqlUtil;
import com.matrixone.apps.framework.ui.UIUtil;
import com.utilites.reportUtils.ListToExcel;
import com.utilities.dataProcessingUtils.processMastershipInfo;
import com.utilities.dataProcessingUtils.processPPInfo;
import com.utilities.dataProcessingUtils.processPPandDPInfoForLinks;
import com.utilities.dataProcessingUtils.processReleasedAndCompleteData;
import com.utilities.dataProcessingUtils.processThumbnailInfo;

import matrix.db.Context;
import matrix.util.Pattern;
import matrix.util.StringList;

public class VPMUtils {
	
	private static Date date = new Date(System.currentTimeMillis());
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	private static String strDate = formatter.format(date);
	
	private static String PPReportWorkBookName = "SX-Validation-Env-PPReport" + "-" + strDate;
	private static String[] PPReportcolumns = {"Type", "Name", "Revision","Part Number", "Description", "Owner", "Originated","Modified","Usage","3Sh Type","3Sh Name","3Sh Revision","3Sh Part Number","3Sh Owner","3Sh Originated","3Sh Modified","3Sh Usage","3Sh File Info","Dwg Type","Dwg Name","Dwg Revision","Dwg Part Number","Dwg Owner","Dwg Originated","Dwg Modified","Dwg Usage","Dwg File Info"};
	
	private static String ThumbReportWorkBookName = "SX-Validation-Env-ThumbnailReport" + "-" + strDate;
	private static String[] ThumbnailReportcolumns = {"Type", "Name", "Revision","Part Number", "Description", "Originated","Modified","Physical ID","Thumbnail Name", "Thumbnail(Yes/No)","DWG Type","DWG Name","DWG Revision","DWG Part Number","DWG Originated","DWG Modified","DWG Physical ID","DWG Thumbnail Name" ,"DWG Thumbnail(Yes/No)"};
	
	private static String MastershipReportWorkBookaName = "SX-Validation-Env-MastershipReport" + "-" + strDate;
	private static String[] MastershipReportcolumns = {"Type", "Name", "Revision","Part Number","Description", "LastMinorVersion", "LastVersion", "Mastership", "3DSh Type", "3DSh Name", "3DSh Revision","3DSh LastMinorVersion", "3DSh LastVersion" ,"3DSh Mastership","DWG Type","DWG Name","DWG Revision","DWG LastMinorVersion", "DWG LastVersion", "DWG Mastership"};

	private static String LinksReportWorkBookaName = "SX-Validation-Env-LinksReport" + "-" + strDate;
	private static String[] LinksReportcolumns = {"Type", "Name", "Revision","Part Number","Description", "Owner", "Usage", "State", "PP Link", "DP Type", "DP Name", "DP Revision","State", "DP Link"};
	
	private static String ReleasedObjsWorkBookaName = "SX-Validation-Env-ReleasedObjsReport" + "-" + strDate;
	private static String[] ReleasedObjReportcolumns = {"Type", "Name", "Revision","State","Connected CA", "CA State", "Connected CO", "State", "Proposed / Realized"};

	
  	private static String SheetName = strDate;

	
	public static void getDetailsFromEnvAndProcess(Context context, String EnvName) throws Exception
	{
		System.out.println("Inside getDetailsFromEnvAndProcess>>>");
		System.out.println("Current getDetailsFromEnvAndProcess Method >>> " +new Timestamp((new Date()).getTime()));

		MapList mlPhyProdDetails = getPhysicalProductDetails(context);
		
		MapList mlThumbnailsInfo = getThumbailsInfo(context, mlPhyProdDetails);
		
		MapList mlDPInfoForLinks = getDPInfoForGeneratingLinks(context, mlPhyProdDetails);
		
		MapList mlReleasedObjInfo = getReleasedObjInfo(context);
		
		context.disconnect();
		
//		Process PP Info to generate Product/Part, 3DShape and Drawing Report
		List<Map<?, ?>> mlReorderedList = processPPInfo.rearrangePPdata(mlPhyProdDetails);
//		Generate PP Report		
		ListToExcel.ListWithMapToExcel(PPReportWorkBookName.replaceAll("Env", EnvName), SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", PPReportcolumns, mlReorderedList);
		
		
//		Process PP & Thumbnail Info to generate Thumbnail Report
		List<Map<?, ?>> mlThumbRearrangedData = processThumbnailInfo.rearrangeThumbnailData(mlPhyProdDetails, mlThumbnailsInfo);
//		Generate Report
		ListToExcel.ListWithMapToExcel(ThumbReportWorkBookName.replaceAll("Env",EnvName) , SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", ThumbnailReportcolumns, mlThumbRearrangedData);
		
//		Process PP, 3Sh and DWG Info Info to generate MasterShip Report
		List<Map<?, ?>> mlMastershipInfo = processMastershipInfo.processMasterShipInfo(mlPhyProdDetails);
//		Generate Mastership Report
		ListToExcel.ListWithMapToExcel(MastershipReportWorkBookaName.replaceAll("Env", EnvName), SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", MastershipReportcolumns, mlMastershipInfo);
		
//		Process PP and DWG Info Info to generate Links Report
		List<Map<?,?>> mlPPAndDPInfoForLinks = processPPandDPInfoForLinks.processInfoForLinks(mlPhyProdDetails, mlDPInfoForLinks, EnvName);
//		Generate Links Report		
		ListToExcel.ListWithMapToExcel(LinksReportWorkBookaName.replaceAll("Env", EnvName), SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", LinksReportcolumns, mlPPAndDPInfoForLinks);
	
//		Generate Released Object Report		
		ListToExcel.ListWithMapToExcel(ReleasedObjsWorkBookaName.replaceAll("Env", EnvName), SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", ReleasedObjReportcolumns, mlReleasedObjInfo);

	}
	
	
	public static MapList getPhysicalProductDetails(Context context) throws Exception
	{
		MapList mlReturn = new MapList();
		
		System.out.println("Inside getPhysicalProductDetails>>>");
		System.out.println("Current getPhysicalProductDetails Method >>> "+new Timestamp((new Date()).getTime()));
		
//		MapList mlPhyProdDetails = new MapList();
		
		StringList slSelects = new StringList();
		
		slSelects.add(DomainConstants.SELECT_TYPE);
		slSelects.add(DomainConstants.SELECT_NAME);
		slSelects.add(DomainConstants.SELECT_REVISION);
		slSelects.add("attribute[PLMEntity.V_Name].value");
		slSelects.add("attribute[PLMEntity.V_description].value");
		slSelects.add(DomainConstants.SELECT_OWNER);
		slSelects.add(DomainConstants.SELECT_CURRENT);
		slSelects.add(DomainConstants.SELECT_ORIGINATED);
		slSelects.add(DomainConstants.SELECT_MODIFIED);
		slSelects.add("attribute[PLMEntity.V_usage].value");
		//----------------------------------------------------------------------------------------------------------------------
		slSelects.add("physicalid");//for Thumbnail Report
		//----------------------------------------------------------------------------------------------------------------------
		slSelects.add("attribute[PLMReference.V_isLastMinorVersion].value");//for mastership Report
		slSelects.add("attribute[PLMReference.V_isLastVersion].value");//for mastership Report
		//----------------------------------------------------------------------------------------------------------------------
		slSelects.add(DomainConstants.SELECT_ID);//For Links Report
		slSelects.add("attribute[PLMReference.V_isLastVersion].value");//For Links Report
		slSelects.add("from[VPLMrel/PLMConnection/V_Owner|to.type==PLMDocConnection].to.type");//For Links Report
		slSelects.add("from[VPLMrel/PLMConnection/V_Owner|to.type==PLMDocConnection].to.paths.path.attribute[LastPIDAndRole].value");//For Links Report
		//----------------------------------------------------------------------------------------------------------------------
			
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.type");  //3DShape Type
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.name");  //3DShape Name
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.revision");  //3DShape Revision
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.attribute[PLMEntity.V_Name].value");  //3DShape Title
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.owner");  //3DShape Owner
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.originated");  //3DShape Originated
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.modified");  //3DShape Modified		
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.attribute[PLMEntity.V_usage].value"); //3DShape Usage attr
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.format.file.name"); //3DShape File Info
		//----------------------------------------------------------------------------------------------------------------------
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.attribute[PLMReference.V_isLastMinorVersion].value");//for mastership Report
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.attribute[PLMReference.V_isLastVersion].value");//for mastership Report
		//----------------------------------------------------------------------------------------------------------------------
		
		
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.type");  //Drawing Type
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.name");  //Drawing Name
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.revision");  //Drawing Revision
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.attribute[PLMEntity.V_Name].value");  //Drawing Title
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.owner");  //Drawing Owner
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.originated");  //Drawing Originated
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.modified");  //Drawing Modified		
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.attribute[PLMEntity.V_usage].value"); //Drawing Usage attr
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.format.file.name"); //Drawing File Info
		//------------------------------------------------------------------------------------------------------------------------
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.physicalid");//for Thumbnail Report
		//------------------------------------------------------------------------------------------------------------------------
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.attribute[PLMReference.V_isLastMinorVersion].value");//for mastership Report
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.attribute[PLMReference.V_isLastVersion].value");//for mastership Report


		
		try 
		{
			//Write Log

			
//			mlPhyProdDetails = DomainObject.getInfo(context,new String [] {"60136.35780.37376.17810"}, slSelects);
			
			MapList mlPhyProdDetails = DomainObject.findObjects(context, "VPMReference", "vplm", DomainConstants.EMPTY_STRING, slSelects);
			
			
//			System.out.println("getPhysicalProductDetails ::: mlPhyProdDetails >>>"+mlPhyProdDetails);
			
//			String strResult  = MqlUtil.mqlCommand(context, "temp query bus VPMReference * * limit 10 select id");
			
			mlReturn.addAll(mlPhyProdDetails);
		
			//Start Processing data to write to Excel
			//processPPInfo.rearrangePPdata(mlPhyProdDetails);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		return mlReturn;
	}
	
	public static MapList getThumbailsInfo(Context context, List<Map<?,?>> mlInput)throws Exception
	{
		MapList mlReturn = new MapList();
		
		System.out.println("Inside getThumbailsInfo>>>");
		System.out.println("Current getThumbailsInfo Method >>> "+new Timestamp((new Date()).getTime()));

		Map<?, ?> mTemp = new HashMap();
		
		Pattern pattName = new Pattern("Test");
		
		StringList slObjSelects = new StringList();
		slObjSelects.add(DomainConstants.SELECT_TYPE);
		slObjSelects.add(DomainConstants.SELECT_NAME);
		slObjSelects.add(DomainConstants.SELECT_REVISION);
		slObjSelects.add("format.file.name");
		
		for(int imlInput = 0; imlInput < mlInput.size(); imlInput++)
		{
				mTemp = (HashMap<?, ?>)mlInput.get(imlInput);
				
				if(UIUtil.isNotNullAndNotEmpty((String)mTemp.get("physicalid")))
				{
					pattName.addPattern((String)mTemp.get("physicalid"));
				}
				
				if(UIUtil.isNotNullAndNotEmpty((String)mTemp.get("from[VPMRepInstance].to.physicalid")))
				{
					pattName.addPattern((String)mTemp.get("from[VPMRepInstance].to.physicalid"));
				}
		}
		
		try
		{
			ContextUtil.pushContext(context);
			
			MapList mlThumbnailObjInfo = DomainObject.findObjects(context, "PLMDerivedObjRepresentation", pattName.getPattern(), DomainConstants.QUERY_WILDCARD, DomainConstants.QUERY_WILDCARD, "vplm", DomainConstants.EMPTY_STRING, false, slObjSelects);
			
			ContextUtil.popContext(context);
			mlReturn.addAll(mlThumbnailObjInfo);
			
			ContextUtil.popContext(context);
			
		}

		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
//		System.out.println("\nmlThumbnailObjInfo >>>"+mlThumbnailObjInfo);
		
		return mlReturn;
	}
	
	public static MapList getDPInfoForGeneratingLinks(Context context, List<Map<?,?>> mlInput)throws Exception
	{
		MapList mlReturn = new MapList();
		
		String strRefDocIdFromPath = "";
		
		StringList slRefDocPIDFromPaths = new StringList();
		StringList slRefDocsPIDs = new StringList();
		StringList slRefDocPID = new StringList();
		
		Map mTemp = new HashMap();
		Map<Integer, String> mRefDocPIDs = new HashMap();
		int iRefDocPIDsCounter = 0;
		
		if(mlInput!=null && mlInput.size()>0)
		{
			for(int k=0; k<mlInput.size(); k++)
			{
				mTemp = (HashMap)mlInput.get(k);
				
				strRefDocIdFromPath = (String)mTemp.get("from[VPLMrel/PLMConnection/V_Owner].to.paths[SemanticRelation].path.attribute[LastPIDAndRole].value");
				if(UIUtil.isNotNullAndNotEmpty(strRefDocIdFromPath))
				{
					if(strRefDocIdFromPath.contains("")) //Contains more than one Reference Document
					{
						slRefDocPIDFromPaths = FrameworkUtil.split(strRefDocIdFromPath, "");
						
						for(int l=0; l<slRefDocPIDFromPaths.size(); l++)
						{
							slRefDocsPIDs = FrameworkUtil.split((String)slRefDocPIDFromPaths.get(l), "|");
							
							mRefDocPIDs.put(iRefDocPIDsCounter, (String)slRefDocsPIDs.get(0));
							iRefDocPIDsCounter++;
						}
					}
					else //Contains only one Reference Document
					{
						slRefDocPID = FrameworkUtil.split(strRefDocIdFromPath, "|");
						
						mRefDocPIDs.put(iRefDocPIDsCounter, (String)slRefDocPID.get(0));
						iRefDocPIDsCounter++;
					}
				}
			}
			
			String refDocPIDs[] = new String[mRefDocPIDs.size()];

			for(int j = 0; j < mRefDocPIDs.size(); j++)
			{
				refDocPIDs[j] = (String)mRefDocPIDs.get(j);
			}
			
			StringList slRefDocSelects = new StringList();
			
			slRefDocSelects.add(DomainConstants.SELECT_TYPE);
			slRefDocSelects.add(DomainConstants.SELECT_NAME);
			slRefDocSelects.add(DomainConstants.SELECT_REVISION);
			slRefDocSelects.add(DomainConstants.SELECT_CURRENT);
			
			slRefDocSelects.add(DomainConstants.SELECT_ID);
			slRefDocSelects.add("physicalid");

		   MapList mlRefDocInfo = DomainObject.getInfo(context, refDocPIDs, slRefDocSelects);
		   
		   MapList mlRefDocIDs = new MapList();
		   Map<?, ?> mRefDocTemp = new HashMap();
		   Map<String, String> mRefDoc = null;
		   String sRefDocType = "";
		   
		   if(mlRefDocInfo != null & mlRefDocInfo.size() > 0)
		   {
			   for(int m=0; m<mlRefDocInfo.size(); m++)
			   {
				   mRefDoc = new HashMap<String, String>();
				   
				   mRefDocTemp = (HashMap<?, ?>)mlRefDocInfo.get(m);
				   sRefDocType = (String)mRefDocTemp.get(DomainConstants.SELECT_TYPE);
				   
				   if(UIUtil.isNotNullAndNotEmpty(sRefDocType) && "Drawing Print".equalsIgnoreCase(sRefDocType))
				   {
					   mRefDoc.put("DP-PID", (String)mRefDocTemp.get("physicalid"));
					   mRefDoc.put("DP-OID", (String)mRefDocTemp.get(DomainConstants.SELECT_ID));
					   mRefDoc.put("type", (String)mRefDocTemp.get(DomainConstants.SELECT_TYPE));
					   mRefDoc.put("name", (String)mRefDocTemp.get(DomainConstants.SELECT_NAME));
					   mRefDoc.put("revision", (String)mRefDocTemp.get(DomainConstants.SELECT_REVISION));
					   mRefDoc.put("current", (String)mRefDocTemp.get(DomainConstants.SELECT_CURRENT));
				   }
				   if(mRefDoc.size()>0)
				   mlRefDocIDs.add(mRefDoc);
			   }
		   }
		  mlReturn.addAll(mlRefDocIDs);
		}
		
		return mlReturn;
	}

	public static MapList getReleasedObjInfo(Context context) throws Exception
	{
		MapList mlReturn = new MapList();
		
		MapList mPPList = new MapList();
		
		MapList mlProcessedList = new MapList();
		
		Pattern pattType = new Pattern("VPMReference");
		pattType.addPattern("Drawing");
		pattType.addPattern("Product Requirement Specification");
		pattType.addPattern("Drawing Print");
		
		Pattern pattVault = new Pattern("vplm");
		pattVault.addPattern("eService Production");
		
		StringList slSelects = new StringList();
		slSelects.add(DomainConstants.SELECT_TYPE);
		slSelects.add(DomainConstants.SELECT_NAME);
		slSelects.add(DomainConstants.SELECT_REVISION);
		slSelects.add(DomainConstants.SELECT_CURRENT);
		slSelects.add("physicalid");
		
		String strWhere = "current == Release || current == RELEASED";
		String strCAWhere = "current == Complete";
		

		StringList slCASelects = new StringList();
		
		slCASelects.add(DomainConstants.SELECT_NAME);
		slCASelects.add(DomainConstants.SELECT_CURRENT);
		slCASelects.add("to[Change Action|.from.type==\"Change Order\"].from.name");
		slCASelects.add("to[Change Action|.from.type==\"Change Order\"].from.current");
		slCASelects.add("from[Proposed Activities].to.paths.path.element.physicalid");
		slCASelects.add("from[Realized Activities].to.paths.path.element.physicalid");
		
		try
		{
		 	MapList mlReleasedObjInfo = DomainObject.findObjects(context, pattType.getPattern(), pattVault.getPattern(), strWhere, slSelects) ;
			
			MapList mlCompleteCAsInfo = DomainObject.findObjects(context, "Change Action", "eService Production", strCAWhere ,slCASelects);
			
			if(mlReleasedObjInfo != null && mlCompleteCAsInfo !=null)
			{
				mlProcessedList = processReleasedAndCompleteData.processReleasedData(mlReleasedObjInfo, mlCompleteCAsInfo);	
			}
			
			if(mlProcessedList !=null)
			{
				mlReturn.addAll(mlProcessedList);	
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return mlReturn;
	}

}
