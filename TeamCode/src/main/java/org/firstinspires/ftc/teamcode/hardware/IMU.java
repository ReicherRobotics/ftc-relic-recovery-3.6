package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class IMU {
    public BNO055IMU imu;

    public IMU(HardwareMap sensorMap, String name) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = sensorMap.get(BNO055IMU.class, name);
        imu.initialize(parameters);
    }

    public double getHeading() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return (angles.firstAngle + 360) % 360;
    }

    public double getError(double targetAngle){
        double angleError = 0.0;
        angleError = (targetAngle - getHeading());
        angleError -= (360 * Math.floor(0.5 + (angleError / 360.0)));
        return angleError;
    }

    public double getError(double targetAngle, double currentAngle){
        double angleError = 0.0;
        angleError = (targetAngle - currentAngle);
        angleError -= (360 * Math.floor(0.5 + (angleError / 360.0)));
        return angleError;
    }
}
