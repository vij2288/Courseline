package entities;

import java.util.ArrayList;
import java.util.Iterator;

public class Course {
	private String univ;
	private String courseName;
	private String courseNumber;
	private String semester;
	private int version;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		String s1 = "Course [univ=" + univ + ", courseName=" + courseName
				+ ", courseNumber=" + courseNumber + ", semester=" + semester
				+ ", submissions=";
		
		String s2 = new String();
		Submission s = null;
		Iterator<Submission> it = submissions.iterator();
		while (it.hasNext()) {
			s = (Submission) it.next();
			s2 += "\n" + s.toString();
		}
		
		return s1 + s2 + " ]";
	}
}
