package br.com.caju.challenge.controller

data class ControllerResponse<T>(
    val success: Boolean,
    val result: T?,
)