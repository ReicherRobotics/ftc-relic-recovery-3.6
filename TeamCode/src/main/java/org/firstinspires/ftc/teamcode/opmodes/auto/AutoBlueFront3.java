package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.GlyphCamera;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.RangeLocator;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurn;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurnGlyph;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDrive;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveGlyph;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Blue Front 3", group="Main 3")
@Disabled
public class AutoBlueFront3 extends RelicOpModes {

    public VuforiaHardware vuforiaHw;
    public GlyphCamera glyphCam;
    public RelicRecoveryVuMark pictograph;
    public JewelDetection.JewelColor jewelColor;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        bot.jewelRemover.extensionUp();
        bot.jewelRemover.hitRight();
        bot.glyphPlatform.gripRelease();
        bot.glyphPlatform.down();

        vuforiaHw = new VuforiaHardware();
        vuforiaHw.Init(this.hardwareMap);

        waitForStart();
        bot.phoneRotator.side();
        bot.glyphPlatform.gripClose();
        timer.reset();
        while (vuforiaHw.getVuMark() == RelicRecoveryVuMark.UNKNOWN && timer.time() < 3.0) {
            vuforiaHw.Loop();
            bot.glyphRamBar.toRam();
        }
        bot.glyphRamBar.stop();
        pictograph = vuforiaHw.getVuMark();
        vuforiaHw.Stop();
        telemetry.addData("Pictograph: ", pictograph);
        telemetry.update();

        bot.jewelRemover.hitterZero();
        bot.jewelRemover.extensionDown();
        bot.phoneRotator.front();
        waitRam(0.6);
        bot.jewelDetection.setJewelColor();
        jewelColor = bot.jewelDetection.getJewelColor();
        telemetry.addData("Pictograph: ", pictograph);
        telemetry.addData("Jewel", jewelColor);
        telemetry.update();
        if(jewelColor == JewelDetection.JewelColor.BLUE){
            bot.jewelRemover.hitLeft();
        } else if(jewelColor == JewelDetection.JewelColor.RED) {
            bot.jewelRemover.hitRight();
        }
        waitRam(0.4);
        bot.jewelRemover.extensionUp();
        bot.jewelRemover.hitterZero();

        switch(pictograph){
            case RIGHT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 30.0).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.5, 45.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 18.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 22.5).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 45.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            case LEFT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 35.0).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDLEFT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDRIGHT, 0.6, 1.0).Run();
                break;
            default:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 35.0).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDLEFT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDRIGHT, 0.6, 1.0).Run();
                break;
        }
        new AutoComGyroTurn(this,1.0, 90.0).Run();
        bot.glyphPlatform.down();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.8, 6.0).Run();

        switch(pictograph){
            case LEFT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.6, 5.5).Run();
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.6, 15.5).Run();
                break;
            case RIGHT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.6, 8.0).Run();
                break;
            default:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.6, 5.5).Run();
                break;
        }
        bot.phoneRotator.side();
        bot.glyphConveyors.in();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 27.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 75.0, 1).Run();
        new AutoComGyroTurnGlyph(this,0.5, 90.0, 1).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 7.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 105.0, 1).Run();
        new AutoComGyroTurnGlyph(this,0.5, 90.0, 1).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 7.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 75.0, 1).Run();
        new AutoComGyroTurn(this,0.5, 90.0).Run();
        bot.glyphConveyors.stop();

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 16.0).Run();

        bot.glyphConveyors.in();

        double rightDistance = bot.rangeLocator.getAvgDistance(RangeLocator.RangeLocation.RIGHT);
        telemetry.addData("Right Distance: ", rightDistance);
        telemetry.update();
        if(rightDistance < 17.0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 1.0, 12.0).Run();
        } else if(rightDistance < 47.0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 1.0, 47.0 - rightDistance).Run();
        }

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 14.0).Run();

        double backDistance = bot.rangeLocator.getAvgDistance(RangeLocator.RangeLocation.BACK) - 17.0;
        telemetry.addData("Right Distance: ", rightDistance);
        telemetry.addData("Back Distance: ", backDistance);
        telemetry.update();
        if(backDistance > 0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.3, backDistance).Run();
        } else {
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.3, backDistance).Run();
        }

        bot.glyphConveyors.stop();
        bot.glyphPlatform.gripClose();
        bot.glyphPlatform.up();
        switch(pictograph){
            case LEFT:
                new AutoComGyroTurn(this,0.5, 78.0).Run();
                break;
            case CENTER:
                new AutoComGyroTurn(this,0.5, 112.0).Run();
                break;
            case RIGHT:
                new AutoComGyroTurn(this,0.5, 112.0).Run();
                break;
            default:
                new AutoComGyroTurn(this,0.5, 112.0).Run();
                break;
        }
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 10.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.2);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.3, 4.0).Run();
        bot.glyphPlatform.down();
        new AutoComGyroTurn(this,0.5, 90.0).Run();
    }

    ElapsedTime waitTime = new ElapsedTime();
    public void wait(double time) {
        waitTime.reset();
        while (waitTime.time() < time && this.opModeIsActive()) {
        }
    }

    public void waitRam(double time) {
        waitTime.reset();
        while (waitTime.time() < time && this.opModeIsActive()) {
            bot.glyphRamBar.toRam();
        }
        bot.glyphRamBar.stop();
    }
}