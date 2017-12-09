package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rishabhbector on 12/2/17.
 */

public class RelicController {

    private MotorController leftMotor;
    private  MotorController rightMotor;

    private ServoController leftServo;
    private ServoController rightServo;
    private ServoController clawServo;

    public RelicController(HardwareMap hardwareMap) {
        leftMotor = new MotorController(hardwareMap.get(DcMotor.class, "relicLeftMotor"));
        rightMotor = new MotorController(hardwareMap.get(DcMotor.class, "relicRightMotor"));

        leftServo = new ServoController(hardwareMap.servo.get("relicLeftServo"));
        rightServo = new ServoController(hardwareMap.servo.get("relicRightServo"));
        clawServo = new ServoController(hardwareMap.servo.get("relicClawServo"));
    }

    public void setMotorPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public void swingUp() {
        leftServo.setPosition(0);
        rightServo.setPosition(1);
    }

    public void swingDown() {
        leftServo.setPosition(1);
        rightServo.setPosition(0);
    }

    public void swingStop() {
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.5);
    }

    public void setClawPower(double direction) {
        clawServo.setPosition(direction);
    }
}
