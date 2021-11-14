package com.mainproject.useraccount.entity;

import javax.persistence.*;

@Entity
public class MailGroup {

    @Id
@GeneratedValue
private int groupId;
    private String groupName;

    @Lob
    @Column(columnDefinition="text")
    private String mailAddresses;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserAuthentication userAuthentication;

    public UserAuthentication getUserAuthentication() {
        return userAuthentication;
    }

    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMailAddresses() {
        return mailAddresses;
    }

    public void setMailAddresses(String mailAddresses) {
        this.mailAddresses = mailAddresses;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
