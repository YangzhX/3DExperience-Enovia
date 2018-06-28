package com.utilities.dataProcessingUtils;

import java.util.HashMap;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.framework.ui.UIUtil;

import matrix.util.StringList;

public class AssociativeDataProcessingUtils {

	public static MapList processPPandAssociativeData(MapList mlPPDetails, MapList mlAssociatedData) throws Exception
	{
		MapList mlReturn = new MapList();
		Map<Integer, String> mlReorderedMap = null;
		int iCounter = 0;
		
		String sPPIDTemp = "";
		
		Map<?, ?> mPPTemp = null;
		Map mADTemp = null;
		
		if(mlPPDetails != null && mlPPDetails.size()>0)
		{
			for(int i=0; i<mlPPDetails.size(); i++)
			{
				mlReorderedMap = new HashMap();
				iCounter = 0;
				
				mPPTemp = (HashMap<?, ?>)mlPPDetails.get(i);
				
				sPPIDTemp = (String)mPPTemp.get(DomainConstants.SELECT_ID);
				
				mlReorderedMap.put(iCounter, (String)mPPTemp.get(DomainConstants.SELECT_TYPE));
				iCounter++;
				mlReorderedMap.put(iCounter, (String)mPPTemp.get(DomainConstants.SELECT_NAME));
				iCounter++;
				mlReorderedMap.put(iCounter, (String)mPPTemp.get(DomainConstants.SELECT_REVISION));
				iCounter++;
				mlReorderedMap.put(iCounter, (String)mPPTemp.get("attribute[PLMEntity.V_Name].value"));
				iCounter++;
				mlReorderedMap.put(iCounter, (String)mPPTemp.get("attribute[PLMEntity.V_description].value"));
				iCounter++;
				mlReorderedMap.put(iCounter, (String)mPPTemp.get(DomainConstants.SELECT_ORIGINATED));
				iCounter++;
				mlReorderedMap.put(iCounter, (String)mPPTemp.get(DomainConstants.SELECT_CURRENT));
				iCounter++;
				
				if(mlAssociatedData != null && mlAssociatedData.size()>0)
				{
					for(int j=0; j<mlAssociatedData.size(); j++)
					{
						mADTemp = (HashMap)mlAssociatedData.get(j);
						
						if(mADTemp !=null && mADTemp.size()>0)
						{
							if(sPPIDTemp.equalsIgnoreCase((String)mADTemp.get(DomainConstants.SELECT_ID)))
							{
								if(UIUtil.isNotNullAndNotEmpty((String)mADTemp.get("from[VPMRepInstance].to.type")))
								{
						   			if(((String)mADTemp.get("from[VPMRepInstance].to.type")).contains(""))
						   			{
						   				StringList slTemp = FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.type"), "");
						   				
						   				if("Knowledgeware".equalsIgnoreCase((String)slTemp.get(0)))
						   				{
						   					//Put Knowledgeware info by fetching all the 0th index data -- Start
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.type"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.name"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.current"), "").get(0));
						   					iCounter++;
						   					//Put Knowledgeware info by fetching all the 0th index data -- End
						   					
						   					//Put Review(Markup) info by fetching all the 1st index data -- Start
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.type"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.name"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.current"), "").get(1));
						   					iCounter++;
						   					//Put Review(Markup) info by fetching all the 1st index data -- End

						   				}
						   				else if("Review".equalsIgnoreCase((String)slTemp.get(0)))
				   						{
						   					//Put Knowledgeware info by fetching all the 1st index data -- Start
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.type"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.name"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.current"), "").get(1));
						   					iCounter++;
						   					//Put Knowledgeware info by fetching all the 1st index data -- End

						   					//Put Review(Markup) info by fetching all the 0th index data -- Start
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.type"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.name"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.current"), "").get(0));
						   					iCounter++;
						   					//Put Review(Markup) info by fetching all the 0th index data -- End
				   						}
						   			}
						   			else if("Knowledgeware".equalsIgnoreCase((String)mADTemp.get(DomainConstants.SELECT_ID)))
						   			{
					   					mlReorderedMap.put(iCounter, (String)mADTemp.get("from[VPMRepInstance].to.type"));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.name"), "").get(0));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.current"), "").get(0));
					   					iCounter++;

						   			}
						   			else if("Review".equalsIgnoreCase((String)mADTemp.get(DomainConstants.SELECT_ID)))
						   			{
						   				if(iCounter == 6)
						   				{
						   					mlReorderedMap.put(iCounter, "");
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, "");
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, "");
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, "");
						   					iCounter++;
						   					mlReorderedMap.put(iCounter, "");
						   					iCounter++;
						   				}
					   					mlReorderedMap.put(iCounter, (String)mADTemp.get("from[VPMRepInstance].to.type"));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.name"), "").get(0));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
					   					iCounter++;
					   					mlReorderedMap.put(iCounter, (String)FrameworkUtil.split((String)mADTemp.get("from[VPMRepInstance].to.current"), "").get(0));
					   					iCounter++;
						   			}
						   			
								}
							}
						}
					}
				}
				mlReturn.add(mlReorderedMap);
			}
		}
		
		return mlReturn;
	}
}
