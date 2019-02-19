package com.vladan.dragdropimages01;


import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.support.v4.app.FragmentManager;
import android.os.Vibrator;
public class DragDropActivity extends AppCompatActivity {
    public static final String TAG = "drag_drop";
    ImageView dropZone;
    int[] imageId = new int[10];
    int[] imageId_bg = new int[10];
    int[] objetsSound = new int[10];
    int dropZone_imgID, drop_imgID,puntaje_int = 0,puntaje_incorecto = 0;
    TextView puntaje,puntajeincorrecto;
    MediaPlayer img_correct,img_change, back_sund,end_sound,incorrect_sound;
    playbackground bgsond;
    Handler mhandler = new Handler();
    int PunteroIMG = 1;
    int dragimg_id = 0;
    int intAletorio = 0;
    String frunto = "";
    TextToSpeech tts;
    Bundle opcion ;
    String op ;
    controller itemes;
    private Vibrator vibrator ;

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
                SpeackOut();
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
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view,"",Snackbar.LENGTH_SHORT)
                 //       .setAction("Action",null).show();
            }
        });
         opcion = getIntent().getExtras();
         op = opcion.getString("opcion");

         itemes = new controller(op);
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
        puntajeincorrecto = (TextView) findViewById(R.id.puntajeincorrecto);

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

        llenarimagenes();//LLena los componentes imageview con las imagenes correspondientes

        llenararreglo();//Llena el el arreglo de imagenes en diferente orden.

        dropZone = (ImageView) findViewById(R.id.drop);

        begin_sounds(); //Iniciar el sonido de backGround

        nextimg();//Asigna la siguiente imagen a mostrar.
        SpeackOut();
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
                                puntaje_int = puntaje_int + 1;
                                puntaje.setText(String.valueOf(puntaje_int));
                                img_correct.start();

                                mhandler.postDelayed(new Runnable() {
                                   // int x = 0;

                                    @Override
                                    public void run() {
                                        //SpeackOut();//Agreagar al click de un Button
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
                                            SpeackOut();
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
                                puntaje_incorecto = puntaje_incorecto + 1;
                                puntajeincorrecto.setText(String.valueOf(puntaje_incorecto));
                                incorrect_sound.start(); //Sonido de respuesta incorrecta.
                                vibrar();               // Vibra el telefono si selecciona la respuesta incorrecta.
                                mhandler.postDelayed(new Runnable() {
                                    //int x = 0;

                                    @Override
                                    public void run() {
                                        dropZone.setImageResource(imageId_bg[intAletorio]);
                                        PunteroIMG++;
                                        intAletorio ++;
                                        if (PunteroIMG <= 10){
                                            nextimg();
                                            SpeackOut();
                                        }
                                        else {
                                            end_sounds();
                                        }
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

    //Hacer que el telefono vibre
    public void vibrar(){
        if(vibrator.hasVibrator()){
            vibrator.vibrate(500);
        }
        else{
            Log.e("VIBRATOR", "Este dispositivo NO puede vibrar");
        }
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
        incorrect_sound = MediaPlayer.create (this,R.raw.incorrect_sound);

        bgsond = new playbackground(back_sund);
        if (PunteroIMG <=10)
           bgsond.execute();
    }

    //Finaliza el sonido backGround e inicial el sonido de juego finalizado.
    public void end_sounds(){
        back_sund.stop();
        end_sound.start();

        FragmentManager manager = getSupportFragmentManager();
        dialigopuntaje dialogo = new dialigopuntaje();
        dialogo.setpuntos(puntaje_int * 10);
        dialogo.show(manager,"Puntaje");

        /*LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.puntaje_layout,(ViewGroup) findViewById(R.id.fgmpuntaje));
        AlertDialog.Builder builder = new AlertDialog.Builder(DragDropActivity.this);
        builder.setView(layout);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();*/
    }

    // Genera la proxima imagen a seleccionar
    public void nextimg(){
        dropZone.setImageResource(imageId_bg[intAletorio]);
        dropZone_imgID = imageId_bg[intAletorio];
        drop_imgID = imageId[intAletorio];
        frunto = getString(objetsSound[intAletorio]);
    }
    public void llenarimagenes(){
        img1.setImageResource(itemes.IDItems[0]);
        img2.setImageResource(itemes.IDItems[1]);
        img3.setImageResource(itemes.IDItems[2]);
        img4.setImageResource(itemes.IDItems[3]);
        img5.setImageResource(itemes.IDItems[4]);
        img6.setImageResource(itemes.IDItems[5]);
        img7.setImageResource(itemes.IDItems[6]);
        img8.setImageResource(itemes.IDItems[7]);
        img9.setImageResource(itemes.IDItems[8]);
        img10.setImageResource(itemes.IDItems[9]);


    }
    public void llenararreglo(){
        Random aleatorio = new Random(System.currentTimeMillis());
        switch (aleatorio.nextInt(5)){
            case (0):{
                imageId[0] = itemes.IDItems[0];
                imageId[1] = itemes.IDItems[1];
                imageId[2] = itemes.IDItems[2];
                imageId[3] = itemes.IDItems[3];
                imageId[4] = itemes.IDItems[4];
                imageId[5] = itemes.IDItems[5];
                imageId[6] = itemes.IDItems[6];
                imageId[7] = itemes.IDItems[7];
                imageId[8] = itemes.IDItems[8];
                imageId[9] = itemes.IDItems[9];

                imageId_bg[0] = itemes.IDItems_bg[0];
                imageId_bg[1] = itemes.IDItems_bg[1];
                imageId_bg[2] = itemes.IDItems_bg[2];
                imageId_bg[3] = itemes.IDItems_bg[3];
                imageId_bg[4] = itemes.IDItems_bg[4];
                imageId_bg[5] = itemes.IDItems_bg[5];
                imageId_bg[6] = itemes.IDItems_bg[6];
                imageId_bg[7] = itemes.IDItems_bg[7];
                imageId_bg[8] = itemes.IDItems_bg[8];
                imageId_bg[9] = itemes.IDItems_bg[9];

                objetsSound[0] = itemes.IDSounds[0];
                objetsSound[1] = itemes.IDSounds[1];
                objetsSound[2] = itemes.IDSounds[2];
                objetsSound[3] = itemes.IDSounds[3];
                objetsSound[4] = itemes.IDSounds[4];
                objetsSound[5] = itemes.IDSounds[5];
                objetsSound[6] = itemes.IDSounds[6];
                objetsSound[7] = itemes.IDSounds[7];
                objetsSound[8] = itemes.IDSounds[8];
                objetsSound[9] = itemes.IDSounds[9];

                break;
            }
            case (1):{
                imageId[4] = itemes.IDItems[0];
                imageId[8] = itemes.IDItems[1];
                imageId[2] = itemes.IDItems[2];
                imageId[6] = itemes.IDItems[3];
                imageId[0] = itemes.IDItems[4];
                imageId[1] = itemes.IDItems[5];
                imageId[5] = itemes.IDItems[6];
                imageId[9] = itemes.IDItems[7];
                imageId[3] = itemes.IDItems[8];
                imageId[7] = itemes.IDItems[9];

                imageId_bg[4] = itemes.IDItems_bg[0];
                imageId_bg[8] = itemes.IDItems_bg[1];
                imageId_bg[2] = itemes.IDItems_bg[2];
                imageId_bg[6] = itemes.IDItems_bg[3];
                imageId_bg[0] = itemes.IDItems_bg[4];
                imageId_bg[1] = itemes.IDItems_bg[5];
                imageId_bg[5] = itemes.IDItems_bg[6];
                imageId_bg[9] = itemes.IDItems_bg[7];
                imageId_bg[3] = itemes.IDItems_bg[8];
                imageId_bg[7] = itemes.IDItems_bg[9];

                objetsSound[4] = itemes.IDSounds[0];
                objetsSound[8] = itemes.IDSounds[1];
                objetsSound[2] = itemes.IDSounds[2];
                objetsSound[6] = itemes.IDSounds[3];
                objetsSound[0] = itemes.IDSounds[4];
                objetsSound[1] = itemes.IDSounds[5];
                objetsSound[5] = itemes.IDSounds[6];
                objetsSound[9] = itemes.IDSounds[7];
                objetsSound[3] = itemes.IDSounds[8];
                objetsSound[7] = itemes.IDSounds[9];

                break;
            }
            case (2):{
                imageId[9] = itemes.IDItems[0];
                imageId[8] = itemes.IDItems[1];
                imageId[7] = itemes.IDItems[2];
                imageId[6] = itemes.IDItems[3];
                imageId[5] = itemes.IDItems[4];
                imageId[4] = itemes.IDItems[5];
                imageId[3] = itemes.IDItems[6];
                imageId[2] = itemes.IDItems[7];
                imageId[1] = itemes.IDItems[8];
                imageId[0] = itemes.IDItems[9];

                imageId_bg[9] = itemes.IDItems_bg[0];
                imageId_bg[8] = itemes.IDItems_bg[1];
                imageId_bg[7] = itemes.IDItems_bg[2];
                imageId_bg[6] = itemes.IDItems_bg[3];
                imageId_bg[5] = itemes.IDItems_bg[4];
                imageId_bg[4] = itemes.IDItems_bg[5];
                imageId_bg[3] = itemes.IDItems_bg[6];
                imageId_bg[2] = itemes.IDItems_bg[7];
                imageId_bg[1] = itemes.IDItems_bg[8];
                imageId_bg[0] = itemes.IDItems_bg[9];

                objetsSound[9] = itemes.IDSounds[0];
                objetsSound[8] = itemes.IDSounds[1];
                objetsSound[7] = itemes.IDSounds[2];
                objetsSound[6] = itemes.IDSounds[3];
                objetsSound[5] = itemes.IDSounds[4];
                objetsSound[4] = itemes.IDSounds[5];
                objetsSound[3] = itemes.IDSounds[6];
                objetsSound[2] = itemes.IDSounds[7];
                objetsSound[1] = itemes.IDSounds[8];
                objetsSound[0] = itemes.IDSounds[9];

                break;
            }
            case (3):{
                imageId[2] = itemes.IDItems[0];
                imageId[4] = itemes.IDItems[1];
                imageId[6] = itemes.IDItems[2];
                imageId[8] = itemes.IDItems[3];
                imageId[0] = itemes.IDItems[4];
                imageId[3] = itemes.IDItems[5];
                imageId[5] = itemes.IDItems[6];
                imageId[7] = itemes.IDItems[7];
                imageId[9] = itemes.IDItems[8];
                imageId[1] = itemes.IDItems[9];

                imageId_bg[2] = itemes.IDItems_bg[0];
                imageId_bg[4] = itemes.IDItems_bg[1];
                imageId_bg[6] = itemes.IDItems_bg[2];
                imageId_bg[8] = itemes.IDItems_bg[3];
                imageId_bg[0] = itemes.IDItems_bg[4];
                imageId_bg[3] = itemes.IDItems_bg[5];
                imageId_bg[5] = itemes.IDItems_bg[6];
                imageId_bg[7] = itemes.IDItems_bg[7];
                imageId_bg[9] = itemes.IDItems_bg[8];
                imageId_bg[1] = itemes.IDItems_bg[9];

                objetsSound[2] = itemes.IDSounds[0];
                objetsSound[4] = itemes.IDSounds[1];
                objetsSound[6] = itemes.IDSounds[2];
                objetsSound[8] = itemes.IDSounds[3];
                objetsSound[0] = itemes.IDSounds[4];
                objetsSound[3] = itemes.IDSounds[5];
                objetsSound[5] = itemes.IDSounds[6];
                objetsSound[7] = itemes.IDSounds[7];
                objetsSound[9] = itemes.IDSounds[8];
                objetsSound[1] = itemes.IDSounds[9];

                break;
            }
            case (4):{
                imageId[3] = itemes.IDItems[0];
                imageId[6] = itemes.IDItems[1];
                imageId[9] = itemes.IDItems[2];
                imageId[2] = itemes.IDItems[3];
                imageId[5] = itemes.IDItems[4];
                imageId[8] = itemes.IDItems[5];
                imageId[1] = itemes.IDItems[6];
                imageId[4] = itemes.IDItems[7];
                imageId[7] = itemes.IDItems[8];
                imageId[0] = itemes.IDItems[9];

                imageId_bg[3] = itemes.IDItems_bg[0];
                imageId_bg[6] = itemes.IDItems_bg[1];
                imageId_bg[9] = itemes.IDItems_bg[2];
                imageId_bg[2] = itemes.IDItems_bg[3];
                imageId_bg[5] = itemes.IDItems_bg[4];
                imageId_bg[8] = itemes.IDItems_bg[5];
                imageId_bg[1] = itemes.IDItems_bg[6];
                imageId_bg[4] = itemes.IDItems_bg[7];
                imageId_bg[7] = itemes.IDItems_bg[8];
                imageId_bg[0] = itemes.IDItems_bg[9];

                objetsSound[3] = itemes.IDSounds[0];
                objetsSound[6] = itemes.IDSounds[1];
                objetsSound[9] = itemes.IDSounds[2];
                objetsSound[2] = itemes.IDSounds[3];
                objetsSound[5] = itemes.IDSounds[4];
                objetsSound[8] = itemes.IDSounds[5];
                objetsSound[1] = itemes.IDSounds[6];
                objetsSound[4] = itemes.IDSounds[7];
                objetsSound[7] = itemes.IDSounds[8];
                objetsSound[0] = itemes.IDSounds[9];

                break;
            }
            case (5):{
                imageId[5] = itemes.IDItems[0];
                imageId[0] = itemes.IDItems[1];
                imageId[1] = itemes.IDItems[2];
                imageId[6] = itemes.IDItems[3];
                imageId[2] = itemes.IDItems[4];
                imageId[7] = itemes.IDItems[5];
                imageId[3] = itemes.IDItems[6];
                imageId[8] = itemes.IDItems[7];
                imageId[4] = itemes.IDItems[8];
                imageId[9] = itemes.IDItems[9];

                imageId_bg[5] = itemes.IDItems_bg[0];
                imageId_bg[0] = itemes.IDItems_bg[1];
                imageId_bg[1] = itemes.IDItems_bg[2];
                imageId_bg[6] = itemes.IDItems_bg[3];
                imageId_bg[2] = itemes.IDItems_bg[4];
                imageId_bg[7] = itemes.IDItems_bg[5];
                imageId_bg[3] = itemes.IDItems_bg[6];
                imageId_bg[8] = itemes.IDItems_bg[7];
                imageId_bg[4] = itemes.IDItems_bg[8];
                imageId_bg[9] = itemes.IDItems_bg[9];

                objetsSound[5] = itemes.IDSounds[0];
                objetsSound[0] = itemes.IDSounds[1];
                objetsSound[1] = itemes.IDSounds[2];
                objetsSound[6] = itemes.IDSounds[3];
                objetsSound[2] = itemes.IDSounds[4];
                objetsSound[7] = itemes.IDSounds[5];
                objetsSound[3] = itemes.IDSounds[6];
                objetsSound[8] = itemes.IDSounds[7];
                objetsSound[4] = itemes.IDSounds[8];
                objetsSound[9] = itemes.IDSounds[9];

                break;
            }

        }


    }
    public void reiniciar(){
        PunteroIMG = 1;
        dragimg_id=0;
        intAletorio=0;
        puntaje_int = 0;
        puntaje_incorecto = 0;
        back_sund.stop();
        puntaje.setText(String.valueOf(puntaje_int));
        puntajeincorrecto.setText(String.valueOf(puntaje_incorecto));
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
               // back_sund.start();
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
                dragimg_id = itemes.IDItems[0];
                //frunto = getString(itemes.IDSounds[0]);
            }
            if (view.getId() == img2.getId() ){
                dragimg_id = itemes.IDItems[1];
                //frunto = getString(itemes.IDSounds[1]);
            }
            if (view.getId() == img3.getId() ){
                dragimg_id = itemes.IDItems[2];
                //frunto = getString(itemes.IDSounds[2]);
            }
            if (view.getId() == img4.getId() ){
                dragimg_id = itemes.IDItems[3];
                //frunto = getString(itemes.IDSounds[3]);
            }
            if (view.getId() == img5.getId() ){
                dragimg_id = itemes.IDItems[4];
                //frunto = getString(itemes.IDSounds[4]);
            }
            if (view.getId() == img6.getId() ){
                dragimg_id = itemes.IDItems[5];
                //frunto = getString(itemes.IDSounds[5]);
            }
            if (view.getId() == img7.getId() ){
                dragimg_id = itemes.IDItems[6];
                //frunto = getString(itemes.IDSounds[6]);
            }
            if (view.getId() == img8.getId() ){
                dragimg_id = itemes.IDItems[7];
                //frunto = getString(itemes.IDSounds[7]);
            }
            if ( view.getId() == img9.getId() ){
                dragimg_id = itemes.IDItems[8];
                //frunto = getString(itemes.IDSounds[8]);
            }
            if (view.getId() == img10.getId() ){
                dragimg_id = itemes.IDItems[9];
                //frunto = getString(itemes.IDSounds[9]);
            }

            return false;
        }
    }
}