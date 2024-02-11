/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	
	private JLabel lblName= new JLabel("Name");
	private JTextField txtName = new JTextField(15);
	private JButton btnAdd=new JButton("Add");
	private JButton btnDelete= new JButton("Delete");
	private JButton btnLookup=new JButton("Lookup");
	private JButton btnChangeStatus=new JButton("Change Status");
	private JTextField txtChangeStatus = new JTextField(15);
	private JButton btnChangePicture=new JButton("Change Picture");
	private JTextField txtChangePicture = new JTextField(15);
	private JButton btnAddFriend=new JButton("Add Friend");
	private JTextField txtAddFriend = new JTextField(15);
	private FacePamphletProfile profile=null;
	private FacePamphletDatabase database;
	private GImage image;
	private FacePamphletCanvas canvas;
	
	public FacePamphlet()
	{
		canvas = new FacePamphletCanvas(); 
		add(canvas);
		database=new FacePamphletDatabase();
	}
	
	public void init() 
	{
		add(lblName,NORTH);
		add(txtName,NORTH);
		add(btnAdd,NORTH);
		add(btnDelete,NORTH);
		add(btnLookup,NORTH);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		add(txtChangeStatus,WEST);
		add(btnChangeStatus,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		add(txtChangePicture,WEST);
		add(btnChangePicture,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		add(txtAddFriend,WEST);
		add(btnAddFriend,WEST);
		
		addActionListeners(this);
		txtName.addActionListener(this);
		txtChangeStatus.addActionListener(this);
		txtChangePicture.addActionListener(this);
		txtAddFriend.addActionListener(this);
		
		//loading existing profiles which are stored in the txt file
		database.loadProfilesFromFile("profiles.txt");
    }
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) 
    {
    	//logic of add button
        if (e.getSource()==btnAdd || e.getSource()==txtName) 
        {
        	String nameToAdd = txtName.getText();
        	if(!nameToAdd.isEmpty())
        	{
        		if(database.containsProfile(nameToAdd))
        		{
        			profile = database.getProfile(nameToAdd);
        			printCurrentProfile("A profile with the name "+ nameToAdd +" already exists");
        			loadDataInputFields(profile);
        		}
        		else
        		{
        			profile = new FacePamphletProfile(txtName.getText());
		    		database.addProfile(profile);
		    		println("Add: new profile "+ database.getProfile(nameToAdd).toString());
		    		printCurrentProfile("New profile created");
		    		resetInputFields();
        		}
        	}
        }
       //logic of delete button
        else if (e.getSource()==btnDelete)
        {
            String nameToDelete = txtName.getText();
            if(database.containsProfile(nameToDelete))
            {
            	database.deleteProfile(nameToDelete);
            	println("Profile deleted: " + nameToDelete);
            	profile=null;
            	printCurrentProfile("Profile of "+ nameToDelete + " deleted");
            }
            else 
            {
                println("Profile not found for deletion: " + nameToDelete);
                printCurrentProfile("Profile not found for deletion");
            }
        } 
        //logic of btnLookup
        else if (e.getSource()==btnLookup)
        {
        	String nameToLookup = txtName.getText();
            if (database.containsProfile(nameToLookup))
            {
                println("Lookup: " + database.getProfile(nameToLookup).toString());
                printCurrentProfile("Displaying " + nameToLookup);
                printLookup(nameToLookup);
            } 
            else 
            {
                println("Profile not found: " + nameToLookup);
                printCurrentProfile("A Profile with the name " + nameToLookup +" doesn't exist");
            }
        } 
        //statuc changing button
        else if (e.getSource()==btnChangeStatus || e.getSource()==txtChangeStatus) 
        {
        	String newStatus = txtChangeStatus.getText();
            if (profile != null) 
            {
                profile.setStatus(newStatus);
                printCurrentProfile("Status Changed to " + newStatus );
            } 
            else 
            {
                printCurrentProfile("Please select a profile to change status");
            }
        } 
        
        //picture changing button
        else if (e.getSource()==btnChangePicture || e.getSource()==txtChangePicture)
        {
        	String newPicture = txtChangePicture.getText();
            if (profile != null) 
            {
            	profile.setImage(newPicture);
            	printCurrentProfile("Picture Updated");
            } 
            else 
            {
                printCurrentProfile("Please select a profile to update profile picture");
            }
        } 
        //adding friend button
        else if (e.getSource()==btnAddFriend || e.getSource()==txtAddFriend)
        {
        	String friend = txtAddFriend.getText();
           if(profile!=null)
           {
        	   if(!database.containsProfile(friend))
        	   {
        		   printCurrentProfile("Such profile doesn't exist");
        	   }
        	   else
        	   {
        		   if(!hasFriend(profile,friend))
        		   {
        			   profile.addFriend(friend);
        			   database.getProfile(friend).addFriend(profile.getName());
        			   printCurrentProfile(friend+" added as a friend");
        		   }
        		   else
        		   {
        			 printCurrentProfile(friend+" is already a friend");
        		   }
        	   }
           }
           else
           {
        	   printCurrentProfile("Please select a profile to add friend");
           }
        }
	}
    // profile on canvas is displayed from this fucntion since this function is executed basically everytime after add/delete/picChange/.. e.t.c
    private void printCurrentProfile(String message)
    {
    	if(profile==null)
    	{
    		resetInputFields();
    	}
    	
    	canvas.displayProfile(profile);
    	canvas.showMessage(message);
    	database.saveProfilesToFile("profiles.txt");
    }
    // similar function to printCurrentProfile, but it shows lookedup profile
    private void printLookup(String name)
    {
    	canvas.displayProfile(database.getProfile(name));
    	canvas.showMessage("Displaying "+name);
    }
    //reseting if the new profile created
    private void resetInputFields() 
    {
        txtChangeStatus.setText("");
        txtChangePicture.setText("");
        txtAddFriend.setText("");
    }
    //loading data in fields if the profile is loaded
    private void loadDataInputFields(FacePamphletProfile profile)
    {
    	resetInputFields();
    	txtChangeStatus.setText(profile.getStatus());
    	txtChangePicture.setText(profile.getImageAddress());
    }
    //function to determine whether profile has this friend or not
    private boolean hasFriend(FacePamphletProfile profile, String name)
    {
    	 Iterator<String> iterator = profile.getFriends();
    	    while (iterator.hasNext()) 
    	    {
    	        String friend = iterator.next();
    	        if (friend.equals(name)) 
    	        {
    	            return true;
    	        }
    	    }
    	    return false;
    }
    
}
