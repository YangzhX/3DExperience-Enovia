package com.utilities.dataProcessingUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.framework.ui.UIUtil;

import matrix.util.StringList;

//This class is for rearranging the data received from the query to write to excel.
//Input data should be a MapList and should contain PP Details and its connected 3DShape and Drawing Details.
//Selectables of the objects include "Type", "Name", "Revision","Part Number", "Description", "Owner", "Originated","Modified","Usage".
//Selectables specific to 3DShape and Drawing = Above Selectables + File Info.

public class processPPInfo {
	
	private static Date date = new Date(System.currentTimeMillis());
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	private static String strDate = formatter.format(date);
	
	public static List rearrangePPdata(List<Map<?, ?>> mlInput) throws Exception
	{
		//System.out.println("Inside reArrangePPData >>>");
		//System.out.println("Current reArrangePPData Method >>> "+new Timestamp((new Date()).getTime()));
		List<Map<?,?>> mlReturn = new ArrayList();
		
		Map<Integer, String> mPPMap = null;
		Map<Integer, String> mRel3ShMap = null;
		Map<Integer, String> mRelDwgMap = null;
		
		Map<Integer, String> mReorderedMap = null;
		
		MapList mReorderedMapList = new MapList();
		MapList mlTempReordered = null;		
		
		String s3ShFiles = "";
		String sDwgFiles = "";
		
		StringList sl3ShFiles = new StringList();
		StringList slDwgFiles = new StringList();
		
		
		int iPPMapCounter = 0;
		int iRel3ShMapCounter = 0;
		int iRelDwgMapCounter = 0;
		
		int iReorderedMapCounter = 0;
		
   	 if(mlInput != null && mlInput.size() > 0)
   	 {
   		 for(int ml=0; ml<mlInput.size(); ml++)
   		 {
   			 Map<?, ?> mTemp = (HashMap<?, ?>)mlInput.get(ml);
   			 
   			for(int mTempSize = 0; mTempSize < mTemp.size(); mTempSize++)
			{
   				/*iReorderedMapCounter = 0;
   				iReorderedMapCounter = 0;
   				iReorderedMapCounter = 0;*/
   				
   				iReorderedMapCounter = 0;
   				
//   			mlTempReordered = new MapList();
   				
   				mReorderedMap = new HashMap<Integer, String>();
   				mReorderedMap = new HashMap<Integer, String>();
   				mReorderedMap = new HashMap<Integer, String>();
   				
   				mReorderedMap = new HashMap<Integer, String>();
   				
   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get(DomainConstants.SELECT_TYPE));
   				iReorderedMapCounter++;
   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get(DomainConstants.SELECT_NAME));
   				iReorderedMapCounter++;
   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get(DomainConstants.SELECT_REVISION));
   				iReorderedMapCounter++;
   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("attribute[PLMEntity.V_Name].value"));
   				iReorderedMapCounter++;
   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("attribute[PLMEntity.V_description].value"));
   				iReorderedMapCounter++;
	   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get(DomainConstants.SELECT_OWNER));
	   			iReorderedMapCounter++;
	   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get(DomainConstants.SELECT_ORIGINATED));
	   			iReorderedMapCounter++;
	   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get(DomainConstants.SELECT_MODIFIED));
	   			iReorderedMapCounter++;
	   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("attribute[PLMEntity.V_usage].value"));
	   			iReorderedMapCounter++;
	   			
	   			if(UIUtil.isNotNullAndNotEmpty((String)mTemp.get("from[VPMRepInstance].to.type")))
				{
	   				//System.out.println("\nInside from[VPMRepInstance].to.type not equal to NullandEmpty :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
	   				
		   			if(((String)mTemp.get("from[VPMRepInstance].to.type")).contains(""))
		   			{
		   				//System.out.println("\nInside from[VPMRepInstance].to.type.contains(\"\") :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
		   				StringList slTemp = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "");
		   				
		   				//******If 3DShape comes 1st in Selectable -- Start ******/
		   				if("3DShape".equalsIgnoreCase((String)slTemp.get(0)))
						{
		   					//System.out.println("\nInside If 3DShape comes 1st in Selectable :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
		   					//******Put 3DShape info (except file info) into rel3DShMap by fetching all the 1st index information -- Start
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(0));
		   					iReorderedMapCounter++;
		   					//******Put 3DShape info (except file info)  into rel3DShMap by fetching all the 1st index information -- End
		   							   					
		   					//******Process File Information -- Start
		   					
		   					//******Get indices of separator
		   					
		   					List<Integer> iIndiceslist = getIndices((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), '');
		   					//*******************
		   					if(iIndiceslist.size() == 3)//Both Representaions's Files got loaded to v6 Format
							{

		   						//******Put 3DShape File Information -- Start
		   						
								s3ShFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of 3DShape
								mReorderedMap.put(iReorderedMapCounter, s3ShFiles.replaceAll("", "\r\n"));
								iReorderedMapCounter++;
								//******Put 3DShape File Information -- End
								//Put DWG information after putting the 3Dshape File Information -- Start
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));
			   					iReorderedMapCounter++;
			   					//Put DWG information after putting the 3Dshape File Information -- End
								
								//******Process Drawing File Information -- Start
								
								sDwgFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get 2Files of Drawing
								
								mReorderedMap.put(iReorderedMapCounter, sDwgFiles.replaceAll("", "\r\n"));
								iReorderedMapCounter++;
								
								//**Put 2 v6 Files separately -- start
								/*slDwgFiles = FrameworkUtil.split(sDwgFiles, ""); //Split 2Files of Drawing
								
								mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(0)); //Put 1st file in relDwgMap
								iReorderedMapCounter++;
								mReorderedMap.put(iReorderedMapCounter, ((String)slDwgFiles.get(1)).replaceAll("", ""));//Put 2nd file in relDwgMap
								iReorderedMapCounter++;*/
								//**Put 2 v6 Files separately -- end
														
								//******Process Drawing File Information -- End
							}
							else if(iIndiceslist.size() == 2)//1 Representation File got converted to v6 Format
							{
								//System.out.println("\nInside If 3DShape comes 1st in Selectable + 1Rep Passed and 1Rep Failed:::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
								
								s3ShFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 1st two files
	//							//System.out.println("s3ShTemp3 >>>"+s3ShTemp3);
								sl3ShFiles = FrameworkUtil.split(s3ShFiles, "");
								
								if(((String)sl3ShFiles.get(0)).contains("CATIA")) //3DShape Failed, Drawing Passed
								{
									//System.out.println("\nInside If 3DShape comes 1st in Selectable + 3DShape Failed and Drawing Passed:::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
									mReorderedMap.put(iReorderedMapCounter, (String)sl3ShFiles.get(0)); //Put Failed 3sh File in rel3ShMap
									iReorderedMapCounter++;
									
									//Put DWG information after putting the 3Dshape File Information -- Start
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));
				   					iReorderedMapCounter++;
				   					//Put DWG information after putting the 3Dshape File Information -- End

									
									//Write DWG Files -- Start
									sDwgFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(0)); //get 2Files of Drawing
									mReorderedMap.put(iReorderedMapCounter, sDwgFiles.replace("", "\r\n")); //Put 1st file in relDwgMap
									iReorderedMapCounter++;
									
									
									//**Put 2 v6 Files separately -- start
									/*slDwgFiles = FrameworkUtil.split(sDwgFiles, "");
									mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(0)); //Put 1st file in relDwgMap
									iReorderedMapCounter++;
									mReorderedMap.put(iReorderedMapCounter, ((String)slDwgFiles.get(1)).replaceAll("", ""));//Put 2nd file in relDwgMap
									iReorderedMapCounter++;*/
									//**Put 2 v6 Files separately -- end
									
									//Write DWG Files -- End
									
								}
								else //3DShape Pass, Drawing Failed
								{
									//System.out.println("\nInside If 3DShape comes 1st in Selectable + 3DShape Passed and Drawing Failed:::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
									
									mReorderedMap.put(iReorderedMapCounter, s3ShFiles.replaceAll("", "\r\n"));
									iReorderedMapCounter++;
									
									//**Put 2 v6 Files separately -- Start
									/*mReorderedMap.put(iReorderedMapCounter, (String)sl3ShFiles.get(0)); //Put 1st file in rel3shMap
									iReorderedMapCounter++;
									mReorderedMap.put(iReorderedMapCounter, ((String)sl3ShFiles.get(1)).replaceAll("", "")); //Put 2nd file in rel3ShMap
									iReorderedMapCounter++;*/
									//**Put 2 v6 Files separately -- end
									
									//Put DWG information after putting the 3Dshape File Information -- Start
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
				   					iReorderedMapCounter++;
				   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));
				   					iReorderedMapCounter++;
				   					//Put DWG information after putting the 3Dshape File Information -- End

									
									//Write DWG File Info -- Start
									sDwgFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get File of Drawing
									mReorderedMap.put(iReorderedMapCounter, sDwgFiles.replaceAll("", "")); //Put Failed  Drawing File in relDwgMap
									iReorderedMapCounter++;
									
									//Write DWG File Info -- End
								}
							}
							else if(iIndiceslist.size() == 1)//Both Representations Failed to convert
							{
								//System.out.println("\nInside If 3DShape comes 1st in Selectable + Both Reps Failed :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
	
								sl3ShFiles = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), "");
								
								mReorderedMap.put(iReorderedMapCounter, (String)sl3ShFiles.get(0)); //Put failed 3Sh File in rel3ShMap
								iReorderedMapCounter++;
								
								//Put DWG information after putting the 3Dshape File Information -- Start
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
			   					iReorderedMapCounter++;
			   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));
			   					iReorderedMapCounter++;
			   					//Put DWG information after putting the 3Dshape File Information -- End

								mReorderedMap.put(iReorderedMapCounter, (String)sl3ShFiles.get(1)); //Put failed Drawing File in relDwgMap
								iReorderedMapCounter++;
							}
		   					
		   					//*********************************************
		   					//******Process File Information -- End
						}
		   				//******If 3DShape comes 1st in Selectable -- End ******//
		   				
		   				//******If Drawing comes 1st in Selectable -- Start ******//
		   				
		   				if("Drawing".equalsIgnoreCase((String)slTemp.get(0)))
		   				{
		   					//System.out.println("\nInside If Drawing comes 1st in Selectable :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
		   					
		   					//******Put 3DShape info (except file info) into rel3DShMap by fetching all the 2nd index information -- Start
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));
		   					iReorderedMapCounter++;
		   					
		   					//******Put 3DShape info (except file info) into rel3DShMap by fetching all the 2nd index information -- End
		   					
		   					//******Put Drawing info (except file info) into relDwgMap by fetching all the 1st index information -- Start
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
		   					iReorderedMapCounter++;
		   					
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(0));
		   					iReorderedMapCounter++;
	
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(0));
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(0));
		   					iReorderedMapCounter++;
		   					
		   					//******Put Drawing info (except file info) into relDwgMap by fetching all the 1st index information -- End
		   					
		   					
	   						//******Process File Information -- Start
		   					//******Get indices of separator
		   					
		   					List<?> iIndiceslist = getIndices((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), '');
		   					
							if(iIndiceslist.size() == 3)//Both Representaions's Files got loaded to v6 Format
							{
								//System.out.println("\nInside If Drawing comes 1st in Selectable ++ Both Representaions's Files got loaded to v6 Format :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
								
								sDwgFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of Drawing
								mReorderedMap.put(iReorderedMapCounter, sDwgFiles.replaceAll("", "\r\n"));
								iReorderedMapCounter++;
								
								//**Put 2 v6 Files separately -- start
								/*slDwgFiles = FrameworkUtil.split(sDwgFiles, "");
								
								mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(0)); //Put 1st file in relDwgMap
								iReorderedMapCounter++;
								mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(1)).replaceAll("", ""); //Put 2nd file in relDwgMap
								iReorderedMapCounter++;*/
								//**Put 2 v6 Files separately -- end
								
								s3ShFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get 2Files of 3DShape
								
								mReorderedMap.put(iReorderedMapCounter, s3ShFiles.replaceAll("", "\r\n"));
								iReorderedMapCounter++;
								
								//**Put 2 v6 Files separately -- start
								/*sl3ShFiles = FrameworkUtil.split(s3ShFiles, "");
														
								mReorderedMap.put(iReorderedMapCounter, (String)sl3ShFiles.get(0)); //Put 1st file in rel3ShMap
								iReorderedMapCounter++;
								mReorderedMap.put(iReorderedMapCounter, ((String)sl3ShFiles.get(1)).replaceAll("", "")); //Put 2nd file in rel3ShMap
								iReorderedMapCounter++;*/
								//**Put 2 v6 Files separately -- end
							}
							else if(iIndiceslist.size() == 2)//1 Representation File got converted to v6 Format
							{
								//System.out.println("\nInside If 3DShape comes 1st in Selectable + 1Rep Passed and 1Rep Failed:::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
							
								sDwgFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of Drawing
								slDwgFiles = FrameworkUtil.split(sDwgFiles, "");
								
								if(((String)slDwgFiles.get(0)).contains("CATIA")) //Drawing Failed,3DShape Passed
								{
									//System.out.println("\nInside If Drawing comes 1st in Selectable + Drawing Failed and 3DShape Passed:::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
									mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(0)); //Put failed Dwg file into relDwgMap
									iReorderedMapCounter++;
	
									s3ShFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(0)); //get 2Files of 3DShape
									mReorderedMap.put(iReorderedMapCounter, s3ShFiles.replaceAll("", "\r\n")); //Put 2nd file in rel3ShMap
									iReorderedMapCounter++;
									
									//**Put 2 v6 Files separately -- start
									/*sl3ShFiles = FrameworkUtil.split(s3ShFiles, "");
									
									mReorderedMap.put(iReorderedMapCounter, (String)sl3ShFiles.get(0)); //Put 1st file in rel3ShMap
									iReorderedMapCounter++;
									
									mReorderedMap.put(iReorderedMapCounter, ((String)sl3ShFiles.get(1)).replaceAll("", "")); //Put 2nd file in rel3ShMap
									iReorderedMapCounter++;*/
									//**Put 2 v6 Files separately -- end
									
								}
								else //Drawing Pass, 3DShape Failed
								{
									//System.out.println("\nInside If Drawing comes 1st in Selectable + Drawing Passed and 3DShape Failed:::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
									
									mReorderedMap.put(iReorderedMapCounter, sDwgFiles.replaceAll("", "\r\n"));
									iReorderedMapCounter++;
									
									//**Put 2 v6 Files separately -- start
									/*mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(0)); //Put 1st Dwg file into relDwgMap
									iReorderedMapCounter++;
									mReorderedMap.put(iReorderedMapCounter, ((String)slDwgFiles.get(1)).replaceAll("", "")); //Put 2nd Dwg file into relDwgMap
									iReorderedMapCounter++;*/
									//**Put 2 v6 Files separately -- end
									
									s3ShFiles = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get File of 3DShape
									mReorderedMap.put(iReorderedMapCounter, s3ShFiles.replaceAll("", ""));
									iReorderedMapCounter++;
								}
							}
							else if(iIndiceslist.size() == 1)//Both Representations Failed
							{
								//System.out.println("\nInside If Drawing comes 1st in Selectable + Both Reps Failed :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
								
								slDwgFiles = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), "");
								
								mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(0));//Put failed Drawing File in relDwgMap
								iReorderedMapCounter++;
								
								mReorderedMap.put(iReorderedMapCounter, (String)slDwgFiles.get(1));//Put failed 3Sh File in rel3ShMap
								iReorderedMapCounter++;
							}
							//******Process File Information -- End
		   				}
		   				//******If Drawing comes 1st in Selectable -- End ******//
		   			}
   			
		   			
		   			if("3DShape".equalsIgnoreCase((String)mTemp.get("from[VPMRepInstance].to.type")))
					{
		   				//System.out.println("\nInside If 3DShape is the Only Representation :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
		   				
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.type"));
		   				iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.name"));
			   			iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.revision"));
			   			iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"));
			   			iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.owner"));
			   			iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.originated"));
			   			iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.modified"));
			   			iReorderedMapCounter++;
			   			mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"));
			   			iReorderedMapCounter++;
			   			
			   			if(((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).contains(""))
			   			{
			   				//System.out.println("\nInside If 3DShape is the Only Representation + 3DShape Passed :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
			   				
			   				
			   				
			   				mReorderedMap.put(iReorderedMapCounter, ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).replaceAll("", "\r\n"));
			   				iReorderedMapCounter++;
			   				
			   				//**Put 2 v6 Files separately -- start
			   				/*StringList slTemp = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), "");
	   						mReorderedMap.put(iReorderedMapCounter, (String)slTemp.get(0));
	   						iReorderedMapCounter++;
			   				mReorderedMap.put(iReorderedMapCounter, (String)slTemp.get(1));
			   				iReorderedMapCounter++;*/
			   				//**Put 2 v6 Files separately -- end
			   			}
			   			else
			   			{
			   				//System.out.println("\nInside If 3DShape is the Only Representation + 3DShape Failed :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
			   				
			   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.format.file.name"));
			   				//System.out.println("\nFailed 3DShape File name :: "+(String)mTemp.get("from[VPMRepInstance].to.format.file.name"));
			   				iReorderedMapCounter++;
			   			}
					}
		   			
		   			 if("Drawing".equalsIgnoreCase((String)mTemp.get("from[VPMRepInstance].to.type")))
		   			 {
//		   				System.out.println("\nInside If Drawing is the Only Representation :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
		   				
		   				if(iReorderedMapCounter == 9) //Adjusting counter to fill the Drawing information properly in excel - Case - PP has only Drawing but no 3DShape
		   				{
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   					mReorderedMap.put(iReorderedMapCounter, "");
		   					iReorderedMapCounter++;
		   				}
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.type"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.name"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.revision"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.owner"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.originated"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.modified"));
		   				iReorderedMapCounter++;
		   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"));
		   				iReorderedMapCounter++;
		   				
			   			if(((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).contains(""))
			   			{
			   				//System.out.println("\nInside If Drawing is the Only Representation + Drawing Passed :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
			   				
			   				mReorderedMap.put(iReorderedMapCounter, ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).replaceAll("", "\r\n"));
			   				iReorderedMapCounter++;
			   				
			   				//**Put 2 v6 Files separately -- start
			   				
			   				/*StringList slTemp = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), "");
			   				
			   				mReorderedMap.put(iReorderedMapCounter, (String)slTemp.get(0));
			   				iReorderedMapCounter++;
			   				mReorderedMap.put(iReorderedMapCounter, (String)slTemp.get(1));
			   				iReorderedMapCounter++;*/
			   				//**Put 2 v6 Files separately -- end
			   			}
			   			else
			   			{
			   				//System.out.println("\nInside If Drawing is the Only Representation + Drawing Failed :::"+(String)mTemp.get("attribute[PLMEntity.V_Name].value"));
			   				
			   				mReorderedMap.put(iReorderedMapCounter, (String)mTemp.get("from[VPMRepInstance].to.format.file.name"));
			   				iReorderedMapCounter++;
			   			}
		   			 }
				}
//	   			mlTempReordered.add(mReorderedMap);
	   			
	   			
	   			/*mlTempReordered.add(mPPMap);
	   			mlTempReordered.add(mRel3ShMap);
	   			mlTempReordered.add(mRelDwgMap);*/
			}
//   			mReorderedMapList.add(mlTempReordered);
   			mReorderedMapList.add(mReorderedMap);
   		 }
		//System.out.println("\nInput MapList  >>>"+mlInput);
//		System.out.println("\nmReorderedMapList >>>"+mReorderedMapList);
		mlReturn.addAll(mReorderedMapList);
   	 }
   	 
   	 //System.out.println("WorkBookName >>"+WorkBookName);
   	 //System.out.println("SheetName >>"+SheetName);
   	 return mlReturn;
	}
	
	private static List<Integer> getIndices(String input, char toBeFound)
    {
    	List<Integer> iRetList = new ArrayList<Integer>();
       int[] retArray = new int[400] ;
        for (int i = 0 ; i<input.length() ; i++)
        {
	        if (input.charAt(i) == toBeFound)
	        {
		        ////System.out.println(i);
	        	iRetList.add(i);
		        ////System.out.println("retArray>>>"+retArray[i]);
	        }
        }
        
        return iRetList;
    }

}
