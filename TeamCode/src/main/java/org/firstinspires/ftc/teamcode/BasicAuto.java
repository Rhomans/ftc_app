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

    double normalPower = 0.35;
    double slowPower = 0.3;
    double turnPower = 0.35;
    double slowTurnPower = 0.35; // jewel turn

    double[] distances1B = {100, 120, 140};
    double[] distances1R = {140, 120, 100};
    double[] distances2B = {55, 75, 95};
    double[] distances2R = {95, 75, 55};
    double dropDistance = 33;

    ///////////////////////////

    private ElapsedTime runtimeAuto = new ElapsedTime();

    private DriveController driveController;
    private VisionController visionController;
    private LiftController liftController;
    private JewelController jewelController;
    private GyroController gyroController;
    private UltrasonicController ultrasonicController;

    public String color;
    public int side;

    public BasicAuto(String c, int s) {
        color = c;
        side = s;
    }

    public void initialize() throws InterruptedException {
        driveController = new DriveController(hardwareMap, telemetry, this);
        visionController = new VisionController(hardwareMap, true, telemetry);
        liftController = new LiftController(hardwareMap, telemetry, this);
        jewelController = new JewelController(hardwareMap, telemetry);
        gyroController = new GyroController(hardwareMap, this);
        ultrasonicController = new UltrasonicController(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        initialize();
        waitForStart();
        runtimeAuto.reset();

        String ballColor = "";

        int pattern = visionController.identifyVisionPattern(telemetry) - 1;
        //int pattern = 0;

        ///   JEWEL   ///

        if(color.equals("blue")) {
            jewelController.leftDown();
            Thread.sleep(1000);
            ballColor = jewelController.leftColor.getColor();
            if(ballColor.equals("blue")) {
                driveController.turnDoubleWheel("left", 20, slowTurnPower, "");
                jewelController.leftUp();
                driveController.turnDoubleWheel("right", 20, slowTurnPower, "");
            }
            if(ballColor.equals("red")) {
                driveController.turnDoubleWheel("right", 20, slowTurnPower, "");
                jewelController.leftUp();
                driveController.turnDoubleWheel("left", 20, slowTurnPower, "");
            }
            jewelController.leftUp();
        }
        if(color.equals("red")) {
            jewelController.rightDown();
            Thread.sleep(1000);
            ballColor = jewelController.rightColor.getColor();
            if(ballColor.equals("blue")) {
                driveController.turnDoubleWheel("left", 20, slowTurnPower, "");
                jewelController.rightUp();
                driveController.turnDoubleWheel("right", 20, slowTurnPower, "");
            }
            if(ballColor.equals("red")) {
                driveController.turnDoubleWheel("right", 20, slowTurnPower, "");
                jewelController.rightUp();
                driveController.turnDoubleWheel("left", 20, slowTurnPower, "");
            }
            jewelController.rightUp();
        }

        telemetry.addData("color:", ballColor);
        telemetry.update();

        jewelController.leftColor.ledOff();
        jewelController.rightColor.ledOff();

        driveController.driveStraight("forward", 60, normalPower, "Forward 1");

        telemetry.addData("Pattern", pattern);
        telemetry.update();

        zeroHeading();

        if(side == 1) {
            if (color.equals("red")) {
                driveController.driveStraightRange(ultrasonicController, "back", distances1R[pattern], slowPower, telemetry);
                driveController.turnDoubleWheel("right", 90, turnPower, "Turn 1");
            }
            if (color.equals("blue")) {
                driveController.driveStraightRange(ultrasonicController, "back", distances1B[pattern], slowPower, telemetry);
                driveController.turnDoubleWheel("left", 90, turnPower, "Turn 1");
            }
            driveController.driveStraightRange(ultrasonicController, "front", dropDistance, slowPower, telemetry);
            liftController.setHighClawPower(1);
            Thread.sleep(500);
            liftController.setHighClawPower(0.5);
        }

        if(side == 2) {
            if(color.equals("red")) {
                driveController.turnDoubleWheel("left", 90, turnPower, "Turn 1");
            }
            if(color.equals("blue")) {
                driveController.turnDoubleWheel("right", 90, turnPower, "Turn 1");
            }
            if(color.equals("red")) {
                driveController.driveStraightRange(ultrasonicController, "back", distances2R[pattern], slowPower, telemetry);
                driveController.turnDoubleWheel("right", 90, turnPower, "Turn 1");
            }
            if(color.equals("blue")) {
                driveController.driveStraightRange(ultrasonicController, "back", distances2B[pattern], slowPower, telemetry);
                driveController.turnDoubleWheel("left", 90, turnPower, "Turn 1");
            }
            driveController.driveStraightRange(ultrasonicController, "front", dropDistance, slowPower, telemetry);
            liftController.setHighClawPower(1);
            Thread.sleep(500);
            liftController.setHighClawPower(0.5);
            driveController.driveStraight("forward", 10, slowPower, "yeet");
            driveController.driveStraight("backward", 10, slowPower, "yeet");
        }
    }

    public void zeroHeading() throws InterruptedException {
        int currentHeading = gyroController.getHeading();
        telemetry.addData("heading:", currentHeading);
        telemetry.update();
        if(currentHeading < 0) {
            driveController.turnDoubleWheel("right", Math.abs(currentHeading), 0.4, "Zero Heading");
        }
        if(currentHeading > 0) {
            driveController.turnDoubleWheel("left", Math.abs(currentHeading), 0.4, "Zero Heading");
        }
    }
}
