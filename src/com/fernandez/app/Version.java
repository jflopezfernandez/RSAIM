package com.fernandez.app;

public class Version {

	private static int major = 0;
	private static int minor = 0;
	private static int build = 2;
	private static int revis = 0;

	public String getString() {
		return String.format("%d.%d.%d.%d", major, minor, build, revis);
	}
}
