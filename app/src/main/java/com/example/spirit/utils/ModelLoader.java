package com.example.spirit.utils;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.spirit.JourneyActivity;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.lang.ref.WeakReference;

public class ModelLoader {
    private final WeakReference<JourneyActivity> owner;
    private static final String TAG = "ModelLoader";

    public ModelLoader(WeakReference<JourneyActivity> owner) {
        this.owner = owner;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadModel(Anchor anchor, Uri uri) {
        if (owner.get() == null) {
            Log.d(TAG, "Activity is null.  Cannot load model.");
            return;
        }
        ModelRenderable.builder()
                .setSource(owner.get(), uri)
                .build()
                .handle((renderable, throwable) -> {
                    JourneyActivity fragment = owner.get();
                    if (fragment == null) {
                        return null;
                    } else if (throwable != null) {
                        fragment.onException(throwable);
                    } else {
                        fragment.addNodeToScene(anchor, renderable);
                    }
                    return null;
                });

        return;
    }
}