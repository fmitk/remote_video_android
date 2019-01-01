package com.example.ubuntu.remote_video_play;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Runnable{

    private SurfaceHolder holder;
    private Thread mythread;
    private Canvas canvas;
    URL videoUrl;
    private String url="http://192.168.42.1";
    private int w;
    private int h;
    HttpURLConnection conn;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        w = getWindowManager().getDefaultDisplay().getWidth();
        h = getWindowManager().getDefaultDisplay().getHeight();
        SurfaceView surface = (SurfaceView)findViewById(R.id.surface);
        surface.setKeepScreenOn(true);
        mythread = new Thread(this);
        holder = surface.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                mythread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                // TODO Auto-generated method stub

            }
        });

    }
    private void draw() {
        // TODO Auto-generated method stub
        try {
            InputStream inputstream = null;
            //创建一个URL对象
//          url = "http://192.168.8.1:8083/?action=snapshot";
            videoUrl=new URL(url);
            //利用HttpURLConnection对象从网络中获取网页数据
            conn = (HttpURLConnection)videoUrl.openConnection();
            //设置输入流
            conn.setDoInput(true);
            //连接
            conn.connect();
            //得到网络返回的输入流
            inputstream = conn.getInputStream();
            //创建出一个bitmap
            bmp = BitmapFactory.decodeStream(inputstream);
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            RectF rectf = new RectF(0, 0, w, h);
            canvas.drawBitmap(bmp, null, rectf, null);
            holder.unlockCanvasAndPost(canvas);
            //关闭HttpURLConnection连接
            conn.disconnect();
        } catch (Exception ex) {
        } finally {
        }
    }


    @Override
    public void run() {

    }
}
