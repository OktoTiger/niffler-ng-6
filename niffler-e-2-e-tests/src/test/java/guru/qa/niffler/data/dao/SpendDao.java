package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.spend.SpendEntity;

import java.util.Optional;
import java.util.UUID;

public interface SpendDao {
    SpendEntity create (SpendEntity spend);
    void delete (UUID id);
    Optional<SpendEntity> findSpendById(UUID id);
}
