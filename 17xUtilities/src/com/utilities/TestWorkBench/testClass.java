package com.utilities.TestWorkBench;

import matrix.db.Context;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

import matrix.util.Pattern;
import matrix.util.StringList;

import com.matrixone.apps.domain.*;
import com.matrixone.apps.domain.util.*;
import org.json.JSONObject;

import java.util.Properties;
import java.util.Date;

//import ge.infra.plm.integrations.GEPLMCommonUtil;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public class testClass
{
	
	private static final String Dev1RMI="http://asxd3612a-1.pw.ge.com:7000/dpplminternal";  //DEV1 noCAS
	private static final String Dev2RMI="http://asxd3650a-2.pw.ge.com:7000/dpplminternal-dev2/";  //DEV2 noCAS
	private static final String QARMI="https://qa1-3dplm-dp.pw.ge.com/dpplminternal/";  //QA1 noCAS
	
	
	
	private static final String User="999901009";
	private static final String Pass="";
//	private static final String Role="ctx::VPLMProjectLeader.General Electric.Engineering";
	private static final String Role="ctx::VPLMCreator.General Electric.Engineering";
//	private static final String Role="ctx::VPLMViewer.General Electric.Engineering";
	
	private Context get12xContext(String User, String Pass, String RMI) throws Exception
	{
		System.out.println("Getting 12x Context");
		System.out.println("Getting 12X Context ::: RMI >>"+RMI);
		System.out.println("Getting 12x Context ::: User >>"+User);
		System.out.println("Getting 12x Context ::: Pass >>"+Pass);
		
		matrix.db.Context context12x = new Context(":bos", RMI);
		context12x.setUser(User);
		context12x.setPassword(Pass);
		context12x.connect();
		
		System.out.println("Got 12x Context Successfully");
		return context12x;
	}

	private Context get17xContext(String User, String Pass, String Role, String RMI) throws Exception
	{
		System.out.println("Getting 17x Context");
		
		System.out.println("Getting 17x Context ::: RMI "+RMI);
		System.out.println("Getting 17x Context ::: User"+User);
		System.out.println("Getting 17x Context ::: Pass"+Pass);
		System.out.println("Getting 17x Context ::: Role"+Role);
		
		matrix.db.Context context17x = new Context(":bos", RMI);
		context17x.setUser(User);
		context17x.setPassword(Pass);
		context17x.setRole(Role);
		context17x.connect();
		System.out.println("Got Context Successfully");
		return context17x;
	}
	
	public static void main(String args[]) throws Exception
	{
//		MapList mlTemp = new MapList();
//		callWebService(mlTemp);
		
		testClass obj = new testClass();
		
		Context context = obj.get17xContext(User,Pass,Role,Dev1RMI);
		
		System.out.println("context--->" +(String)context.getUser());
		System.out.println("context role --->" +(String)context.getRole());
		
		try 
		{
			
			ContextUtil.pushContext(context);
			System.out.println("context (Pushed)--->" +(String)context.getUser());
			System.out.println("context role(Pushed) --->" +(String)context.getRole());
			
			SXValidation(context);
//			sampleMQLQueries(context);
//			correctApplicativeData(context, "60136.35780.42752.41955");
			
			//String strResult = MqlUtil.mqlCommand(context, "print bus VPMReference obj_id-70003943 --A.1 select current.access");
		} 
		
		catch(Exception mx) 
		{
			ContextUtil.abortTransaction(context);
		} 
		
		finally
		{
			ContextUtil.popContext(context);
			context.disconnect();
		}
		System.out.println("Current Time at disconnecting >>> "+new Timestamp((new Date()).getTime()));
		System.out.println("Context Disconnected");
	}


	private static void SXValidation(Context context) throws Exception
	{

		MapList mlPPDetails = getPhyProdDetails(context);
//		MapList mlDWGDetails = getDWGInfo(context);
		
	}
	
	/*private static MapList get3DShapeDetails(Context context) throws Exception
	{
		MapList mlReturn = new MapList();
		
		MapList ml3ShDetails  = new MapList();
		
		StringList slSelects = new StringList();
		
		slSelects.add(DomainConstants.SELECT_TYPE);
		slSelects.add(DomainConstants.SELECT_NAME);
		slSelects.add(DomainConstants.SELECT_REVISION);
		slSelects.add(DomainConstants.SELECT_OWNER);
		slSelects.add(DomainConstants.SELECT_ORIGINATED);
		slSelects.add(DomainConstants.SELECT_MODIFIED);
		slSelects.add("attribute[PLMEntity.V_Name].value");
		slSelects.add("to[VPMRepInstance].from.attribute[PLMEntity.V_Name].value");
		slSelects.add("to[VPMRepInstance].from.attribute[PLMEntity.V_usage].value");
		slSelects.add("format.file.name");
		
		
		try {
			
			ml3ShDetails = DomainObject.findObjects(context, "3DShape", "vplm", DomainConstants.EMPTY_STRING, slSelects);
			System.out.println("ml3ShDetails >>>"+ml3ShDetails);
			
		} 
		catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mlReturn.addAll(ml3ShDetails);
		return mlReturn;
	}*/
	
	
	private static MapList getPhyProdDetails(Context context) throws Exception
	{
		System.out.println("INSIDE getPhyProdDetails");
		
		System.out.println("Current Time start of getPhyProdDetails >>> "+new Timestamp((new Date()).getTime()));
		
		MapList mlReturn = new MapList();
		
		MapList mlPhyProdDetails  = new MapList();
		
		StringList slSelects = new StringList();
		
		slSelects.add(DomainConstants.SELECT_TYPE);
		slSelects.add(DomainConstants.SELECT_NAME);
		slSelects.add(DomainConstants.SELECT_REVISION);
		slSelects.add("attribute[PLMEntity.V_Name].value");
		slSelects.add("attribute[PLMEntity.V_description].value");
		slSelects.add(DomainConstants.SELECT_OWNER);
		slSelects.add(DomainConstants.SELECT_ORIGINATED);
		slSelects.add(DomainConstants.SELECT_MODIFIED);
		slSelects.add("attribute[PLMEntity.V_usage].value");
			
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.type");  //3DShape Type
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.name");  //3DShape Name
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.revision");  //3DShape Revision
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.attribute[PLMEntity.V_Name].value");  //3DShape Title
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.owner");  //3DShape Owner
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.originated");  //3DShape Originated
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.modified");  //3DShape Modified		
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.attribute[PLMEntity.V_usage].value"); //3DShape Usage attr
		slSelects.add("from[VPMRepInstance|to.type==\"3DShape\"].to.format.file.name"); //3DShape File Info
		
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.type");  //Drawing Type
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.name");  //Drawing Name
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.revision");  //Drawing Revision
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.attribute[PLMEntity.V_Name].value");  //Drawing Title
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.owner");  //Drawing Owner
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.originated");  //Drawing Originated
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.modified");  //Drawing Modified		
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.attribute[PLMEntity.V_usage].value"); //Drawing Usage attr
		slSelects.add("from[VPMRepInstance|to.type==\"Drawing\"].to.format.file.name"); //Drawing File Info
		
		
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.type"); //Child PP Type
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.name"); //Child PP Name
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.revision"); //Child PP Revision
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.attribute[PLMEntity.V_Name].value"); //Child PP Title
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.attribute[PLMEntity.V_description].value"); //Child PP Description
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.owner"); //Child PP Owner
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.originated"); //Child PP Originated
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.modified"); //Child PP Modified
		slSelects.add("from[VPMInstance|to.type==\"VPMReference\"].to.attribute[PLMEntity.V_usage].value"); //Child PP Usage
		
		try 
		{
//			mlPhyProdDetails = DomainObject.findObjects(context, "VPMReference", "vplm", DomainConstants.EMPTY_STRING, slSelects);
			
			mlPhyProdDetails = DomainObject.findObjects(context, "VPMReference", "vplm", DomainConstants.EMPTY_STRING, slSelects, (short)10 , DomainConstants.EMPTY_STRINGLIST); 
//			System.out.println("mlPhyProdDetails >>>:: "+mlPhyProdDetails);
//			callWebService(mlPhyProdDetails);
//			textFileToExcel.writeToExcel(mlPhyProdDetails);
			System.out.println("mlPhyProdDetails >>>:: Check Excel");
			
		} 
		catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mlReturn.addAll(mlPhyProdDetails);
		
//		System.out.println("getPhyProdDetails >> mlReturn >>>"+mlReturn);
		System.out.println("Current Time end of getPhyProdDetails >>> "+new Timestamp((new Date()).getTime()));
		return mlReturn;
	}
	
	public static MapList getDWGInfo(Context context) throws Exception
	{
		System.out.println("INSIDE getDWGInfo ::::");
		
		System.out.println("Current Time start of getDWGInfo >>> "+new Timestamp((new Date()).getTime()));
		
		MapList mlDWGReturn = new MapList();
		
		MapList mlDwgInfo = new MapList();
		
		StringList slSelects = new StringList();

		slSelects.add(DomainConstants.SELECT_TYPE);
		slSelects.add(DomainConstants.SELECT_NAME);
		slSelects.add(DomainConstants.SELECT_REVISION);
		slSelects.add("attribute[PLMEntity.V_Name].value");
		slSelects.add("attribute[PLMEntity.V_description].value");
		slSelects.add(DomainConstants.SELECT_OWNER);
		slSelects.add(DomainConstants.SELECT_ORIGINATED);
		slSelects.add(DomainConstants.SELECT_MODIFIED);
		slSelects.add("attribute[PLMEntity.V_usage].value");
		slSelects.add("attribute[PLMCoreRepReference.V_isOnceInstantiable].value");
		slSelects.add("format.file.name");
		
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.type");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.name");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.revision");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.attribute[PLMEntity.V_Name].value");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.attribute[PLMEntity.V_description].value");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.owner");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.originated");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.modified");
		slSelects.add("to[VPMRepInstance|from.type==\"VPMReference\"].from.attribute[PLMEntity.V_usage].value");
		
		try
		{
			mlDwgInfo = DomainObject.findObjects(context, "Drawing", "vplm", DomainConstants.EMPTY_STRING, slSelects);
			
//			mlDwgInfo = DomainObject.findObjects(context, "Drawing", "drw00000103", "---.1", "999901009", "vplm", DomainConstants.EMPTY_STRING, false, slSelects); 
			  
			System.out.println("mlDwgInfo ::: "+mlDwgInfo);
			
			String[] columns = {"Type", "Name", "Revision","Part Number", "Description", "Owner", "Originated","Modified","Usage","Is Shared", "File Info 1","File Info 2","PP Type","PP Name","PP Revision","PP Part Number","PP Description", "PP Owner","PP Originated","PP Modified","PP Usage"};
			
			textFileToExcel.dwgInfoToExcel(columns, mlDwgInfo);
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		mlDWGReturn.addAll(mlDwgInfo);
//		System.out.println("getDWGInfo >> mlDWGReturn >>>"+mlDWGReturn);
		System.out.println("Current Time End of getDWGInfo >>> "+new Timestamp((new Date()).getTime()));
		return mlDWGReturn;
	}


	private static int mapListToExcel(MapList mlInput, String[] columns) throws Exception 
	{
		int retInt = 0;
		
		if(null != mlInput && mlInput.size()>0) 
		{
					
			Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
			
			/* CreationHelper helps us create instances for various things like DataFormat, 
		    Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
			
			CreationHelper createHelper = workbook.getCreationHelper();
			
			// Create a Sheet
		    Sheet sheet = workbook.createSheet("3ShFileValidation");
		    
		    // Create a Font for styling header cells
		    Font headerFont = workbook.createFont();
		    headerFont.setFontHeightInPoints((short) 14);
		    headerFont.setColor(IndexedColors.RED.getIndex());
		
		    // Create a CellStyle with the font
		    CellStyle headerCellStyle = workbook.createCellStyle();
		    headerCellStyle.setFont(headerFont);
		
		    // Create a Row
		    Row headerRow = sheet.createRow(0);
		
		    // Creating cells
		    for(int i = 0; i < columns.length; i++) 
		    {
		        Cell cell = headerRow.createCell(i);
		        cell.setCellValue(columns[i]);
		        cell.setCellStyle(headerCellStyle);
		    }
		
		    // Create Cell Style for formatting Date
		    CellStyle dateCellStyle = workbook.createCellStyle();
		    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		    
		    int rowNum = 1;
	        for(int ml=0; ml<mlInput.size(); ml++) 
	        {
	        	Map mTemp = (Map)mlInput.get(ml);
	            Row row = sheet.createRow(rowNum++);
	
	            row.createCell(0)
	                    .setCellValue((String)mTemp.get(DomainConstants.SELECT_TYPE));
	
	            row.createCell(1)
	                    .setCellValue((String)mTemp.get(DomainConstants.SELECT_NAME));
	
	            row.createCell(2)
                .setCellValue((String)mTemp.get(DomainConstants.SELECT_REVISION));
	            
	            row.createCell(3)
                .setCellValue((String)mTemp.get(DomainConstants.SELECT_OWNER));
	            
	            Cell originatedCell = row.createCell(4);
	            originatedCell.setCellValue((String)mTemp.get(DomainConstants.SELECT_ORIGINATED));
	            originatedCell.setCellStyle(dateCellStyle);
	            
	            row.createCell(5)
                .setCellValue((String)mTemp.get("attribute[PLMEntity.V_Name].value"));
	            
	            row.createCell(6)
                .setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"));
	            
	            row.createCell(7)
                .setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"));
	
	            row.createCell(8)
                .setCellValue((String)mTemp.get("format.file.name"));
	        }
	
			// Resize all columns to fit the content size
	        for(int i = 0; i < columns.length; i++) {
	            sheet.autoSizeColumn(i);
	        }
	
	        // Write the output to a file
	        FileOutputStream fileOut = new FileOutputStream("C:\\Personal\\Work\\SX-DM-Validation.xlsx");
	        workbook.write(fileOut);
	        fileOut.close();
	
	        // Closing the workbook
//	        workbook.close();
		}
    
		
		return retInt;
	
}

	
	private static int sampleMQLQueries(Context context) throws Exception
	{
		int iRet = 0;
		
		String strResult = MqlUtil.mqlCommand(context, "print type CATMCXMechanicalConnection select policy");
		System.out.println("MQL Result ::: "+strResult);
		
		
		return iRet;
	}


	private static void gas12xQueries(Context context) throws Exception
	{
		MapList mlStdParts = new MapList();
		try 
		{
			StringList slSelects = new StringList();
			slSelects.add(DomainConstants.SELECT_TYPE);
			slSelects.add(DomainConstants.SELECT_NAME);
			slSelects.add(DomainConstants.SELECT_REVISION);
			slSelects.add("to[Design Responsibility].from.type");
			slSelects.add("to[Design Responsibility].from.name");
			slSelects.add("to[Design Responsibility].from.revision");
			
			String strWhere = "to[Design Responsibility].from.name==\"STANDARD PARTS\"";
			
			mlStdParts = DomainObject.findObjects(context, "Part", "eService Production", strWhere, slSelects);
			System.out.println("mlPhyProdDetails >>>"+mlStdParts);
			
		} 
		catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int updatePersonDetailsToPassport(Context context, String sUserName,String sFirstName,String sLastName,String sCountry, String sEmailId ) throws Exception
	{
		System.out.println("INSIDE update person Details to Passport");
		int retCode = 0;		
		String sResult = null;
		
		if(sUserName == null || sUserName.equals("") || sEmailId == null || sEmailId.equals("") || sFirstName == null || sFirstName.equals("") || sLastName == null || sLastName.equals("") || sCountry == null || sCountry.equals(""))
		{
			//String sMsg= "user name, email, first name, last name and country are mandatory";
			/*String sMsg=i18nNow.getI18nString("emxFramework.GEPushToPassport.Mandatoryfieldscheck","emxFrameworkStringResource", context.getSession().getLanguage());
			MqlUtil.mqlCommand(context, "notice $1",sMsg );
			System.out.println("INSIDE update person Details to Passport ::: sMsg ::: "+sMsg);
			throw new Exception(sMsg);			*/
			System.out.println("INSIDE update person Details to Passport ::: sMsg ::: Mandatory attributes are empty");
			retCode = 1;
			return retCode;
		}

		try{
//			String sPassportURL = getPassportURL(context);
			String sPassportURL = "https://dev-3dpplm-dp.pw.ge.com/3dpassport/";
			System.out.println("sPassportURL================>"+sPassportURL);
			String sKey = getSkey(sPassportURL);
			System.out.println("sKey================>"+sKey);
//			String strpwd = "TEST123test";
			String pushPersonDetails = "{\"active\":true,\"lastSynchronized\":0,\"sourceRepositories\":[],\"fields\":{\"lastName\":\"" + sLastName + "\",\"country\":\"" + sCountry + "\",\"username\":\"" + sUserName + "\",\"email\":\"" + sEmailId + "\",\"firstName\":\"" + sFirstName + "\"},\"acls\":{\"3dexperience\":\"ACCEPTED\",\"passport\":\"ACCEPTED\"},\"tenant\":null}";
			

			//System.out.println(pushPersonDetails);
			System.out.println("sUserName==============>"+sUserName);

			URL localURL = new URL(sPassportURL + "/api/private/user/update/"+sUserName);
			HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
			localHttpURLConnection.setDoOutput(true);
			localHttpURLConnection.setRequestMethod("POST");
			localHttpURLConnection.addRequestProperty("skey", sKey);
			localHttpURLConnection.addRequestProperty("Content-Type", "application/json");
			localHttpURLConnection.addRequestProperty("Accept", "application/json");
			OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(localHttpURLConnection.getOutputStream());
			localOutputStreamWriter.write(pushPersonDetails);
			localOutputStreamWriter.flush();
			BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));

			for (String str2 = localBufferedReader1.readLine(); str2 != null; str2 = localBufferedReader1.readLine())
			{
				sResult = str2;
			}
			if(sResult.indexOf(":-1")>0 || ("".equals(sResult) ) )
			{
				//throw new Exception(sResult);
				String sMessage = getErrorMessage(sResult);
				if(sMessage !=null)
				{
					MqlUtil.mqlCommand(context, "notice $1", sMessage);
					throw new Exception(sMessage);
				}
			}else
			{
				retCode=0;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		return retCode;

	}
	
	public static String getErrorMessage(String sMessage) throws Exception
	{
		String sResult = sMessage;
		int idx = sResult.indexOf("errorCode=");
		if(idx >0)
		{
			sResult = sResult.substring(idx+10,sMessage.length());
			int idx1 = sResult.indexOf(",");
			sResult = sResult.substring(0,idx1);
		}
		return sResult;
	}
	
