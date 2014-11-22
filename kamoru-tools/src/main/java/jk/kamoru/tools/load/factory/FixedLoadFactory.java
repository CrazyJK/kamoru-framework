package jk.kamoru.tools.load.factory;

import jk.kamoru.core.sleep.SimpleSleep;
import jk.kamoru.core.sleep.Sleep;

public class FixedLoadFactory extends LoadFactoryService {@Override
	
	Sleep getSleep() {
		return new SimpleSleep();
	}


}
