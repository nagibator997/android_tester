package com.tester.vlad.tester.manager;

import java.util.List;

public interface IManagerListener {
	void runQuestion(String question, List<String> answers);
	void reportResult(int result);
	boolean checkPassword();
}
