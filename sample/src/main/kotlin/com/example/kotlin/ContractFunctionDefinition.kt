@file:OptIn(ExperimentalContracts::class)

package com.example.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class ContractFunctionDefinition {
    inline fun foo(block: () -> Unit) {
        contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
        block()
    }
}
