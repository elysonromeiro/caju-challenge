package br.com.caju.challenge.util

class CentsToReal {
    companion object {
        fun convert(real: Double): Int {
            return( real * 100).toInt()
        }
    }
}