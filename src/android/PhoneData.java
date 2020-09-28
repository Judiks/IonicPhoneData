package phonedata;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class PhoneData extends CordovaPlugin implements ActivityCompat.OnRequestPermissionsResultCallback  {

    private static final int PERMISSION_READ_STATE = 0;
    private static final int PERMISSION_READ_SMS = 1;
    private static final int PERMISSION_READ_PHONE_NUMBERS = 2;
    private static boolean isReadPhoneState = false;
    private static boolean isReadSMS = false;
    private static boolean isReadPhoneNumbers = false;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getData")) {
            this.getData(callbackContext);
            return true;
        }
        return false;
    }

    private void getData(CallbackContext callbackContext) {
        Context context = this.cordova.getActivity().getApplicationContext();
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            isReadPhoneState = true;
        } else {
            ActivityCompat.requestPermissions(this.cordova.getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_GRANTED) {
            isReadSMS = true;
        } else {
            ActivityCompat.requestPermissions(this.cordova.getActivity(), new String[]{Manifest.permission.READ_SMS}, PERMISSION_READ_SMS);
        }
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_PHONE_NUMBERS) ==
                PackageManager.PERMISSION_GRANTED) {
            isReadPhoneNumbers = true;
        } else {
            ActivityCompat.requestPermissions(this.cordova.getActivity(), new String[]{Manifest.permission.READ_PHONE_NUMBERS}, PERMISSION_READ_PHONE_NUMBERS);
        }
        if (isReadPhoneNumbers && isReadSMS && isReadPhoneState) {
            TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            callbackContext.success(mPhoneNumber);
        } else {
            callbackContext.error("One of necessary permissions denied!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isReadPhoneState = true;
                } else {
                    isReadPhoneState = false;
                }
                break;
            }
            case PERMISSION_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isReadSMS = true;
                } else {
                    isReadSMS = false;
                }
                break;
            }
            case PERMISSION_READ_PHONE_NUMBERS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isReadPhoneNumbers = true;
                } else {
                    isReadPhoneNumbers = false;
                }
                break;
            }

        }
    }
}