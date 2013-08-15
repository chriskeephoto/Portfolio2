package com.chriskee.android.phs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AddLocFrag extends DialogFragment 
{
	static AddLocFrag newInstance(String title)
	{
		AddLocFrag addLocFrag = new AddLocFrag();
		Bundle args = new Bundle();
		args.putString("title", title);
		addLocFrag.setArguments(args);
		return addLocFrag;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		String title = getArguments().getString("title");
		return new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher)
				.setTitle(title).setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				((AddMarker)getActivity()).doPositiveClick();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton)
			{
				((AddMarker)getActivity()).doNegativeClick();
			}
		}).create();
		
	}

}
