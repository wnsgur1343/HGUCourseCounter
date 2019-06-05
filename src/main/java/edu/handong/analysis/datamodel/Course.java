package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	
	public Course(CSVRecord str) {
		studentId = str.get(0);
		yearMonthGraduated = str.get(1);
		firstMajor = str.get(2);
		secondMajor = str.get(3);
		courseCode = str.get(4);
		courseName = str.get(5);
		courseCredit = str.get(6);
		yearTaken = Integer.parseInt(str.get(7));
		semesterCourseTaken = Integer.parseInt(str.get(8));
	}


	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public int getYearTaken() {
		return yearTaken;
	}
	
	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
	
	public String getcourseCode() {
		return courseCode;
	}
	
	public String getcourseName() {
		return courseName;
	}
	

}
