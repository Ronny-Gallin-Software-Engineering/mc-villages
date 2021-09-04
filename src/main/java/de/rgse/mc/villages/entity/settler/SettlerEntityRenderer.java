package de.rgse.mc.villages.entity.settler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SettlerEntityRenderer extends GeoEntityRenderer<SettlerEntity> {

    private SettlerEntityRenderer(EntityRendererFactory.Context ctx, SettlerEntityModel targetModel) {
        super(ctx, targetModel);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        /*if(bone.getName().equals("right_hand")) {
            stack.push();
            //You'll need to play around with these to get item to render in the correct orientation
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
            //You'll need to play around with this to render the item in the correct spot.
            stack.translate(0.4D, 0.3D, 0.6D);
            //Sets the scaling of the item.
            stack.scale(1.0f, 1.0f, 1.0f);
            // Change mainHand to predefined Itemstack and Mode to what transform you would want to use.
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }*/
        if(bone.getName().equals("left_hand")) {
            stack.push();
            //You'll need to play around with these to get item to render in the correct orientation
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(75));
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
            //You'll need to play around with this to render the item in the correct spot.
            stack.translate(0.4D, 0.3D, 0.6D);
            //Sets the scaling of the item.
            stack.scale(1.0f, 1.0f, 1.0f);
            // Change mainHand to predefined Itemstack and Mode to what transform you would want to use.
            MinecraftClient.getInstance().getItemRenderer().renderItem(offHand, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        if(bone.getName().equals("core")) {
            // not yet
        }
        if(bone.getName().equals("hip")) {
            // not yet
        }
        if(bone.getName().endsWith("lower_leg")) {
           // not yet
        }
        if(bone.getName().endsWith("head")) {
            // not yet
        }

        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public static <E extends SettlerEntityModel> SettlerEntityRenderer of(EntityRendererFactory.Context ctx, Class<E> targetModelClass) {
        try {
            E targetModel = targetModelClass.getConstructor().newInstance();
            return new SettlerEntityRenderer(ctx, targetModel);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
