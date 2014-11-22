package jk.kamoru.tools.load;

import jk.kamoru.core.task.TaskExecutor;
import jk.kamoru.tools.load.executor.LoadTester;
import jk.kamoru.tools.load.factory.FixedLoadFactory;
import jk.kamoru.tools.load.factory.LoadFactoryService;
import jk.kamoru.tools.load.factory.RandomLoadFactory;
import jk.kamoru.tools.load.task.LoadData;

public class LoadTesters {
	
	public static TaskExecutor newLoadTester(int nLoads, LoadFactoryService loadFactory) {
		LoadTester loadTester = new LoadTester();
		loadTester.setNLoads(nLoads);
		loadTester.setLoadFactory(loadFactory);
		return loadTester;
	}
	
	public static TaskExecutor newFixedDelayLoadTester(int nLoads, long delay, LoadData loadData) {
		FixedLoadFactory loadFactory = new FixedLoadFactory();
		loadFactory.setDelay(delay);
		loadFactory.setLoadData(loadData);

		return newLoadTester(nLoads, loadFactory);
	}
	
	public static TaskExecutor newRandomDelayLoadTester(int nLoads, long delay, LoadData loadData) {
		RandomLoadFactory loadFactory = new RandomLoadFactory();
		loadFactory.setDelay(delay);
		loadFactory.setLoadData(loadData);

		return newLoadTester(nLoads, loadFactory);
	}
	

}
