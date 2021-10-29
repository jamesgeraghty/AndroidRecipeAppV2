package org.wit.recipesapp.models


interface UserStore {
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun findAll(): List<UserModel>

}