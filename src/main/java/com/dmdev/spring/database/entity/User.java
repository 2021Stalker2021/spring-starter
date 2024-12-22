package com.dmdev.spring.database.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "User.company",
        attributeNodes = @NamedAttributeNode("company"))
@ToString(exclude = "userChats")
@EqualsAndHashCode(of = "username", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
@Builder
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) // для работы с revision
// RelationTargetAuditMode.NOT_AUDITED - нужен для того чтобы не аудировать другие сущности в User
public class User extends AuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private LocalDate birthDate;

    private String firstname;

    private String lastname;

    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY) // всегда ставим Lazy
    @JoinColumn(name = "company_id")
    private Company company;

    @NotAudited // над колекциями обязательно добавлять эту аннотацию которую не нужно аудировать
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();
}