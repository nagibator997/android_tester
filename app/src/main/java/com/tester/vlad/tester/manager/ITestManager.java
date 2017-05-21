package com.tester.vlad.tester.manager;

import java.util.List;

import com.tester.vlad.tester.entity.Test;
import com.tester.vlad.tester.manager.properties.PropertiesManager;

public interface ITestManager {
	int getCorrectAnswersQuantity();
	boolean isTestsAvailable();
	List<String> getTestNames();
	void removeTest(String testName);
	boolean runTest(String name);
	int getCurrentTestQuestionsSize();
	String getCurrentTestQuestion(int i);
	List<String> getCurrentQuestionAnswers(int i);
	void processAnswer(int answerNumber);
	Test getCurrentTest();
	boolean createTest(String testName, String email, List<String> questionsList, List<List<String>> answers, List<Integer> correctAnswers, String password);
	public PropertiesManager getProperties();
}
