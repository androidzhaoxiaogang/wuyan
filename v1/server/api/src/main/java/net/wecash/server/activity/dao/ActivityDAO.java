package net.wecash.server.activity.dao;

import java.util.Date;
import java.util.List;

import net.wecash.server.mysql.model.Activity;

public interface ActivityDAO {

	List<Activity> getActivity(Integer cursor, Integer limit);
	void deleteActivity(Long activity_id);

	Activity getActivity(Long activity_id);
	void updateActivity(Activity activity, byte[] body);
	void addActivityDetail(String filename, Activity activity, byte[] body);
	void updateActivityDetail(String filename, Activity activity, byte[] body);
	void saveActivity(Date overdueTime, String describe, byte[] body);
	Long getActivityAll();

}
