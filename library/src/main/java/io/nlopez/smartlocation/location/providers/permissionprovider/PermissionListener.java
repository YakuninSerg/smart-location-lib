package io.nlopez.smartlocation.location.providers.permissionprovider;

/**
 * Created by siakunin on 24.01.2018.
 */

public interface PermissionListener {

    /**
     * Notify when user is granted all required permissions
     */
    void onPermissionsGranted();

    /**
     * Notify when user is denied any one of required permissions
     */
    void onPermissionsDenied();
}
