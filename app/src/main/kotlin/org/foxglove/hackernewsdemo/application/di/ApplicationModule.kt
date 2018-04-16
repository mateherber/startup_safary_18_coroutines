package org.foxglove.hackernewsdemo.application.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.kodein.Kodein
import org.kodein.generic.bind
import org.kodein.generic.singleton

val applicationModule = Kodein.Module {
    bind<ObjectMapper>() with singleton { jacksonObjectMapper() }
}