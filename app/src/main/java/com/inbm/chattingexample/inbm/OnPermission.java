package com.inbm.chattingexample.inbm;

public interface OnPermission {
    void onPermissionGranted(int requestCode);
    void onPermissionDenied(int requestCode);
}