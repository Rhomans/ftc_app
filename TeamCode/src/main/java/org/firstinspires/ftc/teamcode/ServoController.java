package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rishabhbector on 10/23/17.
 */

public class ServoController {

    public Servo servo;

    public ServoController(Servo inServo) {
        servo = inServo;
    }

    public void setPosition(double position) {
        servo.setPosition(position);
    }
}
