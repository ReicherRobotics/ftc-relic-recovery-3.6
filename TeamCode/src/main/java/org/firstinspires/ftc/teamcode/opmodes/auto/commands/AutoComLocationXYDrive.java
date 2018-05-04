package org.firstinspires.ftc.teamcode.opmodes.auto.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.RangeLocator;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 3/25/2018.
 */

public class AutoComLocationXYDrive extends AutoCommandMain {
    private double maxSpeed = 0.0;
    private double tolerance = 2.0;
    private double currentX = 0.0;
    private double targetX = 0.0;
    private RangeLocator.RangeLocation locationX;
    private double currentY = 0.0;
    private double targetY = 0.0;
    private RangeLocator.RangeLocation locationY;
    private double zeroX = 0.0;
    private double zeroY = 0.0;
    private double totalX = 0.0;
    private double totalY = 0.0;

    public AutoComLocationXYDrive(RelicOpModes opMode, double speed, double x, double y, RangeLocator.RangeLocation locationX, RangeLocator.RangeLocation locationY){
        super(opMode);

        this.maxSpeed = speed;
        this.targetX = x;
        this.targetY = y;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    @Override
    public void Start(){
        bot.driveMecanum.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
        zeroX = bot.rangeLocator.getDistance(locationX);
        zeroY = bot.rangeLocator.getDistance(locationY);
        totalX = targetX - zeroX;
        totalY = targetY - zeroY;
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
        return !onDistance() && !opMode.isStopRequested();
    }

    private boolean onDistance() {
        double steer;
        double speed;
        double errorX = targetX - currentX;
        double errorY = targetY - currentY;

        if (Math.abs(errorX) <= tolerance) {
            if (Math.abs(errorY) <= tolerance) {
                return true;
            } else {
                steer = errorY / totalY;
                speed = maxSpeed * steer;
                bot.driveMecanum.setDrivePowers(speed, speed, speed, speed);
            }
        }
        else{
                steer = errorX / totalX;
                speed = maxSpeed * steer;
                bot.driveMecanum.setDrivePowers(-speed, speed, speed, -speed);
        }
        return false;
    }
}
