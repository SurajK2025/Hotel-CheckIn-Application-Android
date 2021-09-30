package com.sklabs.hotelcheckin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    TextView username_on_profile;
    DatabaseReference reff;
    RecyclerView recyclerView;
    List<Bookings> mBookings;
    Adapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.BookingHistory:
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                return true;
            case R.id.LogOut:
                MainActivity.currentUsername = null;
                Intent i = new Intent(Dashboard.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username_on_profile = (TextView) findViewById(R.id.username_on_profile);
        recyclerView = findViewById(R.id.booking_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        username_on_profile.setText(MainActivity.currentUsername);

        mBookings = new ArrayList<>();
        reff = FirebaseDatabase.getInstance().getReference("Bookings");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBookings.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Bookings bookings = dataSnapshot.getValue(Bookings.class);
                    if(bookings.getUsername().equals(MainActivity.currentUsername)){
                        mBookings.add(bookings);
                    }

                    adapter = new Adapter(Dashboard.this, mBookings);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}