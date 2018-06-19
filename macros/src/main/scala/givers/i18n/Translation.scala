package givers.i18n

import java.io.File
import java.text.MessageFormat

import play.api.i18n.Messages

import scala.reflect.macros.whitebox

object Translation {
  val messages = {
    val file = new File("conf/messages")
    Messages.parse(Messages.UrlMessageSource(file.toURI.toURL), file.getCanonicalPath).fold(throw _, { m => m})
  }

  def applyImpl(c: whitebox.Context)(key :c.Expr[String], args: c.Expr[Any]*): c.Expr[String] = {
    import c.universe._

    key.tree match {
      case Literal(Constant(k: String)) =>
        messages.get(k) match {
          case Some(text) =>
            val requiredArgSize = new MessageFormat(text).getFormats.length
            if (args.size != requiredArgSize) {
              throw new Exception(s"The key '$k' requires $requiredArgSize arguments. But ${args.size} arguments was given.")
            }
          case None => throw new Exception(s"The key '$k' isn't defined in conf/locale/messages")
        }
      case _ => // do nothing because the key isn't a constant
    }


    import c.universe._
    c.Expr[String](
      q"""
        import ${c.prefix}._
        _root_.libraries.TranslationHelper.doNotUseThisMethodDirectlyTranslate($key, ..$args)
       """
    )
  }
}
