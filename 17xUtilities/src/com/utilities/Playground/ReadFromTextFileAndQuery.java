package com.utilities.Playground;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matrix.db.Context;

import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MqlUtil;
import com.utilities.connectionUtils.connectToEnv;
import com.utilities.dataProcessingUtils.processThumbnailInfo;

import matrix.util.StringList;

public class ReadFromTextFileAndQuery {

	public static void main(String[] args) throws Exception {
    	List<Map<?, ?>> mlInput = new ArrayList<Map<?, ?>>();
    	List<Map<?, ?>> mlInput1 = new ArrayList<Map<?, ?>>();
    	
    	Context context = null;
        try 
        {
        	
            File f = new File("C:\\Personal\\Work\\PartToPartDebug.txt");
            File op = new File("C:\\Personal\\Work\\PartToPartDebug_Result.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));
            
            BufferedWriter bw = null;
            FileWriter fw = null;
            fw = new FileWriter(op);
            bw = new BufferedWriter(fw);

            String readLine = "";
            System.out.println("Reading file using Buffered Reader");
            
            String strResult = "";
            context = connectToEnv.get17xNoCasContext("502785999", "502785999", "EngLeader", "Qa1");
            
            while ((readLine = b.readLine()) != null) 
            {
            	strResult = "";
            	System.out.println("readLine >>"+readLine);
            	strResult = MqlUtil.mqlCommand(context, "expand bus "+readLine+ " select bus attribute[GE_VPMReference.GE_EBOM_Collaboration_Intention].value dump |");
            	System.out.println("strResult >>"+strResult);
            	bw.write(strResult+"\n");
            }
            
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

            

        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
   }
}

