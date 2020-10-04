package com.example.spirit.utils;

import com.example.spirit.objects.Cartesians;
import com.example.spirit.objects.Planet;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static List<Planet> parsePlanetCsv(String planetCsv) throws IOException {
        CSVReader reader = new CSVReader(new StringReader(planetCsv));

        List<Planet> planets = new ArrayList<>();
        String[] line = null;
        line = reader.readNext();

        while ((line = reader.readNext()) != null) {
            Planet planet = new Planet();
            planet.setName(line[0]);
            planet.setRightAscension(Float.parseFloat(line[1].substring(0, 6)));
            planet.setDeclination(Float.parseFloat(line[2].substring(0, 6)));
            planet.setDistance(Float.parseFloat(line[3].substring(0, 6)));
            planet.setYear(Integer.parseInt(line[4]));
            planets.add(planet);
        }

        return planets;
    }

    public static Cartesians equatorialToCartesians(float rightAscension, float declination, float distance){
        double rightAscensionRadians = rightAscension * (Math.PI / 180);
        double declinationRadians = declination * (Math.PI / 180);

        double x = distance * Math.cos(rightAscensionRadians) * Math.cos(declinationRadians);
        double y = distance * Math.sin(rightAscensionRadians) * Math.cos(declinationRadians);
        double z = distance * Math.sin(declinationRadians);

        return new Cartesians(round(x), round(y), round(z));
    }

    public static double round(double input){
        return Math.round(input * 100.0) / 100.0;
    }

}
