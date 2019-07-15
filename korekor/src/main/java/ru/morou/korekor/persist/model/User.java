package ru.morou.korekor.persist.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Access нужна для определения типа доступа (access type) для класса entity, суперкласса, embeddable или отдельных
 * атрибутов, то есть как JPA будет обращаться к атрибутам entity, как к полям класса (FIELD) или как к свойствам класса
 * (PROPERTY), имеющие гетеры (getter) и сетеры (setter).
 *
 * https://ru.stackoverflow.com/questions/874276/%D0%94%D0%BB%D1%8F-%D1%87%D0%B5%D0%B3%D0%BE-accesstype-field-%D0%B8-accesstype-property-%D0%B2-access
 */

// FIXME: 6/19/2019 implements UserDetails - когда следует использовать (на данный моменты мы работаем через UserDetailsService)?
// FIXME: 6/19/2019 @Access - когда следует использовать?

@Entity
@Table(name = "users")
//@Access(AccessType.FIELD)
public class User implements Serializable {

    // FIXME: 6/19/2019 Какая неприятность может произойти, если serialVersionUID не указан при UserDetails?
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "UNSIGNED")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    /**
     * Аннотация @Pattern, предоставляя его регулярное выражение, которое гарантирует, что значение свойства
     * соответствует желаемому формату.
     * http: //www.regularexpressions.info/.
     */
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,60}",
            message="Password is invalid. It should contain at least one: digit, upper, lower case letter, special " +
                    "character and its length should be in range from 6 to 60 chars")
    @Column(name = "password", nullable = false)
    private String password;

//    @Transient
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,60}", message="Password confirmation is invalid. It should contain at least one: digit, "
//            + "upper, lower case letter, special character and its length should be in range from 6 to 60 chars")
//    private String passwordConfirmation;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    // FIXME: 2019-06-19 буду использовать позже
//    @Column(name = "phone", nullable = false, unique = true)
//    @Pattern(regexp="(^$|[0-9]{9})", message="Phone number format is not correct (NNNNNNNNN eg.: 700700700).")
//    private String phone;
//    
//    @Column(name="address")
//    private String address;
//
//    @Column(name="city")
//    private String city;
//
//    @NotEmpty(message="post code cannot be empty.")
//    @Column(name="postcode")
//    @Pattern(regexp="[0-9]{2}\\-[0-9]{3}", message="Post code is incorrect (XX-XXX eg. 20-199).")
//    private String postcode;

    @Lob
    @Column(name = "photo", nullable = false, columnDefinition="MEDIUMBLOB")
    private byte[] photo;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", columnDefinition = "UNSIGNED"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String userName, String password, String firstName, String lastName, String email, byte[] photo, LocalDateTime createAt, LocalDateTime updateAt) {
        this(userName, password, firstName, lastName, email, photo, createAt, updateAt, new HashSet<>());
    }

    public User(String userName, String password, String firstName, String lastName, String email, byte[] photo, LocalDateTime createAt, LocalDateTime updateAt,
                Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photo = photo;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.roles = roles;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + Arrays.toString (photo) +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", roles=" + roles +
                '}';
    }
}