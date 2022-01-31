package ru.ripod.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ripod.utils.dbmodels.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
