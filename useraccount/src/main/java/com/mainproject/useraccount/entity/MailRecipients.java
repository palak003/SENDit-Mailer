package com.mainproject.useraccount.entity;

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
@Table(name = "mail_ids")
public class MailRecipients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail_id")
    private String mailId;

    @ManyToMany(mappedBy = "mailIds")
    private Set<MailGroup> mailGroups = new HashSet<>();

}
