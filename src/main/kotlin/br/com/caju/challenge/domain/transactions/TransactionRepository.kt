package br.com.caju.challenge.domain.transactions

import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, String>