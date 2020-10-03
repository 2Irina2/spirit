package com.example.spirit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Frame;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.ux.ArFragment;

public class JourneyActivity extends AppCompatActivity {

    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        arFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        arFragment.getArSceneView().getPlaneRenderer().setVisible(false);

    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        if (frame == null) {
            return;
        }

//        if (frame.getCamera().getTrackingState() == TrackingState.TRACKING && arFragment.getArSceneView().getSession().getAllAnchors().isEmpty()) {
//            if (!observed) {
//                cameraViewModel.getPlanetCoordinates().observe(this, coordinates -> {
//                    for (Coordinates coords : coordinates) {
//                        addObject(Uri.parse("andy.sfb"), coords);
//                        Log.w(CameraFragment.class.getName(), "Andy loaded");
//                    }
//                });
//                observed = true;
//            }
//        }
    }
}
