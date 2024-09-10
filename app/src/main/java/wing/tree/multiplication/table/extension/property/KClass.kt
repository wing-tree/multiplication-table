@file:Suppress("unused")

package wing.tree.multiplication.table.extension.property

import kotlin.reflect.KClass

val KClass<*>.contentKey: String get() = checkNotNull(qualifiedName ?: simpleName)
val KClass<*>.elementName: String get() = checkNotNull(qualifiedName ?: simpleName)
val KClass<*>.name: String get() = checkNotNull(qualifiedName ?: simpleName)
val KClass<*>.serialName: String get() = checkNotNull(qualifiedName ?: simpleName)
