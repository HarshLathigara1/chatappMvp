package lathigara.harsh.chatappmvp.main;



public class MainActivityPresenter implements MainActivityMVP.Presenter {



    MainActivityMVP.view view;



    @Override
    public void setView(MainActivityMVP.view view) {
        this.view  = view;

    }
}
