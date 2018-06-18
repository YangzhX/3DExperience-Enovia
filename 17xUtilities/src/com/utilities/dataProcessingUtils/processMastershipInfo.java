package com.utilities.dataProcessingUtils;

import java.util.HashMap;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.framework.ui.UIUtil;

import matrix.util.StringList;

public class processMastershipInfo {
	
	public static MapList processMasterShipInfo(MapList mlInput) throws Exception
	{
		MapList mlReturn = new MapList();
		
		Map<?, ?> mTemp = null;
		Map<Integer, String> mMastership = null;
		
		String sRepType = "";
		String sLastMinor = "";
		String sLastVersion = "";
		String sRepLastMinor = "";
		String sRepLastVersion = "";
		String sMastership = "";
		String sRepMastership = "";
		
		StringList slTemp = null;
		
		int iCounter = 0;
		
		
		try
		{
			if(mlInput != null && mlInput.size() > 0)
			{
				for(int i = 0; i < mlInput.size(); i++)
				{
					mTemp = (HashMap<?, ?>)mlInput.get(i);
					mMastership = new HashMap<Integer, String>();
					sMastership = "V5 Master";
					sRepMastership = "V5 Master";

					iCounter = 0;
					
					mMastership.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_TYPE));
					iCounter++;
					mMastership.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_NAME));
					iCounter++;
					mMastership.put(iCounter, (String)mTemp.get(DomainConstants.SELECT_REVISION));
					iCounter++;
					mMastership.put(iCounter, (String)mTemp.get("attribute[PLMEntity.V_Name].value"));
					iCounter++;
					mMastership.put(iCounter, (String)mTemp.get("attribute[PLMEntity.V_description].value"));
					iCounter++;

					
					
					sLastMinor = (String)mTemp.get("attribute[PLMReference.V_isLastMinorVersion].value");
					mMastership.put(iCounter, sLastMinor);
					iCounter++;
					
					sLastVersion = (String)mTemp.get("attribute[PLMReference.V_isLastVersion].value");
					mMastership.put(iCounter, sLastVersion);
					iCounter++;
					
					if("TRUE".equalsIgnoreCase(sLastMinor) && "TRUE".equalsIgnoreCase(sLastVersion))
					{
						sMastership = "3DExperience Master";
						mMastership.put(iCounter, sMastership);
						iCounter++;
					}
					else
					{
						mMastership.put(iCounter, sMastership);
						iCounter++;
					}
					
					sRepType = (String)mTemp.get("from[VPMRepInstance].to.type");
					
					if(UIUtil.isNotNullAndNotEmpty(sRepType))
					{
						if(sRepType.contains(""))
						{
							slTemp = FrameworkUtil.split(sRepType, "");
							
							if("3DShape".equalsIgnoreCase((String)slTemp.get(0)))
							{
								//Put 3DShape info 1st -- Start
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(0));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(0));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
								iCounter++;
								
								sRepLastMinor = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastMinorVersion].value"), "").get(0);
								mMastership.put(iCounter, sRepLastMinor);
								iCounter++;
								
