package lathigara.harsh.chatappmvp.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Set;

import javax.inject.Inject;

import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.main.MainActivity;

public class SettingActivityPresenter implements SettingActivityMVP.Presenter {

    private static final int GALLERY_PICK = 1;
    SettingActivityMVP.View view;


    private Dialog builder;


    @Override
    public void allUserButtonClicked() {

    }

    @Override
    public void accountSettingButtonClicked() {

    }

    @Override
    public void logOutButtonClicked() {

    }

    @Override
    public void setView(SettingActivityMVP.View view) {
        this.view  =  view;

    }

    @Override
    public void imageChangeButtonClicked(Activity context) {

        Intent gallery_intent = new Intent();
        gallery_intent.setType("image/*");
        gallery_intent.setAction(Intent.ACTION_GET_CONTENT);

        context.startActivityForResult(Intent.createChooser(gallery_intent,"SELECT IMAGE"),GALLERY_PICK);



    }

    @Override
    public void statuschangeButtonClicked(SettingActivity activity) {




    }
}
