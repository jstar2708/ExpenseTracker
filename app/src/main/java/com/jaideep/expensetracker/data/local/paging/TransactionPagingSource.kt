package com.jaideep.expensetracker.data.local.paging

import androidx.compose.runtime.collectAsState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.max

class TransactionPagingSource @Inject constructor(
    private val dao: TransactionDao,
    private val dataRetrieve: suspend (loadSize: Int, start: Int) -> Flow<List<Transaction>>
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        // Start paging with the first page if `params.key` is null
        val start = params.key ?: 0
        // Load the data from the database
        val data: MutableStateFlow<List<Transaction>> = MutableStateFlow(ArrayList())
        dataRetrieve(params.loadSize, start).collect {
            data.value = it
        }
//        val data = dao.getAllTransactions(params.loadSize, start)
//        val data = mutableListOf<Transaction>()
//        for (i in start..start + params.loadSize) {
//            data.add(Transaction(i, i.toDouble()*10, 5, i, "Hello", i.toLong()*20, 0))
//        }
        // Return the data and the next key
        return LoadResult.Page(
            data = data.value,
            prevKey = if (start == 0) null else max(start - params.loadSize, 0),
            nextKey = if (data.value.isEmpty()) null else start + params.loadSize + 1
        )
    }
}