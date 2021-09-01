package de.rgse.mc.villages.animation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesAnimationRegistry {

    public static final AnimationBuilder SETTLER_WALK = new AnimationBuilder().addAnimation("animation.villages:settler.walk", true);
    public static final AnimationBuilder SETTLER_WALK_SAD = new AnimationBuilder().addAnimation("animation.villages:settler.walk_sad", true);
    public static final AnimationBuilder SETTLER_IDLE_0 = new AnimationBuilder().addAnimation("animation.villages:settler.idle_0", false);
    public static final AnimationBuilder SETTLER_IDLE_1 = new AnimationBuilder().addAnimation("animation.villages:settler.idle_1", false);
    public static final AnimationBuilder SETTLER_IDLE_2 = new AnimationBuilder().addAnimation("animation.villages:settler.idle_2", false);
    public static final AnimationBuilder SETTLER_IDLE_3 = new AnimationBuilder().addAnimation("animation.villages:settler.idle_3", false);
    public static final AnimationBuilder SETTLER_IDLE_4 = new AnimationBuilder().addAnimation("animation.villages:settler.idle_4", false);

    public static AnimationBuilder randomSettlerIdle() {
        List<AnimationBuilder> animationBuilders = Arrays.asList(SETTLER_IDLE_1, SETTLER_IDLE_2, SETTLER_IDLE_3, SETTLER_IDLE_4);
        Collections.shuffle(animationBuilders);
        return animationBuilders.get(0);
    }
}
