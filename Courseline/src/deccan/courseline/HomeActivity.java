package deccan.courseline;

import entities.Student;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class HomeActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deccan_courseline_activity_home);

		//Student student = (Student) getIntent().getSerializableExtra("student");
	/*	
		ChartFragment chart = new ChartFragment();		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.chartView, chart).commit();
	*/	
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		ChartFragment fragment = new ChartFragment();
		fragmentTransaction.replace(R.id.chartView, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
