package io.nlopez.smartlocation.location.providers.permissionprovider;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Created by siakunin on 24.01.2018.
 */

public class PermissionCompatSource {

    boolean shouldShowRequestPermissionRationale(Fragment fragment, String permission) {
        return fragment.shouldShowRequestPermissionRationale(permission);
    }

    void requestPermissions(Fragment fragment, String[] requiredPermissions, int requestCode) {
        fragment.requestPermissions(requiredPermissions, requestCode);
    }

    boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    void requestPermissions(Activity activity, String[] requiredPermissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, requiredPermissions, requestCode);
    }
}
