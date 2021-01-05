package com.potaten2015.wallz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class HighScoreScreen extends AppCompatActivity {

    Button continueButton;
    Button shareButton;
    Button leaderboardButton;
    public InterstitialAd mInterstitialAd;
    public static final String PREFS_NAME = "WallzPrefsFile";
    public int oldHighScore;
    private int recentScore;
    private AdView mAdView3;
    private AdView mAdView4;
    private static int RC_SIGN_IN = 13;

    @Override
    protected void onStart() {



        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.

        /*
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null){

        }

         */


        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_screen);

        continueButton = findViewById(R.id.continueButton);
        shareButton = findViewById(R.id.shareButton);
//        leaderboardButton = findViewById(R.id.leaderboard_Button);

        Bundle bundle = getIntent().getExtras();

        recentScore = bundle.getInt("score", 0);

        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

/*
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

 */

        oldHighScore = settings.getInt("HIGH_SCORE", 0);


        /* System.out.println("");
        System.out.println("");

        System.out.println("THE OLD HIGH SCORE: " + oldHighScore);
        System.out.println("");
        System.out.println("");

        System.out.println("");
        System.out.println("");

        System.out.println("THE RECENT SCORE: " + recentScore);
        System.out.println("");
        System.out.println("");

         */

        TextView scoreTextView = (TextView) findViewById(R.id.high_score);

        if (recentScore > oldHighScore) {
            editor.putInt("HIGH_SCORE", recentScore);
            scoreTextView.setText(Integer.toString(recentScore));
            oldHighScore = recentScore;
        } else {
            editor.putInt("HIGH_SCORE", oldHighScore);
            scoreTextView.setText(Integer.toString(oldHighScore));
        }
        editor.commit();

        TextView scoreTextView2 = (TextView) findViewById(R.id.last_score);
        scoreTextView2.setText(Integer.toString(recentScore));

        TextView youtubeTextView = findViewById(R.id.youtube_description_text);
        youtubeTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView twitchTextView = findViewById(R.id.twitch_description_text);
        twitchTextView.setMovementMethod(LinkMovementMethod.getInstance());
        MobileAds.initialize(this);

        mAdView3 = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest);

        mAdView4 = findViewById(R.id.adView4);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView4.loadAd(adRequest2);

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Intent intent = new Intent(HighScoreScreen.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Try Wallz on the Google Play Store!");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.potaten2015.wallz");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        /* leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.leaderboard_Button:

                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                        onActivityResult(1,1,signInIntent);

                        break;
                    // ...
                }

            }

        });

         */

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Intent intent = new Intent(HighScoreScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_id), oldHighScore);

            showLeaderboard();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private static final int RC_LEADERBOARD_UI = 9004;

    private void showLeaderboard() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_id))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }


     */



}
