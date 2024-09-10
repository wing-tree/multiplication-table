package wing.tree.multiplication.table.lru.cache

import androidx.collection.lruCache
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

internal object LruCache {
    private const val MAX_SIZE = 4

    val fontSize = lruCache<Pair<Dp, Dp>, TextUnit>(maxSize = MAX_SIZE)
}
