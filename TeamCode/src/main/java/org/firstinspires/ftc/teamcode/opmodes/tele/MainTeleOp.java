package org.firstinspires.ftc.teamcode.opmodes.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.GlyphRamBar;
import org.firstinspires.ftc.teamcode.hardware.RelicGrabber;
import org.firstinspires.ftc.teamcode.lib.Controller;
import org.firstinspires.ftc.teamcode.opmodes.RelicOpModes;

/**
 * Created by Reicher Robotics on 2/26/2018.
 */

@TeleOp(name="Main", group="Version 2.1")
public class MainTeleOp extends RelicOpModes {

    private Controller controller1;
    private Controller controller2;

    private double maxDrivePower;
    private boolean CONVEYOR_ON;
    private boolean PLATFORM_DOWN;
    private boolean GRIPPER_OPEN;
    private RelicGrabber.WristPosition WRIST_POSITION;
    private boolean CLAW_OPEN;
    private GlyphRamBar.RamPosition RAM_POSITION;
    private  boolean PHONE_SIDE;
    private  boolean JEWEL_EXT_UP;
    private  boolean JEWEL_HIT_UP;

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        // Flags
        maxDrivePower = 1.0;
        CONVEYOR_ON = false;
        PLATFORM_DOWN = true;
        GRIPPER_OPEN = true;
        WRIST_POSITION = RelicGrabber.WristPosition.DOWN;
        CLAW_OPEN = false;
        RAM_POSITION = GlyphRamBar.RamPosition.UNKNOWN;
        PHONE_SIDE = true;
        JEWEL_EXT_UP = true;
        JEWEL_HIT_UP = true;

        // Controllers
        controller1 = new Controller(gamepad1);
        controller2 = new Controller(gamepad2);

        // Init
        //bot.glyphPlatform.down();
        bot.glyphPlatform.gripRelease();
        bot.relicGrabber.wristDown();
        bot.relicGrabber.clawClose();
        bot.jewelRemover.extensionUp();
        bot.jewelRemover.hitterZero();
        bot.phoneRotator.side();
        telemetry.addLine("Status Initialized");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            controller1.update();
            controller2.update();

            /**********************************************************************************************
             * Controller 1 Controls
             **********************************************************************************************/

            // Mecanum Drive Joysticks
            // Left Joystick Up/Down - drive forward/backward
            // Left Joystick Left/Right - turn right/left
            // Right Joystick Left/Right - drive left/right
            // Either Joystick Button - slows down max speed, press slows
            if(controller1.rightBumper == Controller.ButtonState.PRESSED) {
                maxDrivePower = 0.4;
            } else {
                maxDrivePower = 0.8;
            }
            bot.driveMecanum.mecanumDrive(maxDrivePower, controller1.leftJoystickYValue, controller1.leftJoystickXValue, controller1.rightJoystickXValue);

            // Conveyor Buttons
            // A - intakes glyphs, on press turns on/off
            // B - shoots out glyphs, pressed on, unpressed off
            if(controller1.aButton == Controller.ButtonState.ON_PRESS){
                CONVEYOR_ON = !CONVEYOR_ON;
                if(PLATFORM_DOWN && GRIPPER_OPEN && CONVEYOR_ON){
                    bot.glyphConveyors.in();
                } else {
                    bot.glyphConveyors.stop();
                }
            }
            if(controller1.bButton == Controller.ButtonState.ON_PRESS || controller1.bButton == Controller.ButtonState.PRESSED){
                bot.glyphConveyors.out();
                CONVEYOR_ON = false;
            }
            if(controller1.bButton == Controller.ButtonState.ON_RELEASE){
                bot.glyphConveyors.stop();
            }

            // Platform Button
            // Y - platform up/down, on press raises/lowers,
            //     glyph conveyors cannot intake while platform up
            if(controller1.yButton == Controller.ButtonState.ON_PRESS) {
                PLATFORM_DOWN = !PLATFORM_DOWN;
                if(PLATFORM_DOWN) {
                    bot.glyphPlatform.down();
                } else {
                    bot.glyphPlatform.up();
                    bot.glyphConveyors.stop();
                    CONVEYOR_ON = false;
                }
            }

            // Gripper Button
            // X - gripper open/close, on press closes/opens,
            //     turns off glyph conveyor intakes when closes
            if(controller1.xButton == Controller.ButtonState.ON_PRESS) {
                GRIPPER_OPEN = !GRIPPER_OPEN;
                if(GRIPPER_OPEN) {
                    bot.glyphPlatform.gripRelease();
                } else {
                    bot.glyphPlatform.gripClose();
                    bot.glyphConveyors.stop();
                    CONVEYOR_ON = false;
                }
            }

