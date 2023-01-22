package com.awesome.zoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class MainActivity extends AppCompatActivity {
  EditText Name, MN, MP;
  Button Join;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Name = findViewById(R.id.etname);
    MN = findViewById(R.id.etmn);
    MP = findViewById(R.id.etmp);
    Join = findViewById(R.id.btnJoinMeeting);

    initializeZoom(this);

    Join.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String MeetingNumber = MN.getText().toString();
        String MeetingPassword = MP.getText().toString();
        String UserName = Name.getText().toString();

        if (MeetingNumber.trim().length() > 0 && MeetingPassword.trim().length() > 0 && UserName.trim().length() > 0) {
          joinMeeting(MainActivity.this, MeetingNumber, MeetingPassword, UserName);
        } else {
          Toast.makeText(MainActivity.this, "Invalid detail", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void initializeZoom(Context context) {
    ZoomSDK sdk = ZoomSDK.getInstance();
    ZoomSDKInitParams params = new ZoomSDKInitParams();
    params.appKey = "";
    params.appSecret = "";
    params.domain = "zoom.us";
    params.enableLog = true;
    ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
      @Override
      public void onZoomSDKInitializeResult(int i, int i1) {

      }

      @Override
      public void onZoomAuthIdentityExpired() {

      }
    };
    sdk.initialize(context, listener, params);
  }

  private void joinMeeting(Context context, String meetingNumber, String meetingPassword, String userName) {
    MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
    JoinMeetingOptions options = new JoinMeetingOptions();
    JoinMeetingParams params = new JoinMeetingParams();
    params.displayName = userName;
    params.meetingNo = meetingNumber;
    params.password = meetingPassword;
    meetingService.joinMeetingWithParams(context, params, options);
  }

}