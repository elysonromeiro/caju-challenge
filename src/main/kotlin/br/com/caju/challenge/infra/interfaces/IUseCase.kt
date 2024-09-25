package br.com.caju.challenge.infra.interfaces

interface IUseCase<P, R> {
    fun execute(params: P): R
}