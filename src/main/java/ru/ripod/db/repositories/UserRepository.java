package ru.ripod.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ripod.utils.dbmodels.LoginData;

@Repository
public interface UserRepository extends JpaRepository<LoginData, Long> {}
