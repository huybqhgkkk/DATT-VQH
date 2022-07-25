package com.aladin.repository;

import com.aladin.domain.Authority;
import com.aladin.domain.User;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import org.apache.commons.beanutils.BeanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Spring Data R2DBC repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends R2dbcRepository<User, String>, UserRepositoryInternal {




    Flux<User> findAllByIdNotNull(Pageable pageable);

    Flux<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    Mono<Long> count();

    @Query("INSERT INTO jhi_user_authority VALUES(:userId, :authority)")
    Mono<Void> saveUserAuthority(String userId, String authority);

    @Query("DELETE FROM jhi_user_authority")
    Mono<Void> deleteAllUserAuthorities();

    @Query("DELETE FROM jhi_user_authority WHERE user_id = :userId")
    Mono<Void> deleteUserAuthorities(Long userId);

    @Query("select * from jhi_authority")
    Flux<Authority> loadAllAuthorities();

}

interface UserRepositoryInternal {
    Mono<User> findOneWithAuthoritiesByLogin(String login);

    Mono<User> create(User user);

    Flux<User> findAllWithAuthorities(Pageable pageable);

    Mono<User> findOneByLogin(String fieldValue);
}

class UserRepositoryInternalImpl implements UserRepositoryInternal {
    private final Logger log = LoggerFactory.getLogger(UserRepositoryInternalImpl.class);

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final R2dbcConverter r2dbcConverter;

    public UserRepositoryInternalImpl(DatabaseClient db, R2dbcEntityTemplate r2dbcEntityTemplate, R2dbcConverter r2dbcConverter) {
        this.db = db;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
        this.r2dbcConverter = r2dbcConverter;
    }

    @Override
    public Mono<User> findOneWithAuthoritiesByLogin(String login) {
        return findOneWithAuthoritiesBy("login", login);
    }

    public User convertUserAuthorities(Row source, RowMetadata metadata){
            log.info("source:"+source + " ;metadata:"+metadata);
            metadata.getColumnMetadatas().forEach( metaName ->
            {   log.info(" metaName:"+metaName.getName() + ", value:"+source.get(metaName.getName()));
            });

            User user = new User();
            user.setId(source.get("ID").toString());
            user.setLogin((String)source.get("LOGIN"));
            user.setFirstName((String)source.get("FIRST_NAME"));
            user.setLastName((String)source.get("LAST_NAME"));
            user.setEmail((String)source.get("EMAIL"));
            user.setImageUrl((String)source.get("IMAGE_URL"));
            user.setActivated(((BigDecimal) source.get("ACTIVATED")).intValue()==1);
            user.setLangKey((String)source.get("LANG_KEY"));
            user.setCreatedBy((String)source.get("CREATED_BY"));
            user.setCreatedDate(((LocalDateTime) source.get("CREATED_DATE")).toInstant(ZoneOffset.UTC));
            user.setLastModifiedBy((String)source.get("LAST_MODIFIED_BY"));
            user.setLastModifiedDate(((LocalDateTime) source.get("LAST_MODIFIED_DATE")).toInstant(ZoneOffset.UTC));
            if(source.get("AUTHORITY_NAME")!=null)
                user.setAuthorities((String)source.get("AUTHORITY_NAME"));
            return user;
    }
    public User convertUser(Row source, RowMetadata metadata){
        log.info("source:"+source + " ;metadata:"+metadata);
        metadata.getColumnMetadatas().forEach( metaName ->
        {   log.info(" metaName:"+metaName.getName() + ", value:"+source.get(metaName.getName()));
        });

        User user = new User();
        user.setId(source.get("ID").toString());
        user.setLogin((String)source.get("LOGIN"));
        user.setFirstName((String)source.get("FIRST_NAME"));
        user.setLastName((String)source.get("LAST_NAME"));
        user.setEmail((String)source.get("EMAIL"));
        user.setImageUrl((String)source.get("IMAGE_URL"));
        user.setActivated(((BigDecimal) source.get("ACTIVATED")).intValue()==1);
        user.setLangKey((String)source.get("LANG_KEY"));
        user.setCreatedBy((String)source.get("CREATED_BY"));
        user.setCreatedDate(((LocalDateTime) source.get("CREATED_DATE")).toInstant(ZoneOffset.UTC));
        user.setLastModifiedBy((String)source.get("LAST_MODIFIED_BY"));
        user.setLastModifiedDate(((LocalDateTime) source.get("LAST_MODIFIED_DATE")).toInstant(ZoneOffset.UTC));
        return user;
    }
    @Override
    public Flux<User> findAllWithAuthorities(Pageable pageable) {
        String property = pageable.getSort().stream().map(Sort.Order::getProperty).findFirst().get();
        String direction = String.valueOf(pageable.getSort().stream().map(Sort.Order::getDirection).findFirst().get());
        long page = pageable.getPageNumber();
        long size = pageable.getPageSize();

        return db
            .sql("SELECT * FROM jhi_user u LEFT JOIN jhi_user_authority ua ON u.id=ua.user_id")
            .map(
                (row, metadata) ->
                    Tuples.of(convertUserAuthorities(row, metadata), Optional.ofNullable(row.get("authority_name", String.class)))
            )
            .all()
            .groupBy(t -> t.getT1().getLogin())
            .flatMap(l -> l.collectList().map(t -> updateUserWithAuthorities(t.get(0).getT1(), t)))
            .sort(
                Sort.Direction.fromString(direction) == Sort.DEFAULT_DIRECTION
                    ? new BeanComparator<>(property)
                    : new BeanComparator<>(property).reversed()
            )
            .skip(page * size)
            .take(size);
    }

