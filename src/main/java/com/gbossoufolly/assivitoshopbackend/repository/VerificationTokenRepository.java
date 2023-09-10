package com.gbossoufolly.assivitoshopbackend.repository;

import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(LocalUser user);

    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
}
