package music.sokanch.com.splitbills;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by saket on 28/6/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_BILLS = "bills_info";
    public String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "person_name";
    public static final String CONTACTS_COLUMN_AMOUNT = "amount";
    public static final String PAID_BY = "paid_by";
    Context context;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = context;


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table IF NOT EXISTS "+TABLE_BILLS +
        " ("+CONTACTS_COLUMN_ID+" integer primary key, "+CONTACTS_COLUMN_NAME+" text, "+CONTACTS_COLUMN_AMOUNT+" integer, "+PAID_BY+" integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_BILLS);
    }
    public boolean insertContact (Bills bills){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, bills.getPerson_name());
        contentValues.put(CONTACTS_COLUMN_AMOUNT, bills.getAmount());
        Log.d("Paid by",""+bills.getPaid_by());
        contentValues.put(PAID_BY,bills.getPaid_by());
        db.insert(TABLE_BILLS, null, contentValues);
        return true;
    }
    public ArrayList<Bills> getDetails(){

        Log.d("Cursor ","Data");
        ArrayList<Bills> billsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BILLS;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Bills bills = new Bills(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)),cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_AMOUNT)),cursor.getInt(cursor.getColumnIndex(PAID_BY)));
                Log.d("Cursor info ",bills.getPerson_name()+"  "+bills.getAmount()+"    "+bills.getPaid_by());
                billsList.add(bills);
            }while (cursor.moveToNext());
        }
        Log.d("Cursor ",""+billsList.size());
        return  billsList;
    }
}
