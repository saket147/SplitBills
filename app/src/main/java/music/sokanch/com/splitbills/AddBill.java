package music.sokanch.com.splitbills;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddBill extends AppCompatActivity {
    private TextView addBill, amount, selectPerson, paidByYou;
    private CheckBox checkBox;
    private EditText enterAmount;
    private Spinner spinner;
    private Button save;
    DatabaseHelper databaseHelper;
    private ArrayList<Bills> billsArrayList;
    SQLiteDatabase  sqLiteDatabase;
    Bills bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        addBill = (TextView)findViewById(R.id.add_bill);
        amount = (TextView)findViewById(R.id.amount_textview);
        selectPerson = (TextView)findViewById(R.id.select_person_name);
        paidByYou = (TextView)findViewById(R.id.paid_by_you);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        enterAmount = (EditText)findViewById(R.id.enter_amount);
        spinner = (Spinner) findViewById(R.id.spinner);
        save = (Button)findViewById(R.id.save);
        databaseHelper = new DatabaseHelper(this);

        sqLiteDatabase = databaseHelper.getWritableDatabase();
        billsArrayList = new ArrayList<>();
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_name, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String shareHolderName =spinner.getSelectedItem().toString();
                    int amount = Integer.parseInt(enterAmount.getText().toString());
                    if (checkBox.isChecked()){
                        Log.d("Inside checkbox","Checked");
                        Bills bill = new Bills(shareHolderName, amount,0);
                        databaseHelper.insertContact(bill);
                    }
                    else {

                        Log.d("Inside checkbox"," not Checked");
                        databaseHelper.insertContact(new Bills(shareHolderName, amount, 1));
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(AddBill.this, "You Need to enter Bill Details", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(AddBill.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });



    }
}
