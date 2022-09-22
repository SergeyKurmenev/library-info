package com.example.libraryinfo.repositories;

import com.example.libraryinfo.entities.BorrowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedInfoRepository extends JpaRepository<BorrowInfo, Long> {
}
