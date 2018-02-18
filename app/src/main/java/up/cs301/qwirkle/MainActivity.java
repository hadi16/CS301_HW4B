package up.cs301.qwirkle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class: MainActivity
 * This class contains the code to interact with the MainActivity XML.
 *
 * @author Alex Hadi
 * @version February 18, 2018
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Method: onCreate
     * Executed when the activity is created.
     * @param savedInstanceState Bundle object with current instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
