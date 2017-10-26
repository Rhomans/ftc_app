package org.firstinspires.ftc.teamcode;

/**
 * Created by The Senate on 10/25/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class VisionController{

    //this is important, but I don't know what is does
    //I think this is essentialy the thing that is used. whatever.
    VuforiaLocalizer vuforia;
    public VisionController(HardwareMap hMap, boolean isFront, Telemetry telemetry){

        //Debugging telemetry
        telemetry.addData("Vision:", "Preparing...");
        telemetry.update();

        //hardwareMap is used to get the camera
        HardwareMap hardwareMap = hMap;



        //Gets the camera view, for veiwing on-screen, then sends it to be constructed. This is for testing, remove it later
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // when we are sure everything works, replace the code bloc ^above^ this with v this v
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        //this is our lisense key; it allows us to use vuforia
        parameters.vuforiaLicenseKey = "AfNZZLv/////AAAAGYoP4N1VRUpbvn38AR+Y2mg+OK2xWQmTIUomrg9LD3edhWpUZicxdz5roqag6eAz0yAXzJOvot5ouljPTBbT9hCuRyszF+HW6YkiveEcwwcXgErjo4x8G516wjbmz5N+7jGCylTJo6DupJrv/U5iJqTFKVqSBWeFEr6q+uzJzwlYLZg4mwc0cnG2ik1oQifSRuB3INwZYpKyOM4WoYlD5SP9kp2u96LkVZSH+B0xT9p7VcoFO9rIv4+2LZ64F8OQcdF4jQjtFSZB9qf/nri0bowld6I80wMF5kf0KtV9H9oT+EDuZ1WPwqYi80k44Za2UU9o3Jd4DQkFjAsis0gyhIpCwdsyX6btWBk5vDZ4+6CZ";

        //for choosing the front or back camera.
        if(isFront){
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        }else{
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        }

        //sends all the parameters to the vuforia thing
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        //this tells vuforia what group of templates to look for,
        // then which specific template it is looking for,
        // then gives it a name
        //NOTE: All 3 images for the 2017-2018 game are the same template, but have a different ID
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        //Debugging telemetry
        telemetry.addData("Vision:", "Ready");
        telemetry.update();
    }

}
