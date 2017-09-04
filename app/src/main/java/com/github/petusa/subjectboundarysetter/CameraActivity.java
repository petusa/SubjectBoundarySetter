package com.github.petusa.subjectboundarysetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.ma1co.pmcademo.app.BaseActivity;
import com.github.ma1co.pmcademo.app.Logger;
import com.sony.scalar.hardware.CameraEx;

import java.io.IOException;

/**
 * Created by peternagy on 7/14/17 based on original CameraActivity
 */

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback {
    // TODO check and compare this class with the original CameraActivity, and see how only the extra event handler + the different layout file can be extracted out into this class
    private SurfaceHolder surfaceHolder;
    private CameraEx camera;
    private long selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_boundaries_camera);

        selectedId = getIntent().getLongExtra("id", 0);

        Logger.log("CameraActivity started...");

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = CameraEx.open(0, null);
        surfaceHolder.addCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.release();
        camera = null;
        surfaceHolder.removeCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.getNormalCamera().setPreviewDisplay(holder);
            camera.getNormalCamera().startPreview();
        } catch (IOException e) {}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    protected boolean onEnterKeyDown() {
        // show live preview
        Intent intent = new Intent(this, SetterActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
        intent.putExtra("id", selectedId);
        startActivity(intent);
        return true;
    }

//    protected boolean onDeleteKeyUp() {
//        startActivity(new Intent(this, MainActivity.class));
//        return true;
//    }


//    @Override
//    protected boolean onFocusKeyDown() {
//        camera.getNormalCamera().autoFocus(null);
//        return true;
//    }
//
//    @Override
//    protected boolean onFocusKeyUp() {
//        camera.getNormalCamera().cancelAutoFocus();
//        return true;
//    }
//
//    @Override
//    protected boolean onShutterKeyDown() {
//        camera.getNormalCamera().takePicture(null, null, null);
//        return true;
//    }
//
//    @Override
//    protected boolean onShutterKeyUp() {
//        camera.cancelTakePicture();
//        return true;
//    }

    @Override
    protected void setColorDepth(boolean highQuality) {
        super.setColorDepth(false);
    }
}
