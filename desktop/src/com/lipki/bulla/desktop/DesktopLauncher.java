package com.lipki.bulla.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lipki.bulla.Application;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.samples = 8;
		config.preferencesDirectory = "Bulla/saveStates/";
		config.preferencesFileType = FileType.Local;
		new LwjglApplication(new Application(), config);
	}
}
