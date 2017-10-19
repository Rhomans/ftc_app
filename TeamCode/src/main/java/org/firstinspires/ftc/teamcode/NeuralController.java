package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.provider.Settings;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by rishabhbector on 10/7/17.
 */

public class NeuralController {

    private static final int inputSize = 11;
    private static final String modelFile = "file:///android_asset/frozen_testmodel.pb";
    private static final String inputNode = "dense_1_input";
    private static final String outputNode = "dense_2/Relu";

    private TensorFlowInferenceInterface inferenceInterface;

    public void loadModel() {
        inferenceInterface = new TensorFlowInferenceInterface(App.getContext().getAssets(), modelFile);
    }

    public float[] getOutput(float[] inputFloats) {

        float[] outputs = new float[] {};
        System.out.println("CALCULATING NETWORK OUTPUT...");

        inferenceInterface.feed(inputNode, inputFloats);
        inferenceInterface.run(new String[] {outputNode});
        inferenceInterface.fetch(outputNode, outputs);

        System.out.println("CALCULATED NETWORK OUTPUT");
        System.out.println(outputs);

        return outputs;
    }

}
