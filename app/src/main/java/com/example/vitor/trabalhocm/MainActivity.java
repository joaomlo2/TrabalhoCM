package com.example.vitor.trabalhocm;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.modifier.ColorBackgroundModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import java.util.ArrayList;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private int width = 1280;
    private  int height = 800;
    private BitmapTextureAtlas ourAtlas;
    private TextureRegion ourquadrado;
    private TiledTextureRegion fundo;//isto tem de se ver
    private Scene scene;
    private Sprite tanke;



    @Override
    protected void onCreateResources() {
        ourAtlas = new BitmapTextureAtlas(this.getTextureManager(),1280,800);
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        ourquadrado= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.ourAtlas, this, "tank.png",64,64);
        /*fundo= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.ourAtlas,this,"fundo.png",1280,800,1,1);*/

        mEngine.getTextureManager().loadTexture(ourAtlas);


    }


    @Override
    protected Scene onCreateScene() {


       scene = new Scene();

        scene.setBackground(new Background(Color.WHITE));

        tanke = new Sprite(ourquadrado.getWidth()/2,height/2,ourquadrado,getVertexBufferObjectManager());


        scene.attachChild(tanke);
        scene.setOnSceneTouchListener(this);
        scene.setTouchAreaBindingOnActionDownEnabled(true);



        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {

        final Camera camera = new Camera(0,0,width,height);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,new RatioResolutionPolicy
                (width,height), camera);
    }



    public void onAreaTouched(float pX, float pY)
    {
        Sprite movetanke =  new Sprite(height/2,width /2,ourquadrado,getVertexBufferObjectManager());
        scene.attachChild(movetanke);



       float offX =  pX- tanke.getX();
        float offY = pY - tanke.getY();
        float ratio = offY/offX;

        int finalX = (int)(width + movetanke.getWidth());
        int finalY = (int)(ratio * finalX + tanke.getY());
        MoveModifier modifier = new MoveModifier(3,
                tanke.getX(),tanke.getY(),
                finalX, finalY);

        tanke.registerEntityModifier(modifier);


    }/*
    public boolean adjustToSceneBinding(Sprite tanke) {

        // Correct the X Boundaries.
        if (tanke.getX() < 0) {
            tanke.setX(0);
        } else if (tanke.getX() + tanke.getWidth() > width) {
            tanke.setX(width - tanke.getWidth());
        }
        if(tanke.getY()<0)
        {
            tanke.setY(0);
        }
        else if(tanke.getY() + tanke.getHeight() > height)
        {
            tanke.setY(height - tanke.getHeight());
        }
        return  true;
    }*/

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

        if (pSceneTouchEvent.isActionDown())
        {
            onAreaTouched(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
            return true;
        }
        return false;
    }
}
