package com.sklabs.hotelcheckin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectRoom extends AppCompatActivity {
    ImageView singleBedImageView, doubleBedImageView, suiteImageView, reservedImageView;

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
                Intent i = new Intent(SelectRoom.this, MainActivity.class);
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
        setContentView(R.layout.activity_select_room);

        singleBedImageView = (ImageView) findViewById(R.id.singleBedImageView);
        doubleBedImageView = (ImageView) findViewById(R.id.doubleBedImageView);
        suiteImageView = (ImageView) findViewById(R.id.suiteImageView);
        reservedImageView = (ImageView) findViewById(R.id.reservedImageView);

        singleBedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoom.this, BillingActivity.class);
                intent.putExtra("Key", "singleBed");
                startActivity(intent);
            }
        });

        doubleBedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoom.this, BillingActivity.class);
                intent.putExtra("Key", "doubleBed");
                startActivity(intent);

            }
        });

        suiteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoom.this, BillingActivity.class);
                intent.putExtra("Key", "suite");
                startActivity(intent);
            }
        });

        reservedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoom.this, BillingActivity.class);
                intent.putExtra("Key", "table");
                startActivity(intent);
            }
        });
    }
}