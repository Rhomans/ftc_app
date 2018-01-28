package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * This class manages all motor and drive base methods.
 * FYI, the grey comments are just things to notice, but the green ones are top priority
 */

public class DriveController {

    ///   HYPERPARAMETERS   ///

    private double robotWidthCM = 38.0;

    private double cmPerTick = 289.5 / 5200.0;

    private double safeArea = 50;

    ///////////////////////////

    public MotorController driveLeftFront;
    public MotorController driveLeftBack;
    public MotorController driveRightFront;
    public MotorController driveRightBack;

    ///////////////////////////

    Telemetry telemetry;
    LinearOpMode linearOpMode;

    public DriveController(HardwareMap hMap, Telemetry tel, LinearOpMode lin) {

        HardwareMap hardwareMap = hMap;
        telemetry = tel;
        linearOpMode = lin;

        driveLeftFront  = new MotorController(hardwareMap.get(DcMotor.class, "driveLeftFront"));
        driveLeftBack = new MotorController(hardwareMap.get(DcMotor.class, "driveLeftBack"));
        driveRightFront = new MotorController(hardwareMap.get(DcMotor.class, "driveRightFront"));
        driveRightBack = new MotorController(hardwareMap.get(DcMotor.class, "driveRightBack"));


        driveRightFront.motor.setDirection(DcMotor.Direction.REVERSE);
        driveRightBack.motor.setDirection(DcMotor.Direction.REVERSE);

        driveRightBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRightFront.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveRightBack.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveRightFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetEncoders() throws InterruptedException {
        driveRightBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftBack.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRightFront.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Thread.sleep(2000);
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

    public double cmToTicks(double cm) {
        return cm / cmPerTick;
    }

    public double[] getTicks() {
        return new double[] {driveLeftBack.motor.getCurrentPosition(), driveRightBack.motor.getCurrentPosition() * -1};
    }

    public void turnSingleWheel(String direction, double degrees, double power, String moveName) throws InterruptedException {
        telemetry.addData("Stage:", moveName);
        telemetry.update();

        resetEncoders();

        double distanceCM = (2 * robotWidthCM * Math.PI) * (degrees / 360);
        double ticks = cmToTicks(distanceCM);
        double target = 0;
        if(direction.equals("right")) {
            target = (driveLeftBack.motor.getCurrentPosition() + ticks);
            driveLeftBack.motor.setTargetPosition((int)target);
            driveLeftFront.motor.setTargetPosition((int)target);
            driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            setIndividualPower(power, 0);
        }
        if(direction.equals("left")) {
            target = (driveRightBack.motor.getCurrentPosition() + ticks);
            driveRightBack.motor.setTargetPosition((int)target);
            driveRightFront.motor.setTargetPosition((int)target);
            driveRightBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            driveRightFront.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            setIndividualPower(0, power);
        }
        while(linearOpMode.opModeIsActive() && (driveLeftBack.motor.isBusy() || driveRightBack.motor.isBusy())) {
            double[] currentTicks = getTicks();
            telemetry.addData("Target", target);
            telemetry.addData("Left", currentTicks[0]);
            telemetry.addData("Right", currentTicks[1]);
            telemetry.addData("Left Busy", driveLeftBack.motor.isBusy());
            telemetry.addData("Right Busy", driveRightBack.motor.isBusy());
            telemetry.update();
        }
        setPower(0);
        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turnDoubleWheel(String direction, double degrees, double power, String moveName) throws InterruptedException {
        telemetry.addData("Stage:", moveName);
        telemetry.update();

        resetEncoders();

        double distanceCM = (robotWidthCM * Math.PI) * (degrees / 360);
        double ticks = cmToTicks(distanceCM);
        double targetLeft = 0;
        double targetRight = 0;

        if(direction.equals("right")) {
            targetLeft = (driveLeftBack.motor.getCurrentPosition() + ticks);
            targetRight = (driveRightBack.motor.getCurrentPosition() - ticks);
        }
        if(direction.equals("left")) {
            targetLeft = (driveLeftBack.motor.getCurrentPosition() - ticks);
            targetRight = (driveRightBack.motor.getCurrentPosition() + ticks);
        }

        driveLeftBack.motor.setTargetPosition((int)targetLeft);
        driveLeftFront.motor.setTargetPosition((int)targetLeft);
        driveRightBack.motor.setTargetPosition((int)targetRight);
        driveRightFront.motor.setTargetPosition((int)targetRight);
        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRightBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRightFront.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(direction.equals("right")) {
            setIndividualPower(power, -power);
        }
        if(direction.equals("left")) {
            setIndividualPower(-power, power);
        }

        while(linearOpMode.opModeIsActive() && (driveLeftBack.motor.isBusy() || driveRightBack.motor.isBusy())) {
            double[] currentTicks = getTicks();
            telemetry.addData("TargetLeft", targetLeft);
            telemetry.addData("TargetRight", targetRight);
            telemetry.addData("Left", currentTicks[0]);
            telemetry.addData("Right", currentTicks[1]);
            telemetry.addData("Left Busy", driveLeftBack.motor.isBusy());
            telemetry.addData("Right Busy", driveRightBack.motor.isBusy());
            telemetry.update();

            if((currentTicks[0] < targetLeft + safeArea) && (currentTicks[0] > targetLeft - safeArea)) {
                if((currentTicks[1] < targetRight + safeArea) && (currentTicks[1] > targetRight - safeArea)) {
                    setPower(0);
                    return;
                }
            }
        }

        setPower(0);


    }

    public void driveStraight(String dir, double distanceCM, double power, String moveName) throws InterruptedException {
        telemetry.addData("Stage:", moveName);
        telemetry.update();

        resetEncoders();

        double distanceTicks = cmToTicks(distanceCM);
        //double distanceTicks = distanceCM;

        double targetLeft = 0;
        double targetRight = 0;

        if(dir.equals("forward")) {
            targetLeft = (driveLeftBack.motor.getCurrentPosition() + distanceTicks);
            targetRight = (driveRightBack.motor.getCurrentPosition() + distanceTicks);
        }
        if(dir.equals("backward")) {
            targetLeft = (driveLeftBack.motor.getCurrentPosition() - distanceTicks);
            targetRight = (driveRightBack.motor.getCurrentPosition() - distanceTicks);
        }

        driveLeftBack.motor.setTargetPosition((int)targetLeft);
        driveLeftFront.motor.setTargetPosition((int)targetLeft);
        driveRightBack.motor.setTargetPosition((int)targetRight);
        driveRightFront.motor.setTargetPosition((int)targetRight);

        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRightBack.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRightFront.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(0.4);

        while(linearOpMode.opModeIsActive() && (driveLeftBack.motor.isBusy() || driveRightBack.motor.isBusy())) {
            double[] ticks = getTicks();
            telemetry.addData("TargetLeft", targetLeft);
            telemetry.addData("TargetRight", targetRight);
            telemetry.addData("Left", ticks[0]);
            telemetry.addData("Right", ticks[1]);
            telemetry.addData("Left Busy", driveLeftBack.motor.isBusy());
            telemetry.addData("Right Busy", driveRightBack.motor.isBusy());
            telemetry.update();

            if((ticks[0] < targetLeft + safeArea) && (ticks[0] > targetLeft - safeArea)) {
                if((ticks[1] < targetRight + safeArea) && (ticks[1] > targetRight - safeArea)) {
                    break;
                }
            }
        }

        setPower(0);

        driveLeftBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightBack.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightFront.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveStraightRange(UltrasonicController ultrasonicController, String direction, double distanceCM, double power, Telemetry telemetry) throws InterruptedException {
        double currentDistance = ultrasonicController.getRange(direction);
        telemetry.addData("CurrentD:", currentDistance);
        telemetry.update();
        if(direction.equals("front")) {
            if (currentDistance > distanceCM) {
                double difference = currentDistance - distanceCM;
                driveStraight("forward", difference, power, "");
            }
            if (distanceCM > currentDistance) {
                double difference = distanceCM - currentDistance;
                driveStraight("backward", difference, power, "");
            }
        }
        if(direction.equals("back")) {
            if (currentDistance > distanceCM) {
                double difference = currentDistance - distanceCM;
                driveStraight("backward", difference, power, "");
            }
            if (distanceCM > currentDistance) {
                double difference = distanceCM - currentDistance;
                driveStraight("forward", difference, power, "");
            }
        }
    }
}
