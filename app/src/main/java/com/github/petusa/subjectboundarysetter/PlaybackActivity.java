package com.github.petusa.subjectboundarysetter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.github.ma1co.openmemories.framework.ImageInfo;
import com.github.ma1co.openmemories.framework.MediaManager;
import com.github.ma1co.pmcademo.app.BaseActivity;
import com.github.ma1co.pmcademo.app.BitmapUtil;
import com.github.ma1co.pmcademo.app.Logger;
import com.github.ma1co.pmcademo.app.ScalingBitmapView;

import java.text.SimpleDateFormat;

/**
 * Created by peternagy on 7/14/17 based on original CameraActivity
 */

public class PlaybackActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private MediaManager mediaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        Logger.log("PlaybackActivity started");
        ListView listView = (ListView) findViewById(R.id.listView);

        mediaManager = MediaManager.create(this);
        Logger.log("MediaManager created");

        Cursor cursor = mediaManager.queryNewestImages();
        Logger.log("Cursor created. Cursor length: " + cursor.getCount());
        listView.setAdapter(new ResourceCursorAdapter(this, R.layout.image_list_item, cursor) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ImageInfo info = mediaManager.getImageInfo(cursor);
                decorateListView(view, info);
            }
        });

        listView.setOnItemClickListener(this);
    }

    public void decorateListView(View view, ImageInfo info) {
        // TODO use placeholder parttern here
        String text1 = info.getFolder() + "/" + info.getFilename();
        String text2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getDate()) + " - " +
                info.getWidth() + "x" + info.getHeight() + " - " +
                info.getFocalLength() + "mm" + " - " +
                "f" + info.getAperture() + " - " +
                "1/" + (int) (info.getExposureTime() != 0 ? 1 / info.getExposureTime() : 0) + "s" + " - " +
                "ISO" + info.getIso();
        Bitmap thumbnail = BitmapUtil.fixOrientation(BitmapFactory.decodeStream(info.getThumbnail()), info.getOrientation());

        ((TextView) view.findViewById(android.R.id.text1)).setText(text1);
        ((TextView) view.findViewById(android.R.id.text2)).setText(text2);
        ((ScalingBitmapView) view.findViewById(R.id.imageView)).setImageBitmap(thumbnail);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long listId) {
        Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        long id = mediaManager.getImageId(cursor);
        storeSelectedId(id);
        Intent intent = new Intent(this, SetterActivity.class);
        intent.putExtra("id", id);

        startActivity(intent);
    }

    private void storeSelectedId(long id) {
        // Store preferences
        SharedPreferences settings = getSharedPreferences(SetterActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("selectedId", id);

        // Commit the edits!
        editor.commit();
    }

}