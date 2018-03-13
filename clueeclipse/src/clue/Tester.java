package clue;



// Testing module - requires Colors class

import java.util.ArrayList;

public class Tester {
	static int totalFailures = 0;
	static int totalTests = 0;
	
	ArrayList<Boolean> results = new ArrayList<Boolean>();

	static boolean testStatic(boolean t, String s) {
		totalTests += 1;
		if (!t) {
			totalFailures += 1;
			System.out.println(Colors.begin("red") + "[31m*** Test failed: " + s + " *** " + Colors.end());
			return false;
		}
		return true;

	}

	boolean test(boolean t, String s) {
		boolean testResult = Tester.testStatic(t, s);
		this.results.add(testResult);
		return testResult;
	}

	boolean assess() {
		ArrayList<Boolean> results = this.results;
		boolean test = true;
		int failures = 0;
		for (Boolean b : results) {
			if (!b) {
				test = false;
				failures += 1;

			}
		}

		if (!test) {
			System.out.println(Colors.begin("red") + failures + " "
					+ Thread.currentThread().getStackTrace()[2].getMethodName() + " tests failed" + Colors.end());
		}
		return test;
	}

	static boolean result(String class_name) {
		System.out.println("Testing for " + class_name + " complete");
		if (totalFailures == 0) {
			System.out.println("- " + totalTests + " tests passed");
			return true;
		} else {
			System.out.println("- " + totalFailures + " tests failed out of " + totalTests + " total tests");
			return false;
		}
	}

	public static void main(String args[]) {
		Colors.load();
		Game.test();
		
		
	
		System.out.println("All Testing Complete");
		if (totalFailures > 0) {
			System.out.println(Colors.begin("red") + "*** " + totalFailures + " tests failed *** " + Colors.end());
		} else {
			System.out.println(Colors.begin("green") + "All " + totalTests + " tests passed" + Colors.end());
		}

	}
}
