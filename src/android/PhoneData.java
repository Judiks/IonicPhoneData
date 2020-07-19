package phone-data;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class PhoneData extends CordovaPlugin {

    @Override
    public boolean execute(String action, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getData")) {
            this.getData(callbackContext);
            return true;
        }
        return false;
    }

    private void getData(CallbackContext callbackContext) {

        callbackContext.success('I am live');
    }
}
