package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class GlyphPlatform {
    private int numGlyphPlatformServos = 3;
    // 0 = Left
    // 1 = Right
    // 2 = Gripper
    public Servo glyphPlatformServos[] = new Servo[numGlyphPlatformServos];

    public GlyphPlatform(HardwareMap glyphMap, String servoNames[]){
        for(int i = 0; i < numGlyphPlatformServos; i++){
            glyphPlatformServos[i] = glyphMap.servo.get(servoNames[i]);
        }
    }

    public void zero(){
        glyphPlatformServos[0].setPosition(0.5);
        glyphPlatformServos[1].setPosition(0.5);
    }

    public void up(){
        glyphPlatformServos[0].setPosition(0.0);
        glyphPlatformServos[1].setPosition(1.0);
    }

    public void down(){
        glyphPlatformServos[0].setPosition(1.0);
        glyphPlatformServos[1].setPosition(0.0);
    }

    public void gripRelease(){
        glyphPlatformServos[2].setPosition(0.5);
    }

    public void gripOpen(){
        glyphPlatformServos[2].setPosition(1.0);
    }

    public void gripClose(){
        glyphPlatformServos[2].setPosition(0.0);
    }
}
