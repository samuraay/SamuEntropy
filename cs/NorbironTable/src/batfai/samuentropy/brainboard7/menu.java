package batfai.samuentropy.brainboard7;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.preference.PreferenceManager;



/**
 * Created by User on 2016. 10. 20..
 */

public class menu extends android.view.SurfaceView implements Runnable {

    public int resId;
    android.graphics.Bitmap newButton;
    android.graphics.Bitmap contButton;
    private SurfaceHolder surfaceHolder;
    private android.content.Context context;
    int width;
    int height;
    public static boolean load=false;
    public android.content.SharedPreferences settings;
    public void getSize()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }

    public void getButton() {

        resId = this.getResources().getIdentifier("nbutton", "drawable",
                "batfai.samuentropy.brainboard7");
        newButton = android.graphics.BitmapFactory.decodeResource(this.getResources(), resId);
        resId = this.getResources().getIdentifier("fbutton", "drawable",
                "batfai.samuentropy.brainboard7");
        contButton = android.graphics.BitmapFactory.decodeResource(this.getResources(), resId);
    }

    public menu(android.content.Context context) {
        super(context);
        this.context = context;
        getButton();
        getSize();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new MenuEvents(this));
        settings = PreferenceManager.getDefaultSharedPreferences(context);


    }

    public menu(android.content.Context context,
                               android.util.AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getButton();
        getSize();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new MenuEvents(this));
        settings = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public menu(android.content.Context context,
                               android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        getButton();
        getSize();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new MenuEvents(this));
        settings = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Override
    public void onDraw(android.graphics.Canvas canvas) {

        if (surfaceHolder.getSurface().isValid()) {

            canvas.save();

            canvas.drawColor(android.graphics.Color.BLACK);

            if (settings.getBoolean("load", false))
                canvas.drawBitmap(contButton, null, new android.graphics.Rect(width / 2 - 100, height / 2, width / 2 + 100, height / 2 + 100), null);

            canvas.drawBitmap(newButton, null, new android.graphics.Rect(width / 2 - 100, height / 2 + 100, width / 2 + 100, height / 2 + 200), null);
            canvas.restore();
        }
    }

    public void repaint() {

        android.graphics.Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                onDraw(canvas);
            }

        } finally {

            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

        }
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {


        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {

            if (x>width/2-100 && x <width/2+100 && y>height/2 && y<height/2+100 && settings.getBoolean("load", false)) {
                NeuronGameActivity.restore=false;
                load=true;
                android.content.Intent i = new android.content.Intent(context, NeuronGameActivity.class);
                context.startActivity(i);
            }

            if (x>width/2-100 && x <width/2+100 && y>height/2+100 && y<height/2+200) {
                NeuronGameActivity.restore=true;
                load=false;
                android.content.Intent i = new android.content.Intent(context, NeuronGameActivity.class);
                context.startActivity(i);
            }
        }

        return true;
    }
    public void stop() {

    }

    @Override
    public void run() {


    }
}