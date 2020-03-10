
package tdd;

import java.util.ArrayList;
import java.util.List;

import tdd.Movement;

public class Account {

  private Bank bank;
  private Customer customer;
  private String number; 
  private List<Movement> withdrawls = new ArrayList<>();
  private List<Movement> deposits = new ArrayList<>();


  public Account(Bank bank, Customer customer, String number){
    this.bank = bank;
    this.customer = customer;
    this.number = number;
  }

  public void transfer(long amount, Account target){
    Movement movement = new Movement(amount);
    this.withdrawls.add(movement);
    target.deposits.add(movement);
  }

  public void transfer(long amount, String targetNumber){
    Account target = this.bank.getAccount(targetNumber);
    Movement movement = new Movement(amount);
    this.withdrawls.add(movement);
    target.deposits.add(movement);
  }

  public Bank getBank(){
    return bank;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getNumber() {
    return number;
  }

  public long getBalance(){
    long withdrawlAmount = 0;
    long depositAmount = 0;
    for(Movement deposit : this.deposits){
      depositAmount += deposit.getAmount();
    }
    for(Movement withdrawl : this.withdrawls){
      withdrawlAmount += withdrawl.getAmount();
    }
    return depositAmount - withdrawlAmount;
  }
}