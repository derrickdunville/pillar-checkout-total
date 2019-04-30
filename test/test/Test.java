package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);
		for(Failure failure: result.getFailures()) {
			System.out.println("Failed: " + failure.getDescription());
		}
		
		if(result.wasSuccessful()) {
			System.out.println("OK ("+ result.getRunCount() + " tests) Time: " + (double) result.getRunTime()/1000);
		}
	}
}