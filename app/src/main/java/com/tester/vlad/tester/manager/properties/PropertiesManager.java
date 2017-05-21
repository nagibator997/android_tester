package com.tester.vlad.tester.manager.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import android.util.Log;

public class PropertiesManager {
	private final String TAG = "PropertiesManager.class";
	private static final String PROPERTIES_FILE_NAME = "com.com.vlad.tester.com.com.vlad.tester.com.com.vlad.tester.properties";
	private static final String CHOOSEN_LANGUAGE_KEY = "ChoosenLanguage";
	private static final String CHOOSEN_DATA_MANAGER_KEY = "ChoosenDataManager";
	private static final String CHOOSEN_PASSTAT_KEY = "ChoosenPaswordStatus";
	private static final ChoosenLanguage DEFAULT_LANGUAGE = ChoosenLanguage.ENGLISH;
	private static final ChoosenDataManager DEFAULT_DATA_MANAGER = ChoosenDataManager.DATA_FILE_MANAGER;
	private static final boolean DEFAULT_PASSWORD_STATUS = true;
	private ChoosenLanguage lang;
	private ChoosenDataManager dataMan;
	private boolean passStat;
	
	public PropertiesManager(){
		File file = new File(PROPERTIES_FILE_NAME);
		if (!file.exists()){
			saveDefaultProperties();
		} else {
			getSavedProperties();
		}
	}

	private void getSavedProperties() {
		Properties prop = new Properties();
		try (InputStream fis = new FileInputStream(PROPERTIES_FILE_NAME)) {
			prop.load(fis);
			lang = ChoosenLanguage.valueOf(prop.getProperty(CHOOSEN_LANGUAGE_KEY));
			dataMan = ChoosenDataManager.valueOf(prop.getProperty(CHOOSEN_DATA_MANAGER_KEY));
			passStat = Boolean.valueOf(prop.getProperty(CHOOSEN_PASSTAT_KEY));
			System.out.println(lang.toString());
			System.out.println(dataMan.toString());
			System.out.println(Boolean.toString(passStat));
		} catch (Exception e) {
			Log.e(TAG, "Failed to get saved properties", e);
			e.printStackTrace();
		}
		
	}

	private void saveDefaultProperties() {
		Properties prop = new Properties();
		try (OutputStream fos = new FileOutputStream(PROPERTIES_FILE_NAME)) {
			// set the properties value
			lang = DEFAULT_LANGUAGE;
			dataMan = DEFAULT_DATA_MANAGER;
			passStat = DEFAULT_PASSWORD_STATUS;
			prop.setProperty(CHOOSEN_LANGUAGE_KEY, DEFAULT_LANGUAGE.toString());
			prop.setProperty(CHOOSEN_DATA_MANAGER_KEY, DEFAULT_DATA_MANAGER.toString());
			prop.setProperty(CHOOSEN_PASSTAT_KEY, Boolean.toString(DEFAULT_PASSWORD_STATUS));
			// save properties to project root folder
			prop.store(fos, null);
		} catch (Exception e) {
			Log.e(TAG, "Failed to create default properties file", e);
			e.printStackTrace();
		}
	}
	
	public boolean saveProperties(ChoosenLanguage lang, ChoosenDataManager dataMan, boolean passStat){
		Properties prop = new Properties();
		try (OutputStream fos = new FileOutputStream(PROPERTIES_FILE_NAME)) {
			lang = DEFAULT_LANGUAGE;
			dataMan = DEFAULT_DATA_MANAGER;
			passStat = DEFAULT_PASSWORD_STATUS;
			prop.setProperty(CHOOSEN_LANGUAGE_KEY, lang.toString());
			prop.setProperty(CHOOSEN_DATA_MANAGER_KEY, dataMan.toString());
			prop.setProperty(CHOOSEN_PASSTAT_KEY, Boolean.toString(passStat));
			prop.store(fos, null);
			this.passStat = passStat;
			this.lang = lang;
			this.dataMan = dataMan;
		} catch (Exception e) {
			Log.e(TAG, "Failed to save Property", e);
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public ChoosenLanguage getLang() {
		return lang;
	}

	public ChoosenDataManager getDataMan() {
		return dataMan;
	}

	public boolean isPassStat() {
		return passStat;
	}
	
} 

