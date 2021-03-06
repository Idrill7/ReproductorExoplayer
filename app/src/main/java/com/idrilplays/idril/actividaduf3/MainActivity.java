package com.idrilplays.idril.actividaduf3;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    // Referencia al mediaPlayer para reproducir sonidos
    private MediaPlayer mp;
    // Referencia a la imagen donde vamos a guardar la View del PlayerView
    private PlayerView playerView;
    // Referencia a una instancia del ExoPlayer que va a ser el reproductor
    private SimpleExoPlayer reproductor;
    private ImageButton exo_rew;
    private ImageButton exo_play;
    private ImageButton exo_pause;
    private ImageButton exo_ffw;
    private ImageButton restart;
    private Animation animacionbotones;
    private ImageButton volumen;
    private Animation animacionrewind;
    private ImageView playLogo;
    private ImageView pauseLogo;
    private String url;
    private DefaultDataSourceFactory dataSourceFactory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos la view segun su identificador
        exo_ffw = (ImageButton) findViewById(R.id.exo_ffwd);
        exo_pause = (ImageButton) findViewById(R.id.exo_pause);
        exo_play = (ImageButton) findViewById(R.id.exo_play);
        exo_rew = (ImageButton) findViewById(R.id.exo_rew);
        playerView = (PlayerView) findViewById(R.id.playerView);
        restart = findViewById(R.id.exo_restar);
        volumen = findViewById(R.id.exo_volumen);
        playLogo = findViewById(R.id.playlogo2);
        pauseLogo = findViewById(R.id.pauselogo2);

        // Creamos la animacion
        animacionbotones = AnimationUtils.loadAnimation(this, R.anim.animacionbotonexo);
        animacionrewind = AnimationUtils.loadAnimation(this, R.anim.animacionrewind);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Anadimos elementos a la barra, este caso los del archivo menu.xml
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // sonido al hacer click en el menuitem
        mp = MediaPlayer.create(this, R.raw.addlink);
        mp.start();

        //Creamos el objeto de EditText que sera la caja de texto que va a ser contenida dentro del AlertDialog, el contexto sera este, this (MainActivity) , por ello lo creamos antes.
        final EditText cajaTexto = new EditText(this);

        // Preparamos el reproductor con la url a reproducir, creamos la instrancia donde cargaremos los datos
        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayer"));



        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml("<font color=\"#96c93d\">" + getString(R.string.dialogTitle) + "</font>"))
                .setView(cajaTexto)
                .setNegativeButton(getString(R.string.dialogNegativeText),null)
                .setPositiveButton(getString(R.string.dialogPositiveText), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Almacenamos la cadena de texto en una variable, que sera el contenido del EditText
                        url= cajaTexto.getText().toString();
                        // Ocultamos el logo
                        playLogo.setVisibility(View.INVISIBLE);
                        pauseLogo.setVisibility(View.INVISIBLE);


                        // Aniadimos el origen del medio que sera la url
                        ExtractorMediaSource archivo = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));

                        // Hacemos un stop para parar el video anterior si se introducen dos enlaces
                        reproductor.stop();
                        //Preparamos el reproductor con el archivo a reproducir, la url
                        reproductor.prepare(archivo);
                        // Iniciamos el video
                        reproductor.setPlayWhenReady(true);



                    }
                })

                .create();


        // mostramos la ventana
        dialog.show();
        // Cambiamos el color de los botones de cancelar y aniadir
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        return super.onOptionsItemSelected(item);
    }

    /**
     * en el onstart creamos la instancia del reproductor para que siempre sea la misma
     *  y al view le establecemos esa instancia
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Creamos el reproductor
        reproductor = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        // Aniadimos el reproductor a la view
        playerView.setPlayer(reproductor);


    }

    /**
     * Cuando el activity ya no sea visible, paramos el exoplayer

        */
    @Override
    protected void onStop() {
        super.onStop();

        if (reproductor != null) {
            // La view le ponemos el reproductor a nulo

            playerView.setPlayer(null);
            // Al propio reproductor lo dejamos en release y la variable a nulo
            reproductor.release();
            reproductor = null;
        }


         //Toast.makeText(this, "hola", Toast.LENGTH_LONG)
            //      .show();
    }


    /**
     * Metodo que sera aniadido como onclick en el xml a la view y reinicia el video a 0
     * Tambien inicia la animacion
     * @param view la view que hace genera el onclick
     */
    public void restart(View view) {


        view.startAnimation(animacionrewind);
        // ponemos el segundo 0
        reproductor.seekTo(0);


    }


    /**
     * Metodo que anima el boton que sera aniadido como onclick en el xml a la view
     * @param view la view que hace genera el onclick
     */
    public void animarBoton(View view) {

            view.startAnimation(animacionbotones);

    }

    /**
     * metodo publico que no devuelve nada y se utilizara en un onclick para
     * establecer en 0 o 1 el volumen
     * @param view, la view que llama a este metodo
     */
    public void setVolumen(View view) {

        if (reproductor.getVolume() == 1) {
            reproductor.setVolume(0);
            view.startAnimation(animacionbotones);
            volumen.setImageResource(R.mipmap.volume_mute);
        } else {
            reproductor.setVolume(1);
            view.startAnimation(animacionbotones);
            volumen.setImageResource(R.mipmap.volume_up);
        }

    }
}
