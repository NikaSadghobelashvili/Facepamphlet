/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 */
	private String _name;
	private String _status;
	private GImage _image;
	private ArrayList<String> friends;
	private String _imageAddress=EMPTY_LABEL_TEXT;
	
	public FacePamphletProfile(String name) 
	{
		_name=name;
		_status="";
		_image = null;
		friends = new ArrayList<String>();
	}

	/** This method returns the name associated with the profile. */ 
	public String getName() 
	{
		return _name;
	}

	/** 
	 * This method returns the image associated with the profile.  
	 * If there is no image associated with the profile, the method
	 * returns null. */ 
	public GImage getImage() 
	{
		return _image;
	}

	/** This method sets the image associated with the profile. */ 
	public void setImage(GImage image) 
	{
		_image = image;
		if(_image!=null)
		{
		_image.scale(IMAGE_WIDTH/_image.getWidth(),IMAGE_HEIGHT/_image.getHeight());
		}
		
	}
	public void setImage(String address)
	{
		if(address.isEmpty() || address.equals(""))
		{
			_imageAddress=address;
			_image=null;
			return;
		}
		try
		{
			_imageAddress=address;
			GImage image = new GImage(_imageAddress);
			setImage(image);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public String getImageAddress()
	{
		return _imageAddress;
	}
	
	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getStatus() 
	{
		return _status;
	}
	
	/** This method sets the status associated with the profile. */ 
	public void setStatus(String status) 
	{
		_status=status;
	}

	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 */
	public boolean addFriend(String friend) 
	{
		if(!friends.contains(friend) && !friend.equals(_name))
		{
			friends.add(friend);
			return true;
		}
		return false;
	}

	/** 
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the 
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name 
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) 
	{
		if(friends.contains(friend))
		{
			friends.remove(friend);
			return true;
		}
		return false;
	}

	/** 
	 * This method returns an iterator over the list of friends 
	 * associated with the profile.
	 */ 
	public Iterator<String> getFriends()
	{
		return friends.iterator();
	}
	
	/** 
	 * This method returns a string representation of the profile.  
	 * This string is of the form: "name (status): list of friends", 
	 * where name and status are set accordingly and the list of 
	 * friends is a comma separated list of the names of all of the 
	 * friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is 
	 * "coding" and who has friends Don, Chelsea, and Bob, this method 
	 * would return the string: "Alice (coding): Don, Chelsea, Bob"
	 */ 
	public String toString() 
	{
		StringBuilder result = new StringBuilder();
        result.append(_name).append(" (").append(_status).append("): ");
        
        if (!friends.isEmpty()) 
        {
            for (int i=0;i<friends.size();i++) 
            {
            	if(i!=friends.size()-1)
            	{
            	result.append(friends.get(i)).append(", ");
            	}
            	else
            	{
            	result.append(friends.get(i));
            	}
            }
        }

        return result.toString();
    }
}
	

