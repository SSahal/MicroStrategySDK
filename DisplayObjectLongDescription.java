/**
 * MicroStrategy SDK Sample
 *
 * Copyright © 2019 MicroStrategy Incorporated. All Rights Reserved.
 *
* MICROSTRATEGY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE 
 * SUITABILITY OF THIS SAMPLE CODE, EITHER EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS 
 * FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MICROSTRATEGY SHALL NOT 
 * BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, 
 * MODIFYING OR DISTRIBUTING THIS SAMPLE CODE OR ITS DERIVATIVES.
 *
 *
 */


package com.microstrategy.sdk.aps;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.webapi.EnumDSSXMLObjectFlags;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;

public class Tooltip_Description{

private static WebObjectsFactory factory = null;
private static WebIServerSession serverSession = null;

public static void main(String[] args) throws WebObjectsException, IllegalArgumentException {
getSession();
WebObjectSource wos = serverSession.getFactory().getObjectSource();
wos.setFlags(wos.getFlags()|EnumDSSXMLObjectFlags.DssXmlObjectComments );

WebObjectInfo woi = wos.getObject("1522424E4CBAD0982EF5718D9625348F",EnumDSSXMLObjectTypes.DssXmlTypeAttribute); //1st Argument is Attribute ID
woi.populate();

String[] str=woi.getComments();

if (str != null)
{
	System.out.println(str[0]);
	
}
	
	else {
		System.out.println("Description not Available");

	}

}

public static WebIServerSession getSession() {
if (serverSession == null) {

//create factory object
factory = WebObjectsFactory.getInstance();
serverSession = factory.getIServerSession();

//Set up session properties
serverSession.setServerName("machinename"); //Should be replaced with the name of an Intelligence Server
serverSession.setServerPort(0);
serverSession.setProjectName("MicroStrategy Tutorial"); //Project where the session should be created
serverSession.setLogin("administrator"); //User ID
serverSession.setPassword(""); //Password

//Initialize the session with the above parameters

try {

System.out.println("\nSession created with ID: " + serverSession.getSessionID());

} catch (WebObjectsException ex) {

handleError(null, "Error creating session:" + ex.getMessage());
}

}

//Return session
return serverSession;
}

/**
* Close Intelligence Server Session
* @param serverSession WebIServerSession
*/

public static void closeSession(WebIServerSession serverSession) {

try {
serverSession.closeSession();
} catch (WebObjectsException ex) {
System.out.println("Error closing session:" + ex.getMessage());
return;

}

System.out.println("Session closed.");

}

/**
* Print out error message, close session and abort execution
* @param serverSession WebIServerSession
* @param message String
*/

public static void handleError(WebIServerSession serverSession, String message) {
System.out.println(message);

if (serverSession != null) {
closeSession(serverSession);

}

System.exit(0);
}
}
