package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.RangeLocator;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurnRam;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Test: Range", group ="Test")
public class RangeSensors extends RelicOpModes {

    public VuforiaHardware vuforiaHw;
    public RelicRecoveryVuMark pictograph;
    public JewelDetection.JewelColor jewelColor;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("Left Distance: ", bot.rangeLocator.getDistance(RangeLocator.RangeLocation.LEFT));
            telemetry.addData("Back Distance: ", bot.rangeLocator.getDistance(RangeLocator.RangeLocation.BACK));
            telemetry.addData("Right Distance: ", bot.rangeLocator.getDistance(RangeLocator.RangeLocation.RIGHT));
            telemetry.update();
        }
    }
}
