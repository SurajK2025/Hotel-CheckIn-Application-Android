package com.sklabs.hotelcheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.Toast;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BillingActivity extends AppCompatActivity {
    ImageView billingImageView, checkImage, checkBackgroundImage;
    Button makebookingButton;
    TextView roomVarient, rentTextView, roomAvailableTextView, textView1, textView2, roomUnavailable;
    DatabaseReference reff;
    Object remainingRooms;
    Integer slot;
    String Date;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    Dialog dialog;
    int min, max, i, doubleBedNumber, singleBedNumber, suiteNumber, tableNumber, var;
    public static int number;
    double room_number;

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
                Intent i = new Intent(BillingActivity.this, MainActivity.class);
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
        setContentView(R.layout.activity_billing);

        billingImageView = (ImageView) findViewById(R.id.billingImageView);
        roomVarient = (TextView) findViewById(R.id.roomvarient);
        rentTextView = (TextView) findViewById(R.id.rentTextView);
        roomAvailableTextView = (TextView) findViewById(R.id.roomAvailableTextView);
        slot = null;
        roomUnavailable = (TextView) findViewById(R.id.roomUnavailable);
        checkImage = (ImageView) findViewById(R.id.chechImageView);
        checkBackgroundImage = (ImageView) findViewById(R.id.chechBackgroundImageView);
        makebookingButton = (Button) findViewById(R.id.makebookingButton);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

        textView1.setText("Rent for one day");
        textView2.setText("Rooms Available");

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyy_HH:mm:ss");

        updateRoomsAvailable();

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String key = (String) bd.get("Key");
        matchKey(key);

        makebookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                calendar = Calendar.getInstance();
                Date = (simpleDateFormat.format(calendar.getTime()));
                HashMap<String, Object> map = new HashMap<>();
                int b = (int)(Math.random()*(9999-1111+1)+1111);
                Object random = b;

                if(slot==1){

                    min=101; max=115;
                    room_number = (int)(Math.random() * (max - min +1) + min);
                    number = (int) room_number;
                    map.put("BookingID", random);
                    map.put("Username", MainActivity.currentUsername);
                    map.put("Date", Date.substring(0, 10));
                    map.put("Time", Date.substring(11));
                    map.put("RoomNumber", number);

                    singleBedNumber = singleBedNumber - 1;
                    FirebaseDatabase.getInstance().getReference().child("SingleBed").setValue(singleBedNumber);
                }

                if(slot==2){

                    min=201; max=210;
                    room_number = Math.random() * (max - min +1) + min;
                    number = (int) room_number;
                    map.put("BookingID", random);
                    map.put("Username", MainActivity.currentUsername);
                    map.put("Date", Date.substring(0, 10));
                    map.put("Time", Date.substring(11));
                    map.put("RoomNumber", number);

                    doubleBedNumber--;
                    FirebaseDatabase.getInstance().getReference().child("DoubleBed").setValue(doubleBedNumber);
                }

                if(slot==3){

                    min=301; max=305;
                    room_number = Math.random() * (max - min +1) + min;
                    number = (int) room_number;
                    map.put("BookingID", random);
                    map.put("Username", MainActivity.currentUsername);
                    map.put("Date", Date.substring(0, 10));
                    map.put("Time", Date.substring(11));
                    map.put("RoomNumber", number);

                    suiteNumber--;
                    FirebaseDatabase.getInstance().getReference().child("Suite").setValue(suiteNumber);
                }

                if(slot==4){

                    min=401; max=415;
                    room_number = Math.random() * (max - min +1) + min;
                    number = (int) room_number;
                    map.put("BookingID", random);
                    map.put("Username", MainActivity.currentUsername);
                    map.put("Date", Date.substring(0, 10));
                    map.put("Time", Date.substring(11));
                    map.put("RoomNumber", number);

                    tableNumber--;
                    FirebaseDatabase.getInstance().getReference().child("TableReservation").setValue(tableNumber);
                }
                FirebaseDatabase.getInstance().getReference("Bookings").child(random.toString()).updateChildren(map);
                openDialog();
            }
        });

    }


    private void matchKey(String key){
        if(key.matches("singleBed")){
            billingImageView.setImageResource(R.drawable.bed);
            roomVarient.setText("Single Bed Room");
            rentTextView.setText("3000Rs");
            slot = 1;

            reff = FirebaseDatabase.getInstance().getReference("SingleBed");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        remainingRooms = snapshot.getValue();
                        roomAvailableTextView.setText(remainingRooms.toString());
                        var = Integer.parseInt((String) remainingRooms.toString());
                    }
                    if(var == 0){
                        roomUnavailable.setVisibility(View.VISIBLE);
                        makebookingButton.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if(key.matches("doubleBed")){
            billingImageView.setImageResource(R.drawable.doublebed);
            roomVarient.setText("Double Bed Room");
            rentTextView.setText("5000Rs");
            slot = 2;

            reff = FirebaseDatabase.getInstance().getReference("DoubleBed");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        remainingRooms = snapshot.getValue();
                        roomAvailableTextView.setText(remainingRooms.toString());
                        var = Integer.parseInt((String) remainingRooms.toString());
                    }
                    if(var == 0){
                        roomUnavailable.setVisibility(View.VISIBLE);
                        makebookingButton.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if(key.matches("suite")){
            billingImageView.setImageResource(R.drawable.suite);
            roomVarient.setText("Luxirious Suite");
            rentTextView.setText("8000Rs");
            slot = 3;

            reff = FirebaseDatabase.getInstance().getReference("Suite");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        remainingRooms = snapshot.getValue();
                        roomAvailableTextView.setText(remainingRooms.toString());
                        var = Integer.parseInt((String) remainingRooms.toString());
                    }
                    if(var == 0){
                        roomUnavailable.setVisibility(View.VISIBLE);
                        makebookingButton.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if(key.matches("table")){
            billingImageView.setImageResource(R.drawable.reserved);
            textView1.setText("Reservation charges");
            textView2.setText("Tables available");
            roomVarient.setText("4 Person Table");
            rentTextView.setText("800Rs");
            slot = 4;

            reff = FirebaseDatabase.getInstance().getReference("TableReservation");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        remainingRooms = snapshot.getValue();
                        roomAvailableTextView.setText(remainingRooms.toString());
                        var = Integer.parseInt((String) remainingRooms.toString());
                    }
                    if(var == 0){
                        roomUnavailable.setVisibility(View.VISIBLE);
                        makebookingButton.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void updateRoomsAvailable(){
        reff = FirebaseDatabase.getInstance().getReference();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object singleBedNum = snapshot.child("SingleBed").getValue();
                singleBedNumber = Integer.parseInt((String) singleBedNum.toString());

                Object doubleBedNum = snapshot.child("DoubleBed").getValue();
                doubleBedNumber = Integer.parseInt((String) doubleBedNum.toString());

                Object suiteNum = snapshot.child("Suite").getValue();
                suiteNumber = Integer.parseInt((String) suiteNum.toString());

                Object tableNum = snapshot.child("TableReservation").getValue();
                tableNumber = Integer.parseInt((String) tableNum.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialog(){
        dialog = new Dialog(BillingActivity.this);
        dialog.setContentView(R.layout.activity_booking_confirmation);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        TextView bookingRoomNumber = dialog.findViewById(R.id.bookingRoomNumber);
        Button okButton = dialog.findViewById(R.id.okButton);
        ImageView closeImageView = dialog.findViewById(R.id.closeImageView);
        bookingRoomNumber.setText(Integer.toString(BillingActivity.number));

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
}




