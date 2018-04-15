package com.gabbi.controllers

import javax.inject.{Inject, Singleton}

import com.gabbi.controllers.RestUtils._
import com.gabbi.model.dao.{CommonHeaders, Location}
import com.gabbi.model.status.OpsFailed
import com.gabbi.mongo.servies.HogwartsService
import com.gabbi.utils.configs.ThreadPools.dbContext
import com.gabbi.utils.rest.Worker
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future

/**
  * Created by gabbi on 19/07/16.
  */
@Singleton
class HarryPotter @Inject()(worker: Worker, val messagesApi: MessagesApi, hogwartsService: HogwartsService) extends Controller with I18nSupport {

  def normal: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Location.locationForm.bindFromRequest().fold(
      errors => Future.successful(BadRequest(errors.errorsAsJson)),
      success => for {
        either <- hogwartsService.fetchHorcrux(success)
      } yield {
        either match {
          case Left(e: OpsFailed) => InternalServerError(Json.toJson(e))
          case Right(horcrux) => Ok(Json.toJson(horcrux))
        }
      }
    )
  }

  import worker._

  def refined: Action[AnyContent] = asyncFormAction(Location.locationForm)((obj: Location, ch: CommonHeaders.type) => for {
    either <- hogwartsService.fetchHorcrux(obj)
  } yield toJsonResult(either)
  )

  def composobale: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val specificHeader = request.headers.get("spell")

    val action = asyncFormAction(Location.locationForm)((obj, ch) => for {
      either <- hogwartsService.fetchHorcrux(obj)
    } yield {
      val result: Result = toJsonResult(either)
      specificHeader.foldLeft(result) { case (acc, hv) => acc.withHeaders("spellInvoked" -> hv) }
    }
    )

    action(request)
  }
}
