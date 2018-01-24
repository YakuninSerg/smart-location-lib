package io.nlopez.smartlocation.location.providers.permissionprovider;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import io.nlopez.smartlocation.location.providers.dialogprovider.DialogListener;
import io.nlopez.smartlocation.location.providers.dialogprovider.DialogProvider;

/**
 * Created by siakunin on 24.01.2018.
 */

public class DefaultPermissionProvider extends PermissionProvider implements DialogListener {

    private final String TAG = getClass().getName();

    private PermissionCompatSource permissionCompatSource;

    public DefaultPermissionProvider(String[] requiredPermissions, @Nullable DialogProvider dialogProvider) {
        super(requiredPermissions, dialogProvider);
    }

    @Override
    public boolean requestPermissions() {
        if (getActivity() == null) {
            Log.e(TAG,"Cannot ask for permissions, "
                    + "because DefaultPermissionProvider doesn't contain an Activity instance.");
            return false;
        }

        if (shouldShowRequestPermissionRationale()) {
            getDialogProvider().setDialogListener(this);
            getDialogProvider().getDialog(getActivity()).show();
        } else {
            executePermissionsRequest();
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCode.RUNTIME_PERMISSION) {

            // Check if any of required permissions are denied.
            boolean isDenied = false;
            for (int i = 0, size = permissions.length; i < size; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isDenied = true;
                }
            }

            if (isDenied) {
                Log.i(TAG,"User denied some of required permissions, task will be aborted!");
                if (getPermissionListener() != null) getPermissionListener().onPermissionsDenied();
            } else {
                Log.i(TAG,"We got all required permission!");
                if (getPermissionListener() != null) getPermissionListener().onPermissionsGranted();
            }
        }
    }

    @Override
    public void onPositiveButtonClick() {
        executePermissionsRequest();
    }

    @Override
    public void onNegativeButtonClick() {
        Log.i(TAG,"User didn't even let us to ask for permission!");
        if (getPermissionListener() != null) getPermissionListener().onPermissionsDenied();
    }

    boolean shouldShowRequestPermissionRationale() {
        boolean shouldShowRationale = false;
        for (String permission : getRequiredPermissions()) {
            shouldShowRationale = shouldShowRationale || checkRationaleForPermission(permission);
        }

        Log.i(TAG,"Should show rationale dialog for required permissions: " + shouldShowRationale);

        return shouldShowRationale && getActivity() != null && getDialogProvider() != null;
    }

    boolean checkRationaleForPermission(String permission) {
        if (getFragment() != null) {
            return getPermissionCompatSource().shouldShowRequestPermissionRationale(getFragment(), permission);
        } else if (getActivity() != null) {
            return getPermissionCompatSource().shouldShowRequestPermissionRationale(getActivity(), permission);
        } else {
            return false;
        }
    }

    void executePermissionsRequest() {
        Log.i(TAG,"Asking for Runtime Permissions...");
        if (getFragment() != null) {
            getPermissionCompatSource().requestPermissions(getFragment(),
                    getRequiredPermissions(), RequestCode.RUNTIME_PERMISSION);
        } else if (getActivity() != null) {
            getPermissionCompatSource().requestPermissions(getActivity(),
                    getRequiredPermissions(), RequestCode.RUNTIME_PERMISSION);
        } else {
            Log.e(TAG,"Something went wrong requesting for permissions.");
            if (getPermissionListener() != null) getPermissionListener().onPermissionsDenied();
        }
    }

    // For test purposes
    void setPermissionCompatSource(PermissionCompatSource permissionCompatSource) {
        this.permissionCompatSource = permissionCompatSource;
    }

    protected PermissionCompatSource getPermissionCompatSource() {
        if (permissionCompatSource == null) {
            permissionCompatSource = new PermissionCompatSource();
        }
        return permissionCompatSource;
    }

}