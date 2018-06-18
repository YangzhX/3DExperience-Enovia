package com.utilities.dataCorrectionUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.domain.util.MqlUtil;
import com.utilites.reportUtils.ListToExcel;

import matrix.db.Context;
import matrix.util.Pattern;
import matrix.util.StringList;

public class ApplicativeDataCorrections {
	
	private static Date date = new Date(System.currentTimeMillis());
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	private static String strDate = formatter.format(date);
	
	private static String SheetName = strDate;
	
	public static int promoteApplicativeDataToInWork(Context context) throws Exception
	{
		
		String[] columns = {"Type", "Name", "Revision"};
		String strWorkBookName = "ApplicativeDataCorrections-QA1-"+strDate;
		
		int iReturn = 0;
		
		String sType = "";
		String sName = "";
		String sRevision = "";
		String sCurrent = "";
		String sObjId = "";
		
		Map mTemp = null;
		
		MapList mlCaptureModified = new MapList();
		Map<Integer, String> mCaptureModified = null ;
		
		int iCaptureModified = 0;
		
		Pattern typePattern = new Pattern("Knowledgeware");
		typePattern.addPattern("Review");
		
		String whereExpr = "current==\"PRIVATE\"";
		
		StringList slObjSelects = new StringList(DomainConstants.SELECT_TYPE);
		slObjSelects.add(DomainConstants.SELECT_NAME);
		slObjSelects.add(DomainConstants.SELECT_REVISION);
		slObjSelects.add(DomainConstants.SELECT_CURRENT);
		slObjSelects.add(DomainConstants.SELECT_ID);
		
		
		DomainObject domAppObj = DomainObject.newInstance(context);
		
		try
		{
			
			MapList mlApplicativeData = DomainObject.findObjects(context, typePattern.getPattern(), "vplm", whereExpr, slObjSelects);
			
			System.out.println("mlApplicativeData >>"+mlApplicativeData);
			
			if(mlApplicativeData != null && mlApplicativeData.size() > 0)
			{
				for(int i = 0; i < mlApplicativeData.size() ; i++)
				{
					mCaptureModified = new HashMap();
					iCaptureModified = 0;
					
					sType = "";
					sName = "";
					sRevision = "";
					sCurrent = "";
					sObjId = "";
					
					mTemp = (HashMap)mlApplicativeData.get(i);
					
					sType = (String)mTemp.get(DomainConstants.SELECT_TYPE);
					sName = (String)mTemp.get(DomainConstants.SELECT_NAME);
					sRevision = (String)mTemp.get(DomainConstants.SELECT_REVISION);
					sCurrent = (String)mTemp.get(DomainConstants.SELECT_CURRENT);
					sObjId = (String)mTemp.get(DomainConstants.SELECT_ID);
					
					System.out.println("sType >>>"+sType);
					System.out.println("sName >>>"+sName);
					System.out.println("sName >>>"+sRevision);
					System.out.println("sName >>>"+sCurrent);
					
					if("Knowledgeware".equalsIgnoreCase(sType) || "Review".equalsIgnoreCase(sType))
					{
						if("PRIVATE".equalsIgnoreCase(sCurrent))
						{
							domAppObj.setId(sObjId);
							MqlUtil.mqlCommand(context, "approve bus " + sObjId + " signature ShareWithinProject comment migratedApplicativeData");
							domAppObj.promote(context);
							
							mCaptureModified.put(iCaptureModified, sType);
							iCaptureModified++;
							mCaptureModified.put(iCaptureModified, sName);
							iCaptureModified++;
							mCaptureModified.put(iCaptureModified, sRevision);
							iCaptureModified++;
							
						}
					}
					mlCaptureModified.add(mCaptureModified);
				}
			}
		}
		catch (Exception ex)
		{
			iReturn = 1;
			ex.printStackTrace();
		}
		finally
		{
			context.disconnect();
			try
			{
				ListToExcel.ListWithMapToExcel(strWorkBookName, SheetName, "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", columns, mlCaptureModified);				
			}
			catch(Exception ex)
			{
				System.out.println("mlCaptureModified from exception >>>"+mlCaptureModified);
				iReturn = 1;
				ex.printStackTrace();
			}

		}
		System.out.println("Current Time End of Applicative Data Corrections Method >>> "+new Timestamp((new Date()).getTime()));
		return iReturn;
	}
}
