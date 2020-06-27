package com.WMS1.drawful.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CanvasView extends View {

    private Paint paint;
    private JSONObject drawing;
    private float factor = 1;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        drawing = new JSONObject();
        try {
            drawing.put("lines", new JSONArray());
        } catch (JSONException ignored) {}
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        factor = (float) h / 768;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFF);
        try {
            JSONArray lines = drawing.getJSONArray("lines");
            for (int i = 0 ; i < lines.length(); i++) {
                JSONObject line = lines.getJSONObject(i);
                JSONObject stroke = line.getJSONObject("stroke");

                JSONArray points = line.getJSONArray("points");
                JSONObject point = points.getJSONObject(0);
                Path path = new Path();
                path.moveTo(point.getInt("x") * factor, point.getInt("y") * factor);
                for (int j = 1; j < points.length(); j++){
                    point = points.getJSONObject(j);
                    path.lineTo(point.getInt("x") * factor, point.getInt("y") * factor);
                }

                paint.setColor(Color.parseColor(stroke.getString("color")));
                paint.setStrokeWidth(stroke.getInt("width") * factor);
                paint.setPathEffect(null);
                canvas.drawPath(path, paint);
            }
        } catch (JSONException ignored) {}
    }

    public void setDrawing(String image) {
        try {
            if (image != null){
                drawing = new JSONObject(image);
            }
        } catch (JSONException ignored) {}
    }
}
