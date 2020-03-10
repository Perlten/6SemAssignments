package tdd.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import tdd.*;
import tdd.mock.dummies.BankDummy;
import tdd.mock.dummies.CustomerDummy;
import tdd.mock.stub.BankStub;

public class AccountTest {
  @Test
  public void testCreateAccount() throws Exception {
    Bank bank = null;
    Customer customer = null;
    String number = null;

    Account account = new Account(bank, customer, number);
    assertNotNull(account);
  }

  @Test
  public void testCreateAccountWithBank() throws Exception {
    Bank bank = new BankDummy();
    Customer customer = null;
    String number = null;
    Account account = new Account(bank, customer, number);
    assertEquals(bank, account.getBank());
    assertNotNull(account);
  }

  @Test
  public void testCreateAccountWithCustomer() {
    Bank bank = new BankDummy();
    Customer customer = new CustomerDummy();
    String number = null;
    Account account = new Account(bank, customer, number);
    assertEquals(customer, account.getCustomer());
    assertNotNull(customer);
  }

  @Test
  public void testCreateAccountWithNumber() {
    Bank bank = new BankDummy();
    Customer customer = new CustomerDummy();
    String number = "123,12";
    Account account = new Account(bank, customer, number);
    assertEquals(number, account.getNumber());
    assertNotNull(number);
  }

  @Test
  public void testCreateAccountWithZeroBalance() {
    Bank bank = new BankDummy();
    Customer customer = new CustomerDummy();
    String number = "123,12";
    Account account = new Account(bank, customer, number);
    assertNotNull(account);
    assertEquals(0l, account.getBalance());
  }

  @Test
  public void testTransferPosetiveAmount(){
    Bank bank = new BankDummy();
    Customer customer = new CustomerDummy();
    Account source = new Account(bank, customer, "SRC123");
    Account target = new Account(bank, customer, "TGT125");
    source.transfer(1000, target);
   
    assertEquals(-1000, source.getBalance()); 
    assertEquals(1000, target.getBalance()); 
  }

  @Test
  public void testTransferPosetiveAmountUsingNumber(){
    BankStub bankStub = new BankStub();
    Customer customer = new CustomerDummy();
    
    Account target = new Account(bankStub, customer, "TGT125");
    Account source = new Account(bankStub, customer, "SRC123");
    bankStub.setAccount(target);

    source.transfer(1000, target.getNumber());
    
    assertEquals(-1000, source.getBalance()); 
    assertEquals(1000, target.getBalance()); 
  }
  
}