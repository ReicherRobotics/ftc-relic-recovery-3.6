package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Reicher Robotics on 2/22/2018.
 */

public class DriveMecanum {

    public enum DriveDirection {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        FORWARDRIGHT,
        FORWARDLEFT,
        BACKWARDRIGHT,
        BACKWARDLEFT,
        UNKNOWN;
    }

    private int numDriveMecMotors = 4;
    // 0 = Left Front
    // 1 = Left Rear
    // 2 = Right Front
    // 3 = Right Rear
    public DcMotor driveMecMotors[] = new DcMotor[numDriveMecMotors];

    public DriveMecanum(HardwareMap driveMap, String motorNames[], DcMotorSimple.Direction motorDirections[]){
        for(int i = 0; i < numDriveMecMotors; i++){
            driveMecMotors[i] = driveMap.dcMotor.get(motorNames[i]);
            driveMecMotors[i].setDirection(motorDirections[i]);
        }
    }

    public boolean isMotorBusy(){
        boolean isBusy = false;
        for (DcMotor _motor : driveMecMotors){
            if(_motor.isBusy()){
                isBusy = true;
            }
        }
        return isBusy;
    }

    public boolean is4MotorsBusy(){
        boolean isBusy = false;
        if(driveMecMotors[0].isBusy() && driveMecMotors[1].isBusy() && driveMecMotors[2].isBusy() && driveMecMotors[3].isBusy()){
                isBusy = true;
        }
        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode){
        for (DcMotor _motor : driveMecMotors){
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode){
        for (DcMotor _motor : driveMecMotors){
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setDrivePowers(double leftFrontPower, double leftRearPower, double rightFrontPower, double rightRearPower){
        driveMecMotors[0].setPower(leftFrontPower);
        driveMecMotors[1].setPower(leftRearPower);
        driveMecMotors[2].setPower(rightFrontPower);
        driveMecMotors[3].setPower(rightRearPower);
    }

    public void driveForward(double forwardPower){
        setDrivePowers(forwardPower, forwardPower, forwardPower, forwardPower);
    }

    public void driveBackward(double backwardPower){
        driveForward(-backwardPower);
    }

    public void driveLeft(double strafePower){
        setDrivePowers(-strafePower, strafePower, strafePower, -strafePower);
    }

    public void driveRight(double strafePower){
        driveLeft(-strafePower);
    }

    public void turnLeft(double turnPower){
        setDrivePowers(-turnPower, -turnPower, turnPower, turnPower);
    }

    public void turnRight(double turnPower){
        setDrivePowers(turnPower, turnPower, -turnPower, -turnPower);
    }

    public void driveStop(){
        driveForward(0.0);
    }

    public void setDriveTargets(int leftFrontTarget, int leftRearTarget, int rightFrontTarget, int rightRearTarget){
        if(leftFrontTarget != 0) {
            driveMecMotors[0].setTargetPosition(leftFrontTarget);
        }
        if(leftRearTarget != 0) {
            driveMecMotors[1].setTargetPosition(leftRearTarget);
        }
        if(rightFrontTarget != 0) {
            driveMecMotors[2].setTargetPosition(rightFrontTarget);
        }
        if(rightRearTarget != 0) {
            driveMecMotors[3].setTargetPosition(rightRearTarget);
        }
    }

    public void setForwardTarget(int forwardTarget){
        setDriveTargets(forwardTarget, forwardTarget, forwardTarget, forwardTarget);
    }

    public void setBackwardTarget(int backwardTarget){
        setForwardTarget(-backwardTarget);
    }

    public void setLeftTarget(int strafeTarget){
        setDriveTargets(-strafeTarget, strafeTarget, strafeTarget, -strafeTarget);
    }

    public void setRightTarget(int strafeTarget){
        driveLeft(-strafeTarget);
    }

    public void setTurnLeftTarget(int turnTarget){
        setDriveTargets(-turnTarget, -turnTarget, turnTarget, turnTarget);
    }

    public void setTurnRightTarget(int turnTarget){
        setTurnLeftTarget(-turnTarget);
    }

    public void mecanumDrive(double maxDrivePower, double drive, double strafe, double turn){
        double leftFrontPower = Range.clip(drive + strafe + turn, -maxDrivePower, maxDrivePower);
        double leftRearPower = Range.clip(drive + strafe - turn, -maxDrivePower, maxDrivePower);
        double rightFrontPower = Range.clip(drive - strafe - turn, -maxDrivePower, maxDrivePower);
        double rightRearPower = Range.clip(drive - strafe + turn, -maxDrivePower, maxDrivePower);

        setDrivePowers(leftFrontPower, leftRearPower, rightFrontPower, rightRearPower);
    }

    public void directionDrive(DriveDirection direction, double speed){
        switch(direction){
            case FORWARD:
                setDrivePowers(speed, speed, speed, speed);
                break;
            case BACKWARD:
                setDrivePowers(-speed, -speed, -speed, -speed);
                break;
            case LEFT:
                setDrivePowers(-speed, speed, speed, -speed);
                break;
            case RIGHT:
                setDrivePowers(speed, -speed, -speed, speed);
                break;
            case FORWARDLEFT:
                setDrivePowers(0, speed, speed, 0);
                break;
            case FORWARDRIGHT:
                setDrivePowers(speed, 0, 0, speed);
                break;
            case BACKWARDLEFT:
                setDrivePowers(0, -speed, -speed, 0);
                break;
            case BACKWARDRIGHT:
                setDrivePowers(-speed, 0, 0, -speed);
                break;
            default:
                setDrivePowers(speed, speed, speed, speed);
                break;
        }
    }
}
