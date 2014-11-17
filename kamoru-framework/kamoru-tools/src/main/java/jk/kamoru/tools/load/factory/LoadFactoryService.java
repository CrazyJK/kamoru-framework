package jk.kamoru.tools.load.factory;

import jk.kamoru.core.sleep.Sleep;
import jk.kamoru.core.task.TaskFactory;
import jk.kamoru.tools.load.task.Load;
import jk.kamoru.tools.load.task.LoadData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class LoadFactoryService implements TaskFactory<Load> {
	
	protected long delay;
	protected LoadData loadData;

	abstract Sleep getSleep();
	
	@Override
	public Load newTask() {
		Load load = new Load();
		load.setDelay(delay);
		load.setLoadData(loadData);
		load.setSleep(getSleep());
		log.info("{}", load);
		return load;
	}

}
