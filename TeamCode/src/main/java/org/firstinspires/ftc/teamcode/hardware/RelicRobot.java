package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Reicher Robotics on 2/22/2018.
 */

public class RelicRobot {
    public HardwareMap hardwareMap;

    public double P = 0.5;
    public double I = 0.0;
    public double D = 0.9;
    public double PID_THRESH = 2.0;

    public double TICKS_PER_REV = 560;
    public double WHEEL_DIAMETER = 6.0;
    public double TICKS_PER_INCH = TICKS_PER_REV / WHEEL_DIAMETER / 3.14159;

    private String                  alignServoNames[] =                 new String[]{"alignExt"};
    private String                  alignDigChNames[] =                 new String[]{"alignSide", "alignBack"};
    private DigitalChannel.Mode     alignDigChModes[] =                 new DigitalChannel.Mode[]{DigitalChannel.Mode.INPUT, DigitalChannel.Mode.INPUT};
    private String                  driveMotorNames[] =                 new String[]{"lFDrive", "lRDrive", "rFDrive", "rRDrive"};
    private DcMotorSimple.Direction driveMotorDirections[] =            new DcMotorSimple.Direction[]{DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.FORWARD};
    private String                  conveyorMotorNames[] =              new String[]{"lConveyor", "rConveyor"};
    private DcMotorSimple.Direction conveyorMotorDirections[] =         new DcMotorSimple.Direction[]{DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE};
    private String                  platformServoNames[] =              new String[]{"lPlat", "rPlat", "gripper"};
    private String                  ramBarCRServoNames[] =              new String[]{"ramBarRot"};
    private CRServo.Direction       ramBarCRServoDirections[] =         new CRServo.Direction[]{CRServo.Direction.REVERSE};
    private String                  ramBarAnInNames[] =                 new String[]{"ramBarAng"};
    private String                  jewelDetectionColorSensorNames[] =  new String[]{"jewDetColor"};
    private String                  jewelRemoverServoNames[] =          new String[]{"jewRemExt", "jewRemHit"};
    private String                  phoneRotatorServoNames[] =          new String[]{"phoneRot"};
    private String                  relicExtMotorNames[] =              new String[]{"relicExt"};
    private DcMotorSimple.Direction relicExtMotorDirections[] =         new DcMotorSimple.Direction[]{DcMotorSimple.Direction.REVERSE};
    private String                  relicGrabServoNames[] =             new String[]{"relicWrist", "relicClaw"};
    private String                  imuNames[] =                        new String[]{"imu"};
    private String                  rangeSensorNames[] =                new String[]{"leftRS", "backRS", "rightRS"};
    private String                  glyphDetectionSensorNames[] =       new String[]{"firGlyphS", "secGlyphS"};

    public AlignmentBar     alignmentBar =      null;
    public DriveMecanum     driveMecanum =      null;
    public GlyphConveyors   glyphConveyors =    null;
    public GlyphPlatform    glyphPlatform =     null;
    public GlyphRamBar      glyphRamBar =       null;
    public JewelDetection   jewelDetection =    null;
    public JewelRemover     jewelRemover =      null;
    public PhoneRotator     phoneRotator =      null;
    public RelicExtension   relicExtension =    null;
    public RelicGrabber     relicGrabber =      null;
    public IMU              gyroIMU =           null;
    public RangeLocator     rangeLocator =      null;
    public GlyphDetection   glyphDetection =    null;

    public RelicRobot(HardwareMap hwM){
        hardwareMap = hwM;

        //alignmentBar =      new AlignmentBar(hardwareMap, alignServoNames, alignDigChNames, alignDigChModes);
        driveMecanum =      new DriveMecanum(hardwareMap, driveMotorNames, driveMotorDirections);
        glyphConveyors =    new GlyphConveyors(hardwareMap, conveyorMotorNames, conveyorMotorDirections);
        glyphPlatform =     new GlyphPlatform(hardwareMap, platformServoNames);
        glyphRamBar =       new GlyphRamBar(hardwareMap, ramBarCRServoNames, ramBarCRServoDirections, ramBarAnInNames);
        jewelDetection =    new JewelDetection(hardwareMap, jewelDetectionColorSensorNames);
        jewelRemover =      new JewelRemover(hardwareMap, jewelRemoverServoNames);
        phoneRotator =      new PhoneRotator(hardwareMap, phoneRotatorServoNames);
        relicExtension =    new RelicExtension(hardwareMap, relicExtMotorNames, relicExtMotorDirections);
        relicGrabber =      new RelicGrabber(hardwareMap, relicGrabServoNames);
        gyroIMU =           new IMU(hardwareMap, imuNames[0]);
        rangeLocator =      new RangeLocator(hardwareMap, rangeSensorNames);
        glyphDetection =    new GlyphDetection(hardwareMap, glyphDetectionSensorNames);

        driveMecanum.setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void Init() {
    }

    public int convertInchToTicks(double inches){
        return (int)(inches * TICKS_PER_INCH);
    }
}