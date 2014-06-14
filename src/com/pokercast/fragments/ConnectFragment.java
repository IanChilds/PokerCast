package com.pokercast.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.pokercast.R;
import com.pokercast.castutils.CastManager;

/**
 *
 */
public class ConnectFragment extends Fragment {
    private CastManager castManager;
    private MediaRouteButton mMediaRouteButton;
    private MediaRouter mMediaRouter;
    private MediaRouteSelector mMediaRouteSelector;
    private MediaRouter.Callback mMediaRouterCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaRouter = MediaRouter.getInstance(getActivity().getApplicationContext());
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID))
                .build();
        mMediaRouterCallback = new MediaRouterCallback();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_connect, container, false);
        mMediaRouteButton = (MediaRouteButton) v.findViewById(R.id.media_route_button);
        mMediaRouteButton.setRouteSelector(mMediaRouteSelector);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback, MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMediaRouter.removeCallback(mMediaRouterCallback);
        //castManager.stopDiscoveringDevices();
    }

    private class MediaRouterCallback extends MediaRouter.Callback {

        @Override
        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
            Log.i("Route", "Add route");
            mMediaRouteButton.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
            mMediaRouteButton.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
//            mSelectedDevice = CastDevice.getFromBundle(info.getExtras());
            String routeId = info.getId();
        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
            //    teardown();
            //  mSelectedDevice = null;
        }

    }
}