package com.example.Dist_sys_lab1_webshop.Model.Item;

import java.util.ArrayList;

public class ImageHelper {



	public static ArrayList<String> getImageNames(){
		ArrayList<String> names = new ArrayList<>();
		for (int i = 1; i <= 20; i++)
			names.add("hat"+i+".jpg");

		return names;
	}

}
