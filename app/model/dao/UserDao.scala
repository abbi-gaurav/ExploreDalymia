package model.dao

import model.request.User
import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by gabbi on 17/04/16.
  */
case class UserDao private (email: String, createdAt: DateTime)

object UserDao {
  def create(user: User): UserDao = UserDao.apply(email = user.email, createdAt = new DateTime())

  implicit val userDaoFormat = Json.format[UserDao]
}
