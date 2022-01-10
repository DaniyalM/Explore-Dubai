package com.dubaiculture.data.repository.base


open class BaseLDS<T> constructor(private val baseDao: BaseDao<T>) {

    suspend fun insert(obj: T) = baseDao.insert(obj)

    suspend fun insertAll(list: List<T>) = baseDao.insertAll(list)

    suspend fun update(obj: T) = baseDao.update(obj)

    suspend fun delete(obj: T) = baseDao.delete(obj)

}