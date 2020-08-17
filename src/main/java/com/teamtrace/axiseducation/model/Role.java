package com.teamtrace.axiseducation.model;

import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;

import javax.persistence.*;
import java.util.Date;

import static com.teamtrace.axiseducation.model.Model.EXPIRY_IN_DAY;
import static com.teamtrace.axiseducation.model.Model.SIZE_LARGE;

@Entity
@Table(name = "role")
@Cacheable(value = true)
@org.eclipse.persistence.annotations.Cache(type = CacheType.FULL, size = SIZE_LARGE, expiry = EXPIRY_IN_DAY, coordinationType = CacheCoordinationType.NONE)
@NamedQueries({
        @NamedQuery(name = "Role.unassignedRolesByRoleType", query = "SELECT o FROM Role o WHERE o.typeId IN :typeIds AND o.roleId NOT IN :roleIds"),
        @NamedQuery(name = "Role.getRolesByTypes", query = "SELECT o FROM Role o WHERE o.typeId IN :typeIds")
})
public class Role {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int roleId;
    @Column(name = "type_id", nullable = false, columnDefinition = "SMALLINT(5) UNSIGNED")
    private int typeId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(200)")
    private String name;
    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(300)")
    private String description;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
