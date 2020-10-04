package net.nanai10a.twomeat.core

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "name",
    "version",
    "listenerCoreClass"
)

data class ListenerProperty(
    @JsonProperty("name") val name: String,
    @JsonProperty("version") val version: String,
    @JsonProperty("listenerCoreClass") val listenerCoreClass: String
)
