package com.utilities.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.framework.ui.UIUtil;
import com.utilities.Playground.GeneratePPandDrawingPrintLinks;

import matrix.util.StringList;

public class ReadFromTextFile {

	public static void main(String[] args) {
		try 
        {
        	MapList mlInput = new MapList();
        	
            File f = new File("C:\\Users\\502244529\\Documents\\LinksTest.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            System.out.println("Reading file using Buffered Reader");

            int i =0;
           
            StringList slTemp = new StringList();
            StringList slTemp1 = new StringList();
            
            String arrayItems[] = null;
            
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
//           GeneratePPandDrawingPrintLinks.testLinks(mlInput);
        }
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
