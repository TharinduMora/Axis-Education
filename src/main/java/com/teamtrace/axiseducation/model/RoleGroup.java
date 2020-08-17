package com.teamtrace.axiseducation.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "role_group")
@Cacheable
public class RoleGroup {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_group_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int roleGroupId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(200)")
    private String name;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "role_group_role",
            joinColumns = {@JoinColumn(name = "role_group_id", referencedColumnName = "role_group_id", columnDefinition = PK_TYPE)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id", columnDefinition = Role.PK_TYPE)})
    private List<Role> roles = new ArrayList<>();
    @Column(name = "created_by_id", columnDefinition = AdminUser.PK_TYPE)
    protected Integer createdById;

    public int getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(int roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }
}
