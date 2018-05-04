package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.GlyphCamera;
import org.firstinspires.ftc.teamcode.hardware.GlyphDetection;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.RangeLocator;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurn;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurnGlyph;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComLocationXYDrive;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDrive;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveGlyph;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComUltraDrive;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Test: Main", group="Test")
public class AutoTest extends RelicOpModes {

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
        glyphCam = new GlyphCamera();

        waitForStart();

        new AutoComUltraDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.3, 17.0).Run();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.3, 12.0).Run();


        bot.driveMecanum.driveStop();
        /*
        switch(pictograph){
            case LEFT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 32.5).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.5, 135.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDLEFT, 0.6, 18.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDRIGHT, 0.6, 1.0).Run();
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 42.5).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 45.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            case RIGHT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 35.0).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 45.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
            default:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 35.0).Run();
                bot.glyphPlatform.gripClose();
                bot.glyphPlatform.up();
                new AutoComGyroTurn(this,0.7, 45.0).Run();
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.6, 12.0).Run();
                bot.glyphPlatform.gripRelease();
                wait(0.5);
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.6, 1.0).Run();
                break;
        }
        new AutoComGyroTurn(this,1.0, 90.0).Run();
        bot.glyphPlatform.down();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.8, 6.0).Run();

        switch(pictograph){
            case LEFT:
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.LEFT, 0.6, 7.5).Run();
                break;
            case RIGHT:
                break;
            default:
                break;
        }
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 12.0).Run();

        bot.phoneRotator.front();
        bot.glyphConveyors.in();

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 12.0).Run();

        DriveMecanum.DriveDirection glyphDir = glyphCam.alignWithGlyphDirection(glyphCam.getGlyphX(), glyphCam.getGlyphY());
        timer.reset();
        while (glyphDir != DriveMecanum.DriveDirection.FORWARD && timer.time() < 3.0 && opModeIsActive()) {
            bot.driveMecanum.directionDrive(glyphDir, 0.2);
            glyphDir = glyphCam.alignWithGlyphDirection(glyphCam.getGlyphX(), glyphCam.getGlyphY());
        }
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 14.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 105.0, 1).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 1.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 75.0, 1).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 1.0, 1).Run();
        if(bot.glyphDetection.glyphFound(1)){
            bot.glyphConveyors.stop();
        }
        new AutoComGyroTurn(this,0.5, 90.0).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 10.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 105.0, 1).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 75.0, 1).Run();
        bot.glyphConveyors.stop();
        new AutoComGyroTurn(this, 1.0, 90.0).Run();

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 1.0, 20.0).Run();

        bot.glyphConveyors.in();
        double leftDistance = bot.rangeLocator.getDistance(RangeLocator.RangeLocation.LEFT);
        if(leftDistance < 48.0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.RIGHT, 1.0, 48.0 - leftDistance).Run();
        } else if(leftDistance > 48.0){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 1.0, leftDistance - 48.0).Run();
        }
        bot.glyphConveyors.stop();

        double backDistance = bot.rangeLocator.getDistance(RangeLocator.RangeLocation.BACK) - 12;
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.8, backDistance).Run();

        new AutoComGyroTurn(this,0.5, 45.0).Run();
        bot.glyphPlatform.gripClose();
        bot.glyphPlatform.up();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARDRIGHT, 0.3, 12.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.2);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARDLEFT, 0.3, 6.0).Run();
        bot.glyphPlatform.down();
        new AutoComGyroTurn(this,0.5, 90.0).Run();
        bot.glyphPlatform.down();
        //new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.3, 4.0).Run();
        */
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