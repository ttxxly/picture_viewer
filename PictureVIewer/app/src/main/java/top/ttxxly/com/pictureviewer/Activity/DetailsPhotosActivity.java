package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.models.Photos;

public class DetailsPhotosActivity extends AppCompatActivity {

    private TextView title;
    private TextView category;
    private TextView keyword;
    private TextView date;
    private TextView description;
    private ImageView pic;
    private String Url = "http://10.0.2.2/picture_viewer";
    private ImageView mReturn;
    private ImageView mEdit;
    private Photos.PhotosBean photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_photos);

        photos = (Photos.PhotosBean) getIntent().getSerializableExtra("photos_data");

        title = (TextView) findViewById(R.id.tv_details_photos_title);
        date = (TextView) findViewById(R.id.tv_details_photos_date);
        description = (TextView) findViewById(R.id.tv_details_photos_description);
        pic = (ImageView) findViewById(R.id.img_details_pic);
        mReturn = (ImageView) findViewById(R.id.img_details_return_top);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEdit = (ImageView) findViewById(R.id.img_details_edit);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsPhotosActivity.this, EditPicActivity.class);
                intent.putExtra("photos_data", photos);
                startActivityForResult(intent, 1);
            }
        });
        Log.i("Detail_url", Url+ photos.getUrl());
        Glide.with(getApplicationContext()).load(Url+ photos.getUrl()).placeholder(R.mipmap.login_pic).centerCrop().into(pic);
        date.setText(photos.getDatetime());
        description.setText(photos.getDescription());
        title.setText(photos.getTitle());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                break;
        }
    }
}
