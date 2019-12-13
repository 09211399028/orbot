package org.torproject.android.mini;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import org.torproject.android.mini.vpn.VPNEnableActivity;
import org.torproject.android.service.OrbotService;
import org.torproject.android.service.TorServiceConstants;
import org.torproject.android.service.util.Prefs;

public class OnBootReceiver extends BroadcastReceiver {

	private static boolean sReceivedBoot = false;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (Prefs.startOnBoot() && (!sReceivedBoot))
		{
			if (Prefs.useVpn())
				startVpnService(context); //VPN will start Tor once it is done
			else
				startService(TorServiceConstants.ACTION_START, context);

			sReceivedBoot = true;
		}
	}
	
	public void startVpnService (final Context context)
    	{
		   Intent intent = new Intent(context,VPNEnableActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);

    	}

	private void startService (String action, Context context)
	{

		Intent intent = new Intent(context, OrbotService.class);
		intent.setAction(action);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			context.startForegroundService(intent);
		}
		else
		{
			context.startService(intent);
		}

	}
	
	
}

