package tdd;

import java.util.Date;

public class Movement {
  private Date time;
  private long amount;

  public Movement(Date time, long amount) {
    this.time = time;
    this.amount = amount;
  }

  public Movement(long amount){
    this(new Date(), amount);
  }

  /**
   * @return the amount
   */
  public long getAmount() {
    return amount;
  }

  /**
   * @return the time
   */
  public Date getTime() {
    return time;
  }
}