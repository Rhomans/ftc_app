package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by rishabhbector on 1/6/18.
 */

public class JewelController {

    public ServoController leftServo;
    public ServoController rightServo;

    public ColorController leftColor;
    public ColorController rightColor;

    Telemetry telemetry;
    OpMode opmode;

    public JewelController(HardwareMap hardwaremap, Telemetry tel) {
        leftServo = new ServoController(hardwaremap.servo.get("jewelLeft"));
        rightServo = new ServoController(hardwaremap.servo.get("jewelRight"));
        leftColor = new ColorController(hardwaremap.colorSensor.get("jewelLeftSensor"), I2cAddr.create8bit(0x4c), tel);
        rightColor = new ColorController(hardwaremap.colorSensor.get("jewelRightSensor"), I2cAddr.create8bit(0x3c), tel);
        leftColor.ledOn();
        rightColor.ledOn();
    }

    public void leftDown() {
        rightServo.setPosition(0.5);
        leftServo.setPosition(1);
    }

    public void leftUp() {
        rightServo.setPosition(0.5);
        leftServo.setPosition(0);
    }

    public void rightDown() {
        leftServo.setPosition(0.5);
        rightServo.setPosition(0);
    }

    public void rightUp() {
        leftServo.setPosition(0.5);
        rightServo.setPosition(1);
    }
}
