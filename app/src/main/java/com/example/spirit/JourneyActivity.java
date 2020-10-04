package com.example.spirit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.spirit.objects.Cartesians;
import com.example.spirit.objects.Planet;
import com.example.spirit.objects.PlanetNode;
import com.example.spirit.objects.PlanetViewModel;
import com.example.spirit.objects.Propulsion;
import com.example.spirit.utils.ModelLoader;
import com.example.spirit.utils.Utils;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.Date;

public class JourneyActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private PlanetViewModel planetViewModel;
    private ModelLoader modelLoader;
    private boolean observed = false;
    private Propulsion propulsion;

    double distanceKm;
    long totalSecs;
    long appTravelTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        modelLoader = new ModelLoader(new WeakReference<>(this));

        arFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        arFragment.getArSceneView().getPlaneRenderer().setVisible(false);

        planetViewModel = ViewModelProviders.of(this).get(PlanetViewModel.class);

        Intent intent = getIntent();
        propulsion = (Propulsion) intent.getSerializableExtra("propulsion");
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        if (frame == null) {
            return;
        }

        if (frame.getCamera().getTrackingState() == TrackingState.TRACKING && arFragment.getArSceneView().getSession().getAllAnchors().isEmpty()) {
            if (!observed) {
                planetViewModel.getPlanets().observe(this, planetList -> {
                    for (Planet planet : planetList) {
                        addObject(Uri.parse("1226 Moon.sfb"), planet);
                    }
                });

                observed = true;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addObject(Uri model, Planet planet) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Session session = arFragment.getArSceneView().getSession();
        Cartesians coords = Utils.equatorialToCartesians(planet.getRightAscension(), planet.getDeclination(), planet.getDistance());
        Anchor anchor = session.createAnchor(frame.getCamera().getPose()
                .compose(Pose.makeTranslation((float) coords.getX(), (float) coords.getY(), (float) coords.getZ())).extractTranslation());
        modelLoader.loadModel(anchor, model, planet);
    }

    public void addNodeToScene(Anchor anchor, ModelRenderable renderable, Planet planet) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        PlanetNode node = new PlanetNode(arFragment.getTransformationSystem(), planet);
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.setOnTapListener((hitTestResult, motionEvent) -> displayJourneyDetails(node));
        node.select();
    }

    public void onException(Throwable throwable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(throwable.getMessage())
                .setTitle("Error loading model!");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayJourneyDetails(PlanetNode node) {
        Planet planet = node.getPlanet();
        totalSecs = calculateRealTravelTime(planet);
        appTravelTime = calculateAppTravelTime(totalSecs);

        AlertDialog.Builder builder = new AlertDialog.Builder(JourneyActivity.this);
        builder.setTitle(R.string.journey_dialog_title)
                .setView(R.layout.journey_dialog)
                .setPositiveButton(R.string.journey_dialog_yes, (dialog, which) -> {
                    Intent intent = new Intent(JourneyActivity.this, TravelActivity.class);
                    intent.putExtra("propulsion", propulsion);
                    intent.putExtra("planet_name", planet.getName());
                    intent.putExtra("planet_year", planet.getYear());
                    intent.putExtra("distance", distanceKm);
                    intent.putExtra("travel_time", totalSecs);
                    intent.putExtra("app_time", appTravelTime);
                    Toast.makeText(JourneyActivity.this, R.string.journey_dialog_toast, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                })
                .setNegativeButton(R.string.journey_dialog_no, (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
        TextView propulsionType = dialog.findViewById(R.id.textview_journey_dialog_propulsion_type);
        propulsionType.setText(propulsion.getName());
        TextView destination = dialog.findViewById(R.id.textview_journey_dialog_destination);
        destination.setText(planet.getName());
        TextView length = dialog.findViewById(R.id.textview_journey_dialog_length);
        length.setText(planet.getDistance() + " parsecs");
        TextView realTime = dialog.findViewById(R.id.textview_journey_dialog_real_time);
        realTime.setText(convertSecondsToDays(totalSecs));
        TextView appTime = dialog.findViewById(R.id.textview_journey_dialog_app_time);
        appTime.setText(String.valueOf(appTravelTime) + " seconds");
    }

    private long calculateRealTravelTime(Planet planet){
        float distancePc = planet.getDistance();
        distanceKm = Utils.round(distancePc * 3.086E13);
        double speed = propulsion.getTravelVelocity();

        double timeSeconds = distanceKm/speed;
        Toast.makeText(this, String.valueOf(timeSeconds), Toast.LENGTH_LONG).show();
        return Math.round(timeSeconds);
    }

    private long calculateAppTravelTime(long secs){
        double constant = Math.pow(10, 10);
        double appSecs = secs/constant;

        if(appSecs < 1){
            appSecs*=20;
        }

        return (long)appSecs;

    }

    private String convertSecondsToDays(long totalSecs){
        Date d = new Date(totalSecs);

        return String.format("%d years, %d months, %d days, %02d hours, %02d minutes, %02d seconds", d.getYear(), d.getMonth(), d.getDay(), d.getHours(), d.getMinutes(), d.getSeconds());
    }
}
