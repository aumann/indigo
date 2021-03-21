package com.example.sandbox.scenes

import indigo._
import indigo.scenes._
import com.example.sandbox.SandboxStartupData
import com.example.sandbox.SandboxGameModel
import com.example.sandbox.SandboxViewModel
import com.example.sandbox.SandboxAssets
import indigoextras.effectmaterials.LegacyEffects
import indigoextras.effectmaterials.Border
import indigoextras.effectmaterials.Thickness
import indigoextras.effectmaterials.Glow

object LegacyEffectsScene extends Scene[SandboxStartupData, SandboxGameModel, SandboxViewModel] {

  type SceneModel     = SandboxGameModel
  type SceneViewModel = SandboxViewModel

  def eventFilters: EventFilters =
    EventFilters.Restricted

  def modelLens: indigo.scenes.Lens[SandboxGameModel, SandboxGameModel] =
    Lens.keepOriginal

  def viewModelLens: Lens[SandboxViewModel, SandboxViewModel] =
    Lens.keepOriginal

  def name: SceneName =
    SceneName("legacy effects")

  def subSystems: Set[SubSystem] =
    Set()

  def updateModel(context: FrameContext[SandboxStartupData], model: SandboxGameModel): GlobalEvent => Outcome[SandboxGameModel] =
    _ => Outcome(model)

  def updateViewModel(context: FrameContext[SandboxStartupData], model: SandboxGameModel, viewModel: SandboxViewModel): GlobalEvent => Outcome[SandboxViewModel] =
    _ => Outcome(viewModel)

  val graphic: Graphic =
    Graphic(Rectangle(0, 0, 40, 40), 1, SandboxAssets.junctionBoxEffectsMaterial)
      .withRef(20, 20)

  def present(context: FrameContext[SandboxStartupData], model: SandboxGameModel, viewModel: SandboxViewModel): Outcome[SceneUpdateFragment] = {
    val viewCenter: Point = context.startUpData.viewportCenter + Point(0, -25)

    Outcome(
      SceneUpdateFragment(
        graphic // tint - identical to ImageEffects material
          .moveTo(viewCenter)
          .moveBy(0, -40)
          .modifyMaterial {
            case m: LegacyEffects => m.withTint(RGBA.Red)
            case m                => m
          },
        graphic // alpha - identical to ImageEffects material
          .moveTo(viewCenter)
          .moveBy(-60, -40)
          .modifyMaterial {
            case m: LegacyEffects => m.withAlpha(0.5)
            case m                => m
          },
        graphic // saturation - identical to ImageEffects material
          .moveTo(viewCenter)
          .moveBy(-30, -40)
          .modifyMaterial {
            case m: LegacyEffects => m.withSaturation(0.0)
            case m                => m
          },
        graphic //color overlay - identical to ImageEffects material
          .moveTo(viewCenter)
          .moveBy(30, -40)
          .modifyMaterial {
            case m: LegacyEffects => m.withOverlay(Fill.Color(RGBA.Magenta.withAmount(0.75)))
            case m                => m
          },
        graphic // linear gradient overlay - identical to ImageEffects material
          .moveTo(viewCenter)
          .moveBy(60, -40)
          .modifyMaterial {
            case m: LegacyEffects => m.withOverlay(Fill.LinearGradient(Point.zero, RGBA.Magenta, Point(40), RGBA.Cyan.withAmount(0.5)))
            case m                => m
          },
        graphic // radial gradient overlay - identical to ImageEffects material
          .moveTo(viewCenter)
          .moveBy(-60, 10)
          .modifyMaterial {
            case m: LegacyEffects => m.withOverlay(Fill.RadialGradient(Point(20), 10, RGBA.Magenta.withAmount(0.5), RGBA.Cyan.withAmount(0.25)))
            case m                => m
          },
        graphic // inner glow
          .moveTo(viewCenter)
          .moveBy(0, 10)
          .modifyMaterial {
            case m: LegacyEffects => m.withGlow(Glow(RGBA.Green, 2.0, 0.0))
            case m                => m
          },
        graphic // outer glow
          .moveTo(viewCenter)
          .moveBy(-30, 10)
          .modifyMaterial {
            case m: LegacyEffects => m.withGlow(Glow(RGBA.Blue, 0.0, 2.0))
            case m                => m
          },
        graphic // inner border
          .moveTo(viewCenter)
          .moveBy(30, 60)
          .modifyMaterial {
            case m: LegacyEffects => m.withBorder(Border(RGBA(1.0, 0.5, 0.0, 1.0), Thickness.Thick, Thickness.None))
            case m                => m
          },
        graphic // outer border
          .moveTo(viewCenter)
          .moveBy(60, 60)
          .modifyMaterial {
            case m: LegacyEffects => m.withBorder(Border(RGBA.Yellow, Thickness.None, Thickness.Thick))
            case m                => m
          },
        graphic // rotate & scale - standard transform
          .moveTo(viewCenter)
          .moveBy(30, 10)
          .rotateBy(Radians(0.2))
          .scaleBy(1.25, 1.25),
        graphic // flipped - standard transform
          .moveTo(viewCenter)
          .moveBy(60, 10)
          .flipHorizontal(true)
          .flipVertical(true)
      )
    )
  }

}
