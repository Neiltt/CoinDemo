package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, Long> {

	List<Coin> findByCodeContaining(String code);
}
