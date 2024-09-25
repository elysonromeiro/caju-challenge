package br.com.caju.challenge.infra.filters

import jakarta.servlet.ServletOutputStream
import jakarta.servlet.WriteListener
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import java.io.ByteArrayOutputStream
import java.io.PrintWriter

class Response(response: HttpServletResponse) : HttpServletResponseWrapper(response) {
    private val outputStream = ByteArrayOutputStream()
    private val printWriter = PrintWriter(outputStream)

    override fun getOutputStream(): ServletOutputStream {
        return object : ServletOutputStream() {
            override fun write(b: Int) {
                outputStream.write(b)
            }

            override fun isReady(): Boolean {
                return true
            }

            override fun setWriteListener(listener: WriteListener?) {}
        }
    }

    override fun getWriter(): PrintWriter {
        return printWriter
    }

    fun getCapturedResponse(): String {
        printWriter.flush()
        return String(outputStream.toByteArray())
    }
}