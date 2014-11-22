package jk.kamoru.core.sleep;

public class SimpleSleep implements Sleep {

	@Override
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new SleepException(e);
		}
		
	}

}
