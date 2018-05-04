package org.firstinspires.ftc.teamcode.opmodes.auto.commands;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 3/25/2018.
 */

public class AutoComGyroTurn extends AutoCommandMain {
    private double maxSpeed = 0.0;
    private double targetHeading = 0.0;
    private double tolerance = 2.5;

    public AutoComGyroTurn(RelicOpModes opMode, double speed, double angle){
        super(opMode);

        this.maxSpeed = speed;
        this.targetHeading = angle;
    }

    @Override
    public void Start(){

    }

    @Override
    public void Loop(){

    }

    @Override
    public void Stop(){

    }

    @Override
    public boolean IsTaskRunning(){
        return !onHeading((int)bot.gyroIMU.getHeading()) && !opMode.isStopRequested();
    }

    private boolean onHeading(int currentHeading) {
        boolean onTarget = false;
        double steer;
        double leftSpeed;
        double rightSpeed;
        double finalError;

        if (Math.abs(targetHeading - currentHeading) <= tolerance) {
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        } else {
            finalError = bot.gyroIMU.getError(targetHeading, currentHeading) / 150;
            if (finalError > 0.0) {
                steer = Range.clip(finalError, 0.01, 1.0);
            } else {
                steer = Range.clip(finalError, -1.0, -0.01);
            }
            rightSpeed = maxSpeed * steer;
            leftSpeed = -rightSpeed;
        }
        bot.driveMecanum.setDrivePowers(leftSpeed, leftSpeed, rightSpeed, rightSpeed);
        return onTarget;
    }
}
