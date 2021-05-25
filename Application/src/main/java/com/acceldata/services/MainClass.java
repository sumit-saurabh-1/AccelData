package com.acceldata.services;

import java.io.IOException;

import com.acceldata.api.IAccelData;

public class MainClass {

	public static void main(String args[]) {
		
		IAccelData obj = new AccelDataImpl();
		
		try {
			obj.initializeCache();
			obj.createOrUpdateEntry("k1", "v1");
			obj.createOrUpdateEntry("k1", "v2");
			obj.createOrUpdateEntry("k2", "v1");
			obj.createOrUpdateEntry("k3", "v1");
			obj.deleteEntry("k1");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
