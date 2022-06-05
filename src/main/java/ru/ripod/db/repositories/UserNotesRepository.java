package ru.ripod.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ripod.utils.dbmodels.UserNotes;
import ru.ripod.utils.dbmodels.UserNotesId;

@Repository
public interface UserNotesRepository extends JpaRepository<UserNotes, UserNotesId> {
}
