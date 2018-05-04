package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class PhoneRotator {
    private int numPhoneRotatorServos = 1;
    // 0 = Rotator
    public Servo phoneRotatorServos[] = new Servo[numPhoneRotatorServos];

    public PhoneRotator(HardwareMap phoneMap, String servoNames[]){
        for(int i = 0; i < numPhoneRotatorServos; i++){
            phoneRotatorServos[i] = phoneMap.servo.get(servoNames[i]);
        }
    }

    public void front(){
        phoneRotatorServos[0].setPosition(0.5);
    }

    public void tooFar(){
        phoneRotatorServos[0].setPosition(1.0);
    }

    public void side(){
        phoneRotatorServos[0].setPosition(0.0);
    }
}
