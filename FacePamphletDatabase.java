/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
    private Map<String, FacePamphletProfile> profiles;

	
	public FacePamphletDatabase()
	{
		profiles = new HashMap<>();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) 
	{
		String name = profile.getName();
        profiles.put(name, profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) 
	{
		return profiles.get(name);
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) 
	{
	    FacePamphletProfile deletedUser = profiles.remove(name);
	    if (deletedUser != null) 
	    {
	    	deleteFriends(name);
	    }
	}
	
	
	private void deleteFriends(String name)
	{
		for (FacePamphletProfile profile : profiles.values()) 
		{
            profile.removeFriend(name);
        }

	}
	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) 
	{
		return profiles.containsKey(name);
		
	}
	private String friendsToString(Iterator<String> friends) {
        StringBuilder friendsString = new StringBuilder();
        while (friends.hasNext()) {
            friendsString.append(friends.next());
            if (friends.hasNext()) {
                friendsString.append(",");
            }
        }
        return friendsString.toString();
    }
	
	
    public void saveProfilesToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) 
        {
            for (FacePamphletProfile profile : profiles.values()) {
                writer.write(profile.getName() + "|" + profile.getStatus() + "|" + profile.getImageAddress() + "|"
                        + friendsToString(profile.getFriends()));
                writer.newLine();
            }
            System.out.println("Profiles saved to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadProfilesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            profiles.clear(); // Clear existing profiles
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String name = parts[0];
                String status = parts[1];
                String imageAddress = parts[2];
                FacePamphletProfile profile = new FacePamphletProfile(name);
                profile.setStatus(status);
                profile.setImage(imageAddress);
                
                if (parts.length > 3) {
                    String[] friendNames = parts[3].split(",");
                    for (String friend : friendNames) {
                        profile.addFriend(friend);
                    }
                }
                
                profiles.put(name, profile);
            }
            System.out.println("Profiles loaded from file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
