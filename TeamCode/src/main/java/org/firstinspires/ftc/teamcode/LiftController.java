package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rishabhbector on 11/1/17.
 */

public class LiftController {

    private MotorController liftMotor;
    private ServoController leftServo;
    private ServoController rightServo;

    public LiftController(HardwareMap hardwareMap) {
        liftMotor = new MotorController(hardwareMap.get(DcMotor.class, "liftMotor"));
        leftServo = new ServoController(hardwareMap.servo.get("leftServo"));
        rightServo = new ServoController(hardwareMap.servo.get("rightServo"));
    }

    public void setLiftPower(double power) {
        liftMotor.setPower(power);
    }

    public void inServos() {
        leftServo.setPosition(1);
        rightServo.setPosition(1);
    }

    public void outServos() {
        leftServo.setPosition(0);
        rightServo.setPosition(0);
    }

    public void stopServos() {
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.5);
    }

}
