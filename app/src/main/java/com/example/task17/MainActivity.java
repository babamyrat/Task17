package com.example.task17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etText;
    Button btSave, btClear;
    JSONObject saved = new JSONObject();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etText = findViewById(R.id.et_text);
        btSave = findViewById(R.id.bt_save);
        btClear = findViewById(R.id.bt_clear);

        initStart();
        Intent intent = getIntent();
        if (intent.getIntExtra("position", -1) != -1) {

            try {
                String s = etText.getText().toString();
                if (!preferences.getString("saved", "").equals(""))
                    saved = new JSONObject(preferences.getString("saved", ""));
                etText.setText(saved.getString("saved" + intent.getIntExtra("position", 0)));
                s = saved.getString("saved" + intent.getIntExtra("position", 0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etText.getText().toString();
                if (s.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter text"
                    ,Toast.LENGTH_SHORT).show();
                } else {
                    etText.setText("");
                }
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etText.getText().toString();
                if (!s.equals("")){
                    try {
                        if (!preferences.getString("saved", "").equals(""))
                            saved = new JSONObject(preferences.getString("saved", ""));
                                    saved.put("saved"+saved.length(), s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("testing", saved+"");
                    editor.putString("saved", saved.toString());
                    editor.apply();
                    etText.setText("");
                    Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent1);
                }
            }
        });

    }


    private void initStart() {

        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        editor = preferences.edit();
        etText = findViewById(R.id.et_text);
        btSave = findViewById(R.id.bt_save);
        btClear = findViewById(R.id.bt_clear);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save){
            if (preferences.getString("saved", "").equals("")){
                Toast.makeText(getApplicationContext(), "Nothing to Save"
                        ,Toast.LENGTH_SHORT).show();

            } else {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}