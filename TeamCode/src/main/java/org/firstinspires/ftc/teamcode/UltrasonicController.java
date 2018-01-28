package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * This class manages all range sensor methods.
 */

public class UltrasonicController {

    public double prevRange;

    //public ModernRoboticsI2cRangeSensor rangeSensor;
    //public ModernRoboticsI2cRangeSensor rangeSensor2;

    byte[] rangeAcache;
    byte[] rangeCcache;

    I2cDevice rangeSensor;
    I2cDevice rangeSensor2;
    I2cDeviceSynch rangeAreader;
    I2cDeviceSynch rangeCreader;

    public UltrasonicController(HardwareMap hm2) {
        HardwareMap hardwareMap = hm2;

        rangeSensor = hardwareMap.i2cDevice.get("range");
        rangeSensor2 = hardwareMap.i2cDevice.get("range2");

        rangeAreader = new I2cDeviceSynchImpl(rangeSensor, I2cAddr.create8bit(0x2a), false);
        rangeCreader = new I2cDeviceSynchImpl(rangeSensor2, I2cAddr.create8bit(0x3a), false);

        rangeAreader.engage();
        rangeCreader.engage();
    }

    public double getRange(String direction) {
        if(direction.equals("front")) {
            rangeCcache = rangeCreader.read(0x04, 2);  //Read 2 bytes starting at 0x04
            int RUS = rangeCcache[0] & 0xFF;
            return RUS;
        }
        rangeAcache = rangeAreader.read(0x04, 2);
        int LUS = rangeAcache[0] & 0xFF;
        return LUS;
    }
}






