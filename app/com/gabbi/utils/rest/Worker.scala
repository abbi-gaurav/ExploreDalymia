package com.gabbi.utils.rest

import play.api.Play.current
import play.api.data.Form
import play.api.i18n.Messages.Implicits._
import play.api.mvc.Action._
import play.api.mvc.Results._
import play.api.mvc.{Action, AnyContent, Request, Result}

import scala.concurrent.Future

/**
  * Created by gabbi on 17/04/16.
  */
object Worker {

  def asyncActionWithBody[T](form: Form[T])(api: T => Future[Result]): Action[AnyContent] = async { implicit request: Request[AnyContent] =>
    form.bindFromRequest().fold({
      (errors: Form[T]) => Future.successful(handleErrors(errors))
    }, {
      (requestObj: T) => api(requestObj)
    })
  }

  def asyncActionGet(doGet: () => Future[Result]): Action[AnyContent] = async(doGet())

  private def handleErrors[T](errors: Form[T]): Result = BadRequest(errors.errorsAsJson)
}