/*	public static String getPassportURL(Context context) throws Exception
	{
		System.out.println("INSIDE getPassportURL");
		String passportURL = null;
		Properties connConfigProps = GEPLMCommonUtil.getObjectProperties(context,"GE Configuration","GE_PLM_IDMConnect","-",  "attribute[\"GE Configuration Data\"]");
		passportURL = connConfigProps.getProperty("PASSPORT_URL");
		System.out.println("=========passportURL==========="+passportURL);
		return passportURL;
	}*/
	
	public static String getSkey(String paramString)
	{
		System.out.println("INSIDE getSkey");
		try
		{
			URL	localURL=new URL(paramString+"/api/public/skey");
			HttpURLConnection	localHttpURLConnection=(HttpURLConnection)localURL.openConnection();
			localHttpURLConnection.setDoOutput(true);
			localHttpURLConnection.setRequestMethod("POST");
			localHttpURLConnection.addRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			localHttpURLConnection.addRequestProperty("Accept", "*/*");
			OutputStreamWriter	localOutputStreamWriter	= new OutputStreamWriter(localHttpURLConnection.getOutputStream());
			localOutputStreamWriter.write("admin_platform");
			localOutputStreamWriter.flush();
			BufferedReader	localBufferedReader = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
			String str1 = localBufferedReader.readLine();
			return str1.replace("\"", "");
		}
		catch (IOException localIOException)
		{
			localIOException.printStackTrace();
		}
		return null;
	}

	public static void correctApplicativeData(Context context, String strObjId) throws Exception
	{
		System.out.println("Inside Correct Applicative Data");
		System.out.println("Current Time >>> "+new Timestamp((new Date()).getTime()));
//		strObjId = "60136.35780.42752.41955";
		
		Pattern relPattern = new Pattern("VPMInstance");
		relPattern.addPattern("VPMRepInstance"); //knowledgeware, Markup
		relPattern.addPattern("VPLMrel/PLMConnection/V_Owner"); //Engineering Connection, PLMDocConnection(Ref Doc), Material
		relPattern.addPattern("VPLMrel/PLMPort/V_Owner"); //Publication
		
		StringList slObjSelects = new StringList();
		
		slObjSelects.add(DomainConstants.SELECT_ID);
		slObjSelects.add(DomainConstants.SELECT_TYPE);
		slObjSelects.add(DomainConstants.SELECT_OWNER);
		slObjSelects.add(DomainConstants.SELECT_CURRENT);
		
		
		DomainObject domVPMRef = DomainObject.newInstance(context, strObjId);
		MapList mlAppDataInfo = domVPMRef.getRelatedObjects(context, relPattern.toString(), DomainConstants.QUERY_WILDCARD, slObjSelects, DomainConstants.EMPTY_STRINGLIST, false, true, (short)0 , DomainConstants.EMPTY_STRING, DomainConstants.EMPTY_STRING, 0);
		
		System.out.println("mlAppDataInfo >>>"+mlAppDataInfo);
		
//				getRelatedObjects(context, relPattern.toString(), DomainConstants.EMPTY_STRING, slObjSelects, DomainConstants.EMPTY_STRINGLIST, true, false, (short)0 , DomainConstants.EMPTY_STRING, 0);
		
	//	(Context context, String relationshipPattern, String typePattern, StringList objectSelects, StringList relationshipSelects, boolean getTo, boolean getFrom, short recurseToLevel, String objectWhere, String relationshipWhere, int limit) ;
	}


	public static void callWebService(String sInput)throws Exception
	{
		StringBuilder sbOutput = new StringBuilder();
		String testUrl = "http://localhost:8080/RSearch/services/hello/test";
		String indexUrl = "http://localhost:8080/RSearch/services/index/data";
		
		String encodedURL=java.net.URLEncoder.encode(sInput,"UTF-8");
		System.out.println(encodedURL);
		
		String params = "?data="+encodedURL;
		
		try
		{
			
			URL url = new URL(indexUrl+params);
			System.out.println("Updated URL >>> "+url);

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/plain");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			
			String output = "";
				
			while ((output = br.readLine()) != null) 
			{
				sbOutput.append(output);
					
			}
			System.out.println("WebService Response >>>"+sbOutput);
			connection.disconnect();
		}

		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}
}