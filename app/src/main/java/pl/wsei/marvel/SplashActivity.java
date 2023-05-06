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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.imageView);

        ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(imageView, "rotation", 0f, -15f);
        rotateLeft.setDuration(500);
        rotateLeft.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator rotateRight = ObjectAnimator.ofFloat(imageView, "rotation", -30f, 360f);
        rotateRight.setDuration(1000);
        rotateRight.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotateLeft, rotateRight);

        final int repeatCount = 2;
        animatorSet.addListener(new AnimatorListenerAdapter() {
            int counter = 0;

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (counter < repeatCount) {
                    counter++;
                    new Handler().postDelayed(animatorSet::start, 100);
                }
            }
        });
        animatorSet.start();

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this,
                    NavigationActivity.class);
            startActivity(i);
            finish();
        }, 4000);
    }
}