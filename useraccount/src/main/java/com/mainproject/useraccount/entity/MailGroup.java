package com.mainproject.useraccount.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mail_groups")
public class MailGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAuthentication userAuthentication;

    @ManyToMany
    @JoinTable(name = "mail_group_mail_ids",
            joinColumns = @JoinColumn(name = "mail_group_id"),
            inverseJoinColumns = @JoinColumn(name = "mail_id"))
    private Set<MailRecipients> mailIds = new HashSet<>();



}
