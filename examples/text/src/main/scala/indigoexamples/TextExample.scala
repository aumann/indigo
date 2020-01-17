package indigoexamples

import indigo._
import indigoexts.entrypoint._

object TextExample extends IndigoGameBasic[Unit, Model, Unit] {

  import FontStuff._

  val config: GameConfig =
    defaultGameConfig
      .withClearColor(ClearColor.fromHexString("0xAA3399"))

  val assets: Set[AssetType] =
    Set(AssetType.Image(fontName, "assets/boxy_font.png"))

  val fonts: Set[FontInfo] =
    Set(fontInfo)

  val animations: Set[Animation] =
    Set()

  val subSystems: Set[SubSystem] =
    Set()

  def setup(assetCollection: AssetCollection): Startup[StartupErrors, Unit] =
    Startup.Success(())

  def initialModel(startupData: Unit): Model =
    Model(Tint.None)

  def update(gameTime: GameTime, model: Model, dice: Dice): GlobalEvent => Outcome[Model] = {
    case ChangeColour =>
      Outcome(model.changeTint(dice))

    case _ =>
      Outcome(model)
  }

  def initialViewModel(startupData: Unit): Model => Unit =
    _ => ()

  def updateViewModel(gameTime: GameTime, model: Model, viewModel: Unit, inputSignals: InputSignals, dice: Dice): Outcome[Unit] =
    Outcome(viewModel)

  def present(gameTime: GameTime, model: Model, viewModel: Unit, inputSignals: InputSignals): SceneUpdateFragment =
    SceneUpdateFragment()
      .addGameLayerNodes(
        Text("Hello, world!\nThis is some text!", config.viewport.width - 10, 20, 1, fontKey)
          .withTint(model.tint)
          .alignRight
          .onEvent {
            case (bounds, MouseEvent.Click(_, _)) if inputSignals.wasMouseClickedWithin(bounds) =>
              List(ChangeColour)

            case _ =>
              Nil
          }
      )

}

case object ChangeColour extends GlobalEvent

final case class Model(tint: Tint) {
  def changeTint(dice: Dice): Model =
    dice.roll(3) match {
      case 1 =>
        this.copy(tint = Tint.Red)

      case 2 =>
        this.copy(tint = Tint.Green)

      case 3 =>
        this.copy(tint = Tint.Blue)
    }

}

object FontStuff {

  val fontName: String = "My boxy font"

  def fontKey: FontKey = FontKey("My Font")

  def fontInfo: FontInfo =
    FontInfo(fontKey, fontName, 320, 230, FontChar("?", 93, 52, 23, 23))
      .addChar(FontChar("A", 3, 78, 23, 23))
      .addChar(FontChar("B", 26, 78, 23, 23))
      .addChar(FontChar("C", 50, 78, 23, 23))
      .addChar(FontChar("D", 73, 78, 23, 23))
      .addChar(FontChar("E", 96, 78, 23, 23))
      .addChar(FontChar("F", 119, 78, 23, 23))
      .addChar(FontChar("G", 142, 78, 23, 23))
      .addChar(FontChar("H", 165, 78, 23, 23))
      .addChar(FontChar("I", 188, 78, 15, 23))
      .addChar(FontChar("J", 202, 78, 23, 23))
      .addChar(FontChar("K", 225, 78, 23, 23))
      .addChar(FontChar("L", 248, 78, 23, 23))
      .addChar(FontChar("M", 271, 78, 23, 23))
      .addChar(FontChar("N", 3, 104, 23, 23))
      .addChar(FontChar("O", 29, 104, 23, 23))
      .addChar(FontChar("P", 54, 104, 23, 23))
      .addChar(FontChar("Q", 75, 104, 23, 23))
      .addChar(FontChar("R", 101, 104, 23, 23))
      .addChar(FontChar("S", 124, 104, 23, 23))
      .addChar(FontChar("T", 148, 104, 23, 23))
      .addChar(FontChar("U", 173, 104, 23, 23))
      .addChar(FontChar("V", 197, 104, 23, 23))
      .addChar(FontChar("W", 220, 104, 23, 23))
      .addChar(FontChar("X", 248, 104, 23, 23))
      .addChar(FontChar("Y", 271, 104, 23, 23))
      .addChar(FontChar("Z", 297, 104, 23, 23))
      .addChar(FontChar("0", 3, 26, 23, 23))
      .addChar(FontChar("1", 26, 26, 15, 23))
      .addChar(FontChar("2", 41, 26, 23, 23))
      .addChar(FontChar("3", 64, 26, 23, 23))
      .addChar(FontChar("4", 87, 26, 23, 23))
      .addChar(FontChar("5", 110, 26, 23, 23))
      .addChar(FontChar("6", 133, 26, 23, 23))
      .addChar(FontChar("7", 156, 26, 23, 23))
      .addChar(FontChar("8", 179, 26, 23, 23))
      .addChar(FontChar("9", 202, 26, 23, 23))
      .addChar(FontChar("?", 93, 52, 23, 23))
      .addChar(FontChar("!", 3, 0, 15, 23))
      .addChar(FontChar(".", 286, 0, 15, 23))
      .addChar(FontChar(",", 248, 0, 15, 23))
      .addChar(FontChar(" ", 145, 52, 23, 23))
}