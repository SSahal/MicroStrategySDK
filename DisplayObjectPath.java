/**
 * MicroStrategy SDK Sample
 * Modified by SSahal
 */


package com.microstrategy.sdk.transforms;

import com.microstrategy.web.app.transforms.AllObjectBrowserTransform;
import com.microstrategy.web.objects.SimpleList;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectsException;

public class MyFirstTransform extends AllObjectBrowserTransform {

    public String getDescription() {
        return "Custom search tooltip.";
    }

    protected String getTooltipInfo(WebObjectInfo item) {
        String path = "";
        WebFolder root = item.getParent();

        if (root != null) {
            try {
                root.populate();

                SimpleList ancestors = root.getAncestors();
                for (int i = 0; i < ancestors.size(); i++) {
                    String p = ((WebObjectInfo) ancestors.item(i)).getName();
                    path += p + " > ";
                }

            } catch (WebObjectsException e) {
                System.out.println("Failed to retrieve folder contents!");
                e.printStackTrace();
            }
            return path += root.getName() + " > " + item.getName();
        } else {
            return super.getTooltipInfo(item);
        }
    }
}
