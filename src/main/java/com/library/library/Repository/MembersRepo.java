package com.library.library.Repository;

import com.library.library.Entities.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MembersRepo extends JpaRepository<Members, String> {
}
