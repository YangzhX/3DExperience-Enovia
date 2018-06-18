package com.utilities.dataProcessingUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.framework.ui.UIUtil;

public class processThumbnailInfo {
	
	public static List<Map> rearrangeThumbnailData(List<Map<?, ?>> mlPPData, List<Map<?,?>> mlThumbnailData) throws Exception
	{
		System.out.println("Inside rearrangeThumbnailData >>>");
		System.out.println("Current rearrangeThumbnailData Method >>> "+new Timestamp((new Date()).getTime()));

		List<Map> mlReturn = new ArrayList();
		
		String sTemp = "";
		
		String sPPThumb = "";
		String sRepThumb = "";
		
		String sPPThumbName = "";
		String sPPThumbFromPPMap = "";
		
		String sRepThumbName = "";
		String sRepThumbNameFromPPMap = "";
		
		String sRepType = "";
		String sRepType1 = "";
		String sRepType2 = "";
		
		
		Map<Integer, String> mThumbnailMap = null;
		
		Map mPPData = new HashMap();
		Map mThumbData = new HashMap();
		
		int iCounter = 0;
		
		if(mlPPData != null && mlPPData.size() > 0)
		{
			for(int imlPPData = 0; imlPPData < mlPPData.size(); imlPPData++)
			{
				mPPData = (HashMap)mlPPData.get(imlPPData);

				for(int imPPData = 0; imPPData < mPPData.size(); imPPData++)
				{
					mThumbnailMap = new HashMap();
					sPPThumbName = "";
					sPPThumbFromPPMap = "";
					sPPThumb = "No";
					
					sRepThumbName = "";
					sRepThumbNameFromPPMap = "";
					sRepThumb = "No";

					sRepType = ""; 
					iCounter = 0;
					
					mThumbnailMap.put(iCounter, (String)mPPData.get(DomainConstants.SELECT_TYPE));
					iCounter++;
					mThumbnailMap.put(iCounter, (String)mPPData.get(DomainConstants.SELECT_NAME));
					iCounter++;
					mThumbnailMap.put(iCounter, (String)mPPData.get(DomainConstants.SELECT_REVISION));
					iCounter++;
					mThumbnailMap.put(iCounter, (String)mPPData.get("attribute[PLMEntity.V_Name].value"));
					iCounter++;
					mThumbnailMap.put(iCounter, (String)mPPData.get("attribute[PLMEntity.V_description].value"));
					iCounter++;
					mThumbnailMap.put(iCounter, (String)mPPData.get(DomainConstants.SELECT_ORIGINATED));
					iCounter++;
					mThumbnailMap.put(iCounter, (String)mPPData.get(DomainConstants.SELECT_MODIFIED));
					iCounter++;
						
					for(int imlThumbData = 0; imlThumbData < mlThumbnailData.size(); imlThumbData++)
					{
						mThumbData = (HashMap)mlThumbnailData.get(imlThumbData);
							
						for(int imThumbData = 0; imThumbData < mThumbData.size(); imThumbData++)
						{
							sTemp = (String)mThumbData.get(DomainConstants.SELECT_NAME);
								
							if(UIUtil.isNotNullAndNotEmpty(sTemp))
							{
								if(((String)mPPData.get("physicalid")).equals(sTemp))
								{
										
									sPPThumbName = sTemp;
									sPPThumbFromPPMap = (String)mPPData.get("physicalid");
									sPPThumb = "Yes";
									
								}
								if(UIUtil.isNotNullAndNotEmpty((String)mPPData.get("from[VPMRepInstance].to.physicalid")) && (((String)mPPData.get("from[VPMRepInstance].to.physicalid")).contains(sTemp) || ((String)mPPData.get("from[VPMRepInstance].to.physicalid")).equals(sTemp)))
								{
									sRepThumbName = sTemp;
									sRepThumbNameFromPPMap = (String)mPPData.get("from[VPMRepInstance].to.physicalid");
									sRepThumb = "Yes";
								}
							}
						}
					}

					mThumbnailMap.put(iCounter, sPPThumbFromPPMap);
					iCounter++;
					mThumbnailMap.put(iCounter, sPPThumbName);
					iCounter++;
					mThumbnailMap.put(iCounter, sPPThumb);
					iCounter++;
						
					if(UIUtil.isNotNullAndNotEmpty((String)mPPData.get(("from[VPMRepInstance].to.type"))))
					{
						sRepType = (String)mPPData.get(("from[VPMRepInstance].to.type"));
							
						if(sRepType.contains("")) //PP has 3DShape + Drawing
						{
							sRepType1 = (String)(FrameworkUtil.split(sRepType, "")).get(0);
							sRepType2 = (String)(FrameworkUtil.split(sRepType, "")).get(1);
								
							if("Drawing".equalsIgnoreCase(sRepType1))//Drawing comes first in selectable
							{
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.type")), "")).get(0));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.name")), "")).get(0));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.revision")), "")).get(0));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value")), "")).get(0));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.originated")), "")).get(0));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.modified")), "")).get(0));
								iCounter++;
								mThumbnailMap.put(iCounter,  sRepThumbName);
								iCounter++;
								mThumbnailMap.put(iCounter,  sRepThumbNameFromPPMap);
								iCounter++;
								mThumbnailMap.put(iCounter,  sRepThumb);
								iCounter++;
							}
							else if("Drawing".equalsIgnoreCase(sRepType2))//Drawing comes second in selectable
							{
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.type")), "")).get(1));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.name")), "")).get(1));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.revision")), "")).get(1));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value")), "")).get(1));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.originated")), "")).get(1));
								iCounter++;
								mThumbnailMap.put(iCounter,  (String)(FrameworkUtil.split((String)mPPData.get(("from[VPMRepInstance].to.modified")), "")).get(1));
								iCounter++;
								mThumbnailMap.put(iCounter,  sRepThumbName);
								iCounter++;
								mThumbnailMap.put(iCounter,  sRepThumbNameFromPPMap);
								iCounter++;
								mThumbnailMap.put(iCounter,  sRepThumb);
								iCounter++;
							}
						}
						else if("Drawing".equalsIgnoreCase(sRepType))//PP has only Drawing
						{
							mThumbnailMap.put(iCounter,  (String)mPPData.get(("from[VPMRepInstance].to.type")));
							iCounter++;
							mThumbnailMap.put(iCounter,  (String)mPPData.get(("from[VPMRepInstance].to.name")));
							iCounter++;
							mThumbnailMap.put(iCounter,  (String)mPPData.get(("from[VPMRepInstance].to.revision")));
							iCounter++;
							mThumbnailMap.put(iCounter,  (String)mPPData.get(("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value")));
							iCounter++;
							mThumbnailMap.put(iCounter,  (String)mPPData.get(("from[VPMRepInstance].to.originated")));
							iCounter++;
							mThumbnailMap.put(iCounter,  (String)mPPData.get(("from[VPMRepInstance].to.modified")));
							iCounter++;
							mThumbnailMap.put(iCounter,  sRepThumbName);
							iCounter++;
							mThumbnailMap.put(iCounter,  sRepThumbNameFromPPMap);
							iCounter++;
							mThumbnailMap.put(iCounter,  sRepThumb);
							iCounter++;
						}
					}
				}
				mlReturn.add(mThumbnailMap);
			}
		}
		return mlReturn;
	}
		
}

