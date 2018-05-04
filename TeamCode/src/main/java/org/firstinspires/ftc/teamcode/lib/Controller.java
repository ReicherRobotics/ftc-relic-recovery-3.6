package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Reicher Robotics on 2/25/2018.
 */

public class Controller {
    Gamepad gamepad = null;

    public enum ButtonState {
        PRESSED,    // Button state when button is held down.
        RELEASED,   // Button state when button is not pushed up.
        ON_PRESS,   // Button state when button is being pressed down.
        ON_RELEASE  // Button state when button is being released up.
    }

    private double      joystickDeadZone = 0.05;
    public ButtonState  leftJoystickX = ButtonState.RELEASED;
    public float        leftJoystickXValue = (float) 0.0;
    public ButtonState  leftJoystickY = ButtonState.RELEASED;
    public float        leftJoystickYValue = (float) 0.0;
    public ButtonState  leftJoystickButton = ButtonState.RELEASED;

    public ButtonState  rightJoystickX = ButtonState.RELEASED;
    public float        rightJoystickXValue = (float) 0.0;
    public ButtonState  rightJoystickY = ButtonState.RELEASED;
    public float        rightJoystickYValue = (float) 0.0;
    public ButtonState  rightJoystickButton = ButtonState.RELEASED;

    public ButtonState  dPadUp = ButtonState.RELEASED;
    public ButtonState  dPadDown = ButtonState.RELEASED;
    public ButtonState  dPadLeft = ButtonState.RELEASED;
    public ButtonState  dPadRight = ButtonState.RELEASED;

    public ButtonState  aButton = ButtonState.RELEASED;
    public ButtonState  bButton = ButtonState.RELEASED;
    public ButtonState  xButton = ButtonState.RELEASED;
    public ButtonState  yButton = ButtonState.RELEASED;

    public ButtonState  leftBumper = ButtonState.RELEASED;
    public ButtonState  rightBumper = ButtonState.RELEASED;

    private double      triggerDeadZone = 0.05;
    public ButtonState  leftTrigger = ButtonState.RELEASED;
    public float        leftTriggerValue = (float) 0.0;
    public ButtonState  rightTrigger = ButtonState.RELEASED;
    public float        rightTriggerValue = (float) 0.0;

    public ButtonState  backButton = ButtonState.RELEASED;
    public ButtonState  guideButton = ButtonState.RELEASED;
    public ButtonState  startButton = ButtonState.RELEASED;

    public Controller(Gamepad _gamepad){
        gamepad = _gamepad;
    }

    public Controller(Gamepad _gamepad, double _joystickDeadZone, double _triggerDeadZone){
        gamepad = _gamepad;
        joystickDeadZone = _joystickDeadZone;
        triggerDeadZone = _triggerDeadZone;
    }

