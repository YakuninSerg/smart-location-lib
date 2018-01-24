package io.nlopez.smartlocation.location.providers.permissionprovider;

import android.support.annotation.NonNull;

/**
 * Created by siakunin on 24.01.2018.
 */

public class StubPermissionProvider extends PermissionProvider {

    public StubPermissionProvider() {
        super(Defaults.LOCATION_PERMISSIONS, null);
    }

    @Override
    public boolean requestPermissions() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
    }
}