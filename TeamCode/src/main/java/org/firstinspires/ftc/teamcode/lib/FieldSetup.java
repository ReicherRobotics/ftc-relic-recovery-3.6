package org.firstinspires.ftc.teamcode.lib;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class FieldSetup {

    public enum StartingPosition {
        RED_FRONT,
        RED_BACK,
        BLUE_FRONT,
        BLUE_BACK,
        UNKNOWN;
    }

    private StartingPosition startingPosition = StartingPosition.UNKNOWN;

    public FieldSetup() {
    }

    public FieldSetup(StartingPosition _startingPosition) {
        startingPosition = _startingPosition;
    }

    public StartingPosition getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(StartingPosition _startingPosition) {
        startingPosition = _startingPosition;
    }
}
