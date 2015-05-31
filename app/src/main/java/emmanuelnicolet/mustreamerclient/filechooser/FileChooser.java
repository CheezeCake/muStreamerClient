package emmanuelnicolet.mustreamerclient.filechooser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import emmanuelnicolet.mustreamerclient.R;


public class FileChooser extends ListActivity
{
	public static final String FILE_SELECTED = "emmanuelnicolet.mustreamerclient.filechooser.FILE_SELECTED";

	List<File> toDisplay = null;
	File currentDirectory;

	public static boolean isRoot(File f)
	{
		return (f.getName().equalsIgnoreCase(Environment.getExternalStorageDirectory().getName()));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY
				.equals(state)) {
			displayContent(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
		}
		else {
			Toast.makeText(this, "Error: cannot read on external storage", Toast.LENGTH_LONG)
					.show();
			finish();
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	private void displayContent(File directory)
	{
		currentDirectory = directory;

		setTitle("Current directory : " + directory.getName());

		File[] files = directory.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				String name = pathname.getName();
				return ((pathname.isDirectory()) || (name.contains(".") && name
						.substring(name.lastIndexOf(".")).equals(".mp3")));
			}
		});

		toDisplay = new ArrayList<>();

		for (File f : files) {
			if (!f.isHidden())
				toDisplay.add(f);
		}

		// not in root
		if (!isRoot(directory)) {
			File parent = directory.getParentFile();
			if (parent != null)
				toDisplay.add(0, parent);
		}

		FileArrayAdapter fileArrayAdapter = new FileArrayAdapter(this, R.layout.activity_file_chooser, toDisplay);
		this.setListAdapter(fileArrayAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		File picked = toDisplay.get(position);

		if (picked.isDirectory()) {
			displayContent(new File(picked.getAbsolutePath()));
		}
		else {
			Intent intent = new Intent();
			intent.putExtra(FILE_SELECTED, picked.getAbsolutePath());
			setResult(Activity.RESULT_OK, intent);
			Log.d("FILE CHOOSER", "picked = " + picked.getAbsolutePath());
			finish();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isRoot(currentDirectory) && (currentDirectory.getParentFile() != null)) {
				currentDirectory = currentDirectory.getParentFile();
				displayContent(currentDirectory);
				return true;
			}
			else {
				Log.d("FileChooser", "canceled");
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
