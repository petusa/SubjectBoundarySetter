package com.github.petusa.subjectboundarysetter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.github.ma1co.openmemories.framework.ImageInfo;
import com.github.ma1co.openmemories.framework.MediaManager;
import com.github.ma1co.pmcademo.app.BaseActivity;
import com.github.ma1co.pmcademo.app.BitmapUtil;

import static com.github.petusa.subjectboundarysetter.BoundaryLine.BOTTOM;
import static com.github.petusa.subjectboundarysetter.BoundaryLine.LEFT;
import static com.github.petusa.subjectboundarysetter.BoundaryLine.RIGHT;
import static com.github.petusa.subjectboundarysetter.BoundaryLine.TOP;

/**
 * Created by peternagy on 7/14/17 based on original CameraActivity
 */

public class SetterActivity extends BaseActivity {
    private Bitmap image;

    private ScalingBitmapWithSubjectBoundariesView imageView;

    private BoundaryLine activeBounderyLine = BoundaryLine.TOP;

    private long selectedId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_boundaries_setter);
        imageView = (ScalingBitmapWithSubjectBoundariesView) findViewById(R.id.imageView);

        restoreLineOffsetValues();

        selectedId = getIntent().getLongExtra("id", -1);
        if (selectedId == -1) {
            selectedId = obtainSelectedId();
        }

        if (selectedId!= -1) {
            MediaManager mediaManager = MediaManager.create(this);
            Context context = null;
            ImageInfo info = mediaManager.getImageInfo(selectedId);
            image = BitmapUtil.fixOrientation(BitmapFactory.decodeStream(info.getPreviewImage()), info.getOrientation());
            imageView.setImageBitmap(image);
        }
    }

    public static final String PREFS_NAME = "StoredValues"; // TODO this stores currently values for one picture only => later we should have these separately stored for all "Recent images"
    private void restoreLineOffsetValues() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        TOP.offset = settings.getInt(TOP.name(), TOP.offset);
        LEFT.offset = settings.getInt(LEFT.name(), LEFT.offset);
        RIGHT.offset = settings.getInt(RIGHT.name(), RIGHT.offset);
        BOTTOM.offset = settings.getInt(BOTTOM.name(), BOTTOM.offset);

    }

    private void storeLineOffsetValues() {
        // Store preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(TOP.name(), TOP.offset);
        editor.putInt(LEFT.name(), LEFT.offset);
        editor.putInt(RIGHT.name(), RIGHT.offset);
        editor.putInt(BOTTOM.name(), BOTTOM.offset);

        // Commit the edits!
        editor.commit();
    }

    protected boolean onLowerDialChanged(int value) {
        activeBounderyLine.offset = activeBounderyLine.offset - value;
        imageView.invalidate();
        return true;
    }

    protected boolean onUpKeyDown() {
        activeBounderyLine = BoundaryLine.TOP;
        return true;
    }
    // protected boolean onUpKeyUp() { return logKey(false, "up"); }

    protected boolean onDownKeyDown() {
        activeBounderyLine = BoundaryLine.BOTTOM;
        return true;
    }
    // protected boolean onDownKeyUp() { return logKey(false, "down"); }

    protected boolean onLeftKeyDown() {
        activeBounderyLine = BoundaryLine.LEFT;
        return false;
    }
    //protected boolean onLeftKeyUp() { return logKey(false, "left"); }

    protected boolean onRightKeyDown() {
        activeBounderyLine = BoundaryLine.RIGHT;
        return true;
    }
    //protected boolean onRightKeyUp() { return logKey(false, "right"); }

    protected boolean onEnterKeyDown() {
        // show live preview
        Intent intent = new Intent(this, CameraActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
        intent.putExtra("id", selectedId);
        startActivity(intent);
        return true;
    }
    //    protected boolean onEnterKeyUp() { return logKey(false, "menu"); }

    protected boolean onUpperDialChanged(int value) {
        activeBounderyLine.offset = activeBounderyLine.offset - 10 * value;
        imageView.invalidate();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeLineOffsetValues();
    }



//    protected boolean onUpKeyDown() { return logKey(true, "up"); }
//    protected boolean onUpKeyUp() { return logKey(false, "up"); }
//    protected boolean onDownKeyDown() { return logKey(true, "down"); }
//    protected boolean onDownKeyUp() { return logKey(false, "down"); }
//    protected boolean onLeftKeyDown() { return logKey(true, "left"); }
//    protected boolean onLeftKeyUp() { return logKey(false, "left"); }
//    protected boolean onRightKeyDown() { return logKey(true, "right"); }
//    protected boolean onRightKeyUp() { return logKey(false, "right"); }
//    protected boolean onEnterKeyDown() { return logKey(true, "enter"); }
//    protected boolean onEnterKeyUp() { return logKey(false, "enter"); }
//    protected boolean onFnKeyDown() { return logKey(true, "fn"); }
//    protected boolean onFnKeyUp() { return logKey(false, "fn"); }
//    protected boolean onAelKeyDown() { return logKey(true, "ael"); }
//    protected boolean onAelKeyUp() { return logKey(false, "ael"); }
//    protected boolean onMenuKeyDown() { return logKey(true, "menu"); }
//    protected boolean onMenuKeyUp() { return logKey(false, "menu"); }
//    protected boolean onFocusKeyDown() { return logKey(true, "focus"); }
//    protected boolean onFocusKeyUp() { return logKey(false, "focus"); }
//    protected boolean onShutterKeyDown() { return logKey(true, "shutter"); }
//    protected boolean onShutterKeyUp() { return logKey(false, "shutter"); }
//    protected boolean onPlayKeyDown() { return logKey(true, "play"); }
//    protected boolean onPlayKeyUp() { return logKey(false, "play"); }
//    protected boolean onMovieKeyDown() { return logKey(true, "movie"); }
//    protected boolean onMovieKeyUp() { return logKey(false, "movie"); }
//    protected boolean onC1KeyDown() { return logKey(true, "c1"); }
//    protected boolean onC1KeyUp() { return logKey(false, "c1"); }
//    protected boolean onLensAttached() { log("lens attached"); return true; }
//    protected boolean onLensDetached() { log("lens detached"); return true; }
//    protected boolean onUpperDialChanged(int value) { return logDial("upper dial", value); }
//    protected boolean onLowerDialChanged(int value) { return logDial("lower dial", value); }
//    protected boolean onModeDialChanged(int value) { return logDial("mode dial", value); }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (image!=null) {
            image.recycle();
            image = null;
        }
    }

    private long obtainSelectedId() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getLong("selectedId", -1);
    }

}
