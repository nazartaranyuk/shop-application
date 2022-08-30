package com.nazartaraniuk.shopapplication.presentation.adapters

interface DisplayableItem {

    fun id() = this.toString()

    fun payload(other: Any): Payloadable = Payloadable.None

    interface Payloadable {
        object None : Payloadable
    }
}