package com.gabbi.mongo.servies

import com.gabbi.model.dao.UserDao
import com.gabbi.model.request.User
import com.gabbi.model.status.{OpsFailed, OpsSucceeded}
import com.gabbi.mongo.servies.ServiceUtils._
import com.gabbi.utils.configs.UserConstants._
import com.google.inject.Inject
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.ReadPreference

import scala.concurrent.{ExecutionContext, Future}

// BSON-JSON conversions/collection
import play.modules.reactivemongo.json.collection._
import reactivemongo.play.json._

/**
  * Created by gabbi on 22/02/16.
  */
class UserService @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  private def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection](collectionName)

  def add(user: User)(implicit executionContext: ExecutionContext): Future[Either[OpsFailed, OpsSucceeded]] = for {
    existingUserOpt <- getUser(user.email)
    res <- doInsert(UserDao.create(user = user), existingUserOpt)
  } yield res

  def getUsers(limit: Int, before: Long)(implicit executionContext: ExecutionContext): Future[List[UserDao]] = {
    collection.find(Json.obj(createdAtLabel -> Json.obj("$lt" -> before)))
      .sort(Json.obj(createdAtLabel -> -1)).cursor[UserDao](ReadPreference.nearest).collect[List](upTo = limit)
  }

  private def getUser(email: String)(implicit executionContext: ExecutionContext): Future[Option[UserDao]] = {
    collection.find(Json.obj(emailFieldName -> email)).one[UserDao]
  }

  private def doInsert(userDao: UserDao, existingUserOpt: Option[UserDao])(implicit executionContext: ExecutionContext): Future[Either[OpsFailed, OpsSucceeded]] = existingUserOpt match {
    case Some(x) => Future.successful(Left(OpsFailed.create("email Taken")))
    case None => collection.insert(userDao) map (x => convertResult(x)(_ => OpsSucceeded("created")))
  }

}
