package com.snakybo.snakemp.common.util;


public class Utils {
	public static float randFloat(float min, float max) {
		return (float)(min + (Math.random() * (max - min)));
	}
}
