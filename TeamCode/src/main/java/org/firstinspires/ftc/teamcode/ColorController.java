package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This class manages color sensor methods.
 */

public class ColorController {

    HardwareMap hardwareMap = null;
    Telemetry telemetry;
    ColorSensor colorSensor;

    public ColorController(ColorSensor sensor, I2cAddr address, Telemetry tel) {
        colorSensor = sensor;
        colorSensor.setI2cAddress(address);
        telemetry = tel;
    }

    public void ledOn() {
        colorSensor.enableLed(true);
    }

    public void ledOff() {
        colorSensor.enableLed(false);
    }

    public String getColor() throws InterruptedException {
        int redVal = colorSensor.red();
        int blueVal = colorSensor.blue();
        int greenVal = colorSensor.green();
        int alphaVal = colorSensor.alpha();

        Thread.sleep(20);

        if(redVal > blueVal && redVal > greenVal) {
            return "red";
        }
        if(blueVal > redVal && blueVal > greenVal) {
            return "blue";
        }

        return "none";
    }

    public void printValues() {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Alpha", colorSensor.alpha());
        telemetry.update();
    }

}
