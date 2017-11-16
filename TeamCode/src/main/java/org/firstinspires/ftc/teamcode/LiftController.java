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
    private ServoController leftLowServo;
    private ServoController rightLowServo;
    private ServoController leftHighServo;
    private ServoController rightHighServo;

    public LiftController(HardwareMap hardwareMap) {
        liftMotor = new MotorController(hardwareMap.get(DcMotor.class, "liftMotor"));
        leftHighServo = new ServoController(hardwareMap.servo.get("leftHighServo"));
        rightHighServo = new ServoController(hardwareMap.servo.get("rightHighServo"));
        leftLowServo = new ServoController(hardwareMap.servo.get("leftLowServo"));
        rightLowServo = new ServoController(hardwareMap.servo.get("rightLowServo"));
    }

    public void setLiftPower(double power) {
        liftMotor.setPower(power);
    }

    public void inLowServos() {
        leftLowServo.setPosition(1);
        rightLowServo.setPosition(1);
    }

    public void outLowServos() {
        leftLowServo.setPosition(0);
        rightLowServo.setPosition(0);
    }

    public void stopLowServos() {
        leftLowServo.setPosition(0.5);
        rightLowServo.setPosition(0.5);
    }

    public void inHighServos() {
        leftHighServo.setPosition(1);
        rightHighServo.setPosition(1);
    }

    public void outHighServos() {
        leftHighServo.setPosition(0);
        rightHighServo.setPosition(0);
    }

    public void stopHighServos() {
        leftHighServo.setPosition(0.5);
        rightHighServo.setPosition(0.5);
    }

}
