package com.acceldata.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.acceldata.api.IAccelData;
import com.acceldata.utils.UtilContants;

@Component
public class AccelDataImpl implements IAccelData {
	private Map<String, String> keyValueCache = new HashMap<>();

	@Override
	public void initializeCache() throws IOException {

		File file = new File(UtilContants.CACHE_FILE_PATH);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		while ((st = br.readLine()) != null) {
			String split[] = st.split(UtilContants.SEPARATOR);
			if(split.length < 2) {
				continue;
			}
			if(!split[1].equals(UtilContants.NULL_STRING)) {
				keyValueCache.put(split[0], split[1]);
			}else {
				keyValueCache.put(split[0], null);
			}
		}

		br.close();
	}

	@Override
	public void createOrUpdateEntry(String key, String value) {

		keyValueCache.put(key, value);
		updateToFile(key, value);
	}

	@Override
	public void deleteEntry(String key) {

		if(keyValueCache.containsKey(key)) {
			keyValueCache.remove(key);
			updateToFile(key, null);
		}
	}

	@Override
	public String readEntry(String key) {
		return keyValueCache.get(key);
	}

	private void updateToFile(String key, String value) {

		try {
			FileWriter fw = new FileWriter(UtilContants.CACHE_FILE_PATH, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			out.append(key + UtilContants.SEPARATOR + value + UtilContants.NEW_LINE);

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPeriodicCleanUp() {
		try {
			FileWriter fw = new FileWriter(UtilContants.CACHE_FILE_PATH);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			for(String key : keyValueCache.keySet()) {
				if(keyValueCache.get(key) != null) {
					out.append(key + UtilContants.SEPARATOR + keyValueCache.get(key) + UtilContants.NEW_LINE);
				}
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
