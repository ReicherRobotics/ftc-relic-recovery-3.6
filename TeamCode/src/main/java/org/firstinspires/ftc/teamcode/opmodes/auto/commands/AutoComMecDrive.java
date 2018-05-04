package org.firstinspires.ftc.teamcode.opmodes.auto.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 3/25/2018.
 */

public class AutoComMecDrive extends AutoCommandMain {
    private DriveMecanum.DriveDirection direction = DriveMecanum.DriveDirection.FORWARD;
    private double speed = 0.0;
    private double speedIncrement = 0.0;
    private double minSpeed = 0.05;
    private double maxSpeed = 0.0;
    private double distance = 0.0;
    private int targetTicks = 0;
    private int rampTargetTicks = 0;
    private int maxRampTargetTicks = (int) bot.TICKS_PER_REV;

    private int currentTick = 0;

    public AutoComMecDrive(RelicOpModes opMode, DriveMecanum.DriveDirection direction, double speed, double distance){
        super(opMode);

        this.direction = direction;
        maxSpeed = speed;
        this.distance = distance;
    }

    @Override
    public void Start(){
        targetTicks = bot.convertInchToTicks(distance);
        rampTargetTicks = Range.clip(targetTicks / 2, 0, maxRampTargetTicks);
        speedIncrement = maxSpeed / maxRampTargetTicks;

        switch(direction){
            case FORWARD:
                bot.driveMecanum.setDriveTargets(targetTicks, targetTicks, targetTicks, targetTicks);
                break;
            case BACKWARD:
                bot.driveMecanum.setDriveTargets(-targetTicks, -targetTicks, -targetTicks, -targetTicks);
                break;
            case LEFT:
                bot.driveMecanum.setDriveTargets(-targetTicks, targetTicks, targetTicks, -targetTicks);
                break;
            case RIGHT:
                bot.driveMecanum.setDriveTargets(targetTicks, -targetTicks, -targetTicks, targetTicks);
                break;
            case FORWARDLEFT:
                bot.driveMecanum.setDriveTargets(0, targetTicks, targetTicks, 0);
                break;
            case FORWARDRIGHT:
                bot.driveMecanum.setDriveTargets(targetTicks, 0, 0, targetTicks);
                break;
            case BACKWARDLEFT:
                bot.driveMecanum.setDriveTargets(-targetTicks, 0, 0, -targetTicks);
                break;
            case BACKWARDRIGHT:
                bot.driveMecanum.setDriveTargets(0, -targetTicks, -targetTicks, 0);
                break;
            default:
                bot.driveMecanum.setDriveTargets(targetTicks, targetTicks, targetTicks, targetTicks);
                break;
        }

        bot.driveMecanum.setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);
        bot.driveMecanum.setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.driveMecanum.setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void Loop(){
        currentTick = Math.abs(bot.driveMecanum.driveMecMotors[0].getCurrentPosition());
        if(currentTick <= rampTargetTicks){
            speed = currentTick * speedIncrement;
        } else if(currentTick >= (targetTicks - rampTargetTicks)){
            speed = (targetTicks - currentTick) * speedIncrement;
        } else {
            speed = maxSpeed;
        }
        speed = Range.clip(speed, minSpeed, maxSpeed);
        switch(direction){
            case FORWARD:
                bot.driveMecanum.setDrivePowers(speed, speed, speed, speed);
                break;
            case BACKWARD:
                bot.driveMecanum.setDrivePowers(-speed, -speed, -speed, -speed);
                break;
            case LEFT:
                bot.driveMecanum.setDrivePowers(-speed, speed, speed, -speed);
                break;
            case RIGHT:
                bot.driveMecanum.setDrivePowers(speed, -speed, -speed, speed);
                break;
            case FORWARDLEFT:
                bot.driveMecanum.setDrivePowers(0, speed*1.5, speed, 0);
                break;
            case FORWARDRIGHT:
                bot.driveMecanum.setDrivePowers(speed, 0, 0, speed*1.5);
                break;
            case BACKWARDLEFT:
                bot.driveMecanum.setDrivePowers(-speed, 0, 0, -speed*1.5);
                break;
            case BACKWARDRIGHT:
                bot.driveMecanum.setDrivePowers(0, -speed*1.5, -speed, 0);
                break;
            default:
                bot.driveMecanum.setDrivePowers(speed, speed, speed, speed);
                break;
        }
        //opMode.telemetry.addData("Speed: ", speed);
        //opMode.telemetry.update();
    }

    @Override
    public void Stop(){
        bot.driveMecanum.driveStop();
        bot.driveMecanum.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public boolean IsTaskRunning(){
        return bot.driveMecanum.is4MotorsBusy();
    }
}