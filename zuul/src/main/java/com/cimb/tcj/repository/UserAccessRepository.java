package com.cimb.tcj.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.tcj.entity.UserAccess;

public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {

	public List<UserAccess> findByUsername(@NotBlank final String username);
}
