package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class RelicGrabber {

    public enum WristPosition {
        UP,
        RELIC,
        DOWN,
        UNKNOWN;
    }

    private int numRelicGrabServos = 2;
    // 0 = Wrist
    // 1 = Claw
    public Servo relicGrabServos[] = new Servo[numRelicGrabServos];

    public RelicGrabber(HardwareMap relicMap, String servoNames[]){
        for(int i = 0; i < numRelicGrabServos; i++){
            relicGrabServos[i] = relicMap.servo.get(servoNames[i]);
        }
    }

    public void wristRelic(){
        relicGrabServos[0].setPosition(0.5);
    }

    public void wristUp(){
        relicGrabServos[0].setPosition(0.0);
    }

    public void wristDown(){
        relicGrabServos[0].setPosition(1.0);
    }

    public void clawZero(){
        relicGrabServos[1].setPosition(0.5);
    }

    public void clawOpen(){
        relicGrabServos[1].setPosition(0.0);
    }

    public void clawClose(){
        relicGrabServos[1].setPosition(1.0);
    }
}
