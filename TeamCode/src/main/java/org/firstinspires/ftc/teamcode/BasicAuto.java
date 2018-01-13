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

    ///   HYPERPARAMETERS   ///

    double normalPower = 0.7;
    double slowPower = 0.4;
    double turnPower = 0.7;

    double[] distances1 = {12, 30, 47};
    double[] distances2 = {};

    ///////////////////////////

    private ElapsedTime runtimeAuto = new ElapsedTime();

    private DriveController driveController;
    private VisionController visionController;
    private LiftController liftController;
    private JewelController jewelController;

    public String color;
    public int side;

    public BasicAuto(String c, int s) {
        color = c;
        side = s;
    }

    public void initialize() {
        driveController = new DriveController(hardwareMap, telemetry, this);
        visionController = new VisionController(hardwareMap, true, telemetry);
        //liftController = new LiftController(hardwareMap, telemetry, this);
        jewelController = new JewelController(hardwareMap, telemetry);
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

        String ballColor = "";

        ///   JEWEL   ///

        if(color.equals("blue")) {
            jewelController.leftDown();
            Thread.sleep(1000);
            ballColor = jewelController.leftColor.getColor();
            knockJewel(ballColor);
        }
        if(color.equals("red")) {
            jewelController.rightDown();
            Thread.sleep(1000);
            ballColor = jewelController.rightColor.getColor();
            knockJewel(ballColor);
        }

        telemetry.addData("color:", ballColor);
        telemetry.update();

        jewelController.leftColor.ledOff();
        jewelController.rightColor.ledOff();



        //driveController.driveStraight(100, 0.5, "Straight 1");
        //driveController.turnDoubleWheel("right", 90, 0.5, "Right 1");

        /*int pattern = visionController.identifyVisionPattern(telemetry);
        telemetry.addData("Pattern", pattern);
        telemetry.update();

        if(side == 1) {
            driveController.driveStraight(60, normalPower, "Forward 1");
            driveController.driveStraight(distances1[pattern], normalPower, "Forward 2");

            if (color.equals("red")) {
                driveController.turnDoubleWheel("right", 90, turnPower, "Turn 1");
            }

            if (color.equals("blue")) {
                driveController.turnDoubleWheel("left", 90, turnPower, "Turn 1");
            }

            driveController.driveStraight(15, slowPower, "Forward 3");
        }*/
    }

    public void knockJewel(String ballColor) throws InterruptedException {
        if(ballColor.equals("blue")) {
            driveController.turnDoubleWheel("left", 20, 0.4, "");
            jewelController.leftUp();
            driveController.turnDoubleWheel("right", 20, 0.4, "");
        }
        if(ballColor.equals("red")) {
            driveController.turnDoubleWheel("right", 20, 0.4, "");
            jewelController.rightUp();
            driveController.turnDoubleWheel("left", 20, 0.4, "");
        }
    }

}

//pseudocode here