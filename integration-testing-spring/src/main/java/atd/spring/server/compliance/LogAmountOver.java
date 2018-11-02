package atd.spring.server.compliance;

import atd.spring.server.bills.LineItem;

public class LogAmountOver implements LineItemTrafficRule{

  private double threshold;

  public LogAmountOver(double threshold) {
    this.threshold = threshold;
  }

  @Override
  public boolean shouldLog(LineItem item) {
    return (item.getItemAmount().doubleValue()>=threshold);
  }

}