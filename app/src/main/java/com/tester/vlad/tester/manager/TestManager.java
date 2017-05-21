package com.tester.vlad.tester.manager;

import java.util.ArrayList;
import java.util.List;

import com.tester.vlad.tester.entity.Questions;
import com.tester.vlad.tester.entity.Test;
import com.tester.vlad.tester.manager.datamanager.DataFileManager;
import com.tester.vlad.tester.manager.datamanager.IDataManager;
import com.tester.vlad.tester.manager.datamanager.SQLDataManager;
import com.tester.vlad.tester.manager.properties.ChoosenDataManager;
import com.tester.vlad.tester.manager.properties.PropertiesManager;
import android.util.Log;

public class TestManager implements ITestManager {
	private static final String TAG = "TestManager.class";
	private IDataManager dataManager;
	private IManagerListener listener;
	private PropertiesManager properties;
	private Test currentTest;
	private int correctAnswers;
	private int testIterator;

	public TestManager(IManagerListener listener) {
		this.listener = listener;
		properties = new PropertiesManager();
		if (properties.getDataMan()==ChoosenDataManager.DATA_FILE_MANAGER){
			dataManager = new DataFileManager();
		} else if (properties.getDataMan()==ChoosenDataManager.SQL_DATA_MANAGER){
			dataManager = new SQLDataManager();
		}
	}

	public boolean isTestsAvailable() {
		return dataManager.getTestList().size() > 0;
	}

	public void removeTest(String testName) {
		dataManager.removeTest(testName);

	}

	public List<String> getTestNames() {
		return dataManager.getTestNames();
	}

	public boolean runTest(String name) {
		currentTest = dataManager.getTest(name);
		correctAnswers = 0;
		testIterator = 0;
		if (currentTest == null) {
			Log.w(TAG, "Test" + name + "was not launched. Current test = null");
			return false;
		} else {
			runQuestion(testIterator);
		}
		return true;
	}

	private void runQuestion(int questionNumber) {
		listener.runQuestion(currentTest.getQuestions().get(questionNumber).getQuestion(),
				currentTest.getQuestions().get(questionNumber).getAnswers());
	}

	private boolean checkPassword() {
		return listener.checkPassword();
	}

	public int getCurrentTestQuestionsSize() { // получаем количество вопросов в тесте
		return currentTest.getQuestions().size();
	}

	public String getCurrentTestQuestion(int i) { // выбираем конкретный вопрос из теста
		return currentTest.getQuestions().get(i).getQuestion();
	}

	public List<String> getCurrentQuestionAnswers(int i) { // выводим все варианты ответов вопросу
		return currentTest.getQuestions().get(i).getAnswers();
	}

	public void processAnswer(int answerNumber) { // проверяем правильный ответ или нет, если да то добавляем +1 в количество правильных ответов
		if (answerNumber == currentTest.getQuestions().get(testIterator).getCorrectAnswers()) {
			correctAnswers++;
			Log.i(TAG, "Test " + currentTest.getTestName() + ", Question #" + testIterator + " answered correctly.");
		} else {
			Log.i(TAG, "Test " + currentTest.getTestName() + ", Question #" + testIterator + " answered wrong.");
		}
		testIterator++;

		if (testIterator == currentTest.getQuestions().size()) {
			if (checkPassword() == true) {
				int result = (correctAnswers * 100) / currentTest.getQuestions().size();
				listener.reportResult(result);
				Log.i(TAG, "Test " + currentTest.getTestName() + " passed correctly. Result = " + result);
			}
		} else {
			runQuestion(testIterator);
		}
	}

	public int getCorrectAnswersQuantity() {
		return correctAnswers;
	}

	public Test getCurrentTest() {
		return currentTest;
	}
	
	public PropertiesManager getProperties(){
		return properties;
	}

	@Override
	public boolean createTest(String testName, String email, List<String> questionsList, List<List<String>> answers,
			List<Integer> correctAnswers, String password) {
		if (questionsList.size() != correctAnswers.size() && questionsList.size() != answers.size()) {
			Log.w(TAG, "Test" + testName
					+ "was not created. Question list size != answers list size or correct answers list size.");
			return false;
		}
		List<Questions> questions = new ArrayList<Questions>();
		for (int i = 0; i < questionsList.size(); i++) {
			questions.add(new Questions(questionsList.get(i), answers.get(i), correctAnswers.get(i)));
		}
		Test test = new Test(testName, email, questions, password);
		if (!dataManager.saveTest(test)) {
			return false;
		}
		return true;
	}


}
