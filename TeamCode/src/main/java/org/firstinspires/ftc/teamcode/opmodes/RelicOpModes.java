package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.RelicRobot;
import org.firstinspires.ftc.teamcode.lib.FieldSetup;

/**
 * Created by Reicher Robotics on 3/19/2018.
 */

public abstract class RelicOpModes extends LinearOpMode {

    public RelicRobot bot;
    public FieldSetup.StartingPosition fieldPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        fieldPosition = FieldSetup.StartingPosition.UNKNOWN;
        bot = new RelicRobot(hardwareMap);
    }
}
