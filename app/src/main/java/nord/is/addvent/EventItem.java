package nord.is.addvent;

/**
 * Created by Ólafur Georg Gylfason on 21-Feb-18.
 */

public class EventItem {
    private String mTitle;
    private String mLocation;
    private String mHost;
    private String mDescription;
    //private int mHour;
    //private int mMinute;
    //private int mDayOfMonth;
    //private int mMonth;
    //private int mYear;
    //private String mWeekday;


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        mHost = host;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
