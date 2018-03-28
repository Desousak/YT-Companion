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
        return ("https://www.youtube.com/embed/" + this.getVideoID() + "?autoplay=1");
    }

}
