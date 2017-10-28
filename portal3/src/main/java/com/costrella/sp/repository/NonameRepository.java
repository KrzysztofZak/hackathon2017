package com.costrella.sp.repository;

import com.costrella.sp.domain.Noname;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Noname entity.
 */
@SuppressWarnings("unused")
public interface NonameRepository extends JpaRepository<Noname,Long> {

}
