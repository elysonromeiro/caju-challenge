package br.com.caju.challenge.constants.enums

enum class BalanceKindEnum(val descricao: String) {
    FOOD("Alimentação"), MEAL("Refeição"), CASH("Saldo Livre");

    override fun toString(): String {
        return descricao
    }
}