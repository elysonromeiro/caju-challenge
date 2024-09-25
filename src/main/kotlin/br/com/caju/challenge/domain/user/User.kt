package br.com.caju.challenge.domain.user

import br.com.caju.challenge.domain.balance.Balance
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    val name: String,
    val email: String,
    @JsonIgnore
    var pass: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var balances: List<Balance> = emptyList()
) : UserDetails {

    constructor() : this(null, "", "", "")

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return listOf(SimpleGrantedAuthority("USER"))
    }

    override fun getPassword(): String? {
        return pass
    }

    override fun getUsername(): String? {
        return email
    }

    fun setBalancesList(balancesToSet: List<Balance>) { this.balances = balancesToSet; }

}