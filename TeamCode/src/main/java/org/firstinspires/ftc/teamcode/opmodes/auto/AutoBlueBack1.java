package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurn;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDrive;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Blue Back 1", group="Main 1")
@Disabled
public class AutoBlueBack1 extends RelicOpModes {

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

        new AutoComMecDriveRam(this, DriveMecanum.DriveDirection.BACKWARD, 0.3, 24.0).Run();

        switch(pictograph){
            case RIGHT:
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 0.3, 15.5).Run();
                break;
            case CENTER:
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 0.3, 8.0).Run();
                break;
            case LEFT:
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 0.3, 0.5).Run();
                break;
            default:
                new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 0.3, 15.5).Run();
                break;
        }
        new AutoComGyroTurn(this,0.5, 30.0).Run();
        bot.glyphPlatform.gripClose();
        bot.glyphPlatform.up();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.LEFT, 0.3, 10.0).Run();
        bot.glyphPlatform.gripRelease();
        wait(0.5);
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.BACKWARD, 0.3, 2.0).Run();
        bot.glyphPlatform.down();
        new AutoComGyroTurn(this,0.5, 0.0).Run();
        new AutoComMecDrive(this, DriveMecanum.DriveDirection.FORWARD, 0.3, 4.0).Run();
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