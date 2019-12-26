package lathigara.harsh.chatappmvp.login;

public interface LoginActivityMVP {

    interface View{

        void loginDone();
        void loginError();
        void pleaseAddUserNameAndPassword();



    }

    interface Presenter{

        void loginButtonClicked(String email,String password);

        void setView(LoginActivityMVP.View view);


    }


}
