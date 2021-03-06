package community.revteltech.nfc;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.TagTechnology;
import android.nfc.tech.Ndef;
import android.nfc.tech.IsoDep;
import android.util.Log;
import com.facebook.react.bridge.*;

class TagTechnologyRequest {
    static String LOG_TAG = "NfcManager-tech";
    Tag mTag;
    TagTechnology mTech;
    String mTechType;
    Callback mJsCallback;

    TagTechnologyRequest(String techType, Callback cb) {
        mTechType = techType;
        mJsCallback = cb;
    }

    Callback getPendingCallback() {
        return mJsCallback;
    }

    TagTechnology getTechHandle() {
        return mTech;
    }

    boolean isConnected() {
        return mTag != null;
    }

    boolean connect(Tag tag) {
        mTech = null;
        mTag = tag;
        if (mTechType.equals("Ndef")) {
            mTech = Ndef.get(tag);
        } else if (mTechType.equals("IsoDep")) {
            mTech = IsoDep.get(tag);
        }

        if (mTech == null) {
            return false;
        }

        try {
            Log.d(LOG_TAG, "connect to " + mTechType);
            mTech.connect();
            return true;
        } catch (Exception ex) {
            Log.d(LOG_TAG, "fail to connect tech");
            return false;
        }
    }

    void close() {
        try {
            mTech.close();
        } catch (Exception ex) {
            Log.d(LOG_TAG, "fail to close tech");
        }
    }
}
