package net.jmb19905.niftycarts.entity;

import net.minecraft.core.PositionAndRotation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.LinearInterpolationHandler;
import net.minecraft.world.entity.PositionPath;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CartInterpolationHandler implements InterpolationHandler {

    private final Entity entity;
    private int interpolationSteps;
    private final CartInterpolationHandler.CartInterpolationData interpolationData = new CartInterpolationHandler.CartInterpolationData(0, Vec3.ZERO, 0.0F, 0.0F, Vec3.ZERO);
    @Nullable
    private Vec3 previousTickPosition;
    @Nullable
    private Vec2 previousTickRot;
    @Nullable
    private Vec3 previousTickVelocity;

    public CartInterpolationHandler(Entity entity) {
        this(entity, LinearInterpolationHandler.DEFAULT_INTERPOLATION_STEPS);
    }

    public CartInterpolationHandler(Entity entity, int interpolationSteps) {
        this.interpolationSteps = interpolationSteps;
        this.entity = entity;
    }

    public @NotNull Vec3 position() {
        return this.interpolationData.steps > 0 ? this.interpolationData.position : this.entity.position();
    }

    public float yRot() {
        return this.interpolationData.steps > 0 ? this.interpolationData.yRot : this.entity.getYRot();
    }

    public float xRot() {
        return this.interpolationData.steps > 0 ? this.interpolationData.xRot : this.entity.getXRot();
    }

    public Vec3 velocity() {
        return this.interpolationData.steps > 0 ? this.interpolationData.deltaMovement : this.entity.getDeltaMovement();
    }

    @Override
    public @NotNull PositionAndRotation target() {
        return PositionAndRotation.of(this.position(), this.yRot(), this.xRot());
    }

    @Override
    public boolean interpolateTo(@Nullable PositionPath path, float yRot, float xRot, boolean useRotation) {
        // Like vanilla: a null path keeps the current target position, useRotation=false keeps the current target rotation
        Vec3 pos = path != null ? path.endPosition() : this.position();
        this.interpolateTo(pos, useRotation ? yRot : this.yRot(), useRotation ? xRot : this.xRot(), Vec3.ZERO);
        return true;
    }

    public void interpolateTo(Vec3 pos, float yRot, float xRot, Vec3 velocity) {
        if (this.interpolationSteps == 0) {
            this.entity.snapTo(pos, yRot, xRot);
            this.entity.setDeltaMovement(velocity);
            this.cancel();
        } else {
            this.interpolationData.steps = this.interpolationSteps;
            this.interpolationData.position = pos;
            this.interpolationData.yRot = yRot;
            this.interpolationData.xRot = xRot;
            this.interpolationData.deltaMovement = velocity;
            this.previousTickPosition = this.entity.position();
            this.previousTickRot = new Vec2(this.entity.getXRot(), this.entity.getYRot());
            this.previousTickVelocity = this.entity.getDeltaMovement();
            this.entity.onInterpolationStart(this);
        }
    }

    public void interpolateTo(@NotNull Vec3 pos, float yRot, float xRot) {
        this.interpolateTo(pos, yRot, xRot, Vec3.ZERO);
    }

    @Override
    public void applyPredictedMovement(@NotNull Vec3 delta) {
        if (!this.hasActiveInterpolation()) return;
        if (this.entity.level().noCollision(this.entity, this.entity.makeBoundingBox().move(this.interpolationData.position.add(delta)))) {
            this.interpolationData.addPositionDelta(delta);
        }
        if (this.previousTickPosition != null) {
            this.previousTickPosition = this.previousTickPosition.add(delta);
        }
    }

    @Override
    public boolean hasActiveInterpolation() {
        return this.interpolationData.steps > 0;
    }

    public void setInterpolationLength(int interpolationLength) {
        this.interpolationSteps = interpolationLength;
    }

    @Override
    public void interpolate() {
        if (!this.hasActiveInterpolation()) {
            this.cancel();
        } else {
            double delta = 1.0 / this.interpolationData.steps;
            if (this.previousTickPosition != null) {
                Vec3 vec3 = this.entity.position().subtract(this.previousTickPosition);
                if (this.entity.level().noCollision(this.entity, this.entity.makeBoundingBox().move(this.interpolationData.position.add(vec3)))) {
                    this.interpolationData.addPositionDelta(vec3);
                }
            }

            if (this.previousTickRot != null) {
                float f = this.entity.getYRot() - this.previousTickRot.y;
                float g = this.entity.getXRot() - this.previousTickRot.x;
                this.interpolationData.addRotation(f, g);
            }

            if (this.previousTickVelocity != null) {
                Vec3 vec3 = this.entity.getDeltaMovement().subtract(this.previousTickVelocity);
                this.interpolationData.addVelocityDelta(vec3);
            }

            double x = Mth.lerp(delta, this.entity.getX(), this.interpolationData.position.x);
            double y = Mth.lerp(delta, this.entity.getY(), this.interpolationData.position.y);
            double z = Mth.lerp(delta, this.entity.getZ(), this.interpolationData.position.z);
            Vec3 interpolatedPos = new Vec3(x, y, z);
            float yRot = (float)Mth.rotLerp(delta, this.entity.getYRot(), this.interpolationData.yRot);
            float xRot = (float)Mth.lerp(delta, this.entity.getXRot(), this.interpolationData.xRot);
            double dx = Mth.lerp(delta, this.entity.getDeltaMovement().x, this.interpolationData.deltaMovement.x);
            double dy = Mth.lerp(delta, this.entity.getDeltaMovement().x, this.interpolationData.deltaMovement.x);
            double dz = Mth.lerp(delta, this.entity.getDeltaMovement().x, this.interpolationData.deltaMovement.x);
            Vec3 interpolatedVelocity = new Vec3(dx, dy, dz);
            this.entity.setPos(interpolatedPos);
            this.entity.setRot(yRot, xRot);
            this.entity.setDeltaMovement(interpolatedVelocity);
            this.interpolationData.decrease();
            this.previousTickPosition = interpolatedPos;
            this.previousTickRot = new Vec2(this.entity.getXRot(), this.entity.getYRot());
            this.previousTickVelocity = interpolatedVelocity;
        }
    }

    @Override
    public void cancel() {
        this.interpolationData.steps = 0;
        this.previousTickPosition = null;
        this.previousTickRot = null;
    }

    static class CartInterpolationData {

        protected int steps;
        Vec3 position;
        float yRot;
        float xRot;
        Vec3 deltaMovement;

        CartInterpolationData(int steps, Vec3 position, float yRot, float xRot, Vec3 deltaMovement) {
            this.steps = steps;
            this.position = position;
            this.yRot = yRot;
            this.xRot = xRot;
            this.deltaMovement = deltaMovement;
        }

        public void decrease() {
            this.steps--;
        }

        public void addPositionDelta(Vec3 delta) {
            this.position = position.add(delta);
        }

        public void addVelocityDelta(Vec3 accel) {
            this.deltaMovement = deltaMovement.add(accel);
        }

        public void addRotation(float yRot, float xRot) {
            this.yRot += yRot;
            this.xRot += xRot;
        }

    }

}
