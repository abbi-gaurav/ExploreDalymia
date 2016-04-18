package utils.configs
import scala.concurrent.ExecutionContext

/**
  * Created by gabbi on 18/04/16.
  */
object ThreadPools {
  implicit val dbContext: ExecutionContext = play.api.libs.concurrent.Execution.Implicits.defaultContext
}
