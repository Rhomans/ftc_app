package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Test Auto", group="Linear Opmode")

public class TestAuto extends LinearOpMode {

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
    private UltrasonicController ultrasonicController;
    private JewelController jewelController;
    private GyroController gyroController;

    public String color;
    public int side;

    public TestAuto(String c, int s) {
        color = c;
        side = s;
    }

    public void initialize() throws InterruptedException {
        driveController = new DriveController(hardwareMap, telemetry, this);
        //visionController = new VisionController(hardwareMap, true, telemetry);
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

        while(true) {
            telemetry.addData("dist:", ultrasonicController.getRange("back"));
            telemetry.update();
            Thread.sleep(200);
        }

        ///   JEWEL   ///

        /*if(color.equals("blue")) {
            jewelController.leftDown();
            Thread.sleep(2000);
            ballColor = jewelController.leftColor.getColor();
            Thread.sleep(1000);
            knockJewel(ballColor);
        }
        if(color.equals("red")) {
            jewelController.rightDown();
            Thread.sleep(2000);
            ballColor = jewelController.rightColor.getColor();
            Thread.sleep(1000);
            knockJewel(ballColor);
        }*/

        /*telemetry.addData("color:", ballColor);
        telemetry.update();

        jewelController.leftColor.ledOff();
        jewelController.rightColor.ledOff();

        int pattern = visionController.identifyVisionPattern(telemetry);
        Thread.sleep(3000);
        telemetry.addData("Pattern", pattern);
        telemetry.update();

        driveController.driveStraight(60, normalPower, "Forward 1");

        if(side == 1) {
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
            jewelController.rightUp();
            driveController.turnDoubleWheel("right", 20, 0.4, "");
        }
        if(ballColor.equals("red")) {
            driveController.turnDoubleWheel("right", 20, 0.4, "");
            jewelController.rightUp();
            jewelController.leftUp();
            driveController.turnDoubleWheel("left", 20, 0.4, "");
        }
    }

    public void zeroHeading() throws InterruptedException {
        int currentHeading = gyroController.getHeading();
        telemetry.addData("heading:", currentHeading);
        telemetry.update();
        if(currentHeading < 0) {
            driveController.turnDoubleWheel("right", Math.abs(currentHeading), 0.5, "Zero Heading");
        }
        if(currentHeading > 0) {
            driveController.turnDoubleWheel("left", Math.abs(currentHeading), 0.5, "Zero Heading");
        }
    }
}
