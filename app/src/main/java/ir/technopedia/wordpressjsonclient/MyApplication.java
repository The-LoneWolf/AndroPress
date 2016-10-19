package ir.technopedia.wordpressjsonclient;

import com.orm.SugarApp;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by user1 on 10/16/2016.
 */
@ReportsCrashes(mailTo = "m.garebaghi@gmail.com",
        mode = ReportingInteractionMode.DIALOG,
        resDialogText = R.string.crash_report,
        resDialogTitle = R.string.crash_title,
        resDialogTheme = R.style.AppCompatAlertDialogStyle,
        resDialogIcon = R.mipmap.ic_launcher)

public class MyApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