            /**********************************************************************************************
             * Controller 2 Controls
             **********************************************************************************************/

            // Relic Extension Joystick
            // Right Joystick Up/Down - extends/retracts relic claw, speed based on joystick,
            //                          encoder prevents over extend/retract
            bot.relicExtension.extendRetractWithLimits(controller2.rightJoystickYValue);
            if(controller2.leftTrigger == Controller.ButtonState.ON_PRESS){
                bot.relicExtension.overrideLimits();
            }
            if(controller2.leftTrigger == Controller.ButtonState.ON_RELEASE){
                bot.relicExtension.resetLimits();
            }

            // Relic Wrist Button
            // A - rotate relic claw, on press rotates to relic/over-wall height,
            //     won't rotate out if claw hasn't been extended out
            if(controller2.aButton == Controller.ButtonState.ON_PRESS) {
                if(WRIST_POSITION == RelicGrabber.WristPosition.DOWN){
                    bot.relicGrabber.wristRelic();
                    WRIST_POSITION = RelicGrabber.WristPosition.RELIC;
                }
                else if(WRIST_POSITION == RelicGrabber.WristPosition.RELIC) {
                    bot.relicGrabber.wristUp();
                    WRIST_POSITION = RelicGrabber.WristPosition.UP;
                }
                else if(WRIST_POSITION == RelicGrabber.WristPosition.UP) {
                    bot.relicGrabber.wristRelic();
                    WRIST_POSITION = RelicGrabber.WristPosition.RELIC;
                }
            }

            // Relic Claw Button
            // Right Bumper - relic claw opens/closes, on press opens/closes
            if(controller2.rightBumper == Controller.ButtonState.ON_PRESS) {
                CLAW_OPEN = !CLAW_OPEN;
                if(CLAW_OPEN) {
                    bot.relicGrabber.clawOpen();
                } else {
                    bot.relicGrabber.clawClose();
                }
            }

            // Ram Bar Joystick
            // Left Joystick Up/Down - ram bar rotates up/down, up/down rotates
            // Ram Bar Button
            // B - ram bar rotates to specific heights, on press rotates
            if(controller2.dPadUp == Controller.ButtonState.PRESSED) {
                RAM_POSITION = GlyphRamBar.RamPosition.UNKNOWN;
                bot.glyphRamBar.up();
            } else if(controller2.dPadDown == Controller.ButtonState.PRESSED) {
                RAM_POSITION = GlyphRamBar.RamPosition.UNKNOWN;
                bot.glyphRamBar.down();
            } else if(controller2.bButton == Controller.ButtonState.ON_PRESS) {
                if (RAM_POSITION == GlyphRamBar.RamPosition.RAM || RAM_POSITION == GlyphRamBar.RamPosition.UNKNOWN) {
                    RAM_POSITION = GlyphRamBar.RamPosition.PERPENDICULAR;
                } else if (RAM_POSITION == GlyphRamBar.RamPosition.PERPENDICULAR) {
                    RAM_POSITION = GlyphRamBar.RamPosition.RAM;
                }
            } else {
                if(RAM_POSITION == GlyphRamBar.RamPosition.RAM) {
                    bot.glyphRamBar.toRam();
                }
                if(RAM_POSITION == GlyphRamBar.RamPosition.PERPENDICULAR) {
                    bot.glyphRamBar.toPerpendicular();
                }
                if(RAM_POSITION == GlyphRamBar.RamPosition.UNKNOWN) {
                    bot.glyphRamBar.stop();
                }

            }

            //Phone Rotator Button
            // Left Bumper - phone rotates, on press rotates
            if(controller2.leftBumper == Controller.ButtonState.ON_PRESS) {
                PHONE_SIDE = !PHONE_SIDE;
                if(PHONE_SIDE) {
                    bot.phoneRotator.side();
                } else {
                    bot.phoneRotator.front();
                }
            }

            // Jewel Extension Button
            // X - jewel extension up/down, on press up/down
            if(controller2.xButton == Controller.ButtonState.ON_PRESS) {
                JEWEL_EXT_UP = !JEWEL_EXT_UP;
                if(JEWEL_EXT_UP) {
                    bot.jewelRemover.extensionUp();
                } else {
                    bot.jewelRemover.extensionDown();
                }
            }

            // Jewel Hitter Button
            // Y - jewel hitter up/down, on press up/down
            if(controller2.yButton == Controller.ButtonState.ON_PRESS) {
                JEWEL_HIT_UP = !JEWEL_HIT_UP;
                if(JEWEL_HIT_UP) {
                    bot.jewelRemover.hitterZero();
                } else {
                    bot.jewelRemover.hitRight();
                }
            }
        }
    }
}