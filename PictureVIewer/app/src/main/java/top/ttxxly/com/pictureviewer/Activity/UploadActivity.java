package top.ttxxly.com.pictureviewer.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import top.ttxxly.com.pictureviewer.R;

public class UploadActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");


    }
}
