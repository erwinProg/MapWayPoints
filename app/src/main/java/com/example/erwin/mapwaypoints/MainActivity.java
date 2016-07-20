package com.example.erwin.mapwaypoints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView droneClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        droneClick = (ImageView)findViewById(R.id.imageView);
        imageclick(droneClick);




    }


    public void imageclick(ImageView droneClick)
    {
        droneClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mapOpen = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapOpen);


            }
        });
    }

}
