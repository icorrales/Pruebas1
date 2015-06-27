package dia.upm.cconvexo.algoritmos.tests;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import dia.upm.cconvexo.algoritmos.Andrew;

/**
 * Created by ivan on 6/12/14.
 */
public class TestAlgorithmSuite extends TestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite("AlgorithmTests");
        suite.addTestSuite(TestAndrew.class);
        suite.addTestSuite(TestAproximacionInferior.class);
        suite.addTestSuite(TestAproximacionSuperior.class);
        suite.addTestSuite(TestGraham.class);
        suite.addTestSuite(TestBusquedasAristas.class);
        suite.addTestSuite(TestDYVp.class);
        suite.addTestSuite(TestIncremental.class);
        suite.addTestSuite(TestJarvis.class);
        suite.addTestSuite(TestPuntosInternos.class);
        suite.addTestSuite(TestQuickHull.class);
        TestResult result = new TestResult();
        suite.run(result);
        return suite;

    }

}
