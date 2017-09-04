package com.github.petusa.subjectboundarysetter;

/**
 * Created by peternagy on 7/17/17.
 */

public class MainActivity extends com.github.ma1co.pmcademo.app.MainActivity {

    {
        activities = new ActivityListItem[] {
                new ActivityListItem(R.string.title_activity_setter, SetterActivity.class),
                new ActivityListItem(R.string.title_activity_playback_recent, PlaybackRecentActivity.class),
                new ActivityListItem(R.string.title_activity_camera, CameraActivity.class),
                new ActivityListItem(R.string.title_activity_playback, PlaybackActivity.class)
        };
    }

    // TODO add LICENSE file
}
