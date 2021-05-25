package com.acceldata.api;

import java.io.IOException;

public interface IAccelData {

	void initializeCache() throws IOException;

	void createOrUpdateEntry(String key, String value);

	void deleteEntry(String key);

	String readEntry(String key);

	void doPeriodicCleanUp();

}