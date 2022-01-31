package ru.ripod.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ripod.utils.dbmodels.LoginData;

@Repository
public interface LoginDataRepository extends JpaRepository<LoginData, Long> {
    @Query("SELECT ld FROM LoginData ld WHERE ld.login = :login")
    public LoginData findByLogin(@Param("login")String login);
}
