package com.example.acofoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//animation for the text view"CREATED WITH LOVE"
    imageView = (ImageView)findViewById(R.id.imageView);
    textView=(TextView)findViewById(R.id.textView);
//this two line indicate an animation for both image & text view with alpha(transparency 0) and duration(0=immediately)
        imageView.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);
//here this line indicate an other animation for imageview with other params(alpha and duration) and
// also a listener(instance of AnimatorListenerAdapter)an empty implementation of the
// Animator.AnimatorListener interface
        imageView.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
        /**
         * {@inheritDoc}
         *
         * @param animation
         */
        // override the onAnimationEnd() method to trigger an textView's animation after the end
        //off imageView animation
        @Override
        public void onAnimationEnd(Animator animation) {
            textView.animate().alpha(1f).setDuration(800);

        }
    });
 //this code delays the start of the MainMenu activity for 3 seconds after launching the MainActivity
        //  then finishes MainActivity once the MainMenu activity has been started.
        new Handler().postDelayed(new Runnable() { //creation of instance handler //postDelayed() method to schedule the execution of a delayed task after a certain delay.
            @Override
            public void run() {
                Fauth= FirebaseAuth.getInstance();
                if(Fauth.getCurrentUser()!=null){
                    if(Fauth.getCurrentUser().isEmailVerified()){
                        Fauth=FirebaseAuth.getInstance();
                        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String role = snapshot.getValue(String.class);
                                if(role.equals("Chef")){
                                    startActivity(new Intent(MainActivity.this,ChefFoodPanel_BottomNavigation.class));
                                    finish();

                                }
                                if(role.equals("Customer")){
                                    startActivity(new Intent(MainActivity.this,CustomerFoodPanel_BottomNavigation.class));
                                    finish();

                                }
                                if(role.equals("Delevery")){
                                    startActivity(new Intent(MainActivity.this,CustomerFoodPanel_BottomNavigation.class));
                                    finish();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Check Whether You Have Verified Your Detail , Otherwise Please Verify");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this,MainMenu.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Fauth.signOut();
                    }
                }else {

                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);

    }
}