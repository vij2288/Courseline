package entities;

import java.util.ArrayList;

public class Course {
	private String univ;
	private String courseName;
	private String courseNumber;
	private String semester;
	public ArrayList<Submission> submissions = new ArrayList<Submission>();

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

	public String getUniv() {
		return univ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	@Override
	public String toString() {
		return "Course [univ=" + univ + ", courseName=" + courseName
				+ ", courseNumber=" + courseNumber + ", semester=" + semester
				+ ", submissions=" + submissions + "]";
	}
	
	
}
