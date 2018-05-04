package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.ClosableVuforiaLocalizer;

/**
 * Created by Reicher Robotics on 3/4/2018.
 */

public class VuforiaHardware {
    OpenGLMatrix lastLocation = null;
    ClosableVuforiaLocalizer vuforia;
    RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
    VuforiaTrackable relicTemplate;

    public void Init(HardwareMap sensorMap){
        int cameraMonitorViewId = sensorMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", sensorMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZVMQkn/////AAAAGWnTDWgAuUMtibgxaQdCLakPw1hQPWq7MN95sbj2X1XMV/H8y+W5Z0p4UcX2XcSFwc0keQu55qlUJSEZnWJVJGv1Wzc28zWrfXeBqNrlwTymybCBrswf4BAgfpYMAuuxLVwZp7bSomD4njAU6hWsD0yj4rqUSFX3O2K+0H9xUdwSoxcfv6uXbRhNMRAMWLdhbrU+rX6fW3XqdC/kR1biAK7VVmSFKkKF9oZ4Y6j+ym5UM3/Oo+NOz3Algl4hx/sjjgkv7ZeEHiFIR0PMSw5/spoZE6yfqyzh2Ozz/o+2BpZH2MS7lbVr6g9JbzU1yBNLhLeId/8VkvdfRYiNMsrVA/W2iyNL80nBaE5dke3ydmS5";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = new ClosableVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate= relicTrackables.get(0);
        relicTrackables.activate();
    }

    public void Loop(){
        if(RelicRecoveryVuMark.from(relicTemplate) != RelicRecoveryVuMark.UNKNOWN){
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }
    }

    public void Stop(){
        vuforia.close();
    }

    public RelicRecoveryVuMark getVuMark() {
        return vuMark;
    }
}
