package br.com.caju.challenge.domain.transactions

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.user.User
import jakarta.persistence.*

@Entity
@Table(name = "transactions")
class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.UUID) var id: String?,
    val account: String,
    @Column(name = "total_amount") var totalAmount: Int,
    @Column(name = "balance_before") val balanceBefore: Int,
    @Column(name = "balance_after") val balanceAfter: Int,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "balance_id") val balance: Balance,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_id") val user: User,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "mcc_id") val mcc: Mcc? = null
) {
    constructor() : this(null, "", 0, 0, 0, Balance(), User(), Mcc())
}