								sRepLastVersion = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastVersion].value"), "").get(0);
								mMastership.put(iCounter, sRepLastVersion);
								iCounter++;
								
								if("TRUE".equalsIgnoreCase(sRepLastMinor) && "TRUE".equalsIgnoreCase(sRepLastVersion))
								{
									sRepMastership = "3DExperience Master";
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								else
								{
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								//Put 3DShape info 1st -- End
								
								//Put Drawing info 2nd -- Start
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
								iCounter++;
								
								sRepLastMinor = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastMinorVersion].value"), "").get(1);
								mMastership.put(iCounter, sRepLastMinor);
								iCounter++;
								
								sRepLastVersion = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastVersion].value"), "").get(1);
								mMastership.put(iCounter, sRepLastVersion);
								iCounter++;
								
								if("TRUE".equalsIgnoreCase(sRepLastMinor) && "TRUE".equalsIgnoreCase(sRepLastVersion))
								{
									sRepMastership = "3DExperience Master";
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								else
								{
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}

								//Put Drawing info 2nd -- End
							}
							else if("3DShape".equalsIgnoreCase((String)slTemp.get(1)))
							{
								//Put 3DShape Info 1st -- Start
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
								iCounter++;
								
								sRepLastMinor = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastMinorVersion].value"), "").get(1);
								mMastership.put(iCounter, sRepLastMinor);
								iCounter++;
								
								sRepLastVersion = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastVersion].value"), "").get(1);
								mMastership.put(iCounter, sRepLastVersion);
								iCounter++;
								
								if("TRUE".equalsIgnoreCase(sRepLastMinor) && "TRUE".equalsIgnoreCase(sRepLastVersion))
								{
									sRepMastership = "3DExperience Master";
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								else
								{
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								//Put 3DShape Info 1st -- End
								
								//Put Drawing Info 2nd -- Start
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(0));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(0));
								iCounter++;
								mMastership.put(iCounter, (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
								iCounter++;
								
								sRepLastMinor = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastMinorVersion].value"), "").get(0);
								mMastership.put(iCounter, sRepLastMinor);
								iCounter++;
								
								sRepLastVersion = (String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastVersion].value"), "").get(0);
								mMastership.put(iCounter, sRepLastVersion);
								iCounter++;
								
								if("TRUE".equalsIgnoreCase(sRepLastMinor) && "TRUE".equalsIgnoreCase(sRepLastVersion))
								{
									sRepMastership = "3DExperience Master";
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								else
								{
									mMastership.put(iCounter, sRepMastership);
								}							
								//Put Drawing Info 2nd -- End
							}
						}
						else
						{
							if("3DShape".equalsIgnoreCase(sRepType))
							{
								mMastership.put(iCounter, sRepType);
								iCounter++;
								mMastership.put(iCounter, (String)mTemp.get("from[VPMRepInstance].to.name"));
								iCounter++;
								mMastership.put(iCounter, (String)mTemp.get("from[VPMRepInstance].to.revision"));
								iCounter++;
								
								sRepLastMinor = (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastMinorVersion].value");
								mMastership.put(iCounter, sRepLastMinor);
								iCounter++;
								
								sRepLastVersion = (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastVersion].value");
								mMastership.put(iCounter, sRepLastVersion);
								iCounter++;
								
								if("TRUE".equalsIgnoreCase(sRepLastMinor) && "TRUE".equalsIgnoreCase(sRepLastVersion))
								{
									sRepMastership = "3DExperience Master";
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								else
								{
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
							}
							
							if("Drawing".equalsIgnoreCase(sRepType))
							{
								if(iCounter == 7)
								{
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
									mMastership.put(iCounter, "");
									iCounter++;
								}
								
								mMastership.put(iCounter, sRepType);
								iCounter++;
								mMastership.put(iCounter, (String)mTemp.get("from[VPMRepInstance].to.name"));
								iCounter++;
								mMastership.put(iCounter, (String)mTemp.get("from[VPMRepInstance].to.revision"));
								iCounter++;
								
								sRepLastMinor = (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastMinorVersion].value");
								mMastership.put(iCounter, sRepLastMinor);
								iCounter++;
								
								sRepLastVersion = (String)mTemp.get("from[VPMRepInstance].to.attribute[PLMReference.V_isLastVersion].value");
								mMastership.put(iCounter, sRepLastVersion);
								iCounter++;
								
								if("TRUE".equalsIgnoreCase(sRepLastMinor) && "TRUE".equalsIgnoreCase(sRepLastVersion))
								{
									sRepMastership = "3DExperience Master";
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
								else
								{
									mMastership.put(iCounter, sRepMastership);
									iCounter++;
								}
							}
						}

					}
					mlReturn.add(mMastership);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return mlReturn;
	}
}
