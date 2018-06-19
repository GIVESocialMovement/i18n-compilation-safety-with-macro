package libraries


import play.api.i18n.Messages

import scala.language.experimental.macros
import givers.i18n.Translation

object TranslationHelper {
  def doNotUseThisMethodDirectlyTranslate(key: String, args: Any*)(implicit messages: Messages) = {
    messages(key, args.map(_.toString):_*)
  }

  def t(key: String, args: Any*): String = macro Translation.applyImpl
}
