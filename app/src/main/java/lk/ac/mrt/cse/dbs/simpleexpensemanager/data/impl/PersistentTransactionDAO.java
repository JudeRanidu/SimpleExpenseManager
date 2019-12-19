package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    private SQLiteDatabase DB = null;
    public PersistentTransactionDAO(SQLiteDatabase database){
        this.DB = database;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String query = "INSERT into transactions VALUES(?,?,?,?)";
        SQLiteStatement statement = DB.compileStatement(query);
        statement.bindString(1,date.toString());
        statement.bindString(2,accountNo);
        statement.bindString(3,expenseType.name());
        statement.bindDouble(4,amount);
        statement.execute();
    }

    @Override
    public List<Transaction> getAllTransactionLogs(){
        String query = "SELECT * FROM transactions";
        Cursor cursor = DB.rawQuery(query,null);
        List<Transaction> TransactionLogs = new ArrayList();
        while(cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String accountNo = cursor.getString(cursor.getColumnIndex("accountNo"));
            String expenseType = cursor.getString(cursor.getColumnIndex("expenseType"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            Transaction transaction = new Transaction(new Date(date),accountNo,ExpenseType.valueOf(expenseType),amount);
            TransactionLogs.add(transaction);
        }
        return TransactionLogs;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        String query = "SELECT * FROM transactions ORDER BY date DESC "+limit+"";
        Cursor cursor = DB.rawQuery(query,null);
        List<Transaction> PaginatedTransactionLogs = new ArrayList();
        while(cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String accountNo = cursor.getString(cursor.getColumnIndex("accountNo"));
            String expenseType = cursor.getString(cursor.getColumnIndex("expenseType"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            Transaction transaction = new Transaction(new Date(date),accountNo,ExpenseType.valueOf(expenseType),amount);
            PaginatedTransactionLogs.add(transaction);
        }
        return PaginatedTransactionLogs;
    }
}