    public void update () {
        // Left Joystick X
        if (Math.abs(gamepad.left_stick_x) > joystickDeadZone) {
            leftJoystickXValue = gamepad.left_stick_x;
            if (leftJoystickX == ButtonState.RELEASED) {
                leftJoystickX = ButtonState.ON_PRESS;
            } else if (leftJoystickX == ButtonState.ON_PRESS) {
                leftJoystickX = ButtonState.PRESSED;
            }
        } else {
            leftJoystickXValue = (float) 0.0;
            if (leftJoystickX == ButtonState.PRESSED) {
                leftJoystickX = ButtonState.ON_RELEASE;
            } else if (leftJoystickX == ButtonState.ON_RELEASE) {
                leftJoystickX = ButtonState.RELEASED;
            }
        }

        // Left Joystick Y
        if (Math.abs(gamepad.left_stick_y) > joystickDeadZone) {
            leftJoystickYValue = -gamepad.left_stick_y;
            if (leftJoystickY == ButtonState.RELEASED) {
                leftJoystickY = ButtonState.ON_PRESS;
            } else if (leftJoystickY == ButtonState.ON_PRESS) {
                leftJoystickY = ButtonState.PRESSED;
            }
        } else {
            leftJoystickYValue = (float) 0.0;
            if (leftJoystickY == ButtonState.PRESSED) {
                leftJoystickY = ButtonState.ON_RELEASE;
            } else if (leftJoystickY == ButtonState.ON_RELEASE) {
                leftJoystickY = ButtonState.RELEASED;
            }
        }

        // Left Joystick Button
        if (gamepad.left_stick_button) {
            if (leftJoystickButton == ButtonState.RELEASED) {
                leftJoystickButton = ButtonState.ON_PRESS;
            } else if (leftJoystickButton == ButtonState.ON_PRESS) {
                leftJoystickButton = ButtonState.PRESSED;
            }
        } else {
            if (leftJoystickButton == ButtonState.PRESSED) {
                leftJoystickButton = ButtonState.ON_RELEASE;
            } else if (leftJoystickButton == ButtonState.ON_RELEASE) {
                leftJoystickButton = ButtonState.RELEASED;
            }
        }

        // Right Joystick X
        if (Math.abs(gamepad.right_stick_x) > joystickDeadZone) {
            rightJoystickXValue = gamepad.right_stick_x;
            if (rightJoystickX == ButtonState.RELEASED) {
                rightJoystickX = ButtonState.ON_PRESS;
            } else if (rightJoystickX == ButtonState.ON_PRESS) {
                rightJoystickX = ButtonState.PRESSED;
            }
        } else {
            rightJoystickXValue = (float) 0.0;
            if (rightJoystickX == ButtonState.PRESSED) {
                rightJoystickX = ButtonState.ON_RELEASE;
            } else if (rightJoystickX == ButtonState.ON_RELEASE) {
                rightJoystickX = ButtonState.RELEASED;
            }
        }

        // Right Joystick Y
        if (Math.abs(gamepad.right_stick_y) > joystickDeadZone) {
            rightJoystickYValue = -gamepad.right_stick_y;
            if (rightJoystickY == ButtonState.RELEASED) {
                rightJoystickY = ButtonState.ON_PRESS;
            } else if (rightJoystickY == ButtonState.ON_PRESS) {
                rightJoystickY = ButtonState.PRESSED;
            }
        } else {
            rightJoystickYValue = (float) 0.0;
            if (rightJoystickY == ButtonState.PRESSED) {
                rightJoystickY = ButtonState.ON_RELEASE;
            } else if (rightJoystickY == ButtonState.ON_RELEASE) {
                rightJoystickY = ButtonState.RELEASED;
            }
        }

        // Right Joystick Button
        if (gamepad.right_stick_button) {
            if (rightJoystickButton == ButtonState.RELEASED) {
                rightJoystickButton = ButtonState.ON_PRESS;
            } else if (rightJoystickButton == ButtonState.ON_PRESS) {
                rightJoystickButton = ButtonState.PRESSED;
            }
        } else {
            if (rightJoystickButton == ButtonState.PRESSED) {
                rightJoystickButton = ButtonState.ON_RELEASE;
            } else if (rightJoystickButton == ButtonState.ON_RELEASE) {
                rightJoystickButton = ButtonState.RELEASED;
            }
        }

        // DPad Up
        if (gamepad.dpad_up) {
            if (dPadUp == ButtonState.RELEASED) {
                dPadUp = ButtonState.ON_PRESS;
            } else if (dPadUp == ButtonState.ON_PRESS) {
                dPadUp = ButtonState.PRESSED;
            }
        } else {
            if (dPadUp == ButtonState.PRESSED) {
                dPadUp = ButtonState.ON_RELEASE;
            } else if (dPadUp == ButtonState.ON_RELEASE) {
                dPadUp = ButtonState.RELEASED;
            }
        }

        // DPad Down
        if (gamepad.dpad_down) {
            if (dPadDown == ButtonState.RELEASED) {
                dPadDown = ButtonState.ON_PRESS;
            } else if (dPadDown == ButtonState.ON_PRESS) {
                dPadDown = ButtonState.PRESSED;
            }
        } else {
            if (dPadDown == ButtonState.PRESSED) {
                dPadDown = ButtonState.ON_RELEASE;
            } else if (dPadDown == ButtonState.ON_RELEASE) {
                dPadDown = ButtonState.RELEASED;
            }
        }

        // DPad Left
        if (gamepad.dpad_left) {
            if (dPadLeft == ButtonState.RELEASED) {
                dPadLeft = ButtonState.ON_PRESS;
            } else if (dPadLeft == ButtonState.ON_PRESS) {
                dPadLeft = ButtonState.PRESSED;
            }
        } else {
            if (dPadLeft == ButtonState.PRESSED) {
                dPadLeft = ButtonState.ON_RELEASE;
            } else if (dPadLeft == ButtonState.ON_RELEASE) {
                dPadLeft = ButtonState.RELEASED;
            }
        }

        // DPad Right
        if (gamepad.dpad_right) {
            if (dPadRight == ButtonState.RELEASED) {
                dPadRight = ButtonState.ON_PRESS;
            } else if (dPadRight == ButtonState.ON_PRESS) {
                dPadRight = ButtonState.PRESSED;
            }
        } else {
            if (dPadRight == ButtonState.PRESSED) {
                dPadRight = ButtonState.ON_RELEASE;
            } else if (dPadRight == ButtonState.ON_RELEASE) {
                dPadRight = ButtonState.RELEASED;
            }
        }

        // A
        if (gamepad.a) {
            if (aButton == ButtonState.RELEASED) {
                aButton = ButtonState.ON_PRESS;
            } else if (aButton == ButtonState.ON_PRESS) {
                aButton = ButtonState.PRESSED;
            }
        } else {
            if (aButton == ButtonState.PRESSED) {
                aButton = ButtonState.ON_RELEASE;
            } else if (aButton == ButtonState.ON_RELEASE) {
                aButton = ButtonState.RELEASED;
            }
        }

        // B
        if (gamepad.b) {
            if (bButton == ButtonState.RELEASED) {
                bButton = ButtonState.ON_PRESS;
            } else if (bButton == ButtonState.ON_PRESS) {
                bButton = ButtonState.PRESSED;
            }
        } else {
            if (bButton == ButtonState.PRESSED) {
                bButton = ButtonState.ON_RELEASE;
            } else if (bButton == ButtonState.ON_RELEASE) {
                bButton = ButtonState.RELEASED;
            }
        }

        // X
        if (gamepad.x) {
            if (xButton == ButtonState.RELEASED) {
                xButton = ButtonState.ON_PRESS;
            } else if (xButton == ButtonState.ON_PRESS) {
                xButton = ButtonState.PRESSED;
            }
        } else {
            if (xButton == ButtonState.PRESSED) {
                xButton = ButtonState.ON_RELEASE;
            } else if (xButton == ButtonState.ON_RELEASE) {
                xButton = ButtonState.RELEASED;
            }
        }

        // Y
        if (gamepad.y) {
            if (yButton == ButtonState.RELEASED) {
                yButton = ButtonState.ON_PRESS;
            } else if (yButton == ButtonState.ON_PRESS) {
                yButton = ButtonState.PRESSED;
            }
        } else {
            if (yButton == ButtonState.PRESSED) {
                yButton = ButtonState.ON_RELEASE;
            } else if (yButton == ButtonState.ON_RELEASE) {
                yButton = ButtonState.RELEASED;
            }
        }

        // Left Bumper
        if (gamepad.left_bumper) {
            if (leftBumper == ButtonState.RELEASED) {
                leftBumper = ButtonState.ON_PRESS;
            } else if (leftBumper == ButtonState.ON_PRESS) {
                leftBumper = ButtonState.PRESSED;
            }
        } else {
            if (leftBumper == ButtonState.PRESSED) {
                leftBumper = ButtonState.ON_RELEASE;
            } else if (leftBumper == ButtonState.ON_RELEASE) {
                leftBumper = ButtonState.RELEASED;
            }
        }

        // Right Bumper
        if (gamepad.right_bumper) {
            if (rightBumper == ButtonState.RELEASED) {
                rightBumper = ButtonState.ON_PRESS;
            } else if (rightBumper == ButtonState.ON_PRESS) {
                rightBumper = ButtonState.PRESSED;
            }
        } else {
            if (rightBumper == ButtonState.PRESSED) {
                rightBumper = ButtonState.ON_RELEASE;
            } else if (rightBumper == ButtonState.ON_RELEASE) {
                rightBumper = ButtonState.RELEASED;
            }
        }

        // Left Trigger
        if (gamepad.left_trigger > triggerDeadZone) {
            leftTriggerValue = gamepad.left_trigger;
            if (leftTrigger == ButtonState.RELEASED) {
                leftTrigger = ButtonState.ON_PRESS;
            } else if (leftTrigger == ButtonState.ON_PRESS) {
                leftTrigger = ButtonState.PRESSED;
            }
        } else {
            leftTriggerValue = (float) 0.0;
            if (leftTrigger == ButtonState.PRESSED) {
                leftTrigger = ButtonState.ON_RELEASE;
            } else if (leftTrigger == ButtonState.ON_RELEASE) {
                leftTrigger = ButtonState.RELEASED;
            }
        }

        // Left Trigger
        if (gamepad.right_trigger > triggerDeadZone) {
            rightTriggerValue = gamepad.left_trigger;
            if (rightTrigger == ButtonState.RELEASED) {
                rightTrigger = ButtonState.ON_PRESS;
            } else if (rightTrigger == ButtonState.ON_PRESS) {
                rightTrigger = ButtonState.PRESSED;
            }
        } else {
            rightTriggerValue = (float) 0.0;
            if (rightTrigger == ButtonState.PRESSED) {
                rightTrigger = ButtonState.ON_RELEASE;
            } else if (rightTrigger == ButtonState.ON_RELEASE) {
                rightTrigger = ButtonState.RELEASED;
            }
        }

        // Back
        if (gamepad.back) {
            if (backButton == ButtonState.RELEASED) {
                backButton = ButtonState.ON_PRESS;
            } else if (backButton == ButtonState.ON_PRESS) {
                backButton = ButtonState.PRESSED;
            }
        } else {
            if (backButton == ButtonState.PRESSED) {
                backButton = ButtonState.ON_RELEASE;
            } else if (backButton == ButtonState.ON_RELEASE) {
                backButton = ButtonState.RELEASED;
            }
        }

        // Guide
        if (gamepad.guide) {
            if (guideButton == ButtonState.RELEASED) {
                guideButton = ButtonState.ON_PRESS;
            } else if (guideButton == ButtonState.ON_PRESS) {
                guideButton = ButtonState.PRESSED;
            }
        } else {
            if (guideButton == ButtonState.PRESSED) {
                guideButton = ButtonState.ON_RELEASE;
            } else if (guideButton == ButtonState.ON_RELEASE) {
                guideButton = ButtonState.RELEASED;
            }
        }

        // Start
        if (gamepad.start) {
            if (startButton == ButtonState.RELEASED) {
                startButton = ButtonState.ON_PRESS;
            } else if (startButton == ButtonState.ON_PRESS) {
                startButton = ButtonState.PRESSED;
            }
        } else {
            if (startButton == ButtonState.PRESSED) {
                startButton = ButtonState.ON_RELEASE;
            } else if (startButton == ButtonState.ON_RELEASE) {
                startButton = ButtonState.RELEASED;
            }
        }
    }

    public double getJoystickDeadZone() {
        return joystickDeadZone;
    }

    public void setJoystickDeadZone(double _joystickDeadZone) {
        joystickDeadZone = _joystickDeadZone;
    }

    public double getTriggerDeadZone() {
        return triggerDeadZone;
    }

    public void setTriggerDeadZone(double _triggerDeadZone) {
        triggerDeadZone = _triggerDeadZone;
    }
}
