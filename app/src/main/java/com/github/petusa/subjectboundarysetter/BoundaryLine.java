package com.github.petusa.subjectboundarysetter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by peternagy on 7/14/17.
 */

public enum BoundaryLine {
    TOP(Color.BLUE, 100, true),
    BOTTOM(Color.RED, -100, true),
    LEFT(Color.GREEN, 100, false),
    RIGHT(Color.YELLOW, -100, false);

    public int color;
    public int offset;
    public boolean horizontal = false;
    public static final int STROKE_WIDTH = 2;

    BoundaryLine(int color, int offset, boolean horizontal) {
        this.color = color;
        this.offset = offset;
        this.horizontal = horizontal;
    }

    static class Painter {
        public static void DrawLines(Canvas canvas) {
            // TODO when ViewFinder / Monitor changes, so changes the resolution => we should set the porper aspect for the lines, too => see setImageDrawable(new ScaledBitmapDrawable(bitmap, frameBufferAspect / displayAspect)); in ScalingBitmapView
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setStrokeWidth(STROKE_WIDTH);
            for (BoundaryLine line : BoundaryLine.values()) {
                p.setColor(line.color);
                if (line.horizontal) {
                    if (line.offset < 0) {
                        canvas.drawLine(0, canvas.getHeight() + line.offset, canvas.getWidth(), canvas.getHeight() + line.offset, p);
                    } else {
                        canvas.drawLine(0, line.offset, canvas.getWidth(), line.offset, p);
                    }
                } else {
                    if (line.offset < 0) {
                        canvas.drawLine(canvas.getWidth() + line.offset, 0, canvas.getWidth() + line.offset, canvas.getHeight(), p);
                    } else {
                        canvas.drawLine(line.offset, 0, line.offset, canvas.getHeight(), p);
                    }
                }
            }
        }
    }

}
