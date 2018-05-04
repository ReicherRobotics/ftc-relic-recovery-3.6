package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.RangeLocator;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurn;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurnGlyph;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurnRam;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDrive;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveGlyph;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Red Back 3", group="Main 3")
public class AutoRedBack3 extends RelicOpModes {

    public VuforiaHardware vuforiaHw;
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
        if(jewelColor == JewelDetection.JewelColor.RED){
            bot.jewelRemover.hitLeft();
        } else if(jewelColor == JewelDetection.JewelColor.BLUE) {
            bot.jewelRemover.hitRight();
        }
        waitRam(0.4);
        bot.jewelRemover.extensionUp();
        bot.jewelRemover.hitterZero();

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.3, 24.0).Run();
        new AutoComGyroTurnRam(this, 0.5, 90).Run();

        switch(pictograph){
            case RIGHT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 10.5).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.5, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 18.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 18.0).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            case LEFT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 25.5).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            default:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 24.5).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDLEFT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDRIGHT, 0.6, 1.0).Run();
                break;
        }
        new AutoComGyroTurn(this,1.0, 180.0).Run();
        bot.glyphPlatform.down();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 4.0).Run();

        switch(pictograph){
            case RIGHT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.8, 20).Run();
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.8, 12.5).Run();
                break;
            case LEFT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.8, 5.0).Run();
                break;
            default:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.RIGHT, 0.8, 5.0).Run();
                break;
        }
        bot.phoneRotator.side();
        bot.glyphConveyors.in();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 23.0, 1).Run();
        new AutoComGyroTurn(this,1.0, 135.0).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 7.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 105.0, 1).Run();
        new AutoComGyroTurnGlyph(this,1.0, 135.0, 1).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 7.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 105.0, 1).Run();
        new AutoComGyroTurn(this,1.0, 135.0).Run();
        bot.glyphConveyors.stop();

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.6, 12.0).Run();
        new AutoComGyroTurn(this,0.5, 180.0).Run();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.9, 15.0).Run();

        bot.glyphConveyors.in();

        double leftDistance = bot.rangeLocator.getAvgDistance(RangeLocator.RangeLocation.LEFT);
        telemetry.addData("Left Distance: ", leftDistance);
        telemetry.update();
        if(leftDistance < 20.0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 1.0, 1.0).Run();
        } else if(leftDistance > 46.0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 1.0, leftDistance - 46.0).Run();
        }

        /*double backDistance = bot.rangeLocator.getAvgDistance(RangeLocator.RangeLocation.BACK) - 24.0;
        telemetry.addData("Right Distance: ", leftDistance);
        telemetry.addData("Back Distance: ", backDistance);
        telemetry.update();
        if(backDistance > 0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.3, backDistance).Run();
        } else {
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.3, backDistance).Run();
        }*/

        bot.glyphConveyors.stop();
        bot.glyphPlatform.gripClose();
        bot.glyphPlatform.up();
        new AutoComGyroTurn(this,0.5, 160.0).Run();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.8, 15.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.2);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.4, 4.0).Run();
        bot.glyphPlatform.down();
        //new AutoComGyroTurn(this,0.5, 180.0).Run();
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
            bot.glyphRamBar.toPerpendicular();
        }
        bot.glyphRamBar.stop();
    }
}