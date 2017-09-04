package com.github.ma1co.pmcademo.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.petusa.subjectboundarysetter.R;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    protected class ActivityListItem extends ListAdapter.ListItem {
        private int nameResource;
        private Class<? extends Activity> clazz;

        public ActivityListItem(int nameResource, Class<? extends Activity> clazz) {
            this.nameResource = nameResource;
            this.clazz = clazz;
        }

        @Override
        public String getText1() {
            return getResources().getString(nameResource);
        }

        public Class<? extends Activity> getActivityClass() {
            return clazz;
        }
    }

    protected ActivityListItem activities[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Logger.log("Environment.getExternalStorageDirectory(): " + Environment.getExternalStorageDirectory());
        Log.i("Sony App", "Hello Bello...");


        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                StringWriter sw = new StringWriter();
                sw.append(throwable.toString());
                sw.append("\n");
                throwable.printStackTrace(new PrintWriter(sw));
                Logger.error(sw.toString());

                System.exit(0);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ListAdapter<ActivityListItem>(this, activities));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ActivityListItem item = (ActivityListItem) adapterView.getItemAtPosition(position);
        startActivity(new Intent(this, item.getActivityClass()));
    }



    public static final String PREFS_NAME = "StoredValues";
    private long obtainSelectedId() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt("selectedId", -1);
    }

    private void storeSelectedId(long id) {
        // Store preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("selectedId", id);

        // Commit the edits!
        editor.commit();
    }
}
