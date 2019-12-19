package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {
    private SQLiteDatabase DB = null;
    public PersistentAccountDAO(SQLiteDatabase database){
        this.DB = database;
    }

    @Override
    public List<String> getAccountNumbersList() {
        String query = "SELECT accountNo from accounts";
        Cursor cursor = DB.rawQuery(query,null);
        List<String> accountNumberList = new ArrayList();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex("accountNo"));
            accountNumberList.add(accountNo);
        }
        return accountNumberList;
    }
    @Override
    public List<Account> getAccountsList() {
        String query = "SELECT * FROM accounts";
        Cursor cursor = DB.rawQuery(query,null);
        List<Account> accountList = new ArrayList();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex("accountNo"));
            String bankName = cursor.getString(cursor.getColumnIndex("bankName"));
            String accountHolderName = cursor.getString(cursor.getColumnIndex("accountHolderName"));
            double balance = cursor.getDouble(cursor.getColumnIndex("balance"));
            Account account = new Account(accountNo,bankName,accountHolderName,balance);
            accountList.add(account);
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String query = "SELECT * from accounts where accountNO=accountNo";
        Cursor cursor = DB.rawQuery(query,null);
        if(cursor.getCount()==0){
            String message = "No such Account Exists";
            throw new InvalidAccountException(message);
        }else{
            cursor.moveToFirst();
            String bankName = cursor.getString(cursor.getColumnIndex("bankName"));
            String accountHolderName = cursor.getString(cursor.getColumnIndex("accountHolderName"));
            double balance = cursor.getDouble(cursor.getColumnIndex("balance"));
            Account account = new Account(accountNo,bankName,accountHolderName,balance);
            return account;
        }
    }

    @Override
    public void addAccount(Account account) {
        String query = "INSERT into accounts VALUES(?,?,?,?)";
        SQLiteStatement statement = DB.compileStatement(query);
        statement.bindString(1,account.getAccountNo());
        statement.bindString(2,account.getBankName());
        statement.bindString(3,account.getAccountHolderName());
        statement.bindDouble(4,account.getBalance());
        statement.execute();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String query = "DELETE from accounts WHERE accountNo=?";
        SQLiteStatement statement = DB.compileStatement(query);
        statement.bindString(1,accountNo);
        try {
            statement.executeInsert();
        }catch(SQLiteException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        String query = "UPDATE accounts SET balance=balance-? where accountNo=?";
        SQLiteStatement statement = DB.compileStatement(query);
        statement.bindDouble(1,amount);
        statement.bindString(2,accountNo);
        try {
            statement.executeUpdateDelete();
        }catch(SQLiteException ex){
            ex.printStackTrace();
        }
    }
}
