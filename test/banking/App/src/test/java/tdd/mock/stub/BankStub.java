package tdd.mock.stub;

import tdd.Account;
import tdd.Bank;

public class BankStub implements Bank {

  private Account account;

  @Override
  public void insertAccount(Account account) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public Account getAccount(String number) {
    return this.account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

}