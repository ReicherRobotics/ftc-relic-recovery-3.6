package org.firstinspires.ftc.teamcode.test;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.DriveMecanum;
import org.firstinspires.ftc.teamcode.hardware.JewelDetection;
import org.firstinspires.ftc.teamcode.hardware.VuforiaHardware;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComGyroTurnRam;
import org.firstinspires.ftc.teamcode.opmodes.auto.commands.AutoComMecDriveRam;

import java.util.Locale;

/**
 * Created by Reicher Robotics on 3/24/2018.
 */

@Autonomous(name="Test: Color", group ="Test")
public class ColorSensors extends RelicOpModes {

    public VuforiaHardware vuforiaHw;
    public RelicRecoveryVuMark pictograph;
    public JewelDetection.JewelColor jewelColor;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        waitForStart();
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // wait for the start button to be pressed.
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {
            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (bot.glyphDetection.platformColorSensors[0].red() * SCALE_FACTOR),
                    (int) (bot.glyphDetection.platformColorSensors[0].green() * SCALE_FACTOR),
                    (int) (bot.glyphDetection.platformColorSensors[0].blue() * SCALE_FACTOR),
                    hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("Distance (in)",
                    String.format(Locale.US, "%.02f", bot.glyphDetection.platformDistanceSensors[0].getDistance(DistanceUnit.INCH)));
            telemetry.addData("Alpha", bot.glyphDetection.platformColorSensors[0].alpha());
            telemetry.addData("Red  ", bot.glyphDetection.platformColorSensors[0].red());
            telemetry.addData("Green", bot.glyphDetection.platformColorSensors[0].green());
            telemetry.addData("Blue ", bot.glyphDetection.platformColorSensors[0].blue());
            telemetry.addData("Hue", hsvValues[0]);

            telemetry.update();
        }
    }
}
