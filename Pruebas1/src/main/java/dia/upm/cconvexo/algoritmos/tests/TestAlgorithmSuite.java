package dia.upm.cconvexo.algoritmos.tests;

import junit.framework.TestResult;
import junit.framework.TestSuite;

import dia.upm.cconvexo.algoritmos.Andrew;

/**
 * Created by ivan on 6/12/14.
 */
public class TestAlgorithmSuite {

    public TestAlgorithmSuite() {}

    public static void main(String[] a) {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestAndrew.class);
        TestResult result = new TestResult();
        suite.run(result);
        System.out.println("Was it successful? "
                +result.wasSuccessful());
        System.out.println("How many tests were there? "
                +result.runCount());
    }





}
