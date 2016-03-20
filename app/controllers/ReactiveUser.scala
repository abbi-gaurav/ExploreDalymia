package controllers

import com.google.inject.Inject
import model._
import play.api.mvc.{Action, Controller}
import servies.read.UserReadService
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

/**
  * Created by gabbi on 13/02/16.
  */
class ReactiveUser @Inject()(userReadService: UserReadService) extends Controller {

  def index = Action {
    Ok(userReadService.name)
  }

  def create = Action { implicit request =>
    userForm.bindFromRequest().fold({
      errors => BadRequest(errors.errorsAsJson)
    }, { value: User =>
      Created(value.name.toUpperCase)
    })
  }
}
