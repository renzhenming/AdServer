package com.example.smartcity.bean;

import java.util.ArrayList;

public class TabBean {
	
	private ArrayList<LeftTabBean> data;
	
	public class LeftTabBean {
		private String title;
		private ArrayList<SlideTabBean> children;
		
		public class SlideTabBean {
			private String title;
			private String url;
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			
			
		}

		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public ArrayList<SlideTabBean> getChildren() {
			return children;
		}

		public void setChildren(ArrayList<SlideTabBean> children) {
			this.children = children;
		}
		
		
	}

	public ArrayList<LeftTabBean> getData() {
		return data;
	}

	public void setData(ArrayList<LeftTabBean> data) {
		this.data = data;
	}
	
	
	
	
	
}
