package io.nlopez.smartlocation.location.providers.permissionprovider;

import android.app.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import io.nlopez.smartlocation.location.providers.dialogprovider.SimpleMessageDialogProvider;
import io.nlopez.smartlocation.location.utils.ContextProcessor;

/**
 * Created by siakunin on 24.01.2018.
 */

public class PermissionHelper {

    private final String ASK_PERMISSON = "Разрешить приложению доступ к данным о местоположении?";

    private ContextProcessor mContextProcessor;

    DefaultPermissionProvider mPermissionProvider = new DefaultPermissionProvider(
            Defaults.LOCATION_PERMISSIONS,
            new SimpleMessageDialogProvider(ASK_PERMISSON));

    public PermissionHelper(@NonNull Activity activity){
        mContextProcessor = new ContextProcessor(activity.getApplicationContext());
        mContextProcessor.setActivity(activity);
        mPermissionProvider.setContextProcessor(mContextProcessor);
    }

    public PermissionHelper (@NonNull Fragment fragment){
        mContextProcessor = new ContextProcessor(fragment.getActivity().getApplicationContext());
        mContextProcessor.setFragment(fragment);
        mPermissionProvider.setContextProcessor(mContextProcessor);
    }

    public void askForPermisson(){
        mPermissionProvider.executePermissionsRequest();
    }
}
