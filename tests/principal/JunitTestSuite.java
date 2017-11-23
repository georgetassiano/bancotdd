package principal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   ClienteTest.class,
   TransacaoDepositoTest.class,
   TransacaoSaqueTest.class,
   TransacaoTransferenciaTest.class
})

public class JunitTestSuite {   
} 