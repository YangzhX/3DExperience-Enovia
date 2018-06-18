package com.utilities.Playground;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.MapList;
import com.utilites.reportUtils.ListToExcel;
import com.utilities.SXValidation.VPMUtils;
import com.utilities.connectionUtils.connectToEnv;
import com.utilities.dataProcessingUtils.processPPandDPInfoForLinks;

import matrix.db.Context;
import matrix.util.StringList;

public class GeneratePPandDrawingPrintLinks {
	
	private static Date date = new Date(System.currentTimeMillis());
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	private static String strDate = formatter.format(date);
	
	private static String SheetName = strDate;
	private static String[] LinksReportcolumns = {"Type", "Name", "Revision","Part Number","Description", "Owner", "Usage", "State", "PP Link", "DP Type", "DP Name", "DP Revision","State", "DP Link"};
	
	private static String LinksReportWorkBookaName = "SX-Validation-Env-LinksSheet" + "-" + strDate;
	public static void main(String[] args) {

		Context context = null;
		try
		{
			//Envs - Local, Dev1, Dev2, Qa1
			context = connectToEnv.get17xNoCasContext("502785999", "502785999", "EngLeader", "Qa1");
			
			MapList mlPhyProdDetails = new MapList();
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
			slSelects.add("attribute[PLMReference.V_isLastVersion].value");//for mastership Report and Links Report
			//----------------------------------------------------------------------------------------------------------------------
			slSelects.add(DomainConstants.SELECT_ID);//For Links Report
			slSelects.add("from[VPLMrel/PLMConnection/V_Owner|to.type==PLMDocConnection].to.type");//For Links Report
			slSelects.add("from[VPLMrel/PLMConnection/V_Owner|to.type==PLMDocConnection].to.paths.path.attribute[LastPIDAndRole].value");//For Links Report
			//----------------------------------------------------------------------------------------------------------------------
				
			
			Map<Integer, String> mRefDocPIDs = new HashMap();
			int iRefDocPIDsCounter = 0;
			
 			mlPhyProdDetails = DomainObject.getInfo(context, new String[] {"60136.35780.57088.47321"}, slSelects);
			System.out.println("mlPhyProdDetails >>"+mlPhyProdDetails);
			
			MapList mlDPInfoForLinks = VPMUtils.getDPInfoForGeneratingLinks(context, mlPhyProdDetails);
			System.out.println("mlDPInfo >>>"+mlDPInfoForLinks);
			
			context.disconnect();
			
			List<Map<?,?>> mlPPAndDPInfoForLinks = processPPandDPInfoForLinks.processInfoForLinks(mlPhyProdDetails, mlDPInfoForLinks, "Local");
			ListToExcel.ListWithMapToExcel(LinksReportWorkBookaName.replaceAll("Env", "Local"), SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", LinksReportcolumns, mlPPAndDPInfoForLinks);
		}
		
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
