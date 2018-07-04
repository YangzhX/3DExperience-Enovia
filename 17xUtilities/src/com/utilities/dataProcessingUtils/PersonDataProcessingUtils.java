package com.utilities.dataProcessingUtils;

import java.util.HashMap;
import java.util.Map;

import com.matrixone.apps.domain.util.MapList;

public class PersonDataProcessingUtils {
	
	public static MapList processPersonInfo(MapList mlInput) throws Exception
	{
		MapList mlReturn = new MapList();
		
		Map mProcessedMap = null;
		int iCounter = 0;
		
		if(mlInput != null && mlInput.size() >0)
		{
			for(int i=0; i<mlInput.size(); i++)
			{
				mProcessedMap = new HashMap();
				iCounter = 0;
			}
		}
		
		return mlReturn;
	}

}
