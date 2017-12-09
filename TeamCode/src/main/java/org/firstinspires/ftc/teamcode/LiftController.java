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
    private ServoController highServo;
    private ServoController lowServo;

    public LiftController(HardwareMap hardwareMap) {
        liftMotor = new MotorController(hardwareMap.get(DcMotor.class, "liftMotor"));
        highServo = new ServoController(hardwareMap.servo.get("highServo"));
        lowServo = new ServoController(hardwareMap.servo.get("lowServo"));

        liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMotorPower(double power) {
        liftMotor.setPower(-power);
    }

    public void setHighClawPower(double direction) {
        highServo.setPosition(direction);
    }

    public void setLowClawPower(double direction) {
        lowServo.setPosition(direction);
    }
}
