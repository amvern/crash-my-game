package github.amvern.crashmygame.client.mixin;

import net.minecraft.CrashReport;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Component title) {
		super(title);
	}

//	@ModifyVariable(method = "init", at = @At("STORE"), name = "topPos")
//	private int modTopPos(int value) {
//	}

	@Inject(method = "createNormalMenuOptions", at = @At("TAIL"), cancellable = true)
	private void crashmygame$createNormalMenuOptions(int topPos, int spacing, CallbackInfoReturnable<Integer> cir) {

		this.addRenderableWidget(
			Button.builder(
				Component.translatable("menu.crashmygame"),
				(button)-> crashthegame()
			)
			.bounds(this.width /2 - 100, topPos + spacing, 200, 20).build()
		);

		cir.setReturnValue(topPos + 24);
	}

	@Unique private void crashthegame() {
		this.minecraft.emergencySaveAndCrash(new CrashReport("You have willingly crashed your own game, congratulations!", new Throwable("Crashed on account of user preference")));
	}
}