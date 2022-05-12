package com.zanchen.develop.wordsdiary.util;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Property;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.IntDef;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import java.util.ArrayList;

/**
 * 旋转工具类
 * Created by Yahya Bayramoglu on 01/12/15.
 */
public class RotatableUtil implements View.OnTouchListener {

    private static final int NULL_INT = -1;

    public static final int ROTATE_BOTH = 0;
    public static final int ROTATE_X = 1;
    public static final int ROTATE_Y = 2;

    @IntDef({ROTATE_X, ROTATE_Y, ROTATE_BOTH})
    public @interface Direction {
    }

    public static final int FRONT_VIEW = 3;
    public static final int BACK_VIEW = 4;

    private final RotationListener rotationListener;
    private final View rootView;
    private View frontView;
    private View backView;

    private boolean touchEnable = true;
    private final boolean shouldSwapViews;

    private final int rotation;
    private int screenWidth = NULL_INT, screenHeight = NULL_INT;
    private int currentVisibleView = FRONT_VIEW;

    private final float rotationCount;
    private final float rotationDistance;
    private float oldX, oldY, currentX, currentY;
    private float currentXRotation = 0, currentYRotation = 0;
    private float maxDistanceX = NULL_INT, maxDistanceY = NULL_INT;

    private RotatableUtil(Builder builder) {
        this.rootView = builder.root;
        this.rotationListener = builder.listener;

        if (builder.frontId != NULL_INT) {
            this.frontView = rootView.findViewById(builder.frontId);
        }

        if (builder.backId != NULL_INT) {
            this.backView = rootView.findViewById(builder.backId);
        }

        this.rotation = builder.rotation;
        this.rotationCount = builder.rotationCount;
        this.rotationDistance = builder.rotationDistance;
        this.shouldSwapViews = frontView != null && backView != null;

        rootView.setOnTouchListener(this);
    }

    /**
     * You may need to enable / disable touch interaction at some point,
     * so it is possible to do it so anytime by rotatable object
     */
    public void setTouchEnable(boolean enable) {
        this.touchEnable = enable;
    }


    public void rotate(int direction, float degree, int duration) {
        rotate(direction, degree, duration, null);
    }

    public void rotate(final int direction, float degree, int duration, Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new FastOutSlowInInterpolator());

        ArrayList<Animator> animators = new ArrayList<>();

        if (direction == ROTATE_X || direction == ROTATE_BOTH) {
            animators.add(getAnimatorForProperty(View.ROTATION_X, direction, degree));
        }

        if (direction == ROTATE_Y || direction == ROTATE_BOTH) {
            animators.add(getAnimatorForProperty(View.ROTATION_Y, direction, degree));
        }

