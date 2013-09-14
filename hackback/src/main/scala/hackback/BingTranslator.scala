package hackback

import hackback.jd.QueryResult
import com.memetix.mst.language.Language
import com.memetix.mst.translate.Translate
import utils.Utils

/**
 * @author Winash
 */
class BingTranslator extends Translator {

  val accountKey = "EKO5TkdHvhgbjOOEBW6esd9M4itttXljmNoPe5uikAQ="
  val endPoint = "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate"


  def translate(obj: QueryResult): QueryResult = {
    val strings: Array[String] = Utils.doTranslate(obj.companyName,obj.address)
    val qr = QueryResult(obj)
    qr.companyName = strings(0)
    qr.address = strings(1)
    qr
  }
}







