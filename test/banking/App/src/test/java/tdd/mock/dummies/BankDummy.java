package tdd.mock.dummies;

import tdd.*;

public class BankDummy implements Bank {

  @Override
  public void insertAccount(Account account) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public Account getAccount(String number) {
    throw new UnsupportedOperationException();
  }
}