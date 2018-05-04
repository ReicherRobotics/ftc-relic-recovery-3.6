package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class AlignmentBar {
    private int numAlignBarServos = 1;
    // 0 = Bar
    public Servo alignBarServos[] = new Servo[numAlignBarServos];

    private int numAlignDigitalSensors = 2;
    // 0 = Side
    // 1 = Back
    public DigitalChannel alignDigitalSensors[] = new DigitalChannel[numAlignDigitalSensors];

    public AlignmentBar(HardwareMap alignmentMap, String servoNames[], String digitalNames[], DigitalChannel.Mode digitalModes[]){
        for(int i = 0; i < numAlignBarServos; i++){
            alignBarServos[i] = alignmentMap.servo.get(servoNames[i]);
        }
        for(int i = 0; i < numAlignDigitalSensors; i++){
            alignDigitalSensors[i] = alignmentMap.digitalChannel.get(digitalNames[i]);
            alignDigitalSensors[i].setMode(digitalModes[i]);
        }
    }

    public void zero(){
        alignBarServos[0].setPosition(0.5);
    }

    public void up(){
        alignBarServos[0].setPosition(1.0);
    }

    public void down(){
        alignBarServos[0].setPosition(0.0);
    }

    public boolean isAlignedSide(){
        return !alignDigitalSensors[0].getState();
    }

    public boolean isAlignedBack(){
        return !alignDigitalSensors[1].getState();
    }
}
