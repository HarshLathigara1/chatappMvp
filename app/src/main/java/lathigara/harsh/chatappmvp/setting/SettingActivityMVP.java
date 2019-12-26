package lathigara.harsh.chatappmvp.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

public interface SettingActivityMVP {

   interface View{
       void imageChanged();

       void statusUpdated();


    }

    interface Model{

    }
    interface Presenter{

       void allUserButtonClicked();
       void accountSettingButtonClicked();
       void logOutButtonClicked();
       void setView(SettingActivityMVP.View view);
       void imageChangeButtonClicked(Activity activity);
       void statuschangeButtonClicked(SettingActivity context);

    }
}
