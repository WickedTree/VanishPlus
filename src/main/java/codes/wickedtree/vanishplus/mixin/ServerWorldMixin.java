package codes.wickedtree.vanishplus.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import codes.wickedtree.vanishplus.VanishPlus;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "playSound")
    private void onPlaySound(@Nullable PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, CallbackInfo ci) {
        if (!VanishPlus.INSTANCE.isActive()) return;
        if (player == null) return;

        if (VanishPlus.INSTANCE.getVanishedPlayers().stream().anyMatch(vanishedPlayer -> vanishedPlayer.getName().equals(player.getEntityName())))
            ci.cancel();
    }
}
