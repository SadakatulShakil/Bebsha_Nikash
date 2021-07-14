package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;

import com.grameen.bebshanikashapp.Adapters.ContactAdapter;
import com.grameen.bebshanikashapp.Model.Contact.ContactList;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

public class PhoneBookListActivity extends AppCompatActivity {

    private RecyclerView listRevView;
    private ContactAdapter contactAdapter;
    private ArrayList<ContactList> mContactListArrayList = new ArrayList<>();
    private EditText search_eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book_list);
        inItView();
        Intent intent = getIntent();
        contactAdapter = new ContactAdapter(PhoneBookListActivity.this, mContactListArrayList);
        listRevView.setLayoutManager(new LinearLayoutManager(PhoneBookListActivity.this));
        listRevView.setAdapter(contactAdapter);

        getContacts();
    }

    private void getContacts() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactList contactList = new ContactList(name, number);
            mContactListArrayList.add(contactList);
            contactAdapter.notifyDataSetChanged();
        }
    }

    private void inItView() {
        listRevView = findViewById(R.id.listRevView);
        search_eText = findViewById(R.id.search_eText);
    }
}