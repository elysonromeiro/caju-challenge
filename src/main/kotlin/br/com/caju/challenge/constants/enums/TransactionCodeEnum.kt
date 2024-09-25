package br.com.caju.challenge.constants.enums

enum class TransactionCodeEnum(val descricao: String) {
    CODE_00("00"),
    CODE_51("51"),
    CODE_07("07");

    override fun toString(): String {
        return descricao
    }
}