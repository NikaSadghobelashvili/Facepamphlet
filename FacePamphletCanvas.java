/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
    private GLabel messageLabel;

	
	public FacePamphletCanvas() 
	{
		messageLabel = new GLabel("");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) 
	{
		messageLabel.setLabel(msg);
		messageLabel.setLocation(LEFT_MARGIN,getHeight()-BOTTOM_MESSAGE_MARGIN);
        messageLabel.setFont(MESSAGE_FONT);
        add(messageLabel);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) 
	{
		removeAll();
		
		if (profile != null) 
		{
            // Display Name
            GLabel nameLabel = new GLabel(profile.getName());
            nameLabel.setFont(PROFILE_NAME_FONT);
            nameLabel.setColor(Color.BLUE);
            add(nameLabel, LEFT_MARGIN, TOP_MARGIN + nameLabel.getAscent());

            // Display Image
            GImage image = profile.getImage();
            double imageY = TOP_MARGIN + IMAGE_MARGIN + nameLabel.getAscent();
            
            if (image != null) 
            {
                double imageX = LEFT_MARGIN;
                add(image, imageX, imageY);
            } 
            else 
            {
            	GRect rect = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
                GLabel noImageLabel = new GLabel("No Image");
                noImageLabel.setFont(PROFILE_IMAGE_FONT);
                rect.setLocation(LEFT_MARGIN, imageY);
                add(rect);
                add(noImageLabel, rect.getX()+IMAGE_WIDTH/3-15,rect.getY()+IMAGE_HEIGHT/2);
            }
            
            // Display Status
            String status = profile.getStatus();
            if (!status.isEmpty()) 
            {
                GLabel statusLabel = new GLabel(status);
                statusLabel.setFont(PROFILE_STATUS_FONT);
                double statusY = imageY + IMAGE_HEIGHT + STATUS_MARGIN + statusLabel.getAscent();
                add(statusLabel, LEFT_MARGIN, statusY);
            }
            displayFriends(profile.getFriends(), getHeight()/8 );
		}
		
		
	}
	 private void displayFriends(Iterator<String> friends, double startY) 
	 {
	        GLabel friendsLabel = new GLabel("Friends");
	        friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
	        add(friendsLabel, LEFT_MARGIN + IMAGE_WIDTH*1.5, startY + friendsLabel.getAscent());

	        double currentY = startY + friendsLabel.getAscent() + TEXT_FIELD_SIZE;
	        while (friends.hasNext()) {
	            String friendName = friends.next();
	            GLabel friendLabel = new GLabel(friendName);
	            friendLabel.setFont(PROFILE_FRIEND_FONT);
	            add(friendLabel, friendsLabel.getX(), currentY + friendLabel.getAscent());
	            currentY += friendLabel.getAscent();
	        }
	    }
}
