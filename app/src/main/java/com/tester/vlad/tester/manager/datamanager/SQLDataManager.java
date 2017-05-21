package com.tester.vlad.tester.manager.datamanager;

import android.util.Log;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.tester.vlad.tester.entity.Test;

public class SQLDataManager implements IDataManager {
	private static final String TAG = "SQLDataManager.class";
	private Connection con;
	private Statement stat;
	private Gson gson;
	
	public SQLDataManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + "Test.db");
			stat = con.createStatement();
			String initializeTableString = "create table if not exists 'Tests' ('testname' text PRIMARY KEY, 'test' text)";
			stat.execute(initializeTableString);
			gson = new Gson();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "SQLDataManager constructor was not initialized", e);
		}
	}

	@Override
	public boolean saveTest(Test test) {
		String request = "insert into 'Tests' ('testname', 'test') values ('"+ test.getTestName() + "', '"+ gson.toJson(test)+"');";
		try {
			stat.execute(request);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG,"SQL file was not saved", e);
			return false;	
		}
	}

	@Override
	public List<Test> getTestList() {
		String request = "select * from Tests";
		List<Test> resultList = new ArrayList<Test>();
		try {
			ResultSet result = (ResultSet) stat.executeQuery(request);
			if (result==null){
				return null;
			}
			while(result.next()){
				resultList.add(gson.fromJson(result.getString(2), Test.class));
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG,"Failed to get test list", e);
			return null;
		}
	}

	@Override
	public void removeTest(String test) {
		String request = "delete from 'Tests' where testname = '"+ test +"';";
		try {
			stat.execute(request);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG,"Failed to remove test from sql table", e);
		}

	}

	@Override
	public List<String> getTestNames() {
		String request = "select * from Tests";
		List<String> resultList = new ArrayList<String>();
		try {
			ResultSet result = (ResultSet) stat.executeQuery(request);
			if (result==null){
				return null;
			}
			while(result.next()){
				resultList.add(result.getString(1));
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "Failed to get test names", e);
			return null;
		}
	}

	@Override
	public Test getTest(String name) {
		String request = "select test from Tests where testname = '" + name + "'";
		try {
			ResultSet result = (ResultSet) stat.executeQuery(request);
			if (result==null){
				return null;
			}
			if (result.next()){
				return gson.fromJson(result.getString(1), Test.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "Failed to get test names", e);
			return null;
		}
		return null;
	}

}
