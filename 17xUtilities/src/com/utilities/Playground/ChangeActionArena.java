package com.utilities.Playground;

import java.util.Map;

import com.dassault_systemes.enovia.enterprisechangemgt.common.ChangeAction;
import com.utilities.connectionUtils.connectToEnv;

import matrix.db.Context;
import matrix.util.StringList;

public class ChangeActionArena {

	public static void main(String[] args) {
		
		Context context = null;
		try
		{
			context = connectToEnv.get17xNoCasContext("", "", "EngLeader", "Local");
			System.out.println("context >>>"+context);
			String newCAId = new ChangeAction().create(context);
			
			ChangeAction ca = new ChangeAction(newCAId);
			Map mConnect = ca.connectAffectedItems(context, new StringList("30200.33322.49009.1209"), true, "For Release", "VPMReference", "VPLM_SMB_Definition");
			System.out.println("mConnect >>>"+mConnect);
			
			context.disconnect();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
