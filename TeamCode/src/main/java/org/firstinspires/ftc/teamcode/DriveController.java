package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * This class manages all motor and drive base methods.
 * FYI, the grey comments are just things to notice, but the green ones are top priority
 */

public class DriveController {

    ///   HYPERPARAMETERS   ///

    private double robotWidthIN = 17;
    private double robotWidthCM = robotWidthIN * 2.54;

    ///////////////////////////

    public MotorController driveLeftFront;
    public MotorController driveLeftBack;
    public MotorController driveRightFront;
    public MotorController driveRightBack;

    public DriveController(HardwareMap hMap, LinearOpMode linearOpMode) {

        HardwareMap hardwareMap = hMap;

        driveLeftFront  = new MotorController(hardwareMap.get(DcMotor.class, "driveLeftFront"));
        driveLeftBack = new MotorController(hardwareMap.get(DcMotor.class, "driveLeftBack"));
        driveRightFront = new MotorController(hardwareMap.get(DcMotor.class, "driveRightFront"));
        driveRightBack = new MotorController(hardwareMap.get(DcMotor.class, "driveRightBack"));


        driveLeftFront.motor.setDirection(DcMotor.Direction.REVERSE);
        driveLeftBack.motor.setDirection(DcMotor.Direction.REVERSE);

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

    public double getTicks(double cm) {
        double ticks = cm;
        return ticks;
    }

    public void turnSingleWheel(String direction, double degrees, double power) {
        initEncoders();
        double distanceCM = (2 * robotWidthCM * Math.PI) * (degrees / 360);
        double ticks = getTicks(distanceCM);
        while(Math.abs(driveLeftBack.motor.getCurrentPosition()) < ticks) {
            if(direction.equals("right")) {
                setIndividualPower(power, 0);
            }
            if(direction.equals("left")) {
                setIndividualPower(0, power);
            }
        }
        setPower(0);
    }

    public void turnDoubleWheel(String direction, double degrees, double power) {
        initEncoders();
        double distanceCM = (robotWidthCM * Math.PI) * (degrees / 360);
        double ticks = getTicks(distanceCM);
        while(Math.abs(driveLeftBack.motor.getCurrentPosition()) < ticks) {
            if(direction.equals("right")) {
                setIndividualPower(power, -power);
            }
            if(direction.equals("left")) {
                setIndividualPower(-power, power);
            }
        }
    }

    public void turnCurve(String direction, double degrees, double power, double radiusCM) {
        initEncoders();
        double distance1CM = (2 * Math.PI * (radiusCM - robotWidthCM)) * (degrees / 360);
        double distance2CM = (2 * Math.PI * robotWidthCM) * (degrees / 360);
        double ticks1 = getTicks(distance1CM);
        double ticks2 = getTicks(distance2CM);

    }

    public void driveEncoders(double distanceCM, double power) throws InterruptedException{
         initEncoders();
         double ticks = getTicks(distanceCM);
         while(Math.abs(driveLeftBack.motor.getCurrentPosition()) < ticks) {
             driveLeftBack.setPower(power);
             driveLeftFront.setPower(power);
             driveRightBack.setPower(power);
             driveRightFront.setPower(power);
         }
         setPower(0);
    }
}
