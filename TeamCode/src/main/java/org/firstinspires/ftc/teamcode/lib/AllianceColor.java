package org.firstinspires.ftc.teamcode.lib;

import android.support.annotation.Nullable;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public enum AllianceColor {
    RED,
    BLUE,
    UNKNOWN;

    public static AllianceColor from(@Nullable FieldSetup startingPosition) {
        if(startingPosition != null) {
            if (startingPosition.equals(FieldSetup.StartingPosition.RED_FRONT) || startingPosition.equals(FieldSetup.StartingPosition.RED_BACK)) {
                return RED;
            }
            if (startingPosition.equals(FieldSetup.StartingPosition.BLUE_FRONT) || startingPosition.equals(FieldSetup.StartingPosition.BLUE_BACK)) {
                return BLUE;
            }
        }
        return UNKNOWN;
    }
}
