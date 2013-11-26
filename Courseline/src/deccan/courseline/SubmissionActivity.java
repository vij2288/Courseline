package deccan.courseline;

import entities.Submission;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class SubmissionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_submission);
		
		Submission subm = (Submission) getIntent().getSerializableExtra("subm");
		Log.d("SUBM", "Weightage: " + subm.getWeightPercent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
		return true;
	}

}
