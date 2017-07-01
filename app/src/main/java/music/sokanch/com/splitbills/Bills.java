package music.sokanch.com.splitbills;

/**
 * Created by saket on 28/6/17.
 */

public class Bills {
    private String person_name;
    private int amount;
    private int paid_by;
    public Bills(String person_name, int amount,int paid_by){
        this.person_name = person_name;
        this.amount = amount;
        this.paid_by = paid_by;
    }

    public String getPerson_name() {
        return person_name;
    }
    public int getAmount() {
        return amount;
    }

    public int getPaid_by() {
        return paid_by;
    }
}
