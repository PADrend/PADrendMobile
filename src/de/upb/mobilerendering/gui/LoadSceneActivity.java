/*
 * This file is part of the PADrendMobile project.
 * Web page: http://www.padrend.de/
 * Copyright (C) 2011-2012 Benjamin Eikel <benjamin@eikel.org>
 * Copyright (C) 2010-2011 Robert Gmyr
 *
 * This project is subject to the terms of the Mozilla Public License, v. 2.0.
 * You should have received a copy of the MPL along with this project; see the
 * file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.upb.mobilerendering.gui;

import java.io.File;
import java.util.LinkedList;

import de.upb.mobilerendering.MobileRendering;
import de.upb.mobilerendering.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * An Activity to select a scene to be loaded.
 */
public class LoadSceneActivity extends ListActivity implements OnItemClickListener
{	
	/**
	 * The directory on the sd card that contains the scenes.
	 */
	private static final String SCENES_DIRECTORY = "/sdcard/MobileRendering/scene/";
	
	/**
	 * An array of addresses to scenes in the network.
	 */
	private static final String [] SCENES_FROM_NETWORK = {
		"http://macabeo.cs.uni-paderborn.de/~padrend/data/scene/szene_1-pkm.minsg"
	};	
	
	/**
	 * Scene extensions that can be handled by the software.
	 */
	private static final String [] VALID_EXTENSIONS = {".minsg", ".txt" };
	
	private LinkedList<String> scenes;
	
	public LoadSceneActivity() {
		scenes = new LinkedList<String>();
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
        // request fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // populate list of scenes
        appendScenesFromNetwork();
        appendScenesFromSDCard();
        
        if(scenes.size() == 0) {
        	Toast.makeText(this, "No scenes available.", Toast.LENGTH_LONG).show();
        	Log.e(MobileRendering.LOG_TAG, "No scenes available.");
        	setResult(RESULT_CANCELED);
        	finish();
        } else {  
	        setListAdapter(new ArrayAdapter<String>(this, R.layout.loadscenelistitem, scenes));
	        getListView().setOnItemClickListener(this);
        }
    }

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String message = getString(R.string.loadingScene) + " " + scenes.get(position) + " ...";
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		Log.d(MobileRendering.LOG_TAG, message);
		
		MobileRendering.setCurrentScenePath(scenes.get(position));
		
		setResult(RESULT_OK);
    	finish();
	}	
	
	private void appendScenesFromNetwork() {
		for(String scenePath : SCENES_FROM_NETWORK) {
			scenes.add(scenePath);
		}
	}
	
	private void appendScenesFromSDCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File dir = new File(SCENES_DIRECTORY);
			if(dir.exists()) {
				String [] scenePaths = dir.list();
				for(int i = 0; i < scenePaths.length; ++i) {
					if(pathIsValid(scenePaths[i]))
						scenes.add(SCENES_DIRECTORY + scenePaths[i]);
				}
			}
		}
	}
	
	private boolean pathIsValid(String path) {
		for(String extension : VALID_EXTENSIONS) {
			if(path.endsWith(extension))
				return true;
		}
		return false;
	}
}
