package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by rishabhbector on 11/1/17.
 */

public class LiftController {

    public MotorController liftMotor;
    private ServoController highServo;
    private ServoController lowServo;

    Telemetry telemetry;
    OpMode opMode;

    public int[] positions = {0, 1400, 2180, 3120};
    public int currentPosition = 0;

    public LiftController(HardwareMap hardwareMap, Telemetry tel, OpMode op) {
        liftMotor = new MotorController(hardwareMap.get(DcMotor.class, "liftMotor"));
        highServo = new ServoController(hardwareMap.servo.get("highServo"));
        lowServo = new ServoController(hardwareMap.servo.get("lowServo"));

        telemetry = tel;
        opMode = op;

        liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getLiftPosition() {
        return liftMotor.motor.getCurrentPosition();
    }

    public void liftUp() {
        currentPosition += 1;
        if(currentPosition > 3) {
            currentPosition = 3;
        }

        liftMotor.motor.setTargetPosition(positions[currentPosition]);
        liftMotor.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor.motor.setPower(1);

        while(liftMotor.motor.isBusy()) {
            telemetry.addData("liftPos:", getLiftPosition());
            telemetry.addData("liftCur:", currentPosition);
            telemetry.addData("liftTar:", positions[currentPosition]);
            telemetry.update();
        }

        liftMotor.setPower(0);
    }

    public void liftDown() {
        currentPosition -= 1;
        if(currentPosition < 0) {
            currentPosition = 0;
        }
        liftMotor.motor.setTargetPosition(positions[currentPosition]);
        liftMotor.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor.setPower(-1);

        while(liftMotor.motor.isBusy()) {
            telemetry.addData("liftPos:", getLiftPosition());
            telemetry.addData("liftCur:", currentPosition);
            telemetry.addData("liftTar:", positions[currentPosition]);
            telemetry.update();
        }

        liftMotor.setPower(0);
    }

    public void setHighClawPower(double direction) {
        highServo.setPosition(direction);
    }

    public void setLowClawPower(double direction) {
        lowServo.setPosition(direction);
    }
}