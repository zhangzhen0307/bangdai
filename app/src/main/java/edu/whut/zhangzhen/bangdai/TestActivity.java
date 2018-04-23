package edu.whut.zhangzhen.bangdai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        EditText edt_test=(EditText) findViewById(R.id.edt_test);
        edt_test.setText(getIntent().getStringExtra("response"));
        Toast.makeText(this,getIntent().getStringExtra("response"),Toast.LENGTH_LONG).show();
    }
}
