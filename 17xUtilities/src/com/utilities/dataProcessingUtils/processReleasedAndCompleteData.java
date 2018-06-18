package com.utilities.dataProcessingUtils;

import java.util.HashMap;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.framework.ui.UIUtil;

import matrix.util.StringList;

public class processReleasedAndCompleteData {
	
	public static MapList processReleasedData(MapList mlReleasedObjData, MapList mlCompleteCAData)throws Exception
	{
		MapList mlReturn = new MapList();
		
		Map<?, ?> mReleasedObjTemp = null;
		String strReleasedObjPID = "";
		
		Map<?, ?> mCompleteCATemp = null;
		
		String strCAName = "";
		String strCACurrent = "";
		String strCOName = "";
		String strCOCurrent = "";
		
		String strRealizedPIDs = "";
		String strRealizedPID = "";
		
		String strProposedPIDs = "";
		String strProposedPID= "";
		
		Map<Integer, String> mProcessedMap = null;
		int iProcessedCtr = 0;
		
		if(mlReleasedObjData !=null && mlReleasedObjData.size()>0)
		{
			for(int i=0; i<mlReleasedObjData.size(); i++)
			{
				mProcessedMap = new HashMap<Integer, String>();
				iProcessedCtr =  0;
				
				mReleasedObjTemp = (HashMap<?, ?>)mlReleasedObjData.get(i);
				
				mProcessedMap.put(iProcessedCtr, (String)mReleasedObjTemp.get(DomainConstants.SELECT_TYPE));
				iProcessedCtr++;
				mProcessedMap.put(iProcessedCtr, (String)mReleasedObjTemp.get(DomainConstants.SELECT_NAME));
				iProcessedCtr++;
				mProcessedMap.put(iProcessedCtr, (String)mReleasedObjTemp.get(DomainConstants.SELECT_REVISION));
				iProcessedCtr++;
				mProcessedMap.put(iProcessedCtr, (String)mReleasedObjTemp.get(DomainConstants.SELECT_CURRENT));
				iProcessedCtr++;
				
				strReleasedObjPID = (String)mReleasedObjTemp.get("physicalid");
				
				if(mlCompleteCAData !=null && mlCompleteCAData.size()>0)
				{
					for(int j=0; j<mlCompleteCAData.size(); j++)
					{
						mCompleteCATemp = (HashMap<?, ?>)mlCompleteCAData.get(j);
						
						strCAName = (String)mCompleteCATemp.get(DomainConstants.SELECT_NAME);
						strCACurrent = (String)mCompleteCATemp.get(DomainConstants.SELECT_CURRENT);
						
						strCOName = (String)mCompleteCATemp.get("to[Change Action].from.name");
						strCOCurrent = (String)mCompleteCATemp.get("to[Change Action].from.current");
						//Traverse through the realized changes and see if Released object is under Realized Change -- Start
						strRealizedPIDs = (String)mCompleteCATemp.get("from[Realized Activities].to.paths[Where].path.element[0].physicalid");
						
						if(UIUtil.isNotNullAndNotEmpty(strRealizedPIDs))
						{
							if(strRealizedPIDs.contains("")) //CA Contains more than 1 Realized Changes
							{
								StringList slMultipleRealizedChgs = FrameworkUtil.split(strRealizedPIDs, "");
								{
									for(int k=0; k<slMultipleRealizedChgs.size(); k++)
									{
										strRealizedPID = (String)slMultipleRealizedChgs.get(k);
										if(strReleasedObjPID.equals(strRealizedPID))
										{
											mProcessedMap.put(iProcessedCtr, strCAName);
											iProcessedCtr++;
											mProcessedMap.put(iProcessedCtr, strCACurrent);
											iProcessedCtr++;
											
											if(UIUtil.isNotNullAndNotEmpty(strCOName))
											{
												mProcessedMap.put(iProcessedCtr, strCOName);
												iProcessedCtr++;
												mProcessedMap.put(iProcessedCtr, strCOCurrent);
												iProcessedCtr++;
											}
											mProcessedMap.put(iProcessedCtr, "Realized Change");
											iProcessedCtr++;
										}
									}
								}
							}
							else //CA contains only 1 Realized Change
							{
								if(strReleasedObjPID.equals(strRealizedPIDs))
								{
									mProcessedMap.put(iProcessedCtr, strCAName);
									iProcessedCtr++;
									mProcessedMap.put(iProcessedCtr, strCACurrent);
									iProcessedCtr++;

									if(UIUtil.isNotNullAndNotEmpty(strCOName))
									{
										mProcessedMap.put(iProcessedCtr, strCOName);
										iProcessedCtr++;
										mProcessedMap.put(iProcessedCtr, strCOCurrent);
										iProcessedCtr++;
									}
									mProcessedMap.put(iProcessedCtr, "Realized Change");
									iProcessedCtr++;
								}
							}
						}
						//Traverse through the realized changes and see if Released object is under Realized Change -- End
						
						//Traverse through the proposed changes and see if Released object is under Proposed Change -- Start
						strProposedPIDs = (String)mCompleteCATemp.get("from[Proposed Activities].to.paths[Where].path.element[0].physicalid");
						
						if(UIUtil.isNotNullAndNotEmpty(strProposedPIDs))
						{
							if(strProposedPIDs.contains("")) //CA Contains more than 1 Realized Changes
							{
								StringList slMultipleProposedChgs = FrameworkUtil.split(strProposedPIDs, "");
								{
									for(int k=0; k<slMultipleProposedChgs.size(); k++)
									{
										strProposedPID = (String)slMultipleProposedChgs.get(k);
										if(strReleasedObjPID.equals(strProposedPID))
										{
											mProcessedMap.put(iProcessedCtr, strCAName);
											iProcessedCtr++;
											mProcessedMap.put(iProcessedCtr, strCACurrent);
											iProcessedCtr++;

											if(UIUtil.isNotNullAndNotEmpty(strCOName))
											{
												mProcessedMap.put(iProcessedCtr, strCOName);
												iProcessedCtr++;
												mProcessedMap.put(iProcessedCtr, strCOCurrent);
												iProcessedCtr++;
											}
											mProcessedMap.put(iProcessedCtr, "Proposed Change");
											iProcessedCtr++;
										}
									}
								}
							}
							else //CA contains only 1 Realized Change
							{
								if(strReleasedObjPID.equals(strProposedPIDs))
								{
									mProcessedMap.put(iProcessedCtr, strCAName);
									iProcessedCtr++;
									mProcessedMap.put(iProcessedCtr, strCACurrent);
									iProcessedCtr++;

									if(UIUtil.isNotNullAndNotEmpty(strCOName))
									{
										mProcessedMap.put(iProcessedCtr, strCOName);
										iProcessedCtr++;
										mProcessedMap.put(iProcessedCtr, strCOCurrent);
										iProcessedCtr++;
									}
									mProcessedMap.put(iProcessedCtr, "Proposed Change");
									iProcessedCtr++;
								}
							}
						}
						//Traverse through the proposed changes and see if Released object is under Proposed Change -- End
					}
				}
				mlReturn.add(mProcessedMap);
			}
		}
		return mlReturn;
	}

}
