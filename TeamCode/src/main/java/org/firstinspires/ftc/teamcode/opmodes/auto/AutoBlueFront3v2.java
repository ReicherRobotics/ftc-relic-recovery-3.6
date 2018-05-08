package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
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

@Autonomous(name="Blue Front 3v2", group="Main 3")
public class AutoBlueFront3v2 extends RelicOpModes {

    public VuforiaHardware vuforiaHw;
    public RelicRecoveryVuMark pictograph;
    public JewelDetection.JewelColor jewelColor;
    ElapsedTime timer = new ElapsedTime();
    double columnDistance = 0.0;
    double targetDistance = 0.0;
    double extraDistance = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Working");
        telemetry.update();
        super.runOpMode();

        bot.jewelRemover.extensionUp();
        bot.jewelRemover.hitRight();
        bot.glyphPlatform.gripRelease();
        bot.glyphPlatform.down();

        vuforiaHw = new VuforiaHardware();
        vuforiaHw.Init(this.hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

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
                columnDistance = 40.5;
                targetDistance = 22.5;
                extraDistance = 12.0;
                break;
            case CENTER:
                columnDistance = 33.0;
                targetDistance = 52.0;
                extraDistance = 6.0;
                break;
            case LEFT:
                columnDistance = 26.5;
                targetDistance = 39.0;
                extraDistance = 0.0;
                break;
            default:
                columnDistance = 26.5;
                targetDistance = 39.0;
                extraDistance = 0.0;
                break;
        }

        new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, columnDistance).Run();
        new AutoComGyroTurnRam(this, 0.5, 90).Run();

        double rightDistance = bot.rangeLocator.getRightDistance();
        if(rightDistance < 34.5 && pictograph == RelicRecoveryVuMark.CENTER) {
            rightDistance+=34.5;
        }
        telemetry.addData("Right Distance", rightDistance);
        telemetry.update();

        if(rightDistance > targetDistance){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.RIGHT, 0.5, rightDistance - targetDistance).Run();
        } else {
            new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.LEFT, 0.5, targetDistance - rightDistance).Run();
        }

        bot.glyphPlatform.up();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 6.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.2);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 6.0).Run();
        bot.glyphPlatform.down();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.RIGHT, 1.0, extraDistance).Run();

        bot.phoneRotator.side();
        bot.glyphConveyors.in();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 28.0, bot.glyphDetection.SECOND).Run();
        new AutoComGyroTurnGlyph(this, 1.0, 75.0, bot.glyphDetection.SECOND).Run();
        new AutoComGyroTurnGlyph(this,0.5, 90.0, bot.glyphDetection.SECOND).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 8.0, bot.glyphDetection.SECOND).Run();
        telemetry.addData("Glyph 1", bot.glyphDetection.glyphFound(bot.glyphDetection.FIRST));
        telemetry.addData("Glyph 2", bot.glyphDetection.glyphFound(bot.glyphDetection.SECOND));
        telemetry.update();
        new AutoComGyroTurnGlyph(this, 1.0, 105.0, bot.glyphDetection.SECOND).Run();
        new AutoComGyroTurnGlyph(this,0.5, 90.0, bot.glyphDetection.SECOND).Run();
        new AutoComMecDriveGlyph(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 8.0, bot.glyphDetection.SECOND).Run();
        telemetry.addData("Glyph 1", bot.glyphDetection.glyphFound(bot.glyphDetection.FIRST));
        telemetry.addData("Glyph 2", bot.glyphDetection.glyphFound(bot.glyphDetection.SECOND));
        telemetry.update();
        new AutoComGyroTurnGlyph(this, 1.0, 75.0, bot.glyphDetection.SECOND).Run();
        bot.glyphConveyors.stop();
        new AutoComGyroTurn(this,0.5, 90.0).Run();

        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 12.0).Run();
        bot.glyphConveyors.in();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 18.0).Run();
        telemetry.addData("Glyph 1", bot.glyphDetection.glyphFound(bot.glyphDetection.FIRST));
        telemetry.addData("Glyph 2", bot.glyphDetection.glyphFound(bot.glyphDetection.SECOND));
        telemetry.update();

        rightDistance = bot.rangeLocator.getRightDistance();
        if (rightDistance < 34.5){
            rightDistance+=34.5;
        }
        telemetry.addData("Right Distance: ", rightDistance);
        telemetry.update();

        targetDistance = 40.0;
        if(rightDistance > targetDistance){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.RIGHT, 0.5, rightDistance - targetDistance).Run();
        } else {
            new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.LEFT, 0.5, targetDistance - rightDistance).Run();
        }

        wait(0.5);

        double backDistance = bot.rangeLocator.getBackDistance();
        telemetry.addData("Back Distance: ", backDistance);
        telemetry.update();

        bot.glyphConveyors.stop();
        bot.glyphPlatform.gripClose();

        targetDistance = 12.0;
        if(backDistance > targetDistance){
            new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, backDistance - targetDistance).Run();
        } else {
            new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, targetDistance - backDistance).Run();
        }

        bot.glyphPlatform.up();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 7.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.2);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 2.0).Run();
        bot.glyphPlatform.down();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 2.0).Run();
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