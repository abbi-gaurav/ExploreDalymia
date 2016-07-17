package com.gabbi.mongo.servies

import javax.inject.{Inject, Singleton}

import com.gabbi.model.dao.UserDao
import com.gabbi.model.request.User
import com.gabbi.model.status.{OpsFailed, OpsSucceeded}
import com.gabbi.mongo.servies.ServiceUtils._
import com.gabbi.utils.configs.UserConstants._
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.ReadPreference
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

// BSON-JSON conversions/collection
import reactivemongo.play.json._

/**
  * Created by gabbi on 22/02/16.
  */
@Singleton
class UserService @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  private implicit def eventualCollection(implicit executionContext: ExecutionContext): Future[JSONCollection] = {
    reactiveMongoApi.database.map(_.collection[JSONCollection](collectionName))
  }

  def add(user: User)(implicit executionContext: ExecutionContext): Future[Either[OpsFailed, OpsSucceeded]] = for {
    existingUserOpt <- getUser(user.email)
    res <- doInsert(UserDao.create(user = user), existingUserOpt)
  } yield res

  def getUsers(limit: Int, before: Long)(implicit executionContext: ExecutionContext): Future[List[UserDao]] = dbOps { collection =>
    collection.find(Json.obj(createdAtLabel -> Json.obj("$lt" -> before)))
      .sort(Json.obj(createdAtLabel -> -1)).cursor[UserDao](ReadPreference.nearest).collect[List](maxDocs = limit)
  }

  private def getUser(email: String)(implicit executionContext: ExecutionContext): Future[Option[UserDao]] = dbOps { collection =>
    collection.find(Json.obj(emailFieldName -> email)).one[UserDao]
  }

  private def doInsert(userDao: UserDao, existingUserOpt: Option[UserDao])(implicit executionContext: ExecutionContext): Future[Either[OpsFailed, OpsSucceeded]] = existingUserOpt match {
    case Some(x) => Future.successful(Left(OpsFailed.create("email Taken")))
    case None => dbOps { collection => collection.insert(userDao) map (x => convertResult(x)(_ => OpsSucceeded("created"))) }
  }

}
