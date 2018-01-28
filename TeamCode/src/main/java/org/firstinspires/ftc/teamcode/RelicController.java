package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rishabhbector on 12/2/17.
 */

public class RelicController {

    private MotorController motor;

    private ServoController leftServo;
    private ServoController rightServo;
    private ServoController clawServo;

    public RelicController(HardwareMap hardwareMap) {
        motor = new MotorController(hardwareMap.get(DcMotor.class, "relicMotor"));

        leftServo = new ServoController(hardwareMap.servo.get("relicLeftServo"));
        rightServo = new ServoController(hardwareMap.servo.get("relicRightServo"));
        clawServo = new ServoController(hardwareMap.servo.get("relicClawServo"));

        //leftServo.setPosition(0.5);
        //rightServo.setPosition(0.5);
        //clawServo.setPosition(0.5);
    }

    public void setMotorPower(double power) {
        motor.setPower(power);
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
