package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.util.Properties
import play.api.libs.json._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class VersionController @Inject() extends Controller {

  case class Version(version: String, commitSha: String)

  implicit val versionWrites = Json.writes[Version]

  val version = {
    val props = new Properties()
    props.load(getClass.getClassLoader.getResourceAsStream("version.properties"))
    Version(props.getProperty("version"), props.getProperty("commitSha"))
  }

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(Json.toJson(version))
  }

}
