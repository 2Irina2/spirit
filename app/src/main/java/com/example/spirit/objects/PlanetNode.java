package com.example.spirit.objects;

import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class PlanetNode extends TransformableNode {

    private Planet planet;

    public PlanetNode(TransformationSystem transformationSystem, Planet planet) {
        super(transformationSystem);
        this.planet = planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Planet getPlanet() {
        return planet;
    }
}
