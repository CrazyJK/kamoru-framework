package jk.kamoru.util;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuntimeUtils {

	public static void exec(String command) {
		log.trace("exec {}", command);
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new RuntimeException("execute error", e);
		}
	}
	
	public static void exec(String[] command) {
		log.trace("exec {}", ArrayUtils.toString(command));
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new RuntimeException("execute error", e);
		}
	}
}
