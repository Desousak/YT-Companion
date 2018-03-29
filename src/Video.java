public class Video {
    private String title;
    private String thumbnailURL;
    private String videoID;

    public Video(String title, String thumbnailURL, String videoID) {
        this.title = title;
        this.thumbnailURL = thumbnailURL;
        this.videoID = videoID;
    }

    public String getTitle() {
        return this.title;
    }
    public String getThumbnailURL() {
        return this.thumbnailURL;
    }
    public String getVideoID() {
        return this.videoID;
    }
    public String getVideoURL(){
        // autoplay=1:       Auto play video on
        // fs=0:             Fullscreen off
        // rel=0:            Related videos off
        // modestbranding=1: No YouTube Logo in player
        // iv_load_policy=3: Default annotations off
        return ("https://www.youtube.com/embed/" + this.getVideoID() + "?autoplay=1&fs=0&rel=0&modestbranding=1&iv_load_policy=3");
    }

}
