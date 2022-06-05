package ru.ripod.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ripod.utils.dbmodels.LoginData;
import ru.ripod.utils.dbmodels.NoteData;

@Repository
public interface NoteDataRepository extends JpaRepository<NoteData, Long> {

    @Query("")
    public LoginData findByLogin(@Param("login")String login);
}
