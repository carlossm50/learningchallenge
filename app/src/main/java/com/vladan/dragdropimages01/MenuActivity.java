package com.vladan.dragdropimages01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
ImageView imgfruit;
ImageView imgnumber;
TextToSpeech tts;
    ImageView geometric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.getDefault());
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","lenguaje no soportado");
                    }
                    else {
                    }
                }
                else{
                    Log.e("TTS","Inicializacion del lenguaje");
                }
            }
            private void  SpeackOut(){
                String text = "Joder tio";
                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        imgfruit = (ImageView) findViewById(R.id.imgfruit);
        imgnumber = (ImageView) findViewById(R.id.imgnumber);
        geometric = (ImageView) findViewById(R.id.geometric);
        imgfruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DragDropActivity.class);
                intent.putExtra("opcion","1");
                startActivity(intent);

            }
        });
        imgnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DragDropActivity.class);
                intent.putExtra("opcion","2");
                startActivity(intent);
            }
        });
        geometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DragDropActivity.class);
                intent.putExtra("opcion","3");
                startActivity(intent);
            }
        });
    }

}
