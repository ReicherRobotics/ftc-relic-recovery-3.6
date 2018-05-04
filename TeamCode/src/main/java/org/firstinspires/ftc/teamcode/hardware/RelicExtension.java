package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class RelicExtension {

    private int TICKS_PER_REV = 1120;
    private double MAX_EXTENSION_REVS = 10.5;
    private double MIN_EXTENSION_REVS = .5;
    public int MAX_EXTENSION_LIMIT = (int) (MAX_EXTENSION_REVS * TICKS_PER_REV);
    public int MIN_EXTENSION_LIMIT = (int) (MIN_EXTENSION_REVS * TICKS_PER_REV);

    private int numRelicExtMotors = 1;
    // 0 = Extension
    public DcMotor relicExtMotors[] = new DcMotor[numRelicExtMotors];

    public RelicExtension(HardwareMap relicMap, String motorNames[], DcMotorSimple.Direction motorDirections[]){
        for(int i = 0; i < numRelicExtMotors; i++){
            relicExtMotors[i] = relicMap.dcMotor.get(motorNames[i]);
            relicExtMotors[i].setDirection(motorDirections[i]);
            relicExtMotors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            relicExtMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public boolean isMotorBusy(){
        boolean isBusy = false;
        for (DcMotor _motor : relicExtMotors){
            if(_motor.isBusy()){
                isBusy = true;
            }
        }
        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode){
        for (DcMotor _motor : relicExtMotors){
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior behavior){
        for (DcMotor _motor : relicExtMotors){
            _motor.setZeroPowerBehavior(behavior);
        }
    }

    public void stop(){
        relicExtMotors[0].setPower(0.0);
    }

    public void extend(){
        relicExtMotors[0].setPower(1.0);
    }

    public void extend(double power){
        relicExtMotors[0].setPower(power);
    }

    public void retract(){
        relicExtMotors[0].setPower(-1.0);
    }

    public void retract(double power){
        relicExtMotors[0].setPower(-power);
    }

    public int getExtensionPosition(){
        return relicExtMotors[0].getCurrentPosition();
    }

    public void extendRetractWithLimits(double power){
        if(power > 0.0 && getExtensionPosition() < MAX_EXTENSION_LIMIT){
            extend(power);
        } else if(power < 0.0 && getExtensionPosition() > MIN_EXTENSION_LIMIT){
            retract(-power);
        } else {
            stop();
        }
    }
}
