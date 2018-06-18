package com.utilities.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.utilites.reportUtils.ListToExcel;
import com.utilities.dataProcessingUtils.processReleasedAndCompleteData;
import com.utilities.dataProcessingUtils.processThumbnailInfo;

import matrix.util.StringList;

public class ReadFromTwoTextFiles {

	public static void main(String[] args) throws Exception {
		
		
    	MapList mlInput = new MapList();
    	MapList mlInput1 = new MapList();
    	

        try 
        {

            File f = new File("C:\\Users\\502244529\\Documents\\ReleasedObjInfo-findObjs -Test.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            System.out.println("Reading file using Buffered Reader");

            int i =0;
           
            StringList slTemp = new StringList();
            StringList slTemp1 = new StringList();
            while ((readLine = b.readLine()) != null) 
            {
            	Map mTemp = new HashMap();   

            	slTemp = FrameworkUtil.split(readLine, ",");
            	for(int j=0; j<slTemp.size();j++)
            	{
            		
            		slTemp1 = FrameworkUtil.split(((String)slTemp.get(j)).trim(), "=");
            		mTemp.put((String)slTemp1.get(0), (String)slTemp1.get(1));
            		
            	}
            	mlInput.add(mTemp);
            }

            File f1 = new File("C:\\Users\\502244529\\Documents\\ReleasedObjInfo-CAList-Test.txt");

            BufferedReader b1 = new BufferedReader(new FileReader(f1));

            String readLine1 = "";

            System.out.println("Reading file using Buffered Reader");

            int i1 =0;
           
            StringList slTemp1_1 = new StringList();
            StringList slTemp2_1 = new StringList();
            while ((readLine1 = b1.readLine()) != null) 
            {
            	Map mTemp1 = new HashMap();   
            	slTemp1_1 = FrameworkUtil.split(readLine1, ",");
            	
            	for(int j=0; j<slTemp1_1.size();j++)
            	{
            		slTemp2_1 = FrameworkUtil.split(((String)slTemp1_1.get(j)).trim(), "=");
            		mTemp1.put((String)slTemp2_1.get(0), (String)slTemp2_1.get(1));
            		
            	}
            	mlInput1.add(mTemp1);
            }
            
            String[] ReleasedObjReportcolumns = {"Type", "Name", "Revision","State","Connected CA", "CA State", "Connected CO", "State", "Proposed / Realized"};
            MapList mlTemp = processReleasedAndCompleteData.processReleasedData(mlInput, mlInput1);
            ListToExcel.ListWithMapToExcel("Released Obj Report", "6-13-2018", "C:\\Personal\\Work\\GE\\Series-X\\17x\\ValidationReports\\", ReleasedObjReportcolumns, mlTemp);
        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}

}
