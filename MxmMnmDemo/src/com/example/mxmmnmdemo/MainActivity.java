package com.example.mxmmnmdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//	git repo 
/*
	touch README.md
	git init
	git add README.md
	git commit -m "first commit"
	git remote add origin https://github.com/Audhil/OpeningBackgroundMinimizedAppThroughNotification.git
	git push -u origin master
*/

//	http://stackoverflow.com/questions/2960459/re-open-background-application-via-notification-item

//	useful links http://codeversed.com/expandable-notifications-android/ http://www.vogella.com/tutorials/AndroidNotifications/article.html https://github.com/Audhil/BQuietV_11/blob/master/BQuiet/src/com/wordpress/smdaudhilbe/bquiet/RingerModeChangeReceiver.java
public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		notificationManager.cancel(0);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		createNotification();
	}

	private void createNotification() {
		Intent homeIntent = new Intent(this, MainActivity.class);
//		working fine
//		homeIntent.setAction("android.intent.action.MAIN");
//		homeIntent.addCategory("android.intent.category.LAUNCHER");
		
//		working fine
		homeIntent.setAction(Intent.ACTION_MAIN);
		homeIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, homeIntent,0);
		
		Notification notiBuilder = new NotificationCompat.Builder(this)   
				.setContentTitle("New mail from " + "test@gmail.com")
		        .setContentText("Subject")
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentIntent(pendingIntent)
		        .addAction(R.drawable.ic_launcher, "Call", pendingIntent)
		        .addAction(R.drawable.ic_launcher, "More", pendingIntent)
		        .addAction(R.drawable.ic_launcher, "And more", pendingIntent).build();
		
		notiBuilder.flags |= Notification.FLAG_AUTO_CANCEL;	
		
		notificationManager.notify(0,notiBuilder);
	}
	
	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy()");
		notificationManager.cancel(0);
	}
}