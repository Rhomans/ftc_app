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
    // Declare OpMode members. AKA, variables and shit.
    public DcMotor driveLeftFront;
    public DcMotor driveLeftBack;
    public DcMotor driveRightFront;
    public DcMotor driveRightBack;

    public DriveController(HardwareMap hMap, LinearOpMode linearOpMode) {

        HardwareMap hardwareMap = hMap;

        //initialize hardware shit. N.B. Names MUST correspond to those in the phones configuration.
        driveLeftFront  = hardwareMap.get(DcMotor.class, "drive_left_front");
        driveLeftBack = hardwareMap.get(DcMotor.class, "drive_left_back");
        driveRightFront = hardwareMap.get(DcMotor.class, "drive_right_front");
        driveRightBack = hardwareMap.get(DcMotor.class, "drive_right_back");

    }
           
    public void initEncoders() {
        driveRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setPower(double power) {
        driveRightFront.setPower(power);
        driveRightBack.setPower(power);
        driveLeftFront.setPower(power);
        driveLeftBack.setPower(power);
    }

    public void setPowerEncoderMotors(double power) {
        driveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveRightFront.setPower(0);
        driveLeftFront.setPower(0);
        driveRightBack.setPower(power);
        driveLeftBack.setPower(power);
    }

    public void setBothPowers(double l, double r) {
        driveRightFront.setPower(r);
        driveRightBack.setPower(r);
        driveLeftFront.setPower(l);
        driveLeftBack.setPower(l);
    }

    public void turnGyro(double degres, Telemetry telemetry, int speed/* speed is between 1 and 5 inclusive*/ ){
        telemetry.addData("Error", "Code not writen yet: DriveController.turnGyro");
        
    }


    /*public void moveForwardWorking(double cm, double lpower, double rpower, boolean coast, Telemetry telemetry, LinearOpMode linearOpMode) throws InterruptedException{

        initEncoders();

        double ticks = cm / cmPerTick;

        driveRightBack.setTargetPosition((int)ticks);
        driveLeftBack.setTargetPosition((int)ticks);

        // Turn On RUN_TO_POSITION
        driveLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveLeftBack.setPower(lpower);
        driveLeftFront.setPower(0);
        driveRightBack.setPower(rpower);
        driveRightFront.setPower(0);

        while((linearOpMode == null || linearOpMode.opModeIsActive()) && Math.abs(driveLeftBack.getCurrentPosition()) < ticks) { //(changed 11/3)
            // WAIT
            telemetry.addData("Right ", driveRightBack.getCurrentPosition());
            telemetry.addData("Left ", driveLeftBack.getCurrentPosition());
            telemetry.addData("cm", cm);
            telemetry.addData("Target Ticks: ", ticks);
            telemetry.update();


            Thread.sleep(10);
        }

        if (coast) {
            driveLeftFront.setPower(0);
            driveRightFront.setPower(0);
            Thread.sleep(1000);
        }
        setPower(0);

        driveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }*/

}
