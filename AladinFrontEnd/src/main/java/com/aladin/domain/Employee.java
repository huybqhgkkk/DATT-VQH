package com.aladin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_day_work", nullable = false)
    private LocalDate first_day_work;

    @NotNull
    @Size(min = 5, max = 2000)
    @Column(name = "full_name", length = 2000, nullable = false)
    private String full_name;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "phone_number", length = 2000, nullable = false)
    private String phone_number;

    @NotNull
    @Size(max = 2000)
    @Column(name = "email", length = 2000, nullable = false)
    private String email;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate date_of_birth;

    @NotNull
    @Size(max = 2000)
    @Column(name = "countryside", length = 2000, nullable = false)
    private String countryside;

    @NotNull
    @Size(max = 2000)
    @Column(name = "current_residence", length = 2000, nullable = false)
    private String current_residence;

    @NotNull
    @Size(max = 2000)
    @Column(name = "relative", length = 2000, nullable = false)
    private String relative;

    @NotNull
    @Size(max = 2000)
    @Column(name = "favourite", length = 2000, nullable = false)
    private String favourite;

    @NotNull
    @Size(max = 2000)
    @Column(name = "education", length = 2000, nullable = false)
    private String education;

    @NotNull
    @Size(max = 2000)
    @Column(name = "experience", length = 2000, nullable = false)
    private String experience;

    @NotNull
    @Size(max = 2000)
    @Column(name = "english", length = 2000, nullable = false)
    private String english;

    @NotNull
    @Size(max = 2000)
    @Column(name = "objective_in_cv", length = 2000, nullable = false)
    private String objective_in_cv;

    @NotNull
    @Size(max = 2000)
    @Column(name = "marital_status", length = 2000, nullable = false)
    private String marital_status;

    @NotNull
    @Size(max = 2000)
    @Column(name = "children", length = 2000, nullable = false)
    private String children;

    @NotNull
    @Size(max = 2000)
    @Column(name = "family", length = 2000, nullable = false)
    private String family;

    @Lob
    @Column(name = "avatar", nullable = false)
    private byte[] avatar;

    @NotNull
    @Column(name = "avatar_content_type", nullable = false)
    private String avatarContentType;

    @NotNull
    @Size(max = 10)
    @Column(name = "gender", length = 10, nullable = false)
    private String gender;

    @Lob
    @Column(name = "certificate")
    private byte[] certificate;

    @Column(name = "certificate_content_type")
    private String certificateContentType;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFirst_day_work() {
        return this.first_day_work;
    }

    public Employee first_day_work(LocalDate first_day_work) {
        this.setFirst_day_work(first_day_work);
        return this;
    }

    public void setFirst_day_work(LocalDate first_day_work) {
        this.first_day_work = first_day_work;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public Employee full_name(String full_name) {
        this.setFull_name(full_name);
        return this;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return this.phone_number;
    }

    public Employee phone_number(String phone_number) {
        this.setPhone_number(phone_number);
        return this;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_of_birth() {
        return this.date_of_birth;
    }

    public Employee date_of_birth(LocalDate date_of_birth) {
        this.setDate_of_birth(date_of_birth);
        return this;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getCountryside() {
        return this.countryside;
    }

    public Employee countryside(String countryside) {
        this.setCountryside(countryside);
        return this;
    }

    public void setCountryside(String countryside) {
        this.countryside = countryside;
    }

    public String getCurrent_residence() {
        return this.current_residence;
    }

    public Employee current_residence(String current_residence) {
        this.setCurrent_residence(current_residence);
        return this;
    }

    public void setCurrent_residence(String current_residence) {
        this.current_residence = current_residence;
    }

    public String getRelative() {
        return this.relative;
    }

    public Employee relative(String relative) {
        this.setRelative(relative);
        return this;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getFavourite() {
        return this.favourite;
    }

    public Employee favourite(String favourite) {
        this.setFavourite(favourite);
        return this;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getEducation() {
        return this.education;
    }

    public Employee education(String education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return this.experience;
    }

    public Employee experience(String experience) {
        this.setExperience(experience);
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEnglish() {
        return this.english;
    }

    public Employee english(String english) {
        this.setEnglish(english);
        return this;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getObjective_in_cv() {
        return this.objective_in_cv;
    }

    public Employee objective_in_cv(String objective_in_cv) {
        this.setObjective_in_cv(objective_in_cv);
        return this;
    }

    public void setObjective_in_cv(String objective_in_cv) {
        this.objective_in_cv = objective_in_cv;
    }

    public String getMarital_status() {
        return this.marital_status;
    }

    public Employee marital_status(String marital_status) {
        this.setMarital_status(marital_status);
        return this;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getChildren() {
        return this.children;
    }

    public Employee children(String children) {
        this.setChildren(children);
        return this;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getFamily() {
        return this.family;
    }

    public Employee family(String family) {
        this.setFamily(family);
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public byte[] getAvatar() {
        return this.avatar;
    }

    public Employee avatar(byte[] avatar) {
        this.setAvatar(avatar);
        return this;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return this.avatarContentType;
    }

    public Employee avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public String getGender() {
        return this.gender;
    }

    public Employee gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getCertificate() {
        return this.certificate;
    }

    public Employee certificate(byte[] certificate) {
        this.setCertificate(certificate);
        return this;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public String getCertificateContentType() {
        return this.certificateContentType;
    }

    public Employee certificateContentType(String certificateContentType) {
        this.certificateContentType = certificateContentType;
        return this;
    }

    public void setCertificateContentType(String certificateContentType) {
        this.certificateContentType = certificateContentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", first_day_work='" + getFirst_day_work() + "'" +
            ", full_name='" + getFull_name() + "'" +
            ", phone_number='" + getPhone_number() + "'" +
            ", email='" + getEmail() + "'" +
            ", date_of_birth='" + getDate_of_birth() + "'" +
            ", countryside='" + getCountryside() + "'" +
            ", current_residence='" + getCurrent_residence() + "'" +
            ", relative='" + getRelative() + "'" +
            ", favourite='" + getFavourite() + "'" +
            ", education='" + getEducation() + "'" +
            ", experience='" + getExperience() + "'" +
            ", english='" + getEnglish() + "'" +
            ", objective_in_cv='" + getObjective_in_cv() + "'" +
            ", marital_status='" + getMarital_status() + "'" +
            ", children='" + getChildren() + "'" +
            ", family='" + getFamily() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            ", gender='" + getGender() + "'" +
            ", certificate='" + getCertificate() + "'" +
            ", certificateContentType='" + getCertificateContentType() + "'" +
            "}";
    }
}
