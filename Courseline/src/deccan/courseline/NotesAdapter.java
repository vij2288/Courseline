package deccan.courseline;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import entities.Notes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotesAdapter extends ArrayAdapter<Notes> {

	Context context;
	int layoutResourceId;
	ArrayList<Notes> data = new ArrayList<Notes>();

	public NotesAdapter(Context context, int layoutResourceId,
			ArrayList<Notes> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		
		//Log.d("NOTES_AD", "data: " + data.get(0).getName());		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ImageHolder holder = null;

		Log.d("NOTES_AD", "in getView()");
		if (row == null) {
			Log.d("NOTES_AD", "Creating new Images list.");
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ImageHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
			holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
			row.setTag(holder);
		} else {
			holder = (ImageHolder) row.getTag();
			Log.d("NOTES_AD", "Images list already exists.");
		}
		Notes note = data.get(position);
		holder.txtTitle.setText("Pic note " + note.name);
		holder.txtTitle.setTextColor(Color.BLACK);
				//note.getName());
		byte[] outImage = note.image;
		ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		holder.imgIcon.setImageBitmap(theImage);
		return row;
	}

	static class ImageHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}
