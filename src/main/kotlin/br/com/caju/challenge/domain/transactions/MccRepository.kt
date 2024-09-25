package br.com.caju.challenge.domain.transactions

import org.springframework.data.repository.CrudRepository
import java.util.*

interface MccRepository : CrudRepository<Mcc, String> {
    fun findByMcc(mcc: String): Optional<Mcc>

}