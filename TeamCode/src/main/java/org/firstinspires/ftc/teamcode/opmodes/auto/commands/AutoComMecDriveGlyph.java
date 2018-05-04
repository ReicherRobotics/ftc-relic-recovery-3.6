package org.firstinspires.ftc.teamcode.opmodes.auto.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 3/25/2018.
 */

public class AutoComMecDriveGlyph extends AutoCommandMain {
    private DriveMecanum.DriveDirection direction = DriveMecanum.DriveDirection.FORWARD;
    private double speed = 0.0;
    private int glyph = 0;
    private double speedIncrement = 0.0;
    private double minSpeed = 0.05;
    private double maxSpeed = 0.0;
    private double distance = 0.0;
    private int targetTicks = 0;
    private int rampTargetTicks = 0;
    private int maxRampTargetTicks = (int) bot.TICKS_PER_REV;

    private int currentTick = 0;

    public AutoComMecDriveGlyph(RelicOpModes opMode, DriveMecanum.DriveDirection direction, double speed, double distance, int glyph){
        super(opMode);

        this.direction = direction;
        maxSpeed = speed;
        this.glyph = glyph;
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
            default:
                bot.driveMecanum.setDriveTargets(targetTicks, targetTicks, targetTicks, targetTicks);
                break;
        }

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
        return bot.driveMecanum.is4MotorsBusy() && !bot.glyphDetection.glyphFound(glyph);
    }
}