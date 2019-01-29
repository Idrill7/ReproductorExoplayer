package com.idrilplays.idril.actividaduf3;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{

    private TextView link;
    private TextView and;
    private TextView play;
    private ImageView playLogo;
    private ImageView pauseLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Obtenemos las views segun su identificador
        link = (TextView)findViewById(R.id.textoSplashLink);
        and = (TextView) findViewById(R.id.textoSplashAnd);
        play = (TextView) findViewById(R.id.textoSplashPlay);
        playLogo = (ImageView) findViewById(R.id.playlogo);
        pauseLogo = (ImageView)findViewById(R.id.pauselogo);

        // Creamos un objeto TypeFace, a este le indicamos que de los asset obtenga la fuente mediante el nombre dado
        Typeface mifuente = Typeface.createFromAsset(getAssets(), "Clip.ttf");
        // Asociamos las views con la fuente mediante el metodo setTypeface
        link.setTypeface(mifuente);
        and.setTypeface(mifuente);
        play.setTypeface(mifuente);

        // Creamos las animaciones
        Animation animacionLink = AnimationUtils.loadAnimation(this, R.anim.animaciontextolink);
        Animation animacionAnd = AnimationUtils.loadAnimation(this, R.anim.animaciontextoand);
        Animation animacionPlay = AnimationUtils.loadAnimation(this, R.anim.animaciontextoplay);

        Animation animacionPlayLogo = AnimationUtils.loadAnimation(this, R.anim.animacionplay);
        Animation animacionPauseLogo = AnimationUtils.loadAnimation(this, R.anim.animacionpause);

        // Ligamos las animaciones

        link.startAnimation(animacionLink);
        and.startAnimation(animacionAnd);
        play.startAnimation(animacionPlay);
        playLogo.startAnimation(animacionPlayLogo);
        pauseLogo.startAnimation(animacionPauseLogo);


        // Ponemos  a la escucha la animacion para pasar al login
        animacionPlay.setAnimationListener(this);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * Usaremos este metodo para que cuando acabe la animacion, se lance la activity Login
     * @param animation la animacion que ponemos a la escucha
     */
    @Override
    public void onAnimationEnd(Animation animation) {

        //Para cambiar de una activity a otra hay que crear un Intent
        //Establecemos por parametro la "ruta" del origen y el destino
        Intent intent = new Intent(this, MainActivity.class);
        // Iniciamos el cambio de activity
        startActivity(intent);

        //Acabamos el Activity del splash ya que no vamos a retroceder a ella
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
