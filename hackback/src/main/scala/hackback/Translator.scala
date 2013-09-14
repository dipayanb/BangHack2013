package hackback

/**
 * @author Winash
 */
trait Translator {

  def translate()

}



object Languages extends Enumeration{
  type Languages = Value
  val Eng,Hin = Value
}
