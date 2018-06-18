package com.utilities.Default;

import java.sql.Timestamp;

import java.util.Date;

import com.utilities.SXValidation.VPMUtils;
import com.utilities.connectionUtils.connectToEnv;
import com.utilities.dataCorrectionUtils.ApplicativeDataCorrections;

import matrix.db.Context;

public class Default 
{

	public static void main(String[] args) throws Exception 
	{
		Context context = null;
		//Envs - Local, Dev1, Dev2, Qa1
		try 
		{
			String EnvName = "Qa1";
			context = connectToEnv.get17xNoCasContext("502785999", "502785999", "EngLeader", EnvName);

			System.out.println("Context user >>"+context.getUser());
			System.out.println("Current Time Main Method >>> "+new Timestamp((new Date()).getTime()));
			
			VPMUtils.getDetailsFromEnvAndProcess(context, EnvName);
//			ApplicativeDataCorrections.promoteApplicativeDataToInWork(context);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		finally
		{
			context.disconnect();
		}
	}

}
