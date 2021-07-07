package com.bob.common.utils;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.subjects.PublishSubject;

public class RxPermissionsFragment2 extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 42;
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap();
    private boolean mLogging;

    public RxPermissionsFragment2() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @TargetApi(23)
    void requestPermissions(@NonNull String[] permissions) {
        this.requestPermissions(permissions, 42);
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 42) {
            boolean[] shouldShowRequestPermissionRationale = new boolean[permissions.length];

            for(int i = 0; i < permissions.length; ++i) {
                shouldShowRequestPermissionRationale[i] = this.shouldShowRequestPermissionRationale(permissions[i]);
            }

            this.onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationale);
        }
    }

    void onRequestPermissionsResult(String[] permissions, int[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        int i = 0;

        for(int size = permissions.length; i < size; ++i) {
            this.log("onRequestPermissionsResult  " + permissions[i]);
            PublishSubject<Permission> subject = (PublishSubject)this.mSubjects.get(permissions[i]);
            if (subject == null) {
                Log.e(RxPermissions2.TAG, "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                return;
            }

            this.mSubjects.remove(permissions[i]);
            boolean granted = grantResults[i] == 0;
            subject.onNext(new Permission(permissions[i], granted, shouldShowRequestPermissionRationale[i]));
            subject.onComplete();
        }

    }

    @TargetApi(23)
    boolean isGranted(String permission) {
        FragmentActivity fragmentActivity = this.getActivity();
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        } else {
            return fragmentActivity.checkSelfPermission(permission) ==  PackageManager.PERMISSION_GRANTED;
        }
    }

    @TargetApi(23)
    boolean isRevoked(String permission) {
        FragmentActivity fragmentActivity = this.getActivity();
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        } else {
            return fragmentActivity.getPackageManager().isPermissionRevokedByPolicy(permission, this.getActivity().getPackageName());
        }
    }

    public void setLogging(boolean logging) {
        this.mLogging = logging;
    }

    public PublishSubject<Permission> getSubjectByPermission(@NonNull String permission) {
        return (PublishSubject)this.mSubjects.get(permission);
    }

    public boolean containsByPermission(@NonNull String permission) {
        return this.mSubjects.containsKey(permission);
    }

    public void setSubjectForPermission(@NonNull String permission, @NonNull PublishSubject<Permission> subject) {
        this.mSubjects.put(permission, subject);
    }

    void log(String message) {
        if (this.mLogging) {
            Log.d(RxPermissions2.TAG, message);
        }
    }
}
