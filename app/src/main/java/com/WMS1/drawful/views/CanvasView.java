package com.WMS1.drawful.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CanvasView extends View {

    private Paint paint;
    private Path path;
    private JSONObject drawing;
    private float factor = 1;

    private String strokeColor;
    private int strokeWidth;
    private boolean canDraw;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        drawing = new JSONObject();
        try {
            drawing.put("lines", new JSONArray());
        } catch (JSONException ignored) {}

        canDraw = false;
        strokeWidth = 8;
        strokeColor = "#000000";
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        factor = (float) h / 768;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            JSONArray lines = drawing.getJSONArray("lines");
            for (int i = 0 ; i < lines.length(); i++) {
                JSONObject line = lines.getJSONObject(i);
                JSONObject stroke = line.getJSONObject("stroke");

                JSONArray points = line.getJSONArray("points");
                JSONObject point = points.getJSONObject(0);
                path.reset();
                path.moveTo(point.getInt("x") * factor, point.getInt("y") * factor);
                for (int j = 1; j < points.length(); j++){
                    point = points.getJSONObject(j);
                    path.lineTo(point.getInt("x") * factor, point.getInt("y") * factor);
                }

                paint.setColor(Color.parseColor(stroke.getString("color")));
                paint.setStrokeWidth(stroke.getInt("width") * factor);
                canvas.drawPath(path, paint);
            }
        } catch (JSONException ignored) {}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.canDraw){
            return  true;
        }
        float x = event.getX();
        float y = event.getY();

        try {
            JSONArray lines = this.drawing.getJSONArray("lines");
            JSONObject line;
            JSONArray points;
            JSONObject point = new JSONObject();
            point.put("x", x / factor);
            point.put("y", y / factor);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                line = new JSONObject();

                JSONObject stroke = new JSONObject();
                stroke.put("width", strokeWidth);
                stroke.put("color", strokeColor);
                line.put("stroke", stroke);

                points = new JSONArray();
                lines.put(line);
            } else {
                line = lines.getJSONObject(lines.length() - 1);
                points = line.getJSONArray("points");
            }

            points.put(point);
            line.put("points", points);
            lines.put(lines.length() - 1, line);
            drawing.put("lines", lines);
        } catch (JSONException ignored){}
        invalidate();


        return true;
    }

    public void setStrokeColor(String color){
        strokeColor = color;
    }

    public void setStrokeWidth(int width){
        strokeWidth = width;
    }

    public void enableDrawing(){
        canDraw = true;
    }

    public void disableDrawing(){
        canDraw = false;
    }

    public void setDrawing(String image) {
        try {
            if (image != null){
                drawing = new JSONObject(image);
            }
        } catch (JSONException ignored) {}
    }

    public JSONObject getDrawing() {
        return drawing;
    }

    public void resetDrawing() {
        drawing = new JSONObject();
        try {
            drawing.put("lines", new JSONArray());
        } catch (JSONException ignored) {}
        invalidate();
    }
}
