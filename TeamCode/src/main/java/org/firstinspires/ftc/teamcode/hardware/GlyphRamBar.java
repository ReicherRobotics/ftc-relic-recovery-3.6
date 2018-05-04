package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class GlyphRamBar {

    public enum RamPosition {
        UP,
        PERPENDICULAR,
        RAM,
        UNKNOWN;
    }

    private final static double MIN_RAM_ROTATION_LIMIT = 0.6;
    private final static double MAX_RAM_ROTATION_LIMIT = 2.5;
    private final static double MIN_PERPENDICULAR_LIMIT = 1.05;
    private final static double MAX_PERPENDICULAR_LIMIT = 1.25;
    private final static double MIN_RAM_LIMIT = 2.0;
    private final static double MAX_RAM_LIMIT = 2.2;

    private int numGlyphRamBarCRServos = 1;
    // 0 = Ram Bar Rotator
    public CRServo glyphRamBarCRServos[] = new CRServo[numGlyphRamBarCRServos];

    private int numGlyphRamAnalogSensors = 1;
    // 0 = Ram Bar Angle
    public AnalogInput glyphRamAnalogSensors[] = new AnalogInput[numGlyphRamAnalogSensors];

    public GlyphRamBar(HardwareMap glyphMap, String crServoNames[], CRServo.Direction crServoDirections[], String analogNames[]){
        for(int i = 0; i < numGlyphRamBarCRServos; i++){
            glyphRamBarCRServos[i] = glyphMap.crservo.get(crServoNames[i]);
            glyphRamBarCRServos[i].setDirection(crServoDirections[i]);
        }
        for(int i = 0; i < numGlyphRamAnalogSensors; i++){
            glyphRamAnalogSensors[i] = glyphMap.analogInput.get(analogNames[i]);
        }
    }

    public double getRamPosition() {
        return glyphRamAnalogSensors[0].getVoltage();
    }

    public void stop(){
        glyphRamBarCRServos[0].setPower(0.0);
    }

    public void up(){
        if(getRamPosition() > MIN_RAM_ROTATION_LIMIT) {
            glyphRamBarCRServos[0].setPower(1.0);
        } else {
            stop();
        }
    }

    public void down(){
        if(getRamPosition() < MAX_RAM_ROTATION_LIMIT) {
            glyphRamBarCRServos[0].setPower(-1.0);
        } else {
            stop();
        }
    }

    public void toPerpendicular() {
        if (getRamPosition() > MAX_PERPENDICULAR_LIMIT) {
            up();
        } else if (getRamPosition() < MIN_PERPENDICULAR_LIMIT) {
            down();
        } else {
            stop();
        }
    }

    public void toRam() {
        if (getRamPosition() > MAX_RAM_LIMIT) {
            up();
        } else if (getRamPosition() < MIN_RAM_LIMIT) {
            down();
        } else {
            stop();
        }
    }
}
