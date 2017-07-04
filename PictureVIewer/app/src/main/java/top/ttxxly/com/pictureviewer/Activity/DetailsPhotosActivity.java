package top.ttxxly.com.pictureviewer.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import top.ttxxly.com.pictureviewer.models.Photos;
import top.ttxxly.com.pictureviewer.R;

public class DetailsPhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_photos);

        String data =getIntent().getStringExtra("data");
        Photos photos=new Gson().fromJson(data,Photos.class);
        Log.d("book title->", photos.getTitle());
        Log.d("book author name->", photos.getDate());

        TextView title = (TextView) findViewById(R.id.tv_details_photos_title);
        TextView category = (TextView) findViewById(R.id.tv_details_photos_category);
        TextView keyword = (TextView) findViewById(R.id.tv_details_photos_keyword);
        TextView date = (TextView) findViewById(R.id.tv_details_photos_date);
        TextView description = (TextView) findViewById(R.id.tv_details_photos_description);

        title.setText(photos.getTitle());
        category.setText(photos.getCategory());
        keyword.setText(photos.getKeyword());
        date.setText(photos.getDate());
        description.setText(photos.getDescription());
    }
}
