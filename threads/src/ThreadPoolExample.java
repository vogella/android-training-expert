

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

	public static void main(String[] args) {
		
		int numberOfCores = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of cores : " + numberOfCores);
		
		LinkedBlockingQueue<Runnable> fifoQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			numberOfCores, 		 // number of cores 
			numberOfCores,       // max number of workers
			1, TimeUnit.SECONDS,	 // keep alive time
			fifoQueue
		);
		
		for (int i=0; i<10; i++) {
			final int taskNumber = i;
			threadPool.execute(new Runnable() {
				@Override public void run() {
					String name = Thread.currentThread().getName();
					System.out.println("Task: " + taskNumber + ", thread: " + name + " started");
					waitHere(2000); // work here
					System.out.println("Task: " + taskNumber + ", thread: " + name + " done");
				}
			});
		}
		
	}
	
	public static final void waitHere(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void timeMeasring() {
		long nano = System.nanoTime();
	}
}
