package wing.tree.multiplication.table.extension.property

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

val <T> Iterable<T>.immutableList: ImmutableList<T> get() = toImmutableList()
