package com.gabbi.utils.rest

import javax.inject.{Inject, Singleton}

import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Action._
import play.api.mvc.Results._
import play.api.mvc.{Action, AnyContent, Request, Result}

import scala.concurrent.Future

/**
  * Created by gabbi on 17/04/16.
  */
@Singleton
class Worker @Inject()(val messagesApi: MessagesApi) extends I18nSupport {

  def asyncFormAction[T](form: Form[T])(api: T => Future[Result]): Action[AnyContent] = async { implicit request: Request[AnyContent] =>
    form.bindFromRequest().fold({
      (errors: Form[T]) => Future.successful(handleErrors(errors))
    }, {
      (requestObj: T) => api(requestObj)
    })
  }

  def asyncAction(f: () => Future[Result]): Action[AnyContent] = async(f())

  private def handleErrors[T](errors: Form[T]): Result = BadRequest(errors.errorsAsJson)
}
