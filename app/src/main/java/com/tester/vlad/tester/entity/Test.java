package com.tester.vlad.tester.entity;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
	private static final long serialVersionUID = 954059713998500470L;
	private String testName;
	private String email;
	private String password;
	private List<Questions> questions;

	public Test(String testName, String email, List<Questions> questions, String password) {
		this.testName = testName;
		this.email = email;
		this.questions = questions;
		this.password = password;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public String getTestName() {
		return testName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String toString() {
		String result = getTestName() + "\n" + getEmail();
		for (int i = 0; i < getQuestions().size(); i++) {
			result = result + getQuestions().get(i).toString();
		}
		return result;
	}

	public String toJson() {
		String result = "\"testName\":" + "\"" + getTestName() + "\"," + "\"email\":" + "\"" + getEmail() + "\","
				+ "\"password\":" + "\"" + getPassword() + "\"," + "\"questions\":";
		for (int i = 0; i < getQuestions().size(); i++) {
			if (getQuestions().size() == 1) {
				result = result + "[" + getQuestions().get(i).toJson() + "]";
			} else if (i == getQuestions().size() - 1 && getQuestions().size() != 1) {
				result = result + getQuestions().get(i).toJson() + "]";
			} else {
				result = result + getQuestions().get(i).toJson() + ",";
			}
		}
		return "{" + result + "}";
	}
}
