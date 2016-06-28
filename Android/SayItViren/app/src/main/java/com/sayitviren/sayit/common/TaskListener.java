package com.sayitviren.sayit.common;

import java.util.ArrayList;


public interface TaskListener {
	
	public void onTaskSuccessFul(ArrayList<Object> places);
	public void onTaskFailure(String msg);

}
