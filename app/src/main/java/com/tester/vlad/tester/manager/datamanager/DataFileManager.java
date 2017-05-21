package com.tester.vlad.tester.manager.datamanager;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


import com.tester.vlad.tester.entity.Test;

public class DataFileManager implements IDataManager {

	private static final String TESTS_DIRECTORY = "test";
	private static final String TAG = "DataFileManager.class";

	public DataFileManager() {
		File file = new File(TESTS_DIRECTORY); // создается объект типа File c
												// путем TESTS_DIRECTORY в
												// аргументе
		if (!file.exists()) { // если не существует ничего,
			file.mkdir(); // создается директория
		} else if (file.isFile()) { // если сушествует и это файл,
			file.delete(); // он удаляется и
			file.mkdir(); // создается директория
		}
	}

	@Override
	public boolean saveTest(Test test) {
		FileOutputStream fout = null;
		ObjectOutputStream outs = null;

		try {
			File tmpFile = new File(TESTS_DIRECTORY + File.separator + test.getTestName()
					+ ".test"); // создаем
																					// файл
			fout = new FileOutputStream(tmpFile); // Создаем "объект доступа"
													// FileOutputStream для
													// доступа к файлу, передаем
													// файл ему в аргумент
			outs = new ObjectOutputStream(fout); // Создаем "объект записи"
													// ObjectOutputStream для
													// записи других объектов,
													// передаем объект доступа
													// ему в аргумент
			outs.writeObject(test); // Записываем объект test с помощью
									// ObjectOutputStream
			Log.i(TAG, "Test" + test.getTestName() + "was saved as file");
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			Log.e(TAG, "Test" + test.getTestName() + "was not saved as file", ex);
			return false;
		} finally { // finally значит в обязательном порядке, после выполнения
					// блока try/catch
			if (fout != null) {
				try {
					fout.close(); // закрываем поток доступа
				} catch (IOException e) {
					Log.e(TAG, "FileOutputStream was not closed properly", e);
					e.printStackTrace();
				}
			}
			if (outs != null) {
				try {
					outs.close(); // закрываем поток записи
				} catch (IOException e) {
					Log.e(TAG, "ObjectOutputStream was not closed properly", e);
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public List<Test> getTestList() {
		ArrayList<Test> result = new ArrayList<Test>();
		File folder = new File(TESTS_DIRECTORY); // мы создаем новый объект
													// который будет ссылаться
													// туда же куда и File file
													// = new
													// File(TESTS_DIRECTORY);
		File[] folderContent = folder.listFiles(); // записываем в массив все
													// файлы в директории folder
		for (int i = 0; i < folderContent.length; i++) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(folderContent[i]))) {
				result.add((Test) ois.readObject());
			} catch (Exception e) {
				Log.e(TAG, "Failed to get test list from ArrayList", e);
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void removeTest(String test) {
		File tmpFile = new File(TESTS_DIRECTORY + File.separator + cutExtensionFromTestName(test)
				+ ".test"); // создаем
																				// временный
																				// объект
																				// (который
																				// будет
																				// ссылаться
																				// туда
																				// же
																				// куда
																				// и
																				// File
																				// file
																				// =
																				// new
																				// File(TESTS_DIRECTORY))
		if (tmpFile.exists()) { // если такой файл существует
			tmpFile.delete(); // удаляем его
		}
	}

	@Override
	public List<String> getTestNames() {
		ArrayList<String> result = new ArrayList<String>();
		File folder = new File(TESTS_DIRECTORY); // мы создаем новый объект
													// который будет ссылаться
													// туда же куда и File file
													// = new
													// File(TESTS_DIRECTORY);?
		File[] folderContent = folder.listFiles(); // записываем в массив все
													// файлы в директории folder
		for (int i = 0; i < folderContent.length; i++) {
			result.add(cutExtensionFromTestName(folderContent[i].getName()));
		}
		return result;
	}

	public Test getTest(String name) {
		List<Test> tests = getTestList();
		for (int i = 0; i < tests.size(); i++) {
			String testName = tests.get(i).getTestName();
			if ((testName).equals(cutExtensionFromTestName(name))) {
				return tests.get(i);
			}
		}
		return null;
	}

	private String cutExtensionFromTestName(String testWithExtension) {
		int extensionIndex = testWithExtension.lastIndexOf(".");
		if (extensionIndex == -1)
			return testWithExtension;
		return testWithExtension.substring(0, extensionIndex);
	}

}
