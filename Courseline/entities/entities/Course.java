package entities;

import java.util.ArrayList;

public class Course {
	private String courseName;
	private String courseNumber;
	public ArrayList<Submission> submissions;

	// Setters and Getters
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
}
