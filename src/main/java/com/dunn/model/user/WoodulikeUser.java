package com.dunn.model.user;

import com.dunn.validation.resetpassword.IResetPasswordValidationGroup;
import com.dunn.validation.login.IWoodulikeUserLoginValidationGroup;
import com.dunn.validation.login.LoginConstraint;
import com.dunn.validation.registration.EmailConstraint;
import com.dunn.validation.registration.IWoodulikeUserRegistrationValidationGroup;
import com.dunn.validation.registration.UsernameConstraint;
import com.dunn.validation.registration.WoodulikePasswordValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@StaticMetamodel(WoodulikeUser.class)
@Entity
@WoodulikePasswordValid(groups = {IWoodulikeUserRegistrationValidationGroup.class, IResetPasswordValidationGroup.class})
@LoginConstraint(groups = IWoodulikeUserLoginValidationGroup.class)
public class WoodulikeUser implements UserDetails, CredentialsContainer, Serializable {

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;

    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    private Collection<UserRole> userRoles;

    private String emailAddress;

    private String country;

    private String firstName;

    private String middleName;

    private String lastName;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    public WoodulikeUser() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "{validation.woodulikeuser.username.notEmpty}", groups = IWoodulikeUserLoginValidationGroup.class)
    @UsernameConstraint(groups = IWoodulikeUserRegistrationValidationGroup.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "{validation.woodulikeuser.password.notEmpty}", groups = {IWoodulikeUserLoginValidationGroup.class, IWoodulikeUserRegistrationValidationGroup.class, IResetPasswordValidationGroup.class})
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @OneToMany(targetEntity = UserRole.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="woodulikeUser_id")
    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @EmailConstraint(groups = IWoodulikeUserRegistrationValidationGroup.class)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @NotEmpty(message = "{validation.woodulikeuser.country.notEmpty}", groups = IWoodulikeUserRegistrationValidationGroup.class)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotEmpty(message = "{validation.woodulikeuser.firstName.notEmpty}", groups = IWoodulikeUserRegistrationValidationGroup.class)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @NotEmpty(message = "{validation.woodulikeuser.lastName.notEmpty}", groups = IWoodulikeUserRegistrationValidationGroup.class)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Past(message = "{validation.woodulikeuser.dateOfBirth.notInPast}", groups = IWoodulikeUserRegistrationValidationGroup.class)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole userRole : this.getUserRoles()){
            authorities.add(userRole);
        };
        return authorities;
    }


    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }
}
