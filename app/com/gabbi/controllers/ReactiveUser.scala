package com.gabbi.controllers

import javax.inject.{Inject, Singleton}

import com.gabbi.model.request.User
import com.gabbi.mongo.servies.UserService
import com.gabbi.utils.configs.ThreadPools.dbContext
import com.gabbi.utils.rest.Worker
import org.joda.time.DateTime
import play.api.libs.json.Json._
import play.api.mvc.{Action, AnyContent, Controller}

/**
  * Created by gabbi on 13/02/16.
  */
@Singleton
class ReactiveUser @Inject()(userService: UserService, worker: Worker) extends Controller {

  import worker._

  def create: Action[AnyContent] = asyncFormAction(User.userForm) { (obj: User) =>
    userService.add(obj) map {
      case Left(e) => BadRequest(toJson(e))
      case Right(_) => Redirect(routes.ReactiveUser.get(10, DateTime.now().getMillis), FOUND)
    }
  }

  def get(limit: Int, before: Long): Action[AnyContent] = asyncAction {
    () => userService.getUsers(limit, before) map (x => Ok(toJson(x)))
  }
}
