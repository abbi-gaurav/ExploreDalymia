package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import servies.read.UserReadService

/**
  * Created by gabbi on 13/02/16.
  */
class ReactiveUser @Inject()(userReadService: UserReadService) extends Controller {

  def index = Action {
    Ok(userReadService.name)
  }
}
