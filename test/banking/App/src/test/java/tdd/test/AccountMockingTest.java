package tdd.test;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import tdd.*;

public class AccountMockingTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Test
  public void testAccountTranfer() {
    Customer customer = context.mock(Customer.class);
    Bank bank = context.mock(Bank.class);
    String targetNumber = "ABC123";
    Account source = new Account(bank, customer, "GHJ456");
    Account target = new Account(bank, customer, targetNumber);
    context.checking(new Expectations() {
      {
        oneOf(bank).getAccount(targetNumber);
        will(returnValue(target));
      }
    });

    source.transfer(1000, targetNumber);
    assertEquals(-1000, source.getBalance());
    assertEquals(1000, target.getBalance());
  }
}