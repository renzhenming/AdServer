package com.example.smartcity.bean;

import java.util.ArrayList;


public class SlideDetailBean {
	
	public String retcode;
	public DataBean data;
	
	public class DataBean {
		public String more;
		public ArrayList<SlideListBean> news;
		public ArrayList<SlideTopBean> topnews;
		
	}

	
	/**
	 * "comment":true,
	 * "commentlist":"http://10.0.3.2:8080/zhbj/10007/comment_1.json",
	 * "commenturl":"http://zhbj.qianlong.com/client/user/newComment/35319",
	 * "id":35311, 
	 * "listimage":"http://10.0.3.2:8080/zhbj/10007/2078369924F9UO.jpg",
	 * "pubdate":"2014-10-1113:18", 
	 * "title":"缃戜笂澶ц鍫傜368鏈熼鍛婏細涔夊姟鐜繚浜轰汉鏈夎矗",
	 * "type":"news",
	 * "url":"http://10.0.3.2:8080/zhbj/10007/724D6A55496A11726628.html"
	 * 
	 * @author Administrator
	 *
	 */
	public class SlideListBean {
		public String id;
		public String listimage;
		public String title;
		public String pubdate;
		public String url;
		@Override
		public String toString() {
			return "SlideListBean [id=" + id + ", listimage=" + listimage
					+ ", title=" + title + ", url=" + url + "]";
		}
		
	}
	
	/**
	 *  "comment":true,
        "commentlist":"http://10.0.3.2:8080/zhbj/10007/comment_1.json",
        "commenturl":"http://zhbj.qianlong.com/client/user/newComment/35301",
        "id":35301,
        "pubdate":"2014-04-0814:24",
        "title":"铚楀眳鐢熸椿",
        "topimage":"http://10.0.3.2:8080/zhbj/10007/1452327318UU91.jpg",
        "type":"news",
        "url":"http://10.0.3.2:8080/zhbj/10007/724D6A55496A11726628.html"
	 */
	public class SlideTopBean{
		public String id;
		public String title;
		public String topimage;
		public String url;
		@Override
		public String toString() {
			return "SlideTopBean [id=" + id + ", title=" + title
					+ ", topimage=" + topimage + ", url=" + url + "]";
		}
		
	}

	
}

