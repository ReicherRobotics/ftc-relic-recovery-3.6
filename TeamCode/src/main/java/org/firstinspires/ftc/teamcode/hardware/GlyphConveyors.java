package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class GlyphConveyors {
    private int numGlyphConveyorMotors = 2;
    // 0 = Left
    // 1 = Right
    public DcMotor glyphConveyorMotors[] = new DcMotor[numGlyphConveyorMotors];

    public GlyphConveyors(HardwareMap glyphMap, String motorNames[], DcMotorSimple.Direction motorDirections[]){
        for(int i = 0; i < numGlyphConveyorMotors; i++){
            glyphConveyorMotors[i] = glyphMap.dcMotor.get(motorNames[i]);
            glyphConveyorMotors[i].setDirection(motorDirections[i]);
        }
    }

    public boolean isMotorBusy(){
        boolean isBusy = false;
        for (DcMotor _motor : glyphConveyorMotors){
            if(_motor.isBusy()){
                isBusy = true;
            }
        }
        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode){
        for (DcMotor _motor : glyphConveyorMotors){
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior behavior){
        for (DcMotor _motor : glyphConveyorMotors){
            _motor.setZeroPowerBehavior(behavior);
        }
    }

    public void setPowers(double leftPower, double rightPower){
        glyphConveyorMotors[0].setPower(leftPower);
        glyphConveyorMotors[1].setPower(rightPower);
    }

    public void stop(){
        setPowers(0.0, 0.0);
    }

    public void in(){
        setPowers(1.0, 1.0);
    }

    public void out(){
        setPowers(-1.0, -1.0);
    }
}
