package jk.kamoru.tools.load.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jk.kamoru.core.task.TaskExecutor;
import jk.kamoru.tools.load.factory.LoadFactoryService;
import jk.kamoru.tools.load.task.Load;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LoadTestService implements TaskExecutor {

	Collection<Load> loads = new ArrayList<Load>();

	abstract int getNLoads();
	abstract LoadFactoryService getLoadFactory(); 

	@Override
	public void execute() {
		log.info("start");
		
		for (int i=0; i<getNLoads(); i++) {
			loads.add(getLoadFactory().newTask());
		}
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(getNLoads());
			executorService.invokeAll(loads);
			executorService.shutdown();
		} catch (InterruptedException e) {
			throw new IllegalStateException();
		}
	}

	@Override
	public void execute(long testingTime) {
		log.info("start {} {}", testingTime, getNLoads());
		
		for (int i=0; i<getNLoads(); i++) {
			loads.add(getLoadFactory().newTask());
		}
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(getNLoads());
			executorService.invokeAll(loads, testingTime, TimeUnit.MILLISECONDS);
			executorService.shutdown();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
