package mongo.servies

import com.google.inject.Inject
import model.dao.UserDao
import model.request.User
import mongo.servies.ServiceUtils._
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.ReadPreference
import utils.configs.UserConstants._

import scala.concurrent.{ExecutionContext, Future}

// BSON-JSON conversions/collection
import play.modules.reactivemongo.json.collection._
import reactivemongo.play.json._

/**
  * Created by gabbi on 22/02/16.
  */
class UserService @Inject()(reactiveMongoApi: ReactiveMongoApi) {
  val emptySelector: JsObject = Json.obj()

  val dupEmail: JsObject = Json.obj("error" -> "emailTaken")

  private def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection](collectionName)

  def add(user: User)(implicit executionContext: ExecutionContext): Future[Either[JsObject, String]] = for {
    existingUserOpt <- getUser(user.email)
    res <- doInsert(UserDao.create(user = user), existingUserOpt)
  } yield res

  def getUsers(limit: Int, before: Long)(implicit executionContext: ExecutionContext): Future[List[UserDao]] = {
    collection.find(Json.obj(createdAtLabel -> Json.obj("$lt" -> before))).sort(Json.obj(createdAtLabel -> -1)).cursor[UserDao](ReadPreference.nearest).collect[List](upTo = limit)
  }

  def getUser(email: String)(implicit executionContext: ExecutionContext): Future[Option[UserDao]] = {
    collection.find(Json.obj(emailFieldName -> email)).one[UserDao]
  }

  private def doInsert(userDao: UserDao, existingUserOpt: Option[UserDao])(implicit executionContext: ExecutionContext): Future[Either[JsObject, String]] = existingUserOpt match {
    case Some(x) => Future.successful(Left(dupEmail))
    case None => collection.insert(userDao) map (x => convert(x)(_ => "created"))
  }

}
