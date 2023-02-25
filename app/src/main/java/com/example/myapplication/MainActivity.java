package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStartGame(View view){
        Spinner spinner=(Spinner)findViewById(R.id.choose_difficulty);
        Intent intent=new Intent(this,Game.class);

        String difficulty=String.valueOf(spinner.getSelectedItem());
        intent.putExtra(Game.EXTRA_DIF,difficulty);
        startActivity(intent);
    }
}