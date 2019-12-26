package lathigara.harsh.chatappmvp.signUp;

public interface SignUpActivityMvp {

    // it will act as a container for Other Interfaces


     interface Model{}


    interface View{
         void AuthenticationDone();
         void errorWhileAuthenricating();
         void pleaseAddUserNameOrPassword();

    }



    interface Presenter {
         void setView(SignUpActivityMvp.View view);

         void RegisterButtonClicked(String displayName,String email,String password);
    }
}
