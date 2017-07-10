package top.ttxxly.com.pictureviewer.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import top.ttxxly.com.pictureviewer.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView mReturn;
    private Button mSearch;
    private EditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mReturn = (ImageView) findViewById(R.id.img_search_return_top);
        mSearch = (Button) findViewById(R.id.btn_search);
        mInput = (EditText) findViewById(R.id.et_search_input);

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mInput.getText().toString();
                Toast.makeText(getApplicationContext(), "么么哒", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
