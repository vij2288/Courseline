package entities;

import java.util.ArrayList;
import java.util.Date;

public class Submission {
	private String subName;
	private SubType subType;
	private Date releaseDate;
	private Date dueDate;
	private int weightPercent; // percentage grading
	private int weightPoints; // absolute weightage
	private String description;
	public ArrayList<String> instructorNotes;
	public ArrayList<String> studentNotes;
	public ArrayList<String> studentPicsPaths;

	// Setters and Getters
	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public SubType getSubType() {
		return subType;
	}

	public void setSubType(SubType subType) {
		this.subType = subType;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getWeightPercent() {
		return weightPercent;
	}

	public void setWeightPercent(int weightPercent) {
		this.weightPercent = weightPercent;
	}

	public int getWeightPoints() {
		return weightPoints;
	}

	public void setWeightPoints(int weightPoints) {
		this.weightPoints = weightPoints;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
