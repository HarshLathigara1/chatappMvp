package lathigara.harsh.chatappmvp.Start;


import android.content.Intent;

public interface StartActivityMvp {

     interface View{

         void moveToSignUp();
         void moveToSignin();

         void moveToForgerPassword();




    }


    interface Presenter{

        void setView(StartActivityMvp.View view);

        void GotoSignUp();

        void GotoLogin();





    }




}
