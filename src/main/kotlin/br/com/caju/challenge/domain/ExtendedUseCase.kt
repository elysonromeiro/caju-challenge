package br.com.caju.challenge.domain

import br.com.caju.challenge.infra.interfaces.IUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory


open class ExtendedUseCase<P, R>() : IUseCase<P, R> {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun execute(params: P): R {
        try {
            return call(params)
        } catch (error: Exception) {
            this.logError(error)
            throw error
        }
    }

    /*
 * Should be overridden
 * */
    open fun call(params: P): R {
        TODO("Method not implemented.")
    }

    protected fun logBegin(params: P?) {
        this.logger.info("[${this::class.java}] begin", params)
    }

    protected fun logProgress(prograssParams: Any?) {
        this.logger.info("[${this::class.java}] progress", prograssParams)
    }

    protected fun logResult(result: Any?) {
        this.logger.info("[${this::class.java}] finish", result)
    }

    protected fun logError(error: Exception) {
        this.logger.info("[${this::class.java}] error -> ${error.message}", error)
    }

}