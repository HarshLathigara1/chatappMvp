package lathigara.harsh.chatappmvp.alluser;

public class AllUser {

    public String image;
    public String displayName;
    public String status;

    public AllUser(){

    }

    public AllUser(String image, String displayName, String status) {
        this.image = image;
        this.displayName = displayName;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
