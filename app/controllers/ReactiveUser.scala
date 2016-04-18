package controllers

import com.google.inject.Inject
import model.request.User
import mongo.servies.UserService
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import utils.configs.ThreadPools.dbContext
import utils.rest.Worker._

/**
  * Created by gabbi on 13/02/16.
  */
class ReactiveUser @Inject()(userService: UserService) extends Controller {

  def create: Action[AnyContent] = asyncActionWithBody(User.userForm) { (obj: User) =>
    userService.add(obj) map {
      case Left(e) => BadRequest(e)
      case Right(_) => Redirect(routes.ReactiveUser.get(10, DateTime.now().getMillis))
    }
  }

  def get(limit: Int, before: Long): Action[AnyContent] = asyncActionGet { () => userService.getUsers(limit, before) map (x => Ok(Json.toJson(x))) }
}
