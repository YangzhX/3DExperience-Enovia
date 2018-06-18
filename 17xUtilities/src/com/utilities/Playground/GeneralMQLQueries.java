package com.utilities.Playground;


import com.matrixone.apps.domain.util.MqlUtil;
import com.utilities.connectionUtils.connectToEnv;

import matrix.db.Context;

public class GeneralMQLQueries {

	public static void main(String[] args) {
		
		Context context = null;
		try
		{
			//Envs - Local, Dev1, Dev2, Qa1
			context = connectToEnv.get17xNoCasContext("105035538", "", "EngLeader", "Qa1");
			
			String strResult = MqlUtil.mqlCommand(context, "connect bus 60136.35780.63744.19803 rel GEManufactures from 36696.59785.35840.60851 ");
			System.out.println("strResult >>"+strResult);
			context.disconnect();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
