package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Reicher Robotics on 1/14/2018.
 */

public class GlyphDetection {

    public enum GlyphColor {
        GRAY,
        BROWN,
        UNKNOWN;
    }

    public int FIRST = 0;
    public int SECOND = 1;

    private int numPlatformGlyphColorSensors = 2;
    private int numPlatformGlyphDistanceSensors = 2;
    // 0 = Second
    public ColorSensor platformColorSensors[] = new ColorSensor[numPlatformGlyphColorSensors];
    // 0 = Second
    public DistanceSensor platformDistanceSensors[] = new DistanceSensor[numPlatformGlyphDistanceSensors];

    private GlyphColor glyphColor = GlyphColor.UNKNOWN;

    public GlyphDetection(HardwareMap glyphMap, String sensorNames[]) {
        for(int i = 0; i < numPlatformGlyphColorSensors; i++){
            platformColorSensors[i] = glyphMap.colorSensor.get(sensorNames[i]);
            platformDistanceSensors[i] = glyphMap.get(DistanceSensor.class, sensorNames[i]);
        }
    }

    public GlyphColor getGlyphColor() {
        return glyphColor;
    }

    public void setGlyphColor(int glyph) {
        if(platformColorSensors[glyph].red() > platformColorSensors[glyph].blue()){
            glyphColor = glyphColor.GRAY;
        } else if(platformColorSensors[glyph].blue() > platformColorSensors[glyph].red()){
            glyphColor = glyphColor.BROWN;
        } else {
            glyphColor = glyphColor.UNKNOWN;
        }
    }

    public void setGlyphColor(GlyphColor _glyphColor) {
        glyphColor = _glyphColor;
    }

    public void setGlyphColor(int redHue, int blueHue) {
        if(redHue > blueHue){
            glyphColor = GlyphColor.GRAY;
        } else if(blueHue > redHue){
            glyphColor = GlyphColor.BROWN;
        } else {
            glyphColor = GlyphColor.UNKNOWN;
        }
    }

    public boolean glyphFound(int glyph){
        if(Double.isNaN(platformDistanceSensors[glyph].getDistance(DistanceUnit.INCH)))
            return false;
        return true;
    }
}
