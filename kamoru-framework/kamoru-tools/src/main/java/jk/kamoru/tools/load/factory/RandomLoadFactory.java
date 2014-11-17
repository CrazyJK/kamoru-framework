package jk.kamoru.tools.load.factory;

import jk.kamoru.core.sleep.RandomSleep;
import jk.kamoru.core.sleep.Sleep;

public class RandomLoadFactory extends LoadFactoryService {

	@Override
	Sleep getSleep() {
		return new RandomSleep();
	}


}
