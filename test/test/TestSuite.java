package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	StoreTest.class,
	ItemTest.class,
	CheckoutTest.class 
	})
public class TestSuite {}
