package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Reicher Robotics on 1/14/2018.
 */

public class JewelDetection {

    public enum JewelColor {
        RED,
        BLUE,
        UNKNOWN;
    }

    private int numJewelRemoverColorSensors = 1;
    // 0 = Hitter End
    public ColorSensor jewelRemoverColorSensors[] = new ColorSensor[numJewelRemoverColorSensors];

    private JewelColor jewelColor = JewelColor.UNKNOWN;

    public JewelDetection(HardwareMap jewelMap, String colorSensorNames[]) {
        for(int i = 0; i < numJewelRemoverColorSensors; i++){
            jewelRemoverColorSensors[i] = jewelMap.colorSensor.get(colorSensorNames[i]);
        }
    }

    public JewelColor getJewelColor() {
        return jewelColor;
    }

    public void setJewelColor() {
        if(jewelRemoverColorSensors[0].red() > jewelRemoverColorSensors[0].blue()){
            jewelColor = JewelColor.RED;
        } else if(jewelRemoverColorSensors[0].blue() > jewelRemoverColorSensors[0].red()){
            jewelColor = JewelColor.BLUE;
        } else {
            jewelColor = JewelColor.UNKNOWN;
        }
    }

    public void setJewelColor(JewelColor _jewelColor) {
        jewelColor = _jewelColor;
    }

    public void setJewelColor(int redHue, int blueHue) {
        if(redHue > blueHue){
            jewelColor = JewelColor.RED;
        } else if(blueHue > redHue){
            jewelColor = JewelColor.BLUE;
        } else {
            jewelColor = JewelColor.UNKNOWN;
        }
    }
}