    @Override
    public Mono<User> create(User user) {
        return r2dbcEntityTemplate.insert(User.class).using(user).defaultIfEmpty(user);
    }

    public Mono<User> findOneByLogin(String fieldValue) {
        String fieldName = "login";
        log.info( "fieldName:"+fieldName + " ;fieldValue:"+fieldValue);
        return db
            .sql("SELECT * FROM jhi_user u WHERE u." + fieldName + " = :" + fieldName)
            .bind(fieldName, fieldValue)
            .map(
                (row, metadata) -> convertUser( row, metadata)
        ).one();
    }

    private Mono<User> findOneWithAuthoritiesBy(String fieldName, Object fieldValue) {
        log.info( "fieldName:"+fieldName + " ;fieldValue:"+fieldValue);
        return db
            .sql("SELECT * FROM jhi_user u LEFT JOIN jhi_user_authority ua ON u.id=ua.user_id WHERE u." + fieldName + " = :" + fieldName)
            .bind(fieldName, fieldValue)
            .map(
                (row, metadata) ->
                    Tuples.of(convertUserAuthorities( row, metadata), Optional.ofNullable(row.get("authority_name", String.class)))
            )
            .all()
            .collectList()
            .filter(l -> !l.isEmpty())
            .map(l -> updateUserWithAuthorities(l.get(0).getT1(), l));
    }

    private User updateUserWithAuthorities(User user, List<Tuple2<User, Optional<String>>> tuples) {
        log.info( "updating user:"+user + " ;tuples:"+tuples);
        user.setAuthorities(
            tuples
                .stream()
                .filter(t -> t.getT2().isPresent())
                .map(
                    t -> {
                        Authority authority = new Authority();
                        authority.setName(t.getT2().get());
                        return authority;
                    }
                )
                .collect(Collectors.toSet())
        );

        return user;
    }
}

class UserSqlHelper {
    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("login", table, columnPrefix + "_login"));
        columns.add(Column.aliased("first_name", table, columnPrefix + "_first_name"));
        columns.add(Column.aliased("last_name", table, columnPrefix + "_last_name"));
        columns.add(Column.aliased("email", table, columnPrefix + "_email"));
        columns.add(Column.aliased("activated", table, columnPrefix + "_activated"));
        columns.add(Column.aliased("lang_key", table, columnPrefix + "_lang_key"));
        columns.add(Column.aliased("image_url", table, columnPrefix + "_image_url"));
        return columns;
    }
}
