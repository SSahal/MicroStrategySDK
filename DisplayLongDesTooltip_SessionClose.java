/**
 * MicroStrategy SDK Sample
 *
 * Copyright © 2018 MicroStrategy Incorporated. All Rights Reserved.
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
package com.microstrategy.sdk.transforms;
import com.microstrategy.web.app.transforms.AllObjectBrowserTransform;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.webapi.EnumDSSXMLObjectFlags;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
public class Classic extends AllObjectBrowserTransform {
	private static WebObjectsFactory factory = null;
	private static WebIServerSession serverSession = null;
	public String getDescription() {
        return "Custom search tooltip.";
    }
	protected String getTooltipInfo(WebObjectInfo item)   {
		getSession();
		
		WebObjectSource wos = serverSession.getFactory().getObjectSource();
    	wos.setFlags(wos.getFlags()|EnumDSSXMLObjectFlags.DssXmlObjectComments );
    	try {
    	WebObjectInfo woi = wos.getObject(item.getID(),EnumDSSXMLObjectTypes.DssXmlTypeAttribute); //1st Argument is Attribute ID
    	woi.populate();
    	String[] str=woi.getComments();
    	System.out.println(str);
    	if (str != null)
    	{
    		return str[0];
    	}
    		else {
    			return "Description not Available";
    		}
    	}
    	 catch (WebObjectsException ex1) {
 		   
 		    closeSession(serverSession);
 		  return super.getTooltipInfo(item);
 		    }
	}
   
    public static WebIServerSession getSession() {
    	if (serverSession == null) {
    	//create factory object
    	factory = WebObjectsFactory.getInstance();
    	serverSession = factory.getIServerSession();
    	//Set up session properties
    	serverSession.setServerName("10.161.97.6"); //Should be replaced with the name of an Intelligence Server
    	serverSession.setServerPort(34952);
    	serverSession.setProjectName("Brands Enterprise Analytics"); //Project where the session should be created
    	serverSession.setLogin("mstr_svc"); //User ID
    	serverSession.setPassword("Brands@2019"); //Password 
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
    	serverSession = null;
    	} 
    	catch (WebObjectsException ex) {
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
