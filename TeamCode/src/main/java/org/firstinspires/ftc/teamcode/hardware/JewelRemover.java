package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class JewelRemover {
    private int numJewelRemoverServos = 2;
    // 0 = Extension
    // 1 = Hitter
    public Servo jewelRemoverServos[] = new Servo[numJewelRemoverServos];

    public JewelRemover(HardwareMap jewelMap, String servoNames[]){
        for(int i = 0; i < numJewelRemoverServos; i++){
            jewelRemoverServos[i] = jewelMap.servo.get(servoNames[i]);
        }
    }

    public void extensionZero(){
        jewelRemoverServos[0].setPosition(0.5);
    }

    public void extensionUp(){
        jewelRemoverServos[0].setPosition(0.0);
    }

    public void extensionDown(){
        jewelRemoverServos[0].setPosition(1.0);
    }

    public void hitterZero(){
        jewelRemoverServos[1].setPosition(0.5);
    }

    public void hitLeft(){
        jewelRemoverServos[1].setPosition(1.0);
    }

    public void hitRight(){
        jewelRemoverServos[1].setPosition(0.0);
    }
}
