package com.test.notice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  public List<Notice> findByTitle(String title);

	@Query("select c from Notice c")
	Page<Notice> selectInfoList(Pageable pageable);

}