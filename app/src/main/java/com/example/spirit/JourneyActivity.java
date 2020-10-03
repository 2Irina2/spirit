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
import com.example.spirit.objects.PlanetViewModel;
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

public class JourneyActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private PlanetViewModel planetViewModel;
    private ModelLoader modelLoader;
    private boolean observed = false;

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
                        Cartesians cartesians = Utils.equatorialToCartesians(planet.getRightAscension(), planet.getDeclination(), planet.getDistance());
                        addObject(Uri.parse("1226 Moon.sfb"), cartesians);
                    }
                });

                observed = true;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addObject(Uri model, Cartesians coords) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Session session = arFragment.getArSceneView().getSession();
        Anchor anchor = session.createAnchor(frame.getCamera().getPose()
                .compose(Pose.makeTranslation((float) coords.getX(), (float) coords.getY(), (float) coords.getZ())).extractTranslation());
        modelLoader.loadModel(anchor, model);
    }

    public void addNodeToScene(Anchor anchor, ModelRenderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.setOnTapListener((hitTestResult, motionEvent) -> displayJourneyDetails());
        node.select();
    }

    public void onException(Throwable throwable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(throwable.getMessage())
                .setTitle("Error loading model!");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayJourneyDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JourneyActivity.this);
        builder.setTitle(R.string.journey_dialog_title)
                .setView(R.layout.journey_dialog)
                .setPositiveButton(R.string.journey_dialog_yes, (dialog, which) -> {
                    Intent intent = new Intent(JourneyActivity.this, TravelActivity.class);
                    Toast.makeText(JourneyActivity.this, R.string.journey_dialog_toast, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                })
                .setNegativeButton(R.string.journey_dialog_no, (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();

        TextView propulsionType = dialog.findViewById(R.id.textview_journey_dialog_propulsion_type);
        propulsionType.setText("X");
        TextView destination = dialog.findViewById(R.id.textview_journey_dialog_destination);
        destination.setText("Planet x");
        TextView length = dialog.findViewById(R.id.textview_journey_dialog_length);
        length.setText("1000 kms");
        TextView realTime = dialog.findViewById(R.id.textview_journey_dialog_real_time);
        realTime.setText("A really long long time");
        TextView appTime = dialog.findViewById(R.id.textview_journey_dialog_app_time);
        appTime.setText("Shorter time");
    }
}
