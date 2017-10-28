package com.costrella.sp.repository;

import com.costrella.sp.domain.Family;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Family entity.
 */
@SuppressWarnings("unused")
public interface FamilyRepository extends JpaRepository<Family,Long> {

}
