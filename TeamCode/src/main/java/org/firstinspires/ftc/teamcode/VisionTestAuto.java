package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Vision Test2", group="Linear Opmode")

public class VisionTestAuto extends LinearOpMode {

    private ElapsedTime runtimeAuto = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        VisionController vision = new VisionController(hardwareMap,false,telemetry);
        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        waitForStart();
        runtimeAuto.reset();


        telemetry.addData("Status", "Running temp");
        telemetry.update();

        vision.temp2(telemetry);

        telemetry.addData("Status", "Temp run; waiting");
        telemetry.update();

        try {
            Thread.sleep(10000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        telemetry.addData("Status", "Running temp");
        telemetry.update();

        vision.temp2(telemetry);
        telemetry.addData("Status", "Done");
        telemetry.update();

        try {
            Thread.sleep(10000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }
}