        if (listener != null) {
            animatorSet.addListener(listener);
        }

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                updateRotationValues(true);
            }
        });

        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    private Animator getAnimatorForProperty(Property property, final int direction, float degree) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rootView, property, degree);

        if (shouldSwapViews) {
            animator.addUpdateListener(animation -> {
                updateRotationValues(false);
                swapViews(direction);
            });
        }
        return animator;
    }

    private void updateRotationValues(boolean notifyListener) {
        currentXRotation = rootView.getRotationX();
        currentYRotation = rootView.getRotationY();

        if (notifyListener) {
            notifyListenerRotationChanged();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (touchEnable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    restoreOldPositions(event);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    restoreNewPositions(event);
                    handleRotation();

                    if (shouldSwapViews) {
                        swapViews(rotation);
                    }
                    notifyListenerRotationChanged();
                    break;
                }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP: {
                    fitRotation();
                    break;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void restoreOldPositions(MotionEvent event) {
        if (shouldRotateX()) {
            oldY = getYValue(event.getRawY());
        }

        if (shouldRotateY()) {
            oldX = getXValue(event.getRawX());
        }
    }

    private float getXValue(float rawX) {
        if (rotationCount != NULL_INT && maxDistanceX != NULL_INT) {
            return rawX * rotationCount * 180 / maxDistanceX;
        }

        if (rotationDistance != NULL_INT) {
            return rawX * 180 / rotationDistance;
        }

        return rawX;
    }

    private float getYValue(float rawY) {
        if (rotationCount != NULL_INT && maxDistanceY != NULL_INT) {
            return rawY * rotationCount * 180 / maxDistanceY;
        }

        if (rotationDistance != NULL_INT) {
            return rawY * 180 / rotationDistance;
        }

        return rawY;
    }

    private void restoreNewPositions(MotionEvent event) {
        if (shouldRotateX()) {
            if (rotationCount != NULL_INT && maxDistanceY == NULL_INT) {
                maxDistanceY = (event.getRawY() - oldY) > 0 ? (getScreenHeight() - oldY) : oldY;
                oldY = getYValue(oldY);
            }
            currentY = getYValue(event.getRawY());
        }

        if (shouldRotateY()) {
            if (rotationCount != NULL_INT && maxDistanceX == NULL_INT) {
                maxDistanceX = (event.getRawX() - oldX) > 0 ? (getScreenWidth() - oldX) : oldX;
                oldX = getXValue(oldX);
            }
            currentX = getXValue(event.getRawX());
        }
    }

    private int getScreenWidth() {
        if (screenWidth == NULL_INT) {
            calculateScreenDimensions();
        }
        return screenWidth;
    }

    private int getScreenHeight() {
        if (screenHeight == NULL_INT) {
            calculateScreenDimensions();
        }
        return screenHeight;
    }

    private void calculateScreenDimensions() {
        Display display = ((WindowManager) rootView.getContext()
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    private boolean shouldRotateX() {
        return rotation == ROTATE_X || rotation == ROTATE_BOTH;
    }

    private boolean shouldRotateY() {
        return rotation == ROTATE_Y || rotation == ROTATE_BOTH;
    }

    private void handleRotation() {
        if (shouldRotateX()) {
            float newXRotation = (rootView.getRotationX() + (oldY - currentY)) % 360;
            rootView.setRotationX(newXRotation);
            currentXRotation = newXRotation;
            oldY = currentY;
        }

        if (shouldRotateY()) {
            float newYRotation;
            if (isInFrontArea(currentXRotation)) {
                newYRotation = (rootView.getRotationY() + (currentX - oldX)) % 360;
            } else {
                newYRotation = (rootView.getRotationY() - (currentX - oldX)) % 360;
            }

            rootView.setRotationY(newYRotation);
            currentYRotation = newYRotation;
            oldX = currentX;
        }
    }

    private boolean isInFrontArea(float value) {
        return (-270 >= value && value >= -360)
                || (-90 <= value && value <= 90)
                || (270 <= value && value <= 360);
    }

    private void swapViews(int rotation) {
        boolean isFront = false;
        if (rotation == ROTATE_Y) {
            isFront = isInFrontArea(currentYRotation);

            if (!isInFrontArea(currentXRotation)) {
                isFront = !isFront;
            }
        }

        if (rotation == ROTATE_X) {
            isFront = isInFrontArea(currentXRotation);

            if (!isInFrontArea(currentYRotation)) {
                isFront = !isFront;
            }
        }

        if (rotation == ROTATE_BOTH) {
            isFront = (currentXRotation > -90 && currentXRotation < 90) && (currentYRotation > -90 && currentYRotation < 90)

                    || (currentXRotation > -90 && currentXRotation < 90) && (currentYRotation > -360 && currentYRotation < -270)
                    || (currentXRotation > -360 && currentXRotation < -270) && (currentYRotation > -90 && currentYRotation < 90)

                    || (currentXRotation > -90 && currentXRotation < 90) && (currentYRotation > 270 && currentYRotation < 360)
                    || (currentXRotation > 270 && currentXRotation < 360) && (currentYRotation > -90 && currentYRotation < 90)

                    || (currentXRotation > 90 && currentXRotation < 270) && (currentYRotation > -270 && currentYRotation < -90)
                    || (currentXRotation > -270 && currentXRotation < -90) && (currentYRotation > 90 && currentYRotation < 270)

                    || (currentXRotation > 90 && currentXRotation < 270) && (currentYRotation > 90 && currentYRotation < 270)
                    || (currentXRotation > -270 && currentXRotation < -90) && (currentYRotation > -270 && currentYRotation < -90);
        }

        boolean shouldSwap = (isFront && currentVisibleView == BACK_VIEW) || (!isFront && currentVisibleView == FRONT_VIEW);
        if (shouldSwap) {
            frontView.setVisibility(isFront ? View.VISIBLE : View.GONE);
            backView.setVisibility(isFront ? View.GONE : View.VISIBLE);
            currentVisibleView = isFront ? FRONT_VIEW : BACK_VIEW;
        }
    }

    private void notifyListenerRotationChanged() {
        if (rotationListener != null) {
            rotationListener.onRotationChanged(currentXRotation, currentYRotation);
        }
    }

    private void fitRotation() {
        AnimatorSet animatorSet = new AnimatorSet();
        int FIT_ANIM_TIME = 300;
        animatorSet.setDuration(FIT_ANIM_TIME);
        animatorSet.setInterpolator(new FastOutSlowInInterpolator());

        ArrayList<Animator> animators = new ArrayList<>();

        if (shouldRotateY()) {
            animators.add(ObjectAnimator.ofFloat(rootView, View.ROTATION_Y, getRequiredRotation(rootView.getRotationY())));
        }

        if (shouldRotateX()) {
            animators.add(ObjectAnimator.ofFloat(rootView, View.ROTATION_X, getRequiredRotation(rootView.getRotationX())));
        }

        animatorSet.playTogether(animators);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                updateRotationValues(true);
            }
        });
        animatorSet.start();

        // Reset max values to calculate again on touch down
        maxDistanceX = NULL_INT;
        maxDistanceY = NULL_INT;
    }

    private float getRequiredRotation(float currentRotation) {
        float requiredRotation;
        if (currentRotation < -270) {
            requiredRotation = -360;
        } else if (currentRotation < -90 && currentRotation > -270) {
            requiredRotation = -180;
        } else if (currentRotation > -90 && currentRotation < 90) {
            requiredRotation = 0;
        } else if (currentRotation > 90 && currentRotation < 270) {
            requiredRotation = 180;
        } else {
            requiredRotation = 360;
        }
        return requiredRotation;
    }

    /**
     * Listener to get notified whenever view's rotation is changed
     */
    public interface RotationListener {
        void onRotationChanged(float newRotationX, float newRotationY);
    }

    public static class Builder {

        private final View root;
        private RotationListener listener;
        private int rotation = NULL_INT;
        private int frontId = NULL_INT;
        private int backId = NULL_INT;
        private float rotationCount = NULL_INT;
        private final float rotationDistance = NULL_INT;

        public Builder(View viewToRotate) {
            this.root = viewToRotate;
        }

        /**
         * Declaring sides will provide swapping between them when necessary,
         * if not declared, then rootView will be rotating by itself without any other effect
         */
        public Builder sides(int frontViewId, int backViewId) {
            this.frontId = frontViewId;
            this.backId = backViewId;
            return this;
        }

        /**
         * Specify an axis or both axises to rotate around
         */
        public Builder direction(@Direction int rotation) {
            this.rotation = rotation;
            return this;
        }

        /**
         * This method provides view to rotate only as given rotation count,
         * irrelevant to its position or touch distance
         */
        public Builder rotationCount(float count) {

            this.rotationCount = count;
            return this;
        }

        public RotatableUtil build() {
            if (rotation == NULL_INT || !isRotationValid(rotation)) {
                throw new IllegalArgumentException("You must specify a direction!");
            }
            return new RotatableUtil(this);
        }

    }

    private static boolean isRotationValid(int value) {
        return value == ROTATE_X || value == ROTATE_Y || value == ROTATE_BOTH;
    }

}