package music.sokanch.com.splitbills;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    TextView split_summary, b_amount, c_amount, d_amount, you_owe, you_get, total;
    ImageView addBill;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase, database;
    private String query = "select * from bill_info";
    private Bills bills;
    //BA means B gave A
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDatabase();
        split_summary = (TextView) findViewById(R.id.split_summary);
        b_amount = (TextView) findViewById(R.id.b_amount);
        c_amount = (TextView) findViewById(R.id.c_amount);
        d_amount = (TextView) findViewById(R.id.d_amount);
        you_owe = (TextView) findViewById(R.id.you_owe);
        you_get = (TextView) findViewById(R.id.you_get);
        total = (TextView) findViewById(R.id.total_amount);
        addBill = (ImageView) findViewById(R.id.add_bill);
        databaseHelper = new DatabaseHelper(this);

        sqLiteDatabase = databaseHelper.getReadableDatabase();

        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBill.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        billSummary();

    }

    public void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("MyDBName.db", Context.MODE_PRIVATE, null);
    }

    public void billSummary() {
        int owe = 0, get = 0;

        ArrayList<Bills> billsArrayList = databaseHelper.getDetails();
        HashMap<String,Integer> hashMap = new HashMap<>();
        hashMap.put("AB",0);
        hashMap.put("BA",0);
        hashMap.put("AC",0);
        hashMap.put("CA",0);
        hashMap.put("AD",0);
        hashMap.put("DA",0);

        for(int i=0;i<billsArrayList.size();i++){
            Bills bill = billsArrayList.get(i);
            switch (bill.getPerson_name()){
                case "B":
                    if(bill.getPaid_by()==0){
                        hashMap.put("AB",hashMap.get("AB")+(bill.getAmount()/2));
                    }
                    else{
                            hashMap.put("BA",hashMap.get("BA")+(bill.getAmount()/2));

                    }
                    break;
                case "C":
                    if(bill.getPaid_by()==0){
                        hashMap.put("AC",hashMap.get("AC")+(bill.getAmount()/2));
                    }
                    else{
                        hashMap.put("CA",hashMap.get("CA")+(bill.getAmount()/2));

                    }break;
                case "D":
                    if(bill.getPaid_by()==0){
                        hashMap.put("AD",hashMap.get("AD")+(bill.getAmount()/2));
                    }
                    else{
                        hashMap.put("DA",hashMap.get("DA")+(bill.getAmount()/2));
                    }
                    break;

            }

        }
        Log.d("Price","AB "+hashMap.get("AB"));
        Log.d("Price","BA "+hashMap.get("BA"));

        Log.d("Price","AC "+hashMap.get("AC"));
        Log.d("Price","CA "+hashMap.get("CA"));

        Log.d("Price","AD "+hashMap.get("AD"));
        Log.d("Price","DA "+hashMap.get("DA"));
        int ab=0,ac=0,ad=0;
        if (hashMap.get("AB")>hashMap.get("BA")){
            ab = hashMap.get("AB")-hashMap.get("BA");
            Log.d("B owes A","  "+ab);
            b_amount.setText("B owes A " +ab);
            get = ab;
        }
        else
        {
            ab = hashMap.get("BA")-hashMap.get("AB");
            Log.d("A owes B","  "+ab);
            b_amount.setText("A owes B " +ab);
            owe = ab;
        }
        if (hashMap.get("AC")>hashMap.get("CA")){
            ac = hashMap.get("AC")-hashMap.get("CA");
            Log.d("C owes A","  "+ac);
            c_amount.setText("C owes A " +ac);
            get += ac;
        }
        else{
            ac = hashMap.get("CA")-hashMap.get("AC");
            Log.d("A owes C","  "+ac);
            c_amount.setText("A owes C " +ac);
            owe += ac;
        }
        if (hashMap.get("AD")>hashMap.get("DA")){
            ad = hashMap.get("AD")-hashMap.get("DA");
            Log.d("D owes A","  "+ad);
            d_amount.setText("D owes A " +ad);
            get += ad;
        }
        else{
            ad = hashMap.get("DA")-hashMap.get("AD");
            Log.d("A owes D","  "+ad);
            d_amount.setText("A owes D " +ad);
            owe += ad;
        }
        you_owe.setText("you owe " +owe);
        you_get.setText("you will get " +get);
        total.setText("Total balance " +(owe-get));
    }
}