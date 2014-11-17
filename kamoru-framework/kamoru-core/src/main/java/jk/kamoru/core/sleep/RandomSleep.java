package jk.kamoru.core.sleep;

public class RandomSleep implements Sleep {

	@Override
	public void sleep(long millis) {
		try {
			Thread.sleep((long)(Math.random() * millis));
		} catch (InterruptedException e) {
			throw new SleepException(e);
		};
	}

}
