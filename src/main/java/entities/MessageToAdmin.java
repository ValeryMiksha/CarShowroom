package entities;


import javax.persistence.*;

@Entity
@Table(name = "messages")
public class MessageToAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login")
    private String userLogin;
    @Column(name = "massag")
    private String content;

    public MessageToAdmin() {
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
