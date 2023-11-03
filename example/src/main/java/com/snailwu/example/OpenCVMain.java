package com.snailwu.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @author WuQinglong
 * @since 2023/9/15 5:36 PM
 */
public class OpenCVMain {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        String srcImagePath = "/Users/didi/Downloads/th.jpeg";
        Mat srcMat = Imgcodecs.imread(srcImagePath);

        String dstImagePath = "/Users/didi/Downloads/th-dst.jpeg";
        Mat dstMat = Imgcodecs.imread(dstImagePath);

        Imgproc.resize(srcMat, dstMat, new Size(100, 100));

    }


}
