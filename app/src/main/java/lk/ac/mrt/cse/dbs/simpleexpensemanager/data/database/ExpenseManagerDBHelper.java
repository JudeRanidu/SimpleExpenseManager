package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseManagerDBHelper extends SQLiteOpenHelper {
    public static final String DB_Name = "170504N";
    public static final int version = 1;

    public static final String tb1_Name = "accounts";
    public static final String tb1_col1 = "accountNo";
    public static final String tb1_col2 = "bankName";
    public static final String tb1_col3 = "accountHolderName";
    public static final String tb1_col4 = "balance";

    public static final String tb2_Name = "transactions";
    public static final String tb2_col1 = "transactionID";
    public static final String tb2_col2 = "date";
    public static final String tb2_col3 = "accountNo";
    public static final String tb2_col4 = "expenseType";
    public static final String tb2_col5 = "amount";


    public ExpenseManagerDBHelper(Context context){
        super(context,DB_Name,null,1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+tb1_Name+" ("+tb1_col1+" VARCHAR(20) PRIMARY KEY, "+tb1_col2+" VARCHAR(40) , "+tb1_col3+" VARCHAR(40), "+tb1_col4+" DOUBLE)");
        db.execSQL("create table "+tb2_Name+" ("+
                tb2_col1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                tb2_col2+" date, "+
                tb2_col3+" VARCHAR(20) , "+
                tb2_col4+" VARCHAR(20), "+
                tb2_col5+" DOUBLE, FOREIGN KEY("+
                tb2_col3+") REFERENCES "+tb1_Name+" ("+tb1_col1+"))");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
