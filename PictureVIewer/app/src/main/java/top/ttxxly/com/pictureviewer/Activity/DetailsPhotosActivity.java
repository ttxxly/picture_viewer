package top.ttxxly.com.pictureviewer.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.models.Photos;

public class DetailsPhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_photos);

        Photos.PhotosBean photos = (Photos.PhotosBean) getIntent().getSerializableExtra("photos_data");

        TextView title = (TextView) findViewById(R.id.tv_details_photos_title);
        TextView category = (TextView) findViewById(R.id.tv_details_photos_category);
        TextView keyword = (TextView) findViewById(R.id.tv_details_photos_keyword);
        TextView date = (TextView) findViewById(R.id.tv_details_photos_date);
        TextView description = (TextView) findViewById(R.id.tv_details_photos_description);
        ImageView pic = (ImageView) findViewById(R.id.img_details_pic);

        Glide.with(getApplicationContext()).load(photos.getUrl()).placeholder(R.mipmap.login_pic).centerCrop().into(pic);
        category.setText(photos.getCategoryid());
        keyword.setText(photos.getKeyword());
        date.setText(photos.getDatetime());
        description.setText(photos.getDescription());
        title.setText(photos.getTitle());
    }
}
