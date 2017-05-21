package com.tester.vlad.tester.manager.datamanager;

import java.util.List;

import com.tester.vlad.tester.entity.Test;

public interface IDataManager {
	public boolean saveTest(Test test);
	public List<Test> getTestList();
	public void removeTest(String test);
	public List<String> getTestNames();
	Test getTest(String name);
}
