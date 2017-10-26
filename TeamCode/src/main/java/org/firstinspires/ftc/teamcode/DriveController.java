package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * This class manages all motor and drive base methods.
 * FYI, the grey comments are just things to notice, but the green ones are top priority
 */

public class DriveController {

    public MotorController driveLeftFront;
    public MotorController driveLeftBack;
    public MotorController driveRightFront;
    public MotorController driveRightBack;

    private double cmPerTick = 100;

    public DriveController(HardwareMap hMap, LinearOpMode linearOpMode) {

        HardwareMap hardwareMap = hMap;

        driveLeftFront  = new MotorController(hardwareMap.get(DcMotor.class, "drive_left_front"));
        driveLeftBack = new MotorController(hardwareMap.get(DcMotor.class, "drive_left_back"));
        driveRightFront = new MotorController(hardwareMap.get(DcMotor.class, "drive_right_front"));
        driveRightBack = new MotorController(hardwareMap.get(DcMotor.class, "drive_right_back"));

        /**
         //reverse the motors that need to be reversed
         driveLeftFront.setDirection(DcMotor.Direction.???);
         driveLeftBack.setDirection(DcMotor.Direction.???);
         driveRightFront.setDirection(DcMotor.Direction.???);
         driveRightBack.setDirection(DcMotor.Direction.???);
         */

         driveRightBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         driveLeftBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

         driveRightBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         driveRightFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void initEncoders() {
        driveRightBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRightBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //driveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //driveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setPower(double power) {
        driveRightFront.setPower(power);
        driveRightBack.setPower(power);
        driveLeftFront.setPower(power);
        driveLeftBack.setPower(power);
    }

    public void setIndividualPower(double l, double r) {
        driveRightFront.setPower(r);
        driveRightBack.setPower(r);
        driveLeftFront.setPower(l);
        driveLeftBack.setPower(l);
    }

    public void driveEncoders(double cm, double lpower, double rpower, Telemetry telemetry, LinearOpMode linearOpMode) throws InterruptedException{

         initEncoders();

         double ticks = cm / cmPerTick;

         driveRightBack.motor.setTargetPosition((int)ticks);
         driveLeftBack.motor.setTargetPosition((int)ticks);

         driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         driveRightBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

         setIndividualPower(lpower, rpower);

         while((linearOpMode == null || linearOpMode.opModeIsActive()) && Math.abs(driveLeftBack.motor.getCurrentPosition()) < ticks) {
             driveLeftBack.setPower(lpower);
             driveLeftFront.setPower(0);
             driveRightBack.setPower(rpower);
             driveRightFront.setPower(0);
         }

         setPower(0);

         driveRightFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         driveRightBack.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

}
