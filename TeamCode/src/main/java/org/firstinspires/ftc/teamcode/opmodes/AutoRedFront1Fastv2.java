package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurn;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDrive;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Red Front 1v2", group="Main 1")
public class AutoRedFront1Fastv2 extends RelicOpModes {

    public VuforiaHardware vuforiaHw;
    public RelicRecoveryVuMark pictograph;
    public JewelDetection.JewelColor jewelColor;
    ElapsedTime timer = new ElapsedTime();

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
        if(jewelColor == JewelDetection.JewelColor.RED){
            bot.jewelRemover.hitLeft();
        } else if(jewelColor == JewelDetection.JewelColor.BLUE) {
            bot.jewelRemover.hitRight();
        }
        waitRam(0.4);
        bot.jewelRemover.extensionUp();
        bot.jewelRemover.hitterZero();

        //new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 36.0).Run();
        //wait(5.0);

        switch(pictograph){
            case LEFT:
                double targetDistance = 19.0;
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 33.0).Run();
                double backDistance = bot.rangeLocator.getBackDistance();
                telemetry.addData("Back Distance", backDistance);
                telemetry.update();
                if(backDistance > 33){
                    targetDistance = 47.0;
                }
                if(backDistance > targetDistance){
                    new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, backDistance - targetDistance).Run();
                }
                else{
                    new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, targetDistance - backDistance).Run();
            }
                new AutoComGyroTurn(this,0.5, 110.0).Run();
                break;
            case CENTER:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 28.5).Run();
                new AutoComGyroTurn(this,0.5, 110.0).Run();
                break;
            case RIGHT:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 36.0).Run();
                new AutoComGyroTurn(this,0.5, 70.0).Run();
                break;
            default:
                new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.FORWARD, 0.5, 36.0).Run();
                new AutoComGyroTurn(this,0.5, 70.0).Run();
                break;
        }

        bot.glyphPlatform.up();
        new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.5, 8.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.2);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 1.0, 2.0).Run();
        new AutoComGyroTurn(this,1.0, 90.0).Run();
        bot.glyphPlatform.down();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 1.0, 4.0).Run();
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