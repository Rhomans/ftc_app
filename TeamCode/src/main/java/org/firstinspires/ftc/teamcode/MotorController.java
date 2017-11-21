package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by rishabhbector on 10/21/17.
 */

public class MotorController {

    public DcMotor motor;

    public MotorController(DcMotor inMotor) {
        motor = inMotor;
    }

    public void setPower(double power) {
        motor.setPower(power);
    }
}
