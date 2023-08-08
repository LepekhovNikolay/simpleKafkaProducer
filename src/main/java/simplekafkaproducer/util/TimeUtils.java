package simplekafkaproducer.util;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

	public static void sleep(long amount, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(amount);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
