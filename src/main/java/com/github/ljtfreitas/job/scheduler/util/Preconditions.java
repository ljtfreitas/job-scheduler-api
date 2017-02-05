package com.github.ljtfreitas.job.scheduler.util;

import java.util.Objects;

public class Preconditions {

	public static boolean isFalse(boolean condition, String message) {
		if (condition) {
			throw new IllegalArgumentException(message);
		} else {
			return true;
		}
	}

	public static <T> T isNotNull(T object, String message) {
		return Objects.requireNonNull(object, message);
	}
}
