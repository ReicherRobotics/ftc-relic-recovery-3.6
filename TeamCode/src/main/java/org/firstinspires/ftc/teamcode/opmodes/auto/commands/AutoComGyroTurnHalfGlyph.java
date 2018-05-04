package org.firstinspires.ftc.teamcode.opmodes.auto.commands;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 3/25/2018.
 */

public class AutoComGyroTurnHalfGlyph extends AutoCommandMain {
    private double maxSpeed = 0.0;
    int glyph  = 0;
    private DriveMecanum.DriveDirection direction = DriveMecanum.DriveDirection.FORWARD;
    private double targetHeading = 0.0;
    private double tolerance = 2.5;

    public AutoComGyroTurnHalfGlyph(RelicOpModes opMode, DriveMecanum.DriveDirection direction, double speed, double angle, int glyph){
        super(opMode);

        this.maxSpeed = speed;
        this.direction = direction;
        this.targetHeading = angle;
        this.glyph = glyph;
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
        return !onHeading((int)bot.gyroIMU.getHeading()) && !opMode.isStopRequested() && !bot.glyphDetection.glyphFound(glyph);
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
            finalError = bot.gyroIMU.getError(targetHeading, currentHeading) / 90;
            if (finalError > 0.0) {
                steer = Range.clip(finalError, 0.01, 1.0);
            } else {
                steer = Range.clip(finalError, -1.0, -0.01);
            }
            rightSpeed = maxSpeed * steer;
            leftSpeed = -rightSpeed;
        }
        switch(direction){
            case FORWARD:
                rightSpeed = Range.clip(rightSpeed, 0.01, 1.0);
                leftSpeed = Range.clip(leftSpeed, 0.01, 1.0);
                break;
            case BACKWARD:
                rightSpeed = Range.clip(rightSpeed, -1.0, 0.01);
                leftSpeed = Range.clip(leftSpeed, -1.0, 0.01);
                break;
            default:
                rightSpeed = Range.clip(rightSpeed, 0.01, 1.0);
                leftSpeed = Range.clip(leftSpeed, 0.01, 1.0);
                break;
        }
        bot.driveMecanum.setDrivePowers(leftSpeed, leftSpeed, rightSpeed, rightSpeed);
        return onTarget;
    }
}
