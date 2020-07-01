package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.WMS1.drawful.R;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.WMS1.drawful.views.CanvasView;
import com.android.volley.Request;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class representing the drawing activity.
 * Contains a title and the canvas and it's controls.
 */
public class DrawingActivity extends AppCompatActivity {

    CanvasView canvas;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        initSeekBar();

        title = this.findViewById(R.id.titleView);
        setTheme();
        canvas = this.findViewById(R.id.canvas);
        canvas.enableDrawing();
    }


    /**
     * Function to initiate the seekbar
     */
    private void initSeekBar() {
        SeekBar bar = this.findViewById(R.id.strokeSize);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value = 8;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                canvas.setStrokeWidth(value);
            }
        });
    }

    /**
     * Function to set the theme to draw
     */
    private void setTheme() {
        String gameId = SharedPrefrencesManager.getInstance(getApplicationContext()).getGameid();
        String url = RequestQueueSingleton.BASE_URL + "/game/" + gameId + "/theme";
        @SuppressLint("SetTextI18n") JwtJsonObjectRequest request  = new JwtJsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                title.setText("Draw: " + response.getString("theme"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Something went wrong while getting the theme", Toast.LENGTH_SHORT).show(),
                getApplicationContext());
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Sets the color to use when drawing
     *
     * @param envelope envelope containing the color to use
     */
    private void setColor(ColorEnvelope envelope) {
        String color = "#" + envelope.getHexCode().substring(2); // Remove alpha value and add #
        canvas.setStrokeColor(color);
    }


    /**
     * Submits the current drawing to the server
     *
     * @param view the view to call the function
     */
    public void submitDrawing(View view) {
        JSONObject image = canvas.getDrawing();

        String gameId = SharedPrefrencesManager.getInstance(getApplicationContext()).getGameid();
        String url = RequestQueueSingleton.BASE_URL + "/game/" + gameId + "/drawing/add";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("drawing", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    canvas.disableDrawing();
                    Toast.makeText(getApplicationContext(), "Drawing received", Toast.LENGTH_SHORT).show();
                }, error ->
                Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show(),
                getApplicationContext());
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Shows the color picker
     *
     * @param view the view to call the function
     */
    public void showColorPicker(View view) {
        new ColorPickerDialog.Builder(this, AlertDialog.BUTTON_POSITIVE)
            .setTitle("Pick a color")
            .setPreferenceName("ColorPickerDialog")
            .setPositiveButton(getString(R.string.confirm),
                    (ColorEnvelopeListener) (envelope, fromUser) -> setColor(envelope))
            .setNegativeButton(getString(R.string.cancel),
                    (dialogInterface, i) -> dialogInterface.dismiss())
            .attachAlphaSlideBar(false)
            .attachBrightnessSlideBar(true)
            .show();
    }

    /**
     * Resets the drawing to an empty canvas
     *
     * @param view the view to call the function
     */
    public void resetDrawing(View view) {
        canvas.resetDrawing();
    }
}