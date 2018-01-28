package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by rishabhbector on 1/17/18.
 */

public class GyroController {

    GyroSensor gyro;

    public GyroController(HardwareMap hardwareMap, LinearOpMode linearOpMode) throws InterruptedException {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        while(gyro.isCalibrating()) {
            Thread.sleep(10);
        }
        gyro.resetZAxisIntegrator();
    }

    public int getHeading() {
        int currentHeading = gyro.getHeading();
        int output = currentHeading;
        if(currentHeading > 180 && currentHeading < 360) {
            output = currentHeading - 360;
        }
        return output * -1;
    }
}
