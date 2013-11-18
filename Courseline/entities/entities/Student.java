package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
	// Student Credentials
	private String emailId;
	private String firstName;
	private String lastName;
	private String univ;

	// Courses added
	public ArrayList<Course> courses = new ArrayList<Course>();

	// Profile pic location
	private String profilePicPath;

	// Setters and Getters
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUniv() {
		return univ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	@Override
	public String toString() {
		return "Student [emailId=" + emailId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", univ=" + univ + ", courses="
				+ courses + ", profilePicPath=" + profilePicPath + "]";
	}
	
	
}
