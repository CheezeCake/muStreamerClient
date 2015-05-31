package emmanuelnicolet.mustreamerclient.filechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import emmanuelnicolet.mustreamerclient.R;

public class FileArrayAdapter extends ArrayAdapter<File>
{
	private Context context;
	private int textViewResourceId;
	private List<File> fileList;

	public FileArrayAdapter(Context context, int textViewResourceId, List<File> objects)
	{
		super(context, textViewResourceId, objects);

		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.fileList = objects;
	}

	public File getItem(int i)
	{
		return fileList.get(i);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater)context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(textViewResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView)convertView.findViewById(R.id.icon);
			viewHolder.name = (TextView)convertView.findViewById(R.id.file_name);
			viewHolder.details = (TextView)convertView.findViewById(R.id.file_info);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}

		File f = fileList.get(position);
		if (f != null) {
			if (position == 0 && FileChooser.isRoot(f)) {
				viewHolder.icon.setImageResource(R.drawable.back);
				viewHolder.name.setText("..");
				viewHolder.details.setText("Parent");
			}
			else {
				if (f.isDirectory()) {
					viewHolder.icon.setImageResource(R.drawable.folder);
				}
				else {
					String name = f.getName().toLowerCase();
					viewHolder.icon.setImageResource(R.drawable.mp3);
				}

				viewHolder.name.setText(f.getName());
				viewHolder.details.setText(f.length() / 1024 + "KB");
			}

		}
		return convertView;
	}

	private class ViewHolder
	{
		ImageView icon;
		TextView name;
		TextView details;
	}

}
