package com.tester.vlad.tester.entity;

import java.io.Serializable;
import java.util.List;

public class Questions implements Serializable {
	private static final long serialVersionUID = -8755219453808194350L;
	private String question;
	private List<String> answers;
	private int correctAnswer;

	public Questions(String question, List<String> answers, int correctAnswer) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public int getCorrectAnswers() {
		return correctAnswer;
	}

	public String toString() {
		String result = question;
		for (int i = 0; i < getAnswers().size(); i++) {
			result = result + "\n" + (i + 1) + ":" + getAnswers().get(i);
		}
		result = "\n" + result + "\n" + "Correct answer: " + correctAnswer;
		return result;
	}

	public String toJson() {
		String answers = new String();
		for (int i = 0; i < getAnswers().size(); i++) {
			if (i != getAnswers().size() - 1) {
				answers = answers + "\"" + getAnswers().get(i) + "\",";
			} else {
				answers = answers + "\"" + getAnswers().get(i) + "\"";
			}
		}
		answers = "[" + answers + "]";
		String result = "{" + "\"question\":" + "\"" + question + "\"," + "\"answers\":" + answers + ","
				+ "\"correctAnswer\":" + correctAnswer + "}";
		return result;
	}

}
