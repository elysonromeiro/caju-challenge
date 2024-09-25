package br.com.caju.challenge.domain.transactions

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import jakarta.persistence.*

@Entity
@Table(name = "mccs")
class Mcc(
    @Id @GeneratedValue(strategy = GenerationType.UUID) var id: String?,
    val mcc: String,
    @Enumerated(EnumType.STRING) val balanceKind: BalanceKindEnum
) {
    constructor() : this(null, "", BalanceKindEnum.CASH)
}