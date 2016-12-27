package cn.com.dtmobile.hadoop.biz.userHome.job;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.Path;

import cn.com.dtmobile.hadoop.biz.userHome.mr.UserHome;
import cn.com.dtmobile.hadoop.biz.userHome.mr.UserHomeDedup;

public class UserHomeApi {
	public static void main(String[] args) throws ClassNotFoundException,
			IllegalArgumentException, IOException, InterruptedException,
			URISyntaxException {
		Path input_busy = new Path(args[0]);
		Path input_free = new Path(args[1]);
		Path input_volte = new Path(args[2]);
		Path output = new Path(args[3]);
		String phone_addr = args[4];
		Path total_output = new Path(args[5]);
		String path_volte = new Path(output, "/Volte-r-00000").toString();
		Path path_4G = new Path(output, "/FourG*");

		UserHomeDedup.firMR(input_busy, input_free, input_volte, output);
		UserHome.secMR(path_4G, total_output, path_volte, phone_addr);
	}
}
