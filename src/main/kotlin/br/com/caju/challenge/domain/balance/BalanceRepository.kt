package br.com.caju.challenge.domain.balance

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface BalanceRepository : CrudRepository<Balance, String> {
    fun findByUserIdAndKind(userId: String, kind: BalanceKindEnum): Optional<Balance>
}