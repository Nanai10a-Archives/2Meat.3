package net.nanai10a.twomeat.core

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("listenerProperty")

data class TwoTJson(@JsonProperty("listenerProperty") val listenerProperty: ListenerProperty)
