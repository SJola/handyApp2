package com.jolasudol.sampleprojectnew.fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jolasudol.sampleprojectnew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashlightFragment extends Fragment {

    private Camera cam;
    private Context context;
    private boolean toggle;
    private Camera.Parameters params;
    private boolean isFlashOn;

    @BindView(R.id.fragment_flashlight)
    RelativeLayout relativeLayout;
    private ImageView imageView;

    public FlashlightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashlight, container, false);
        ButterKnife.bind(this, view);
        imageView = (ImageView) view.findViewById(R.id.flash);
        imageView.setImageResource(R.drawable.flash_off);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void flashForMAndAboveVersion() {
        toggle = !toggle;
        imageView = (ImageView) getActivity().findViewById(R.id.flash);
        try {
            CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            for (String id : cameraManager.getCameraIdList()) {
                if (cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    cameraManager.setTorchMode(id, toggle);
                    if (toggle) {
                        relativeLayout.setBackgroundResource(android.R.color.black);
                        imageView.setImageResource(R.drawable.flash_on);
                    } else {
                        relativeLayout.setBackgroundResource(android.R.color.black);
                        imageView.setImageResource(R.drawable.flash_off);
                    }
                }
            }
        } catch (Exception e2) {
            Log.d("TORCH", String.valueOf(e2));
        }
    }

    private void turnOnFlash() {
        relativeLayout.setBackgroundResource(R.drawable.background2);
        cam = Camera.open();
        params = cam.getParameters();
        if (cam == null || params == null) {
            return;
        }
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(params);
        cam.startPreview();
        isFlashOn = true;
    }

    private void turnOffFlash() {
        relativeLayout.setBackgroundResource(android.R.color.black);
        cam = Camera.open();
        params = cam.getParameters();
        if (cam == null || params == null) {
            return;
        }
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(params);
        cam.stopPreview();
        isFlashOn = false;
    }

    @OnClick(R.id.toogleButton)
    public void flash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flashForMAndAboveVersion();
        } else {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                Toast.makeText(context, "We need your permission to flash", Toast.LENGTH_SHORT).show();
            } else {
                if (!isFlashOn)
                    turnOnFlash();
                else
                    turnOffFlash();
            }
        }
    }
}