package com.tester.vlad.tester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button runButton;
    private Button createButton;
    private Button deleteButton;
    private Button optionsButton;
    private Button exitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runButton = (Button) findViewById(R.id.menu_button_run_test);
        createButton = (Button) findViewById(R.id.menu_button_create_test);
        deleteButton = (Button) findViewById(R.id.menu_button_delete_test);
        optionsButton = (Button) findViewById(R.id.menu_button_options_test);
        exitButton = (Button) findViewById(R.id.menu_button_exit);

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RunTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
