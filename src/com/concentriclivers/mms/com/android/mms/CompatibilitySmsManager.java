/*
 * Copyright (C) 2012 Adam Nybäck
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

// Thanks to original version from
// https://github.com/nadam/compatibility-sms-manager
package com.concentriclivers.mms.com.android.mms;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.PendingIntent;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Help class than mimics the interfaces of {@link android.telephony.SmsManager}
 * and {@link android.telephony.gsm.SmsManager} and uses the best one for each
 * device. There are also two work-arounds to prevent double messages being sent
 * on Samsung Galaxy S2 with Android 4.0.3 and HTC Tattoo.
 */
public class CompatibilitySmsManager
{
	private static Method method;

	static
	{
		try
		{
			Class<?> classSmsManager = Class.forName("android.telephony.SmsManager");

			Class<?> paramTypes[] = new Class[9];

			paramTypes[0] = String.class;
			paramTypes[1] = String.class;
			paramTypes[2] = ArrayList.class;
			paramTypes[3] = ArrayList.class;
			paramTypes[4] = ArrayList.class;
			paramTypes[5] = Boolean.TYPE;
			paramTypes[6] = Integer.TYPE;
			paramTypes[7] = Integer.TYPE;
			paramTypes[8] = Integer.TYPE;

			method = classSmsManager.getMethod("sendMultipartTextMessage", paramTypes);
		}
		catch (Exception ex)
		{
			Log.d("CompatibilitySmsManager", "Not using i9100 workaround.");
		}
	}
	
	public static boolean isAvailable()
	{
		return method != null;
	}

	public static void sendMultipartTextMessage(SmsManager smsManager, String destinationAddress, String scAddress, ArrayList<String> parts,
			ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents)
	{
		Object args[] = new Object[9];
		args[0] = destinationAddress;
		args[1] = scAddress;
		args[2] = parts;
		args[3] = sentIntents;
		args[4] = deliveryIntents;
		args[5] = Boolean.valueOf(false);
		args[6] = Integer.valueOf(0);
		args[7] = Integer.valueOf(0);
		args[8] = Integer.valueOf(0);

		try
		{
			method.invoke(smsManager, args);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}