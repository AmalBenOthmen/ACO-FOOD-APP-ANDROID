package com.example.acofoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
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
}}