package org.firstinspires.ftc.teamcode.hardware;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.GlyphDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class GlyphCamera {
    public GlyphDetector glyphDetector;
    public double answer = 65.0;
    public double tolerance = 3.0;

    public void Init(HardwareMap sensorMap) {
        glyphDetector = new GlyphDetector();
        glyphDetector.init(sensorMap.appContext, CameraViewDisplay.getInstance());
        glyphDetector.minScore = 1;
        glyphDetector.downScaleFactor = 0.3;
        glyphDetector.speed = GlyphDetector.GlyphDetectionSpeed.SLOW;
        glyphDetector.enable();
    }

    public double getGlyphX() {
        return glyphDetector.getChosenGlyphPosition().y;
    }

    public double getGlyphY() {
        return glyphDetector.getChosenGlyphPosition().x;
    }

    public DriveMecanum.DriveDirection alignWithGlyphDirection(double x, double y){
        double solution = y - 25 * x / 16;
        double solutionError = (answer - solution) / answer;
        if(Math.abs(solutionError) > tolerance) {
            if (solutionError < 0.0) {
                return DriveMecanum.DriveDirection.LEFT;
            }
            if (solutionError > 0.0) {
                return DriveMecanum.DriveDirection.RIGHT;
            }
        }
        return DriveMecanum.DriveDirection.FORWARD;
    }
}
