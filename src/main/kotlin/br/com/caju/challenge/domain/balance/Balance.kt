package br.com.caju.challenge.domain.balance

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.domain.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "balances")
class Balance(
    @Id @GeneratedValue(strategy = GenerationType.UUID) var id: String?,
    val name: String,
    var amount: Int,
    @Enumerated(EnumType.STRING) val kind: BalanceKindEnum,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "user_id")
    @JsonIgnore
    val user: User? = null
) {
    constructor() : this(null, "", 0, BalanceKindEnum.CASH)
}