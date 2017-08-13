package com.codepath.iwantbootcamp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.codepath.iwantbootcamp.R.id.parent;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    int selectTextPos = -1;
    AdapterView<?> gAdapter;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = openDB();
        populateDBItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void populateDBItems() {
        items = new ArrayList<>();
        queryItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");

        insertItem();
    }

    private void setupListViewListener() {
/*
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        for (int j = 0; j < adapter.getChildCount(); j++)
                            adapter.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();

                        insertItem();

                        return true;
                    }
                }
        );
*/
        // ====================================================================================================
        // Add up on click listener to select one text for modify
        // ====================================================================================================
/*
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        selectTextPos = pos;
                        editTextData();
                    }
                }
        );
*/

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        for (int j = 0; j < adapter.getChildCount(); j++)
                            adapter.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                        item.setBackgroundColor(Color.YELLOW);
                        selectTextPos = pos;
                        gAdapter = adapter;
                    }
                }
        );
    }

    // ====================================================================================================
    // Get select text and pass it to EditItemActivity using INTEND
    // ====================================================================================================
    private void editTextData() {
        String selectText = itemsAdapter.getItem(selectTextPos);

        Intent editItemIntent = new Intent(getBaseContext(), EditItemActivity.class);
        editItemIntent.putExtra(GlobalInfo.KEY_CURR_TEXT, selectText);

        startActivityForResult(editItemIntent, 100);
    }

    // ====================================================================================================
    // Receive modified text from EditItemActivity using INTEND
    // and set the modified text to the List
    // ====================================================================================================
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        if(resultCode == GlobalInfo.MODIFIED_TEXT_OK) {
            String modifiedText = resultIntent.getStringExtra(GlobalInfo.KEY_MODIFIED_TEXT);

            items.remove(selectTextPos);
            itemsAdapter.notifyDataSetChanged();
            items.add(selectTextPos, modifiedText);

            insertItem();
        }
        else {
            Toast.makeText(getBaseContext(), "No Change.", Toast.LENGTH_SHORT).show();
        }
    }

    // ====================================================================================================
    // Open new Database
    // ====================================================================================================
    private SQLiteDatabase openDB() {
        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db;
    }

    private void insertItem () {
        deleteItem();

        int cnt = items.size();
        String insertItem = "";
        String INSERT_SQL = "";

        for(int i = 0; i < cnt; i++) {
            insertItem = items.get(i);
            INSERT_SQL = "insert into " + GlobalInfo.TABLE_NAME + " (" + "ITEM" + ") " + "values" + "('" + insertItem + "');";
            mDatabase.execSQL(INSERT_SQL);
        }
    }

    private void queryItem() {
        String SQL = "select ITEM "
                + " from " + GlobalInfo.TABLE_NAME;
        Cursor c1 = mDatabase.rawQuery(SQL, null);

        int cnt = c1.getCount();
        String name = "";

        Toast.makeText(this, "Num of Data = " + cnt , Toast.LENGTH_SHORT).show();

        for(int i = 0; i < cnt; i++) {
            c1.moveToNext();
            name = c1.getString(0);

            Toast.makeText(this, "Item = " + name , Toast.LENGTH_SHORT).show();
        }

        c1.close();
    }

    private void queryItems() {
        String SQL = "select ITEM "
                + " from " + GlobalInfo.TABLE_NAME;
        Cursor c1 = mDatabase.rawQuery(SQL, null);

        int cnt = c1.getCount();
        String itemInfo = "";
        items.clear();

        for(int i = 0; i < cnt; i++) {
            c1.moveToNext();
            itemInfo = c1.getString(0);
            items.add(itemInfo);
        }

        c1.close();
    }

    private void deleteItem() {
        mDatabase.execSQL( "delete from " + GlobalInfo.TABLE_NAME );
    }

    public void onClickDeleteItem(View view) {
        if(selectTextPos < 0)
            Toast.makeText(this, "Please, select item, first", Toast.LENGTH_SHORT).show();
        else {
            items.remove(selectTextPos);
            itemsAdapter.notifyDataSetChanged();

            cleanLineColor();

            insertItem();
            selectTextPos = -1;
        }
    }

    public void onClickEditItem(View view) {
        if(selectTextPos < 0)
            Toast.makeText(this, "Please, select item, first", Toast.LENGTH_SHORT).show();
        else {
            editTextData();
        }
    }

    private void cleanLineColor() {
        for (int j = 0; j < gAdapter.getChildCount(); j++)
            gAdapter.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
    }
}