package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * This class manages the teleop driver controlled period.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOp extends OpMode {

    ///   Hyperparameters   ///

    private double slowModeScalar = 0.08;

    ///////////////////////////

    private DriveController driveController;
    private LiftController liftController;

    private boolean toggleSlow = false;
    private boolean justToggledSlow = false;

    private float powerLeft;
    private float powerRight;

    @Override
    public void init() {
        driveController = new DriveController(hardwareMap, null);
        liftController = new LiftController(hardwareMap);
        //colorController = new ColorController(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

        // Gamepad 1
        float game1Stick1Y = gamepad1.left_stick_y;
        float game1Stick1X = gamepad1.left_stick_x;
        float game1Stick2Y = gamepad1.right_stick_y;
        float game1Stick2X = gamepad1.right_stick_x;
        boolean game1Stick2B = gamepad1.right_stick_button;
        boolean game1Stick1B = gamepad1.left_stick_button;
        boolean game1A = gamepad1.a;
        boolean game1B = gamepad1.b;
        boolean game1X = gamepad1.x;
        boolean game1Y = gamepad1.y;
        float game1LT = gamepad1.left_trigger;
        float game1RT = gamepad1.right_trigger;
        boolean game1LB = gamepad1.left_bumper;
        boolean game1RB = gamepad1.right_bumper;
        boolean game1Up = gamepad1.dpad_up;
        boolean game1Down = gamepad1.dpad_down;
        boolean game1Right = gamepad1.dpad_right;
        boolean game1Left = gamepad1.dpad_left;

        // Gamepad 2
        float game2StickX = gamepad2.right_stick_y;
        float game2StickY = gamepad2.left_stick_y;
        boolean game2A = gamepad2.a;
        boolean game2B = gamepad2.b;
        boolean game2X = gamepad2.x;
        boolean game2Y = gamepad2.y;
        float game2LT = gamepad2.left_trigger;
        float game2RT = gamepad2.right_trigger;
        boolean game2LB = gamepad2.left_bumper;
        boolean game2RB = gamepad2.right_bumper;
        boolean game2Up = gamepad2.dpad_up;
        boolean game2Down = gamepad2.dpad_down;
        boolean game2Right = gamepad2.dpad_right;
        boolean game2Left = gamepad2.dpad_left;

        game1Stick1Y = Range.clip(game1Stick1Y, -1, 1);
        game1Stick2Y = Range.clip(game1Stick2Y, -1, 1);
        game1Stick1X = Range.clip(game1Stick1X, -1, 1);
        game1Stick2X = Range.clip(game1Stick2X, -1, 1);

        ///   Drive Controller   ///

        powerLeft = (float) scaleInput(game1Stick1Y);
        powerRight = (float) scaleInput(game1Stick2Y);

        if(toggleSlow) {
            powerLeft *= slowModeScalar;
            powerRight *= slowModeScalar;
        }

        telemetry.addData("L Power:", powerLeft);
        telemetry.addData("R Power:", powerRight);
        driveController.setIndividualPower(powerLeft, powerRight);

        if(game1A && !justToggledSlow) {
            if(toggleSlow) {
                telemetry.addData("Slowmode:", "Off");
                toggleSlow = false;
            } else {
                telemetry.addData("Slowmode:", "On");
                toggleSlow = true;
            }
            justToggledSlow = true;
        }
        if(!game1A) {
            justToggledSlow = false;
        }

        ///   Lift Controller   ///

        if(game2Up) {
            liftController.setLiftPower(1);
        }
        if(game2Down) {
            liftController.setLiftPower(-1);
        }
        if(!game2Up && !game2Down) {
            liftController.setLiftPower(0);
        }

        telemetry.addData("Trigger", game2LT);

        telemetry.update();
    }

    private double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        // index should be positive.
        if (index < 0) {
            index = -index;
        }
        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }
        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }
        return dScale;
    }
}


