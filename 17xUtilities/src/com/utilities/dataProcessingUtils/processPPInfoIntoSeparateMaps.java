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

public class processPPInfoIntoSeparateMaps {
	
	private static Date date = new Date(System.currentTimeMillis());
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	private static String strDate = formatter.format(date);
	
	public static List rearrangePPdata(List<Map<?, ?>> mlInput) throws Exception
	{
		List mlReturn = new ArrayList();
		
		List mlPPList = new ArrayList();
		
		Map<?, ?> mTemp = null;
		
		Map<Integer, String> mPPMap = null;
		Map<Integer, String> mRel3ShMap = null;
		Map<Integer, String> mRelDwgMap = null;
		Map<Integer, String> mRelPPMap = null;
		
		int iCounter = 0;
		
	   	 if(mlInput != null && mlInput.size() > 0)
	   	 {
	   		 for(int ml=0; ml<mlInput.size(); ml++)
	   		 {
	   			mTemp  = (HashMap<?, ?>)mlInput.get(ml);
	   			 
	   			for(int mTempSize = 0; mTempSize < mTemp.size(); mTempSize++)
				{
	   				/*iCounter = 0;
	   				iCounter = 0;
	   				iCounter = 0;*/
	   				iCounter = 0;
	   				
	   				mPPMap = new HashMap();
	   				mRel3ShMap = new HashMap();
	   				mRelDwgMap = new HashMap();
	   				mRelPPMap = new HashMap();
	   				
	   				mPPMap.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_TYPE));
	   				iCounter++;
	   				mPPMap.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_NAME));
	   				iCounter++;
	   				mPPMap.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_REVISION));
	   				iCounter++;
	   				mPPMap.put(iCounter, (String)mTemp.get("attribute[PLMEntity.V_Name].value"));
	   				iCounter++;
	   				mPPMap.put(iCounter, (String)mTemp.get("attribute[PLMEntity.V_description].value"));
	   				iCounter++;
		   			mPPMap.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_OWNER));
		   			iCounter++;
		   			mPPMap.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_ORIGINATED));
		   			iCounter++;
		   			mPPMap.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_MODIFIED));
		   			iCounter++;
		   			mPPMap.put(iCounter, (String)mTemp.get("attribute[PLMEntity.V_usage].value"));
		   			iCounter++;
	   				
		   			//Process PP Related Objects Information - 3DShape, Drawing, Child PP(1st level only) -- Start
		   			
		   			if(UIUtil.isNotNullAndNotEmpty((String)mTemp.get("from[VPMRepInstance].to.type")))
		   			{
		   				//PP has more than one representation
		   				if(((String)mTemp.get("from[VPMRepInstance].to.type")).contains(""))
			   			{
		   					
			   			}
		   			}
		   			
		   			//Process PP Related Objects Information - 3DShape, Drawing, Child PP(1st level only) -- End
	   				
				}
	   		 }
	   		 mlPPList.add(mPPMap);
	   		 mlPPList.add(mRel3ShMap);
	   		 mlPPList.add(mRelDwgMap);
	   		 mlPPList.add(mRelPPMap);
	   	 }
	   	mlReturn.add(mlReturn);
	   	 
		
		return mlReturn;
	}

}
