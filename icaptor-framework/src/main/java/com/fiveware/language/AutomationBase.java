package com.fiveware.language;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.sikuli.basics.Settings;
import org.sikuli.script.Screen;

public abstract class AutomationBase {
	
	protected ChromeDriver driver;
	protected Screen screen;	
	
	protected void initWebDriver() {


 		File chromeDriverFile = new File("/usr/local/bin/chromedriver");
        if(chromeDriverFile.exists())
		{
		
        	System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");                   
		
		}else
		{
            System.setProperty("webdriver.chrome.driver", "./chromedriver");
		}

		ChromeOptions options = new ChromeOptions();



		Map<String, Object> preferences = new Hashtable<String, Object>();
		options.setExperimentalOption("prefs", preferences);

		// disable flash and the PDF viewer
		preferences.put("plugins.always_open_pdf_externally", true);
		preferences.put("download.default_directory", "/tmp");
		preferences.put("profile.password_manager_enabled",false);

		driver = new ChromeDriver(options);

		driver.manage().deleteAllCookies();   

		driver.manage().window().maximize();



	}

	protected void initSikuli()
	{
          screen =  new Screen();
	      Settings.MinSimilarity = 1;
	      Settings.OcrTextSearch = true;

	      Settings.OcrTextRead = true;
	}

}
