package com.harmony.kindless.apis.domain.client;

import com.harmony.kindless.apis.domain.BaseEntity;
import com.harmony.kindless.apis.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.harmony.kindless.apis.domain.Tables.SECURITY_SCHEMA;
import static com.harmony.kindless.apis.domain.Tables.SECURITY_TABLE_PREFIX;

@Getter
@Setter
@Entity
@Table(
        name = SECURITY_TABLE_PREFIX + "client",
        schema = SECURITY_SCHEMA,
        uniqueConstraints = @UniqueConstraint(
                name = "unique_client_app_key",
                columnNames = "appKey"
        )
)
public class Client extends BaseEntity {

    private static final long serialVersionUID = 2491558861633944385L;
    private String appKey;
    private String appSecret;

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "fk_client_user_id"))
    private User owner;

    @ManyToMany
    @JoinTable(
            name = SECURITY_TABLE_PREFIX + "client_developers",
            schema = SECURITY_SCHEMA,
            joinColumns = @JoinColumn(
                    name = "client_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_client_developers_client_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_client_developers_user_id")
            )
    )
    private List<User> developers;

}
