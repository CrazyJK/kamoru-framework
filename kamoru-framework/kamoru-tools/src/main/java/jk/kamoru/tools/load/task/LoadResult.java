package jk.kamoru.tools.load.task;

import jk.kamoru.core.task.Result;
import lombok.Data;

@Data
public class LoadResult implements Result {

	private boolean success;
	private String error;
	private long totalLoad;
	
	public void count() {
		totalLoad++;
	}
	
}
