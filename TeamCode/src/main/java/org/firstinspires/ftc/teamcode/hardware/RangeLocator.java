package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.FieldSetup;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class RangeLocator {

    public enum RangeLocation {
        LEFT,
        BACK,
        RIGHT;
    }
    private int avgNum = 3;
    private int numRangeSensors = 3;
    // 0 = Left
    // 1 = Back
    // 2 = Right
    public ModernRoboticsI2cRangeSensor rangeSensors[] = new ModernRoboticsI2cRangeSensor[numRangeSensors];

    public RangeLocator(HardwareMap rangeMap, String rangeNames[]){
        for(int i = 0; i < numRangeSensors; i++){
            rangeSensors[i] = rangeMap.get(ModernRoboticsI2cRangeSensor.class, rangeNames[i]);
        }
    }

    public double getLeftDistance(){
        double distance = rangeSensors[0].rawUltrasonic() / 2.54;
        while(distance > 90.0){
            distance = rangeSensors[0].rawUltrasonic() / 2.54;
        }
        return distance;
    }

    public double getLeftAvgDistance(){
        //return rangeSensors[0].getDistance(DistanceUnit.INCH);
        double average = 0.0;
        for(int i = 0; i < avgNum; i++){
            average = average + getLeftDistance();
        }
        return average / avgNum;
    }

    public double getBackDistance(){
        double distance = rangeSensors[1].rawUltrasonic() / 2.54;
        while(distance > 90.0){
            distance = rangeSensors[1].rawUltrasonic() / 2.54;
        }
        return distance;
    }

    public double getBackAvgDistance(){
        //return rangeSensors[0].getDistance(DistanceUnit.INCH);
        double average = 0.0;
        for(int i = 0; i < avgNum; i++){
            average = average + getLeftDistance();
        }
        return average / avgNum;
    }

    public double getRightDistance(){
        double distance = rangeSensors[2].rawUltrasonic() / 2.54;
        while(distance > 90.0){
            distance = rangeSensors[2].rawUltrasonic() / 2.54;
        }
        return distance;
    }

    public double getRightAvgDistance(){
        //return rangeSensors[0].getDistance(DistanceUnit.INCH);
        double average = 0.0;
        for(int i = 0; i < avgNum; i++){
            average = average + getRightDistance();
        }
        return average / avgNum;
    }

    public double getDistance(RangeLocation location){
        switch(location){
            case LEFT:
                return getLeftDistance();
            case BACK:
                return getBackDistance();
            case RIGHT:
                return getRightDistance();
            default:
                return getBackDistance();
        }
    }

    public double getAvgDistance(RangeLocation location){
        switch(location){
            case LEFT:
                return getLeftAvgDistance();
            case BACK:
                return getBackAvgDistance();
            case RIGHT:
                return getRightAvgDistance();
            default:
                return getBackAvgDistance();
        }
    }
}
