package controllers

import com.gabbi.controllers.ReactiveUser
import com.gabbi.model.request.User
import com.gabbi.model.status.OpsSucceeded
import com.gabbi.mongo.servies.UserService
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsJson, Result}
import play.api.test.FakeRequest
import testUtils.BaseSpec

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by gabbi on 23/04/16.
  */
class UserControllerSpec extends BaseSpec {

  import UserControllerSpec._

  "UserController" must {
    "add valid User successfully" in {
      val request = getFakeRequest(
        s"""
           |{
           |"email" : "test@test.com"
           |}
        """.stripMargin
      )
      val res: Future[Result] = controller.create.apply(request)
      whenReady(res, defaultTimeOut) { r =>
        println(r)
      }
    }
  }

  private def getFakeRequest(req: String): FakeRequest[AnyContentAsJson] = {
    FakeRequest().withJsonBody(Json.parse(req))
  }
}

object UserControllerSpec {

  import org.mockito.Matchers._
  import org.mockito.Mockito._

  val userService: UserService = mock(classOf[UserService])
  when(userService.add(any[User])(any[ExecutionContext])).thenReturn(Future.successful(Right(OpsSucceeded("created"))))
  val controller = new ReactiveUser(userService)
}
