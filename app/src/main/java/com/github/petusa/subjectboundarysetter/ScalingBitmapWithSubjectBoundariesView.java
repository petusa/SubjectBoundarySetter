package com.github.petusa.subjectboundarysetter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.ma1co.pmcademo.app.ScalingBitmapView;

/**
 * Created by peternagy on 7/17/17.
 */

public class ScalingBitmapWithSubjectBoundariesView extends ScalingBitmapView {
    // TODO check whether we need both the SubjectBoundariesOverlayViev, ScalingBitmapWithSubjectBoundariesView classes, or we can replace one with the other

    public ScalingBitmapWithSubjectBoundariesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BoundaryLine.Painter.DrawLines(canvas);
    }
}
