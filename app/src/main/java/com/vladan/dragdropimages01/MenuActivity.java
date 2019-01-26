package com.vladan.dragdropimages01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
ImageView imgfruit;
ImageView imgnumber;
    ImageView geometric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imgfruit = (ImageView) findViewById(R.id.imgfruit);
        imgnumber = (ImageView) findViewById(R.id.imgnumber);
        geometric = (ImageView) findViewById(R.id.geometric);
        imgfruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DragDropActivity.class);
                startActivity(intent);

            }
        });
        imgnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NumberChallenge.class);
                startActivity(intent);
            }
        });
        geometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),GeometricActivity.class);
                startActivity(intent);
            }
        });
    }
}
