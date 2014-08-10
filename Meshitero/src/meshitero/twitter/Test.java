package meshitero.twitter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Test {
	public static void main(String[] args) throws TwitterException, IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		//		twitter4j.User user = twitter.verifyCredentials();
		Query query = new Query("飯テロ");
		List<Status> s;
		int i = 0;
		int max= 200;
		do{
			query.setCount(100);
			query.setResultType(ResultType.recent);
			QueryResult results = twitter.search(query);
			s = results.getTweets();
			
			//		FileWriter fw = new FileWriter("test.txt");
			
			List<String> list = new ArrayList<String>();
			for(Status st : s){
				String txt = st.toString();
				if(txt.contains("mediaURL")){
					String regex = "mediaURL+\\S*";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(txt);
					if(m.find()){
						String result = m.group();
						result = result.replaceAll("mediaURL=", "");
						result = result.replaceAll(",","");
						boolean isaccord = true;
						if(list.size() == 0){
							list.add(result);
						}else{
							for(String str : list){
								if(str.equals(result)){
									isaccord = false;
								}
							}
							if(isaccord){
								list.add(result);
							}
						}
						//					System.out.println(result);
						//					URL test = new URL(result);
						//					URLConnection connection = test.openConnection();
						//					BufferedImage bimg = ImageIO.read(connection.getInputStream());
						//					ImageIO.write(bimg, "png", new File("takeru" + i + ".png"));
						//					i++;
					}

					//				String temp = m.replaceAll("");
					//				System.out.println(temp);
				}
				//			System.out.println(st.toString());
				//			fw.write(st.toString()+"\r\n");
			}
			for(String str : list){
				URL test = new URL(str);
				System.out.println(str);
				URLConnection connection = test.openConnection();
				Bitmap bimg = BitmapFactory.decodeStream(connection.getInputStream());
//				ImageIO.write(bimg, "png", new File("takeru" + i + ".png"));
				i++;
			}
			if(s.size() != 0){
				query.setMaxId(s.get(s.size() - 1).getId() - 1);
			}
			//		fw.close();
//			if(i == 200){
				
//			}
		}while(s.size() != 0 && i < 200);
		System.out.println(s.size());
		System.out.println(s.get(s.size() - 1).getId());
		//		System.out.println(user.getName());
		//		System.out.println(user.getScreenName());
		//		System.out.println(user.getFriendsCount());
		//		System.out.println(user.getFollowersCount());
	}
}
