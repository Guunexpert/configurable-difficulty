package com.example.biomediff.fabric.mixin;

import com.example.biomediff.core.XpHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityExperienceMixin {
    @Inject(method = "getExperienceReward", at = @At("RETURN"), cancellable = true)
    private void biomediff$scaleExperience(ServerLevel level, Entity entity, CallbackInfoReturnable<Integer> cir) {
        int scaled = XpHandler.scaleExperience(cir.getReturnValue(), (LivingEntity) (Object) this);
        cir.setReturnValue(scaled);
    }
}