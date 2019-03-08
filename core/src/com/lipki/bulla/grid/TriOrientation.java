package com.lipki.bulla.grid;

public class TriOrientation {

    final double f0, f1, f2, f3;
    final double b0, b1, b2, b3;
    final double start_angle; // in multiples of 60°
    final String name;

    public static final TriOrientation POINTY = new TriOrientation(
    		Math.sqrt(3)/2f,  1/2f, Math.sqrt(3)/12f, 0,
    		Math.sqrt(3)/3f, -1/3f,                0, 2/3f,
            0, "POINTY");
    public static final TriOrientation FLAT = new TriOrientation(
    		1/2f, -Math.sqrt(3)/2f,    0 , -Math.sqrt(3)/12f,
    		2/3f,               0 , -1/3f,  Math.sqrt(3)/3f,
            0.25, "FLAT");

    private TriOrientation( double f0, double f1, double f2, double f3,
                        double b0, double b1, double b2, double b3,
                        double start_angle, String name ) {

        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.start_angle = start_angle;
        this.name = name;

    }

    @Override
    public String toString() {
        return name;
    }

}
