package edu.handong.analysis.datamodel;

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
	
	
	public Course(String line) {
		String[] ary = line.split(",\\s");
		studentId = ary[0];
		yearMonthGraduated = ary[1];
		firstMajor = ary[2];
		secondMajor = ary[3];
		courseCode = ary[4];
		courseName = ary[5];
		courseCredit = ary[6];
		yearTaken = Integer.parseInt(ary[7]);
		semesterCourseTaken = Integer.parseInt(ary[8]);
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
	

}
