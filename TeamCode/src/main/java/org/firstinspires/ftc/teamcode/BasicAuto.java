package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Basic Auto", group="Linear Opmode")

public class BasicAuto extends LinearOpMode {

    private ElapsedTime runtimeAuto = new ElapsedTime();

    private DriveController driveController;

    public void initialize() {
        driveController = new DriveController(hardwareMap, telemetry, this);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        waitForStart();
        runtimeAuto.reset();

        initialize();
        driveController.driveStraight(100, 0.5, "Straight 1");
        driveController.turnDoubleWheel("right", 90, 0.5, "Right 1");
    }
}