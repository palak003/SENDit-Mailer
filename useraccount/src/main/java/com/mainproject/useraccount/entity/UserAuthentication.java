package com.mainproject.useraccount.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserAuthentication {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private  String mailAddress;
    private String password;
    @Column(columnDefinition = "boolean default false")
    private boolean isPremium;

    @OneToMany(mappedBy="userAuthentication",cascade= CascadeType.ALL)
    private List<MailGroup> mailGroupList = new ArrayList<>();

    public List<MailGroup> getMailGroupList() {
        return mailGroupList;
    }

    public void setMailGroupList(List<MailGroup> mailGroupList) {
        this.mailGroupList = mailGroupList;
    }

    public UserAuthentication(){

    }
    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
