package top.ttxxly.com.pictureviewer.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import top.ttxxly.com.pictureviewer.R;

public class DetailsPhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_photos);

        String data =getIntent().getStringExtra("data");

        TextView title = (TextView) findViewById(R.id.tv_details_photos_title);
        TextView category = (TextView) findViewById(R.id.tv_details_photos_category);
        TextView keyword = (TextView) findViewById(R.id.tv_details_photos_keyword);
        TextView date = (TextView) findViewById(R.id.tv_details_photos_date);
        TextView description = (TextView) findViewById(R.id.tv_details_photos_description);

    }
}
