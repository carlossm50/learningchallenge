package com.vladan.dragdropimages01;

import android.content.ClipData;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;
import android.speech.tts.TextToSpeech;
public class DragDropActivity extends AppCompatActivity {

    public static final String TAG = "drag_drop";
    ImageView dropZone;
    int[] imageId = new int[10];
    int[] imageId_bg = new int[10];
    int dropZone_imgID, drop_imgID,puntaje_int = 0;
    TextView puntaje;
    MediaPlayer img_correct,img_change, back_sund,end_sound;
    playbackground bgsond;
    Handler mhandler = new Handler();
    int PunteroIMG = 1;
    int dragimg_id=0;
    int intAletorio=0;
    String frunto= "";
    TextToSpeech tts;

    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.volup:{
                //Toast.makeText(getApplicationContext(),"Hola menu",Toast.LENGTH_LONG).show();
                if (back_sund.isPlaying()){
                    back_sund.stop();
                }
                    else{
                        begin_sounds();
                    }
                    break;
            }
            case R.id.refresh:{
                reiniciar();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onStart() {
        //Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();

        super.onStart();
    }

    @Override
    protected void onStop() {
        back_sund.stop();
        back_sund.release();
       // Toast.makeText(this,"Stop",Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onPause() {
        //back_sund.pause();
      //  Toast.makeText(this,"Pausa",Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        back_sund.release();
        //Toast.makeText(this,"Destroy",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //Toast.makeText(this,"Resume",Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    protected void onRestart() {
            begin_sounds();
//        Toast.makeText(this,"Restart",Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view,"",Snackbar.LENGTH_SHORT)
                 //       .setAction("Action",null).show();
            }
        });
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

        });


        puntaje = (TextView) findViewById(R.id.puntaje);

        img1 = (ImageView) findViewById(R.id.imageView1);
        img2 = (ImageView) findViewById(R.id.imageView2);
        img3 = (ImageView) findViewById(R.id.imageView3);
        img4 = (ImageView) findViewById(R.id.imageView4);
        img5 = (ImageView) findViewById(R.id.imageView5);
        img6 = (ImageView) findViewById(R.id.imageView6);
        img7 = (ImageView) findViewById(R.id.imageView7);
        img8 = (ImageView) findViewById(R.id.imageView8);
        img9 = (ImageView) findViewById(R.id.imageView9);
        img10 = (ImageView) findViewById(R.id.imageView10);
        img1.setTag(R.string.manzana);


        img1.setOnTouchListener(new ChoiceTouchListener());
        img2.setOnTouchListener(new ChoiceTouchListener());
        img3.setOnTouchListener(new ChoiceTouchListener());
        img4.setOnTouchListener(new ChoiceTouchListener());
        img5.setOnTouchListener(new ChoiceTouchListener());
        img6.setOnTouchListener(new ChoiceTouchListener());
        img7.setOnTouchListener(new ChoiceTouchListener());
        img8.setOnTouchListener(new ChoiceTouchListener());
        img9.setOnTouchListener(new ChoiceTouchListener());
        img10.setOnTouchListener(new ChoiceTouchListener());

        llenararreglo();//Llena el el arreglo de imagenes en diferente orden.

        dropZone = (ImageView) findViewById(R.id.drop);
        nextimg();
        begin_sounds(); //Iniciar el sonido de backGround
        dropZone.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()){
                    case DragEvent.ACTION_DRAG_ENDED:{
                        //if( Objects.equals(drop_imgID,dragimg_id))
                        if( drop_imgID == dragimg_id)
                        {
                            if (PunteroIMG <= 10) {
                                dropZone.setImageResource(dragimg_id);
                                puntaje_int = puntaje_int + 100;
                                puntaje.setText(String.valueOf(puntaje_int));
                                img_correct.start();

                                mhandler.postDelayed(new Runnable() {
                                   // int x = 0;

                                    @Override
                                    public void run() {
                                        SpeackOut();//Agreagar al click de un Button
                                        dropZone.setImageResource(R.drawable.correct_green);
                                        intAletorio ++;
                                    }
                                }, 500);

                                mhandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        PunteroIMG++;
                                        if (PunteroIMG <= 10) {
                                            nextimg();
                                            img_change.start();
                                        } else {
                                            end_sounds(); //sonido final
                                        }
                                    }
                                }, 1000);
                                //break;
                            }
                        }
                        else {
                            if (PunteroIMG <= 10) {
                                dropZone.setImageResource(R.drawable.wrong_red);
                                mhandler.postDelayed(new Runnable() {
                                    //int x = 0;

                                    @Override
                                    public void run() {
                                        dropZone.setImageResource(imageId_bg[intAletorio]);
                                    }
                                }, 500);

                            }
                        }
                        break;
                    }
                }
                return true;
            }
        });
    }
    //Reproduse el sonido de las frutas
    private void  SpeackOut(){
        String text = frunto;
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    //procedimiento que inicializa las varialbles de sonida y dispara el sonido de backGround
    public void begin_sounds(){
        img_correct = MediaPlayer.create (this,R.raw.mario_bros_woo_hoo);
        img_change = MediaPlayer.create (this,R.raw.mario_bros_jump);
        back_sund= MediaPlayer.create (this,R.raw.angry_birds_videojuegos);
        end_sound = MediaPlayer.create (this,R.raw.win);

        bgsond = new playbackground(back_sund);
        if (PunteroIMG <=10)
           bgsond.execute();
    }

    //Finaliza el sonido backGround e inicial el sonido de juego finalizado.
    public void end_sounds(){
        back_sund.stop();
        end_sound.start();
    }
    // Genera la proxima imagen a seleccionar
    public void nextimg(){
        //Random aleatorio = new Random(System.currentTimeMillis());
        // Producir nuevo int aleatorio entre 0 y 9
        //intAletorio = aleatorio.nextInt(5);
        dropZone.setImageResource(imageId_bg[intAletorio]);
        dropZone_imgID = imageId_bg[intAletorio];
        drop_imgID = imageId[intAletorio];
    }
    public void llenararreglo(){
        Random aleatorio = new Random(System.currentTimeMillis());
        switch (aleatorio.nextInt(5)){
            case (0):{
                imageId[0] = R.drawable.apple;
                imageId[1] = R.drawable.bananas;
                imageId[2] = R.drawable.carrot;
                imageId[3] = R.drawable.cherries;
                imageId[4] = R.drawable.grapes;
                imageId[5] = R.drawable.lemon;
                imageId[6] = R.drawable.pear;
                imageId[7] = R.drawable.strawberry;
                imageId[8] = R.drawable.tomato;
                imageId[9] = R.drawable.watermelon;

                imageId_bg[0] = R.drawable.apple_bg;
                imageId_bg[1] = R.drawable.bananas_bg;
                imageId_bg[2] = R.drawable.carrot_bg;
                imageId_bg[3] = R.drawable.cherries_bg;
                imageId_bg[4] = R.drawable.grapes_bg;
                imageId_bg[5] = R.drawable.lemon_bg;
                imageId_bg[6] = R.drawable.pear_bg;
                imageId_bg[7] = R.drawable.strawberry_bg;
                imageId_bg[8] = R.drawable.tomato_bg;
                imageId_bg[9] = R.drawable.watermelon_bg;

                break;
            }
            case (1):{
                imageId[4] = R.drawable.apple;
                imageId[8] = R.drawable.bananas;
                imageId[2] = R.drawable.carrot;
                imageId[6] = R.drawable.cherries;
                imageId[0] = R.drawable.grapes;
                imageId[1] = R.drawable.lemon;
                imageId[5] = R.drawable.pear;
                imageId[9] = R.drawable.strawberry;
                imageId[3] = R.drawable.tomato;
                imageId[7] = R.drawable.watermelon;

                imageId_bg[4] = R.drawable.apple_bg;
                imageId_bg[8] = R.drawable.bananas_bg;
                imageId_bg[2] = R.drawable.carrot_bg;
                imageId_bg[6] = R.drawable.cherries_bg;
                imageId_bg[0] = R.drawable.grapes_bg;
                imageId_bg[1] = R.drawable.lemon_bg;
                imageId_bg[5] = R.drawable.pear_bg;
                imageId_bg[9] = R.drawable.strawberry_bg;
                imageId_bg[3] = R.drawable.tomato_bg;
                imageId_bg[7] = R.drawable.watermelon_bg;
                break;
            }
            case (2):{
                imageId[9] = R.drawable.apple;
                imageId[8] = R.drawable.bananas;
                imageId[7] = R.drawable.carrot;
                imageId[6] = R.drawable.cherries;
                imageId[5] = R.drawable.grapes;
                imageId[4] = R.drawable.lemon;
                imageId[3] = R.drawable.pear;
                imageId[2] = R.drawable.strawberry;
                imageId[1] = R.drawable.tomato;
                imageId[0] = R.drawable.watermelon;

                imageId_bg[9] = R.drawable.apple_bg;
                imageId_bg[8] = R.drawable.bananas_bg;
                imageId_bg[7] = R.drawable.carrot_bg;
                imageId_bg[6] = R.drawable.cherries_bg;
                imageId_bg[5] = R.drawable.grapes_bg;
                imageId_bg[4] = R.drawable.lemon_bg;
                imageId_bg[3] = R.drawable.pear_bg;
                imageId_bg[2] = R.drawable.strawberry_bg;
                imageId_bg[1] = R.drawable.tomato_bg;
                imageId_bg[0] = R.drawable.watermelon_bg;
                break;
            }
            case (3):{
                imageId[2] = R.drawable.apple;
                imageId[4] = R.drawable.bananas;
                imageId[6] = R.drawable.carrot;
                imageId[8] = R.drawable.cherries;
                imageId[0] = R.drawable.grapes;
                imageId[3] = R.drawable.lemon;
                imageId[5] = R.drawable.pear;
                imageId[7] = R.drawable.strawberry;
                imageId[9] = R.drawable.tomato;
                imageId[1] = R.drawable.watermelon;

                imageId_bg[2] = R.drawable.apple_bg;
                imageId_bg[4] = R.drawable.bananas_bg;
                imageId_bg[6] = R.drawable.carrot_bg;
                imageId_bg[8] = R.drawable.cherries_bg;
                imageId_bg[0] = R.drawable.grapes_bg;
                imageId_bg[3] = R.drawable.lemon_bg;
                imageId_bg[5] = R.drawable.pear_bg;
                imageId_bg[7] = R.drawable.strawberry_bg;
                imageId_bg[9] = R.drawable.tomato_bg;
                imageId_bg[1] = R.drawable.watermelon_bg;
                break;
            }
            case (4):{
                imageId[3] = R.drawable.apple;
                imageId[6] = R.drawable.bananas;
                imageId[9] = R.drawable.carrot;
                imageId[2] = R.drawable.cherries;
                imageId[5] = R.drawable.grapes;
                imageId[8] = R.drawable.lemon;
                imageId[1] = R.drawable.pear;
                imageId[4] = R.drawable.strawberry;
                imageId[7] = R.drawable.tomato;
                imageId[0] = R.drawable.watermelon;

                imageId_bg[3] = R.drawable.apple_bg;
                imageId_bg[6] = R.drawable.bananas_bg;
                imageId_bg[9] = R.drawable.carrot_bg;
                imageId_bg[2] = R.drawable.cherries_bg;
                imageId_bg[5] = R.drawable.grapes_bg;
                imageId_bg[8] = R.drawable.lemon_bg;
                imageId_bg[1] = R.drawable.pear_bg;
                imageId_bg[4] = R.drawable.strawberry_bg;
                imageId_bg[7] = R.drawable.tomato_bg;
                imageId_bg[0] = R.drawable.watermelon_bg;
                break;
            }
            case (5):{
                imageId[5] = R.drawable.apple;
                imageId[0] = R.drawable.bananas;
                imageId[1] = R.drawable.carrot;
                imageId[6] = R.drawable.cherries;
                imageId[2] = R.drawable.grapes;
                imageId[7] = R.drawable.lemon;
                imageId[3] = R.drawable.pear;
                imageId[8] = R.drawable.strawberry;
                imageId[4] = R.drawable.tomato;
                imageId[9] = R.drawable.watermelon;

                imageId_bg[5] = R.drawable.apple_bg;
                imageId_bg[0] = R.drawable.bananas_bg;
                imageId_bg[1] = R.drawable.carrot_bg;
                imageId_bg[6] = R.drawable.cherries_bg;
                imageId_bg[2] = R.drawable.grapes_bg;
                imageId_bg[7] = R.drawable.lemon_bg;
                imageId_bg[3] = R.drawable.pear_bg;
                imageId_bg[8] = R.drawable.strawberry_bg;
                imageId_bg[4] = R.drawable.tomato_bg;
                imageId_bg[9] = R.drawable.watermelon_bg;
                break;
            }

        }


    }
    public void reiniciar(){
        PunteroIMG = 1;
        dragimg_id=0;
        intAletorio=0;
        puntaje_int = 0;
        back_sund.stop();
        puntaje.setText(String.valueOf(puntaje_int));
        llenararreglo();
        nextimg();
        begin_sounds();
    }

// Esta clase es para que el sonido de backGound se ejecute en un hilo
// separado a las funciones principales de la aplicación (extendiendo AsyncTask)
    class playbackground extends AsyncTask<Void,Integer,Boolean> {
        MediaPlayer back_sund;
        playbackground(MediaPlayer sund){
            this.back_sund = sund;
            back_sund.setLooping(true);
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
                back_sund.start();
            return true;
        }
    }
    public final class ChoiceTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
            view.startDrag(data , shadow,null, 0);
            Log.d(TAG,String.valueOf(view.getId()));

            if (view.getId() == img1.getId()){
                dragimg_id = R.drawable.apple;
                frunto = getString(R.string.manzana);
            }
            if (view.getId() == img2.getId() ){
                dragimg_id = R.drawable.bananas;
                frunto = getString(R.string.platano);
            }
            if (view.getId() == img3.getId() ){
                dragimg_id = R.drawable.carrot;
                frunto = getString(R.string.zanahoria);
            }
            if (view.getId() == img4.getId() ){
                dragimg_id = R.drawable.cherries;
                frunto = getString(R.string.cereza);
            }
            if (view.getId() == img5.getId() ){
                dragimg_id = R.drawable.lemon;
                frunto = getString(R.string.limon);
            }
            if (view.getId() == img6.getId() ){
                dragimg_id = R.drawable.grapes;
                frunto = getString(R.string.uva);
            }
            if (view.getId() == img7.getId() ){
                dragimg_id = R.drawable.pear;
                frunto = getString(R.string.pera);
            }
            if (view.getId() == img8.getId() ){
                dragimg_id = R.drawable.strawberry;
                frunto = getString(R.string.fresa);
            }
            if ( view.getId() == img9.getId() ){
                dragimg_id = R.drawable.tomato;
                frunto = getString(R.string.tomate);
            }
            if (view.getId() == img10.getId() ){
                dragimg_id = R.drawable.watermelon;
                frunto = getString(R.string.sandia);
            }

            return false;
        }
    }

}