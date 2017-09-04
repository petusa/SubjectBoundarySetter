package com.github.petusa.subjectboundarysetter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by peternagy on 7/6/17.
 */

public class SubjectBoundariesOverlayView extends View {
    public SubjectBoundariesOverlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BoundaryLine.Painter.DrawLines(canvas);
    }
}
