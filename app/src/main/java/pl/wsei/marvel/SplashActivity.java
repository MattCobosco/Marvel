package pl.wsei.marvel;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);

        final ImageView imageView = this.findViewById(R.id.imageView);

        final ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(imageView, "rotation", 0.0f, -15.0f);
        rotateLeft.setDuration(500);
        rotateLeft.setInterpolator(new DecelerateInterpolator());

        final ObjectAnimator rotateRight = ObjectAnimator.ofFloat(imageView, "rotation", -30.0f, 360.0f);
        rotateRight.setDuration(1000);
        rotateRight.setInterpolator(new AccelerateDecelerateInterpolator());

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotateLeft, rotateRight);

        final int repeatCount = 2;
        animatorSet.addListener(new AnimatorListenerAdapter() {
            int counter;

            @Override
            public void onAnimationEnd(final Animator animation) {
                super.onAnimationEnd(animation);

                if (repeatCount > counter) {
                    this.counter++;
                    new Handler().postDelayed(animatorSet::start, 100);
                }
            }
        });
        animatorSet.start();

        new Handler().postDelayed(() -> {
            final Intent i = new Intent(this,
                    NavigationActivity.class);
            this.startActivity(i);
            this.finish();
        }, 4000);
    }
}