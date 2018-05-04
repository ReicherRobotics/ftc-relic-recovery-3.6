package org.firstinspires.ftc.teamcode.opmodes.auto.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 3/25/2018.
 */

public class AutoComUltraDrive extends AutoCommandMain {
    private DriveMecanum.DriveDirection direction = DriveMecanum.DriveDirection.FORWARD;
    private double maxSpeed = 0.0;
    private double targetDistance = 0.0;

    public AutoComUltraDrive(RelicOpModes opMode, DriveMecanum.DriveDirection direction, double speed, double distance){
        super(opMode);

        this.direction = direction;
        this.maxSpeed = speed;
        this.targetDistance = distance;
    }

    @Override
    public void Start(){
        bot.driveMecanum.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void Loop(){
        opMode.telemetry.addData("Distance: ", bot.rangeLocator.getBackDistance());
        opMode.telemetry.update();
    }

    @Override
    public void Stop(){
        bot.driveMecanum.driveStop();
    }

    @Override
    public boolean IsTaskRunning(){
        return !onDistance(bot.rangeLocator.getBackDistance()) && !opMode.isStopRequested();
    }

    private boolean onDistance(double currentDistance) {
        boolean onTarget = false;
        double steer;
        double speed;
        double errorDistance = currentDistance - targetDistance;
        double errorPercent = errorDistance / targetDistance;

        if (Math.abs(errorDistance) <= bot.PID_THRESH) {
            speed  = 0.0;
            onTarget = true;
        } else {
            if (errorDistance > 0.0) {
                steer = Range.clip(errorPercent, 0.05, 0.8);
            } else {
                steer = Range.clip(errorPercent, -0.8, -0.05);
            }
            speed = maxSpeed * steer;
        }
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
        return onTarget;
    }
}
